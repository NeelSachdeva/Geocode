import play.api._
import utils.FetchLatitudeAndLongitudeUtil

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    Logger.info("Application has started.........!!!!!!")
    val valuesFetchedForLatitudeAndLongitude = FetchLatitudeAndLongitudeUtil.fetchLatitudeAndLongitude("New Delhi")
    println(valuesFetchedForLatitudeAndLongitude)
  }

}