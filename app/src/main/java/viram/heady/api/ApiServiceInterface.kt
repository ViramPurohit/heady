package viram.heady.api

import io.reactivex.Observable
import retrofit2.http.GET
import viram.heady.model.CategoryResult

/**
 * Created by viram on 12/3/2017.
 */
interface ApiServiceInterface {


//    https://stark-spire-93433.herokuapp.com/json
    @GET("json")
    fun getCategoryList(): Observable<CategoryResult>
}