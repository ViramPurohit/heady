package viram.heady.inject.component

import dagger.Component
import viram.heady.MainActivity
import viram.heady.inject.module.ActivityModule
import viram.heady.inject.scope.PerActivity

/**
 * Created by viram on 12/3/2017.
 */
@PerActivity
@Component( modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)

}