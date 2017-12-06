package viram.heady.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull

@Entity(tableName = "ranking")
class Ranking {

    @PrimaryKey()
    @NotNull
    @SerializedName("ranking")
    @Expose
    var ranking: String = ""

    @Ignore
    @SerializedName("products")
    @Expose
    var products: List<Product_>? = null

}
