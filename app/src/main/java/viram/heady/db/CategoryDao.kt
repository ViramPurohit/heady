package viram.heady.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import viram.heady.model.Category

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Dao
interface CategoryDao {

    @Query("SELECT * FROM category")
    fun getAll(): List<Category>


    @Query("SELECT child_category FROM category WHERE id = :id")
    fun getChild_Category(id : Int) : String

    @Query("SELECT count(*) FROM category")
    fun getCategoryCount() : Int
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(category: ArrayList<Category>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(category: Category)
}