/**
 * Created by AlexS on 7/27/2017.
 */

fun main(args : Array<String>) {
    var session = ""
    if (DostaFuns.checkUser(session)) {
        var worker = Worker(session, 1000)
        worker.start()
    }
}