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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg category: Category)

}