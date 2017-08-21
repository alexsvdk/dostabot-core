import java.util.*

/**
 * Created by AlexS on 7/28/2017.
 */

class Worker (user: String,interval: Long?) : Runnable{

    var user: String? = null
    var interval: Long = 1000
    var thread: Thread = Thread(this)

    init {
        this.user = user
        if (interval!=null) this.interval = interval
    }

    fun start(){
        thread.start()
    }

    fun stop(){
        thread.stop()
    }


    override fun run() {
        var orders = LinkedList<Order>()
        var removeids = LinkedList<Int>()
        while (true){

            var temp = DostaFuns.updateOrders(user!!)

            for (j in 0..(temp.size-1)) {
                var new = true
                for (i in 0..(orders.size - 1)) {
                    if (orders.get(i) == temp.get(j)) {
                        orders.get(i).eneble = true
                        new = false
                        break
                    }
                }
                if(new){
                    orders.add(temp.get(j))
                    Log.out("order " + temp.get(j).name + " added")}
            }

            for (i in 0..(orders.size-1)){
                if (!orders.get(i).eneble){
                    Log.out("order " + orders.get(i).name + " removed")
                    removeids.add(i)
                }
                else if (!orders.get(i).complited){
                    DostaFuns.acceptOrder(user!!,orders.get(i))
                    Thread.sleep(300)
                   // DostaFuns.rejectOrder(user!!,orders.get(i))
                    orders.get(i).complited=true
                    Log.out("order " + orders.get(i).name + " complited")
                }
                else
                    orders.get(i).eneble=false
            }

            for (i in 0..(removeids.size-1))
                orders.removeAt(removeids.get(i))
            removeids.clear()



            Thread.sleep(interval)
        }
    }
}