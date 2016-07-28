package leviysoft.serach

import javafx.scene.control.Alert
import javafx.scene.{input => jfxsi}

import com.sksamuel.elastic4s.ElasticClient
import leviysoft.serach.compiler.DSLCompiler

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextArea, TextField}
import scalafx.scene.input.KeyEvent
import scalafxml.core.macros.sfxml

@sfxml
class MainController(
  private val serverAddress: TextField,
  private val connectButton: Button,
  private val executeButton: Button,
  private val requestEditor: TextArea
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
  }

  def onExecute(ev: ActionEvent): Unit = {
    val query = DSLCompiler.compileQuery(requestEditor.text())

    val alert = new Alert(Alert.AlertType.INFORMATION)

    alert.setTitle("Information")
    alert.setHeaderText(null)
    alert.setContentText(query.builder.toString)

    alert.showAndWait()
  }
}
