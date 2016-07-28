package leviysoft.serach.compiler

import com.sksamuel.elastic4s.QueryDefinition

import scala.tools.reflect.ToolBox
import scala.reflect.runtime.{universe => ru}
import ru._

object DSLCompiler {
  private val toolBox = ru.runtimeMirror(getClass.getClassLoader).mkToolBox()

  val elastic4sImports =
    s"""
       |import com.sksamuel.elastic4s.ElasticDsl._
     """.stripMargin

  def compileQuery(code: String): QueryDefinition = {
    val fullQueryCode =
      s"""
         |$elastic4sImports
         |$code
       """.stripMargin

    toolBox.eval(toolBox.parse(fullQueryCode)).asInstanceOf[QueryDefinition]
  }
}
