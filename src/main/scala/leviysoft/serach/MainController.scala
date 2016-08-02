package leviysoft.serach

import javafx.beans.value.ObservableValue
import javafx.scene.control.Alert
import javafx.scene.{input => jfxsi}

import com.sksamuel.elastic4s.ElasticClient
import leviysoft.serach.compiler.DSLCompiler
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest
import play.api.libs.json.JsValue

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
  private val responseAst: TreeView[JsValue]
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

  def indexSelected(ev: ActionEvent): Unit = {

  }

  def onExecute(ev: ActionEvent): Unit = {
    client.admin.indices()

    val query = DSLCompiler.compileQuery(requestEditor.text())

    val alert = new Alert(Alert.AlertType.INFORMATION)

    alert.setTitle("Information")
    alert.setHeaderText(null)
    alert.setContentText(query.builder.toString)

    alert.showAndWait()
  }
}
