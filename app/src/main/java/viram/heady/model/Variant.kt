package viram.heady.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Variant : Serializable{

    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("color")
    @Expose
    var color: String? = null
    @SerializedName("size")
    @Expose
    var size: Double? = null
    @SerializedName("price")
    @Expose
    var price: Int? = null

}
