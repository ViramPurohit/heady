package viram.heady.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryResult : Serializable {

    @SerializedName("categories")
    @Expose
    var categories: List<Category>? = null
    @SerializedName("rankings")
    @Expose
    var rankings: List<Ranking>? = null

}
