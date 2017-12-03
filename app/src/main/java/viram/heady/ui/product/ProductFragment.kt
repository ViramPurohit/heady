package viram.heady.ui.product


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_product.view.*
import viram.heady.R
import viram.heady.inject.component.DaggerProductComponent
import viram.heady.inject.module.ProductModule
import viram.heady.model.Category
import viram.heady.model.Product
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ProductFragment : Fragment(),ProductImpl.View {


    @Inject
    lateinit var productPresenter : ProductImpl.Presenter

    lateinit var mView : View

    lateinit var category : Category
    fun instance() : ProductFragment{
        return ProductFragment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDepedency()

        productPresenter.attachView(this)

        val bundle = this.arguments
        if (bundle != null) {
            category = bundle.getSerializable("product") as Category
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mView = inflater!!.inflate(R.layout.fragment_product, container, false)

        productPresenter.loadProduct()

        return mView
    }


    override fun updateView() {

        mView!!.product_recyclerview.setLayoutManager(GridLayoutManager(context, 2))

        if (category != null) {
            mView!!.product_recyclerview.adapter = ProductListAdapter(context,category.products!!,
                    object  : ProductListAdapter.OnItemClickListener{
                        override fun onItemClick(item: Product) {
                            Toast.makeText(activity," item "+item.name, Toast.LENGTH_SHORT).show()
                        }
                    })
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productPresenter.subscribe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        productPresenter.unSubscribe()
    }

    fun injectDepedency(){
        var productComponent = DaggerProductComponent.builder()
                .productModule(ProductModule())
                .build()
        productComponent.inject(this)
    }

}// Required empty public constructor
