package viram.heady.inject.component

import dagger.Component
import viram.heady.inject.module.ProductDetailsModule
import viram.heady.inject.scope.PerActivity
import viram.heady.ui.productdetails.ProductDetails

/**
 * Created by viram on 12/3/2017.
 */
@PerActivity
@Component(modules = arrayOf(ProductDetailsModule::class))
interface ProductDetailsComponent {
    fun inject(productDetails: ProductDetails)
}