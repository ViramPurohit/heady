package viram.heady.inject.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import viram.heady.inject.scope.PerApplication
import javax.inject.Singleton
import viram.heady.MainApplication
import viram.heady.db.AppDatabase
import viram.heady.db.CategoryDao
import viram.heady.util.Constants

/**
 * Created by viram on 12/3/2017.
 */
@Module
class ApplicationModule(private val mainApplication: MainApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Application {
        return mainApplication
    }

    @Provides
    @Singleton
    fun provideAppContext() : Context = mainApplication

    @Provides
    @Singleton
    fun provideDatabase(context: Context)
            = Room.databaseBuilder(context, AppDatabase::class.java, Constants().DB_NAME).build()


//    @Provides
//    fun database(context: Context): AppDatabase =
//            Room.databaseBuilder(context, AppDatabase::class.java, "roomdb").build()


}