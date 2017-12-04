package viram.heady.inject.component

import dagger.Component
import viram.heady.inject.module.SubCategoryModule
import viram.heady.inject.scope.PerActivity
import viram.heady.ui.subcategory.SubCategoryFragment

/**
 * Created by viram on 12/3/2017.
 */
@PerActivity
@Component(modules = arrayOf(SubCategoryModule::class))
interface SubCategoryComponent {
    fun inject(subCategoryFragment: SubCategoryFragment)
}