package viram.heady.ui.category

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.category_list_item.view.*
import viram.heady.R
import viram.heady.model.Category
import com.amulyakhare.textdrawable.util.ColorGenerator
import com.amulyakhare.textdrawable.TextDrawable





/**
 * Created by viram on 12/2/2017.
 */
class CategoryListAdapter  (private val mValues: List<Category>,
                            private val mListener: OnItemClickListener?) :
        RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mView.category_name.text = mValues[position].name

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

        holder.mView.img_name.setImageDrawable(ic2)
        holder.mView.setOnClickListener {
            mListener?.onItemClick(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Category? = null

        override fun toString(): String {
            return super.toString() + " '" + mView.category_name.text + "'"
        }
    }

    interface OnItemClickListener{
        fun onItemClick(dummyItem: Category)
    }
}
