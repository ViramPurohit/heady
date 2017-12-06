package viram.heady.inject.module

import com.example.viram.heady_test.ui.category.CategoryPresenter
import dagger.Module
import dagger.Provides
import viram.heady.db.AppDatabase
import viram.heady.ui.category.CategoryImpl
import javax.inject.Singleton

/**
 * Created by viram on 12/3/2017.
 */
@Module()
class CategoryModule {

    @Provides
    fun getCategoryPresenter(): CategoryImpl.Presenter{
        return CategoryPresenter()
    }


//    @Provides
//    fun providesCategoryDao(database: AppDatabase) = database.categoryDao()

}