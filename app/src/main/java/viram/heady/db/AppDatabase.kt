package viram.heady.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import viram.heady.db.converter.Converters
import viram.heady.model.*
import viram.heady.util.Constants


/**
 * Created by viram on 12/5/2017.
 */

@Database(entities = arrayOf(
        Category::class/*,Product::class,
        Variant::class,Tax::class,
        Ranking::class,Product_::class*/),
        version = 1,exportSchema = false)
//@TypeConverters(Converters::class)
abstract class AppDatabase (): RoomDatabase() {


    abstract fun categoryDao(): CategoryDao
    private var sInstance: AppDatabase? = null
    protected abstract fun injectMembers()

    fun getAppData(context: Context): AppDatabase {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                    Constants().DB_NAME)
                    // allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    //                            .allowMainThreadQueries()
                    //                            .addMigrations(MIGRATION_1_2)
                    .build()
        }
        return sInstance as AppDatabase

    }

    fun destroyInstance() {
        sInstance = null
    }

}