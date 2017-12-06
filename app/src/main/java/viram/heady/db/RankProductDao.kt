package viram.heady.db

import android.arch.persistence.room.*
import viram.heady.model.*

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Dao
interface RankProductDao {


    @Query("SELECT * FROM rank_products")
    fun getAll(): List<Product_>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(ranking: List<Product_>)
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateAll(ranking: List<Product_>)


    @Query("UPDATE rank_products SET " +
            "id = :id,viewCount = :view_count where id = :id ;")
    fun UpdateView_count(id :Int,view_count :Int);


    @Query("UPDATE rank_products SET " +
            "id = :id,orderCount = :order_count where id = :id ;")
    fun UpdateOrder_count(id :Int,order_count :Int);

    @Query("UPDATE rank_products SET " +
            "id = :id,shares= :shares where id = :id ;")
    fun UpdateShare_count(id :Int,shares :Int);


//    @Query("INSERT OR REPLACE INTO rank_products (id,view_count,order_count,shares) VALUES " +
//            "(:id,:view_count,:order_count,:shares);")
//    fun insertOrUpdateView_count(id :Int,view_count :Int,order_count :Int, shares :Int);

//    @Query("INSERT OR REPLACE INTO rank_products (id,orderCount) VALUES " +
//            "(:id,:view_count,:order_count,:shares);")
//    fun insertOrUpdateOrder_count(id :Int,order_count :Int);
//
//
//
//    @Query("INSERT OR REPLACE INTO rank_products (id,shares) VALUES " +
//            "(:id,:shares);")
//    fun insertOrUpdateShare_count(id :Int,shares :Int);


//    @Query("INSERT OR REPLACE INTO rank_products (id,shares) VALUES " +
//            "(:id, :view_count, :order_count,:shares);")
//    fun insertOrUpdateShare_count(id :Int,shares :Int);


//    @Query("SELECT * FROM ordered_products")
//    fun getAllOrdered(): List<Product_Ordered>
//
////
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllOrdered(product_Ordered: List<Product_Ordered>)
////
////
//    @Query("SELECT * FROM viewed_products")
//    fun getAllViewed(): List<Product_Viewed>
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllViewed(product_Viewed: List<Product_Viewed>)
//
//
//    @Query("SELECT * FROM shared_products")
//    fun getAllShared(): List<Product_Shared>
//
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAllShared(product_Viewed: List<Product_Shared>)

}