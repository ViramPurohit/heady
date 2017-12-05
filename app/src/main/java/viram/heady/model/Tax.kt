package viram.heady.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Tax : Serializable{
    @PrimaryKey(autoGenerate = true)
    var id : Integer ?= null

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("value")
    @Expose
    var value: Double? = null

}
