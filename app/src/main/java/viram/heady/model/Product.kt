package viram.heady.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "product")
class Product : Serializable{
    @PrimaryKey
    @ColumnInfo(name = "p_id")
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("date_added")
    @Expose
    var dateAdded: String? = null

    @Ignore
    @SerializedName("variants")
    @Expose
    var variants: List<Variant>? = null

    @Ignore
    @SerializedName("tax")
    @Expose
    var tax: Tax? = null

}
