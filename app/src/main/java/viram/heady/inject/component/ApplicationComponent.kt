package viram.heady.inject.component

import dagger.Component
import viram.heady.MainApplication
import viram.heady.inject.module.AppModule
import viram.heady.inject.module.ApplicationModule

/**
 * Created by viram on 12/3/2017.
 */
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: MainApplication)
}