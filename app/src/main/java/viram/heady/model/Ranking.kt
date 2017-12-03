package viram.heady.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Ranking {

    @SerializedName("ranking")
    @Expose
    var ranking: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Product_>? = null

}
