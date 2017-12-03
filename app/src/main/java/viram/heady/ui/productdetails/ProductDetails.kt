package viram.heady.ui.productdetails


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import kotlinx.android.synthetic.main.fragment_product_details.view.*
import viram.heady.R
import viram.heady.inject.component.DaggerProductDetailsComponent
import viram.heady.inject.module.ProductDetailsModule
import viram.heady.model.Product
import viram.heady.model.Variant
import javax.inject.Inject
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView






/**
 * A simple [Fragment] subclass.
 */
class ProductDetails : Fragment(),ProductDetailsImpl.View {



    @Inject
    lateinit var productdetailsPresenter : ProductDetailsImpl.Presenter

    lateinit var mview : View

    lateinit var product : Product

    fun instance() : ProductDetails {
        return ProductDetails()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            product = bundle.getSerializable("product") as Product
        }

        injectDepedency()
        productdetailsPresenter.attachView(this)


    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mview = inflater!!.inflate(R.layout.fragment_product_details, container, false)

        productdetailsPresenter.loadProductDetails()

        return mview
    }

    fun injectDepedency(){
        var productDetailsComponent = DaggerProductDetailsComponent.builder()
                .productDetailsModule(ProductDetailsModule())
                .build()
        productDetailsComponent.inject(this)
    }

    override fun updateView() {

        if(product != null){

            if(product.variants!!.size > 0){
                Log.e("TAG ", " updateView "+ product.variants!![0].price.toString());

                mview.product_details_price.text = context.getString(R.string.Rs)+
                        product.variants!![0].price.toString()

                addSize();
            }else{
                mview.product_details_price.text = context.getString(R.string.Rs)+"0.0"
                mview.constraint_size.visibility = View.VISIBLE
            }
            /*Create Image based on Name*/
            val generator = ColorGenerator.MATERIAL

            var color1 = generator.randomColor

            val builder = TextDrawable.builder()
                    .beginConfig()
                    .withBorder(4)
                    .endConfig()
                    .rect()
            var  txt_image =  product.name!!.replace(" ","").substring(0,1)

            val ic2 = builder.build(txt_image, color1)

            mview.product_details_img_name.setImageDrawable(ic2)

            mview.product_details_name.text = product.name
        }

    }

    fun addSize(){
        if (product.variants != null) {
            val mLayoutManager: RecyclerView.LayoutManager
            // The number of Columns
            mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mview!!.recyclerview_size.setLayoutManager(mLayoutManager)


            mview!!.recyclerview_size.adapter = SizeAdapter(context,product.variants!!,true,
                    object  : SizeAdapter.OnItemClickListener{
                        override fun onItemClick(variant: Variant) {
                            Toast.makeText(activity," item "+variant.size, Toast.LENGTH_SHORT).show()
                            if(product.variants!!.size > 0){
                                updateProduct(variant)
                            }
                        }
                    })
        }
    }

    fun updateProduct(variant: Variant) {
        mview.product_details_price.text = context.getString(R.string.Rs)+
                variant.price.toString()
        mview.size_label.text = "Size : "+variant.size.toString()

        mview.color_label.text = "Color : "+variant.color.toString()

        addColor(variant.size);
    }

    fun addColor(size: Double?) {
        if (product.variants != null) {
            var arrayList = ArrayList<Variant>()
            for (variant: Variant in product.variants!!){

                if(variant.size == size){
                    arrayList.add(variant)
                }
            }
            val mLayoutManager: RecyclerView.LayoutManager
            // The number of Columns
            mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            mview!!.recyclerview_color.setLayoutManager(mLayoutManager)


            mview!!.recyclerview_color.adapter = SizeAdapter(context,arrayList!!,false,
                    object  : SizeAdapter.OnItemClickListener{
                        override fun onItemClick(variant: Variant) {
                            Toast.makeText(activity," item "+variant.size, Toast.LENGTH_SHORT).show()
                            if(arrayList!!.size > 0){
                                updateProduct(variant)
                            }
                        }
                    })
        }
    }
}// Required empty public constructor
