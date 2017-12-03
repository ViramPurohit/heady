package viram.heady.inject.module

import dagger.Module
import dagger.Provides
import viram.heady.ui.product.ProductImpl
import viram.heady.ui.product.ProductPresenter

/**
 * Created by viram on 12/3/2017.
 */
@Module
class ProductModule {

    @Provides
    fun getProductPresenter():  ProductImpl.Presenter{
        return ProductPresenter()
    } 
}