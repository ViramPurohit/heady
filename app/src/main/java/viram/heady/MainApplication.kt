package viram.heady

import android.app.Application
import android.arch.persistence.room.Room
import viram.heady.db.AppDatabase
import viram.heady.inject.component.ApplicationComponent
import viram.heady.inject.component.DaggerApplicationComponent
import viram.heady.inject.module.ApplicationModule
import viram.heady.util.Constants


/**
 * Created by viram on 12/3/2017.
 */
class MainApplication : Application(){

    lateinit var component : ApplicationComponent


    override fun onCreate() {
        super.onCreate()

        instance = this
        setupInjector()

        MainApplication.database = Room.databaseBuilder(this, AppDatabase::class.java,
                    Constants().DB_NAME)
//                    // allow queries on the main thread.
//                    // Don't do this on a real app! See PersistenceBasicSample for an example.
//                    //                            .allowMainThreadQueries()
//                    //                            .addMigrations(MIGRATION_1_2)
                    .build()
    }


    private fun setupInjector() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }


    companion object{
        lateinit var instance : MainApplication private set

        var database: AppDatabase? = null
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }
}