package viram.heady

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.viram.heady_test.ui.category.CategoryFragment
import viram.heady.inject.component.DaggerActivityComponent
import viram.heady.inject.module.ActivityModule
import viram.heady.ui.productdetails.ProductDetails
import viram.heady.util.ActivityUtil
import viram.heady.util.CheckInternet
import viram.heady.util.PreferencesUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()

        var checkInternet = CheckInternet()

        val productDetails = ProductDetails()
        val bundle = Bundle()


        if(PreferencesUtils().getFromPreferences(this)!=null){
            bundle.putBoolean("fromcache", true)

            productDetails.setArguments(bundle)
            ActivityUtil().addFragmentToActivity(supportFragmentManager,
                    CategoryFragment().newInstance(), R.id.frame, "CategoryFragment")

        }else{
            if(checkInternet.isConnected(this)){
                bundle.putBoolean("fromcache", false)

                productDetails.setArguments(bundle)
                ActivityUtil().addFragmentToActivity(supportFragmentManager,
                        CategoryFragment().newInstance(), R.id.frame, "CategoryFragment")
            }
        }


    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .build()
        activityComponent.inject(this)
    }

//    override fun onBackPressed() {
//        if (fragmentManager.backStackEntryCount > 1) {
//            fragmentManager.popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }
}
