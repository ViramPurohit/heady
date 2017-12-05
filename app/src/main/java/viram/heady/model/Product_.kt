package viram.heady.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Product_ : Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("view_count")
    @Expose
    var viewCount: Int? = null
    @SerializedName("order_count")
    @Expose
    var orderCount: Int? = null
    @SerializedName("shares")
    @Expose
    var shares: Int? = null

}
