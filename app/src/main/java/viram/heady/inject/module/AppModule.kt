package viram.heady.inject.module

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import viram.heady.db.AppDatabase

/**
 * Created by Viram Purohit on 12/5/2017.
 */
@Module
class AppModule (private val context: Context) {

    @Provides
    fun providesAppContext() = context

    @Provides fun providesAppDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "todo_database").build()

    @Provides fun providesCategoryDao(database: AppDatabase) = database.categoryDao()
}