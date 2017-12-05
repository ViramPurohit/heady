package viram.heady.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class Ranking {
    @PrimaryKey(autoGenerate = true)
    var id : Integer ?= null

    @SerializedName("ranking")
    @Expose
    var ranking: String? = null
    @SerializedName("products")
    @Expose
    var products: List<Product_>? = null

}
