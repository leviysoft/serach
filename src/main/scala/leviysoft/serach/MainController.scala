package leviysoft.serach

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, TextArea}
import scalafxml.core.macros.sfxml

@sfxml
class MainController(
  private val connectButton: Button,
  private val executeButton: Button,
  private val requestEditor: TextArea
) {
  def onConnect(ev: ActionEvent): Unit = {
    println("Connect")
  }

  def onExecute(ev: ActionEvent): Unit = {
    println("Execute")
  }
}
