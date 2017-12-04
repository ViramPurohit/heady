package viram.heady.ui.productdetails

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.category_list_item.view.*
import viram.heady.R
import viram.heady.model.Category
import kotlinx.android.synthetic.main.size_list_item.view.*
import viram.heady.model.Product
import viram.heady.model.Variant


/**
 * Created by viram on 12/2/2017.
 */
class ColorAdapter(val context: Context, private val mValues: List<Variant>,
                   private val mListener: OnItemClickListener?) :
        RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    var selectPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.size_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mView.size_name.text = ""+mValues[position].color

        if(selectPosition == position) {
            holder.mView.setBackgroundColor(Color.parseColor("#000000"));
        }else {
            holder.mView.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.mView.setOnClickListener {
            mListener?.onItemClick(holder.mItem!!)
            selectPosition = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Variant? = null

        override fun toString(): String {
            return super.toString() + " '" + mView.size_name.text + "'"
        }
    }

    interface OnItemClickListener{
        fun onItemClick(variant: Variant)
    }
}
