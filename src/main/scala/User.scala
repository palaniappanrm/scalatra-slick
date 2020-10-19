import slick.driver.MySQLDriver.api._
import slick.lifted.{ProvenShape, Tag}

class User(tag: Tag) extends Table[(Int, String, String, String, Int)](tag, "USER") {
  def id = column[Int]("USER_ID", O.PrimaryKey, O.AutoInc)

  def email = column[String]("EMAIL")

  def image = column[String]("IMAGE_URL")

  def name = column[String]("NAME")

  def userType = column[Int]("USER_TYPE")

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, String, String, Int)] = (id, email, image, name, userType)

}