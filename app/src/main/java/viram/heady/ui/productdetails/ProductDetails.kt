package viram.heady.ui.productdetails


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import kotlinx.android.synthetic.main.fragment_product_details.view.*
import viram.heady.MainActivity
import viram.heady.R
import viram.heady.inject.component.DaggerProductDetailsComponent
import viram.heady.inject.module.ProductDetailsModule
import viram.heady.model.Product
import viram.heady.model.Variant
import javax.inject.Inject


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

        /*Update title details*/
        (activity as MainActivity).getSupportActionBar()!!.title = product.name
        (activity as MainActivity).getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true);

        productdetailsPresenter.loadProductDetails(context,product.variants)

        return mview
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                activity.onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun injectDepedency(){
        var productDetailsComponent = DaggerProductDetailsComponent.builder()
                .productDetailsModule(ProductDetailsModule())
                .build()
        productDetailsComponent.inject(this)
    }

    override fun updateView() {

        if(product != null){

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


    fun updateProduct(variant: Variant,isFromSize: Boolean) {
        mview.product_details_price.text =
                context.getString(R.string.Rs)+
                variant.price.toString() +
                " + ("+ product.tax!!.name +
                       " "+ context.getString(R.string.Rs)+
                ""+getPercentage(variant.price!!, product.tax!!.value!!) + ")"
        mview.size_label.text = "Size : "+variant.size.toString()

        mview.color_label.text = "Color : "+variant.color.toString()


        /*It's update color list based on size*/
        if(isFromSize){
            addColor(variant.size);
        }

    }

    fun getPercentage(price: Int, tax: Double): Double{
        var percentage : Double
        percentage = ((price * tax)/100)
        return percentage
    }

    override fun showSize(isVisible: Boolean) {
      if(isVisible){
          mview.card_size.visibility = View.VISIBLE
      }else{
          mview.card_size.visibility = View.GONE
      }
    }

    override fun updateSize(variants: ArrayList<Variant>) {

        val mLayoutManager: RecyclerView.LayoutManager
        // The number of Columns
        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mview!!.recyclerview_size.setLayoutManager(mLayoutManager)

        var sizeAdater : SizeAdapter


        sizeAdater = SizeAdapter(context,variants!!,
                object  : SizeAdapter.OnItemClickListener{
                    override fun onItemClick(variant: Variant) {
                        if(variants!!.size > 0){
                            updateProduct(variant,true)
                        }
                    }
                })

        mview!!.recyclerview_size.adapter = sizeAdater

        /*Update Price and Color First time */
        if(variants.size > 0){
            updateProduct(variants[0],true)
        }

    }

    override fun updateColors(variants: ArrayList<Variant>) {

        loadColor(variants)
        /*Update Price */
        if(variants.size > 0){
            updateProduct(variants[0],false)
        }

    }
    fun addColor(size: Double?) {
        if (product.variants != null) {
            var arrayList = ArrayList<Variant>()
            for (variant: Variant in product.variants!!){

                if(variant.size == size){
                    arrayList.add(variant)
                }
            }

            loadColor(arrayList)
        }
    }
    fun loadColor(variants: ArrayList<Variant>){

        val mLayoutManager: RecyclerView.LayoutManager
        mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mview!!.recyclerview_color.setLayoutManager(mLayoutManager)


        mview!!.recyclerview_color.adapter = ColorAdapter(context,variants!!,
                object  : ColorAdapter.OnItemClickListener{
                    override fun onItemClick(variant: Variant) {
                        if(variants!!.size > 0){
                            updateProduct(variant,false)
                        }
                    }
                })
    }
}
