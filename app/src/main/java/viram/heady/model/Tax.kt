package viram.heady.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "tax")
class Tax : Serializable{

    @PrimaryKey
    @ColumnInfo(name = "p_id")
    var p_id: Int? = null

    @NotNull
    @SerializedName("name")
    @Expose
    var name: String = ""



    @SerializedName("value")
    @Expose
    var value: Double? = null

}
