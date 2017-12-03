package viram.heady.ui.product

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amulyakhare.textdrawable.TextDrawable
import com.amulyakhare.textdrawable.util.ColorGenerator
import kotlinx.android.synthetic.main.category_list_item.view.*
import kotlinx.android.synthetic.main.product_item.view.*
import viram.heady.R
import viram.heady.model.Product

/**
 * Created by viram on 12/2/2017.
 */
class ProductListAdapter(val context: Context,private val mValues: List<Product>,
                         private val mListener: OnItemClickListener?) :
        RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mView.product_name.text = mValues[position].name

       if(mValues[position].variants!!.size > 0){
           holder.mView.product_price.text = context.getString(R.string.Rs)+
                   mValues[position].variants!![0].price.toString()
       }else{
           holder.mView.product_price.text = context.getString(R.string.Rs)+"0.0"
       }


        /*Create Image based on Name*/
        val generator = ColorGenerator.MATERIAL

        var color1 = generator.randomColor

        val builder = TextDrawable.builder()
                .beginConfig()
                .withBorder(4)
                .endConfig()
                .rect()
        var  txt_image =  mValues[position].name!!.replace(" ","").substring(0,1)

        val ic2 = builder.build(txt_image, color1)

        holder.mView.product_img_name.setImageDrawable(ic2)

        holder.mView.setOnClickListener {
            mListener?.onItemClick(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Product? = null

        override fun toString(): String {
            return super.toString() + " '" + mView.category_name.text + "'"
        }
    }

    interface OnItemClickListener{
        fun onItemClick(product: Product)
    }
}
