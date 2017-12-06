package viram.heady.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "category")
class Category : Serializable{

    @PrimaryKey
    @ColumnInfo
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @ColumnInfo
    @SerializedName("name")
    @Expose
    var name: String? = null


    @ColumnInfo(name = "child_category")
    var child_category: String? = ""

    @Ignore
    @SerializedName("products")
    @Expose
    var products: List<Product>? = null

    @Ignore
    @SerializedName("child_categories")
    @Expose
    var childCategories: ArrayList<Int>? = null



}
