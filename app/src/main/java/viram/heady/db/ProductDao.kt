package viram.heady.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import viram.heady.model.Product

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE c_id = :c_id")
    fun getCategoryProduct(c_id: Int) : List<Product>

    @Query("SELECT note.id, note.title, note.description, note.category_id " +
            "FROM note " +
            "LEFT JOIN category ON note.category_id = category.id " +
            "WHERE note.id = :noteId")
    fun getCategoryNote(noteId: Long): Product

//    @Query("SELECT * FROM product WHERE p_id = :p_id")
//    fun getCategoryProduct(p_id : Int) : List<Product>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(category: ArrayList<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(product: Product)

}