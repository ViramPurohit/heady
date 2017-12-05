package viram.heady.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Product : Serializable{
    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null
    @SerializedName("variants")
    @Expose
    var variants: List<Variant>? = null
    @SerializedName("tax")
    @Expose
    var tax: Tax? = null

}
