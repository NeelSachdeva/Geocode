package utils

import java.net.URLEncoder
import play.api.libs.ws.WS
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._

object FetchLatitudeAndLongitudeUtil {

  /**
   * Fetch Latitude & Longitude
   */
  def fetchLatitudeAndLongitude(address: String): Option[(Double, Double)] = {
    implicit val timeout = Timeout(50000 milliseconds)
    
    // Encoded the address in order to remove the spaces from the address (spaces will be replaced by '+')
    //@purpose There should be no spaces in the parameter values for a GET request
    val addressEncoded = URLEncoder.encode(address, "UTF-8");
    val jsonContainingLatitudeAndLongitude = WS.url("http://maps.googleapis.com/maps/api/geocode/json?address=" + addressEncoded + "&sensor=true").get()
   
    val future = jsonContainingLatitudeAndLongitude map {
      response => (response.json \\ "location")
    }

    // Wait until the future completes (Specified the timeout above)

    val result = Await.result(future, timeout.duration).asInstanceOf[List[play.api.libs.json.JsObject]]
    
    //Fetch the values for Latitude & Longitude from the result of future
    val latitude = (result(0) \\ "lat")(0).toString.toDouble
    val longitude = (result(0) \\ "lng")(0).toString.toDouble
    Option(latitude, longitude)
  }
}
