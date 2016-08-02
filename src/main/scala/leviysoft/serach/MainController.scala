package leviysoft.serach

import javafx.beans.value.ObservableValue
import javafx.scene.{input => jfxsi}

import com.sksamuel.elastic4s.ElasticClient
import leviysoft.serach.compiler.DSLCompiler
import leviysoft.serach.elastic.playJsonHitAs
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest
import play.api.libs.json.{JsArray, JsObject, JsValue}

import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.scene.input.KeyEvent
import scalafxml.core.macros.sfxml

@sfxml
class MainController(
  private val serverAddress: TextField,
  private val connectButton: Button,
  private val indexSelector: ComboBox[String],
  private val typeSelector: ComboBox[String],
  private val executeButton: Button,
  private val requestEditor: TextArea,
  private val responseAst: TreeView[String]
) {
  private var client: ElasticClient = _

  requestEditor.addEventFilter(KeyEvent.KeyPressed, (e: jfxsi.KeyEvent) => {
    if (e.getCode == jfxsi.KeyCode.TAB) {
      val s = ' '.toString * 4
      requestEditor.insertText(requestEditor.getCaretPosition, s)
      e.consume()
    }
  })

  def onConnect(ev: ActionEvent): Unit = {
    client = ElasticClient.transport(s"elasticsearch://${serverAddress.text()}")
    client.admin.cluster().prepareState().execute().actionGet().getState.getMetaData.concreteAllIndices().foreach { indexName =>
      indexSelector += indexName
    }
    indexSelector.value.addListener((observable: ObservableValue[_ <: String], oldValue: String, newValue: String) => {
      typeSelector.getItems.clear()
      client.admin.indices().getMappings(new GetMappingsRequest().indices(newValue)).get().mappings().get(newValue).keysIt()
        .forEachRemaining((t: String) => typeSelector += t)
    })
  }

  def convertToNodes(rootNodeName: String)(jso: JsValue): TreeItem[String] = {
    jso match {
      case JsObject(fields) =>
        val node = new TreeItem[String](rootNodeName)
        node.children = fields.map(kv => convertToNodes(kv._1)(kv._2)).toSeq
        node
      case JsArray(items) =>
        new TreeItem[String](items.mkString(","))
      case jsv =>
        new TreeItem[String](s"$rootNodeName: $jsv")
    }
  }

  def onExecute(ev: ActionEvent): Unit = {
    import com.sksamuel.elastic4s.ElasticDsl._

    val queryDef = DSLCompiler.compileQuery(requestEditor.text())
    val request = search in indexSelector.value.value / typeSelector.value.value query queryDef
    val result = client.execute(request).await.as[JsObject].map(convertToNodes("hit"))

    val root = new TreeItem[String]("Response")
    root.children = result

    responseAst.root = root
  }
}
