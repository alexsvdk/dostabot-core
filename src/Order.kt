/**
 * Created by AlexS on 7/28/2017.
 */

class Order(id : String, name : String, pay : String,subway: String, lat: String, lon: String) {
    var id: String? = null
    var pay: String? = null
    var name: String? = null
    var eneble: Boolean = true
    var complited: Boolean = false
    var subway: String? = null
    var lon: String? = null
    var lat: String? = null

    init {
        this.id = id
        this.pay = pay
        this.name = name
        this.subway = subway
        this.lon = lon
        this.lat = lat
    }

    override fun equals(other: Any?): Boolean {
        return (this.id==(other as Order).id)
    }

    override fun toString(): String {
        return name!!
    }
}