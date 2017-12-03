package viram.heady.inject.component

import dagger.Component
import viram.heady.inject.module.ProductModule
import viram.heady.inject.scope.PerActivity
import viram.heady.ui.product.ProductFragment

/**
 * Created by viram on 12/3/2017.
 */
@PerActivity
@Component(modules = arrayOf(ProductModule::class))
interface ProductComponent {
    fun inject(productFragment: ProductFragment)
}