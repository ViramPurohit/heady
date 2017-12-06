package viram.heady.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import viram.heady.model.Variant

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Dao
interface VariantDao {

    @Query("SELECT * FROM variant")
    fun getAll(): List<Variant>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(variant: Variant)
}