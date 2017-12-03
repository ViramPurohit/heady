package viram.heady

import android.app.Application
import timber.log.Timber
import viram.heady.inject.component.ApplicationComponent
import viram.heady.inject.component.DaggerApplicationComponent
import viram.heady.inject.module.ApplicationModule


/**
 * Created by viram on 12/3/2017.
 */
class MainApplication : Application(){

    lateinit var component : ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        instance = this
        setupInjector()

    }


    private fun setupInjector() {
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this)).build()
        component.inject(this)
    }


    companion object{
        lateinit var instance : MainApplication private set
    }

    fun getApplicationComponent(): ApplicationComponent {
        return component
    }
}