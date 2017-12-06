package viram.heady.model

import android.arch.persistence.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "variant"/*,
        foreignKeys = arrayOf(ForeignKey(
            entity = Product::class,
            parentColumns = arrayOf("p_id"),
            childColumns = arrayOf("v_id"))
        )*/)
class Variant : Serializable{

    @PrimaryKey
    @ColumnInfo(name = "v_id")
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @ColumnInfo(name = "p_id")
    var p_id: Int? = null

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
