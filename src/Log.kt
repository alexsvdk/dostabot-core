import java.util.*
import java.text.SimpleDateFormat



/**
 * Created by AlexS on 7/31/2017.
 */
class Log {
    companion object{

        var formatForDateNow = SimpleDateFormat("hh:mm")

        fun out(msg:String){
            System.out.print("\n["+formatForDateNow.format(Date()) +"] "+ msg)
        }
    }
}