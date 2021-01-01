import io.getquill.PostgresAsyncContext
import io.getquill.Literal
import com.typesafe.config.ConfigFactory
import scala.jdk.CollectionConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.concurrent.duration._

case class t(name: Option[String])

object Main {
  def main(args: Array[String]): Unit = {
    val context = new PostgresAsyncContext(Literal, ConfigFactory.parseMap(Map(
      "host" -> "localhost",
      "port" -> "5432",
      "database" -> "test-quill-2052",
      "user" -> "<user>",
      "password" -> "",
      "charset" -> "UTF-8",
    ).asJava))

    import context._

    val name: Option[String] = Some("A")
    println(Await.result(
      run(
        query[t]
          .filter(t => t.name == lift(name))
      ),
      5.seconds
    ))
  }
}
