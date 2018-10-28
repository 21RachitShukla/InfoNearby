package rs21.infonearby.CustomAdapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rs21.infonearby.Model.DataPOJO

import kotlinx.android.synthetic.main.item_desc.view.*
import rs21.infonearby.R
import rs21.infonearby.Views.ImageLabel
import rs21.infonearby.Views.Landmark

class MainAdapter(private val DataList: List<DataPOJO>) : RecyclerView.Adapter<MainAdapter.HomeHolder>() {

    private lateinit var context: Context

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val currItem = DataList[position]
        with(holder.itemView) {
            tViewApiName.text = currItem.title
            tViewApiDesc.text = currItem.desc
            iViewApi.setImageResource(currItem.imageId)
            cViewHome.setOnClickListener {
                when (currItem.id) {
                    0 -> context.startActivity(Intent(context, ImageLabel::class.java))
                    1 -> context.startActivity(Intent(context, Landmark::class.java))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.HomeHolder {
        context = parent.context
        return MainAdapter.HomeHolder(LayoutInflater.from(context).inflate(R.layout.item_desc, parent, false))
    }

    override fun getItemCount() = DataList.size
}