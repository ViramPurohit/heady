package viram.heady.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Product {

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
