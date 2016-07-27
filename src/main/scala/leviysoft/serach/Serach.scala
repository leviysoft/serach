package leviysoft.serach

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafxml.core.{DependenciesByType, FXMLView}

object Serach extends JFXApp {
  stage = new JFXApp.PrimaryStage() {
    title = "Test window"
    scene = new Scene(
      FXMLView(
        getClass.getResource("main.fxml"),
        new DependenciesByType(Map())
      )
    )
  }
}
