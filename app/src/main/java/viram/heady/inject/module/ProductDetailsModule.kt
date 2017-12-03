package viram.heady.inject.module

import dagger.Module
import dagger.Provides
import viram.heady.ui.productdetails.ProductDetailsImpl
import viram.heady.ui.productdetails.ProductDetailsPresenter

/**
 * Created by viram on 12/3/2017.
 */
@Module
class ProductDetailsModule {

    @Provides
    fun getProductDetailsPresenter():  ProductDetailsImpl.Presenter{
        return ProductDetailsPresenter()
    } 
}