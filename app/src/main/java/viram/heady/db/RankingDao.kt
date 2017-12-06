package viram.heady.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import viram.heady.model.Category
import viram.heady.model.Product
import viram.heady.model.Ranking
import viram.heady.model.Variant

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Dao
interface RankingDao {

    @Query("SELECT * FROM ranking")
    fun getAll(): List<Ranking>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(ranking: List<Ranking>)
}