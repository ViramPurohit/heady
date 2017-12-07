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


    @Query("SELECT " +
            "product.p_id,product.name,product.dateAdded,product.c_id " +
            "FROM " +
            "product " +
            "LEFT JOIN " +
            "rank_products " +
            "ON " +
            "product.p_id = rank_products.id "+
            "where " +
            "rank_products.orderCount in (select orderCount from rank_products) order by rank_products.orderCount")
    fun getProductByOrderCount(): List<Product>


    @Query("SELECT " +
            "product.p_id,product.name,product.dateAdded,product.c_id " +
            "FROM " +
            "product" +
            " LEFT JOIN" +
            " rank_products " +
            "ON " +
            "product.p_id = rank_products.id "+
            "where " +
            "rank_products.viewCount in (select viewCount from rank_products) order by rank_products.viewCount")
    fun getProductByViewCount(): List<Product>

    @Query("SELECT " +
            "product.p_id,product.name,product.dateAdded,product.c_id " +
            "FROM " +
            "product " +
            "LEFT JOIN " +
            "rank_products " +
            "ON " +
            "product.p_id = rank_products.id "+
            "where " +
            "rank_products.shares in (select shares from rank_products) order by rank_products.shares")
    fun getProductByShares(): List<Product>

    @Query("SELECT * FROM product WHERE name like :name")
    fun getProductByName(name: String) : List<Product>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(product: Product)

}