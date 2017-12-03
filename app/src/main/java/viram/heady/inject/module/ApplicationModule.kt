package viram.heady.inject.module

import android.app.Application
import dagger.Module
import dagger.Provides
import viram.heady.inject.scope.PerApplication
import javax.inject.Singleton
import viram.heady.MainApplication

/**
 * Created by viram on 12/3/2017.
 */
@Module
class ApplicationModule(private val mainApplication: MainApplication) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApplicationContext(): Application {
        return mainApplication
    }
}