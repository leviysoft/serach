package leviysoft.serach

import javafx.scene.{input => jfxsi}

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextArea}
import scalafx.scene.input.KeyEvent
import scalafxml.core.macros.sfxml

@sfxml
class MainController(
  private val connectButton: Button,
  private val executeButton: Button,
  private val requestEditor: TextArea
) {
  requestEditor.addEventFilter(KeyEvent.KeyPressed, (e: jfxsi.KeyEvent) => {
    if (e.getCode == jfxsi.KeyCode.TAB) {
      val s = ' '.toString * 4;
      requestEditor.insertText(requestEditor.getCaretPosition, s)
      e.consume();
    }
  })

  def onConnect(ev: ActionEvent): Unit = {
    println("Connect")
  }

  def onExecute(ev: ActionEvent): Unit = {
    println("Execute")
  }
}
