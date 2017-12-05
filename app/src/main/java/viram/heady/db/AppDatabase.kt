package viram.heady.db

import android.arch.persistence.db.SupportSQLiteOpenHelper
import android.arch.persistence.room.*
import android.content.Context
import viram.heady.model.*
import android.arch.persistence.room.Room



/**
 * Created by viram on 12/5/2017.
 */

@Database(entities = arrayOf(
        Category::class,Product::class,
        Variant::class,Tax::class,
        Ranking::class,Product_::class),version = 1)
abstract class AppDatabase : RoomDatabase() {


    private var INSTANCE: AppDatabase? = null

    fun getDataBase(context: Context) : AppDatabase{

        if(INSTANCE == null){
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.applicationContext,AppDatabase::class.java)
                            .allowMainThreadQueries()
                            .build()


        }
        return INSTANCE as AppDatabase
    }

    fun destroyInstance(){
        INSTANCE = null
    }



}