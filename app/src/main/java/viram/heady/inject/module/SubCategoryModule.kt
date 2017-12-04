package viram.heady.inject.module

import com.example.viram.heady_test.ui.category.CategoryPresenter
import dagger.Module
import dagger.Provides
import viram.heady.ui.subcategory.SubCategoryImpl
import viram.heady.ui.subcategory.SubCategoryPresenter

/**
 * Created by viram on 12/3/2017.
 */
@Module
class SubCategoryModule {

    @Provides
    fun getSubCategoryPresenter(): SubCategoryImpl.Presenter{
        return SubCategoryPresenter()
    }
}