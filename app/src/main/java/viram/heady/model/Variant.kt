package viram.heady.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Variant {

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("color")
    @Expose
    var color: String? = null
    @SerializedName("size")
    @Expose
    var size: Any? = null
    @SerializedName("price")
    @Expose
    var price: Int? = null

}
