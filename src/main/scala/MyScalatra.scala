import org.scalatra._
import org.scalatra.scalate.ScalateSupport
import slick.driver.MySQLDriver.api._
import slick.lifted.TableQuery

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class MyScalatra() extends ScalatraServlet with ScalateSupport {

  val db = Database.forURL("jdbc:mysql://localhost:3306/test?useSSL=false", user = "root", password = "password", driver = "com.mysql.cj.jdbc.Driver")
//  val db = Database.forConfig("mydb")

  val usersTable = TableQuery[User]

  get("/") {
    <html>
     <h1>Hello, world!</h1>
     <a href="woof">Try an SSP Page</a>
	  </html>
  }
  
   get("/woof") {
     contentType="text/html"
    ssp("woof.ssp","date" -> new java.util.Date)
  } 
  
  case class Flower(slug: String, name: String) {
    def toXML= <flower name={name}>{slug}</flower>
  }
  
   val all = List(
      Flower("yellow-tulip", "Yellow Tulip"),
      Flower("red-rose", "Red & Rose"),
      Flower("black-rose", "Black Rose"))
   
  get("/flowers"){
     contentType="text/xml"
    <flowers> 
      { all.map(_.toXML) }
     </flowers>
  }

  get("/users"){
    val future: Future[Option[(Int, String, String, String, Int)]] = db.run(usersTable.filter(_.id === 1).result.headOption)
    val result = Await.result(future, Duration.Inf)
    if(result.isDefined){
      println(result.get._1)
    }

  }
   
}