package viram.heady.inject.module

import android.app.Activity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by viram on 12/3/2017.
 */
@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivityContext() : Activity {
        return activity
    }
}