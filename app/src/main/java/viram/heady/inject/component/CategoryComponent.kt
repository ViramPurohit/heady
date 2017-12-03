package viram.heady.inject.component

import com.example.viram.heady_test.ui.category.CategoryFragment
import dagger.Component
import viram.heady.inject.module.CategoryModule
import viram.heady.inject.scope.PerActivity

/**
 * Created by viram on 12/3/2017.
 */
@PerActivity
@Component(modules = arrayOf(CategoryModule::class))
interface CategoryComponent {
    fun inject(categoryFragment: CategoryFragment)
}