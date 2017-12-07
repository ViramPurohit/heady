package viram.heady.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import viram.heady.model.Tax
import viram.heady.model.Variant

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Dao
interface TaxDao {

    @Query("SELECT * FROM tax")
    fun getAll(): List<Tax>

    @Query("SELECT * FROM tax WHERE p_id = :p_id")
    fun getTax(p_id: Int) : Tax

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(category: ArrayList<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tax: Tax)
}