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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(variant: Variant)

    @Query("SELECT * FROM variant WHERE p_id = :p_id")
    fun getVariant(p_id: Int) : List<Variant>
}