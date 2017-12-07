package viram.heady.ui.category

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.sorting_list_item.view.*
import viram.heady.R
import viram.heady.model.Ranking


/**
 * Created by viram on 12/2/2017.
 */
class SortingListAdapter(private val mValues: ArrayList<Ranking>,
                         private val mListener: OnItemClickListener?) :
        RecyclerView.Adapter<SortingListAdapter.ViewHolder>() {

    private var lastSelectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.sorting_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mView.txt_filter.text = mValues[position].ranking

        holder.mView.txt_filter.setChecked(lastSelectedPosition == position);


        holder.mView.txt_filter.setOnClickListener {
            lastSelectedPosition = position;
            notifyDataSetChanged();
            mListener?.onItemClick(holder.mItem!!)
        }

    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Ranking? = null

        override fun toString(): String {
            return super.toString() + " '" + mView.txt_filter.text + "'"
        }
    }

    interface OnItemClickListener{
        fun onItemClick(ranking: Ranking)
    }
}
