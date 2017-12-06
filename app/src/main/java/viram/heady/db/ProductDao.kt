package viram.heady.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import viram.heady.model.Category
import viram.heady.model.Product

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(category: ArrayList<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(product: ArrayList<Product>)
}