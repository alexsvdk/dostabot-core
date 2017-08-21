import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.net.URL
import java.net.URLEncoder
import java.util.*

/**
 * Created by AlexS on 7/28/2017.
 */

class DostaFuns {
    companion object{

        @Throws(Exception::class)
                /* fun loadInfo(user: User){
                     var connection = URL("https://robot.dostavista.ru/api/phone-auth?v=1.10&av=940&region_id=1&phone="+user.login+"&code="+user.code).openConnection()
                     connection.setRequestProperty("Accept-Charset", "UTF-8")
                     val res = connection.getInputStream().bufferedReader().use { it.readText() }
                     var json = JSONParser().parse(res) as JSONObject
                     if ((json.get("result")as String) != "true")
                         throw Exception("access denied")
                     user.status = json.get("status")as String
                     user.session = json.get("session")as String
                     System.out.println("user " + user.login+ " successfully entered")
                 }*/

        fun checkUser(session: String) :Boolean{
            var connection = URL("https://robot.dostavista.ru/api/profile-info").openConnection()
            connection.setRequestProperty("Accept-Charset", "UTF-8")
            connection.setRequestProperty("cache-control", "no-cache")
            connection.setRequestProperty("x-dostavista-session", session)
            return (connection.getInputStream().bufferedReader().use { it.readText() })!="{\"error_code\":[4]}"
        }

        fun updateOrders(session: String) :List<Order> {
            var connection = URL("https://robot.dostavista.ru/api/orders-enable").openConnection()
            connection.setRequestProperty("Accept-Charset", "UTF-8")
            connection.setRequestProperty("cache-control", "no-cache")
            connection.setRequestProperty("x-dostavista-session", session)
            var json = JSONParser().parse(connection.getInputStream().bufferedReader().use { it.readText() }) as JSONArray
            var orders = LinkedList<Order>()
            for (item in json ) {
                item as JSONObject
                if (!(item.get("denied") as Boolean))
                    orders.add(Order(item.get("order_id") as String,
                            item.get("order_name") as String,
                            item.get("payment_courier") as String ,
                            ((item.get("points") as JSONArray).get(0) as JSONObject).get("subway_station_name") as String,
                            ((item.get("points") as JSONArray).get(0) as JSONObject).get("latitude") as String,
                            ((item.get("points") as JSONArray).get(0) as JSONObject).get("longitude") as String
                    ))
            }
            System.out.print("orders opdated")
            return orders
        }

        fun acceptOrder(session: String,order: Order){
            var connection = URL("https://robot.dostavista.ru/api/courier-acept?order="+order.id+"&lat="+order.lat+"&lon="+order.lon).openConnection()
            connection.setRequestProperty("Accept-Charset", "UTF-8")
            connection.setRequestProperty("cache-control", "no-cache")
            connection.setRequestProperty("x-dostavista-session", session)
            val res = connection.getInputStream().bufferedReader().use { it.readText() }
        }

        fun rejectOrder(session: String,order: Order){
            var connection = URL("https://robot.dostavista.ru/api/courier-reject?order="+order.id+location(order)).openConnection()
            connection.setRequestProperty("Accept-Charset", "UTF-8")
            connection.setRequestProperty("cache-control", "no-cache")
            connection.setRequestProperty("x-dostavista-session", session)
            val res = connection.getInputStream().bufferedReader().use { it.readText() }
        }

        fun location(order: Order) :String{
            var connection = URL("https://maps.googleapis.com/maps/api/geocode/json?&address="+ URLEncoder.encode("москва, метро "+order.subway)).openConnection()
            connection.setRequestProperty("Accept-Charset", "UTF-8")
            var json = ((((JSONParser().parse(connection.getInputStream().bufferedReader().use { it.readText() }) as JSONObject).get("results") as JSONArray).get(0) as JSONObject).get("geometry") as JSONObject).get("location") as JSONObject
            return "&lat="+json.get("lat")+"&lon="+json.get("lng")
        }


    }
}