package leviysoft.serach

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.text.Font
import scalafxml.core.{DependenciesByType, FXMLView}

object Serach extends JFXApp {
  Font.loadFont(getClass.getResource("/fonts/cousine/Cousine-Regular.ttf").toExternalForm, 12)

  val mainScene = new Scene(
    FXMLView(
      getClass.getResource("main.fxml"),
      new DependenciesByType(Map())
    )
  )

  mainScene.stylesheets.add(getClass.getResource("main.css").toExternalForm)

  stage = new JFXApp.PrimaryStage() {
    title = "Serach [Alpha] for ElasticSearch 2.3.x"
    scene = mainScene
  }
}
