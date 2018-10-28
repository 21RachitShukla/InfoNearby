package rs21.infonearby.CustomAdapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.ml.vision.cloud.label.FirebaseVisionCloudLabel
import com.google.firebase.ml.vision.label.FirebaseVisionLabel
import kotlinx.android.synthetic.main.item_layout.view.*
import rs21.infonearby.R

class ImageAdapter(private val firebaseVisionList: List<Any>, private val isCloud: Boolean) : RecyclerView.Adapter<ImageAdapter.ItemHolder>() {
    lateinit var context: Context

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

//        fun bindCloud(currentItem: FirebaseVisionCloudLabel) {
//            when {
//                currentItem.confidence > .75 -> itemView.itemPrecision.setTextColor(ContextCompat.getColor(context, R.color.green))
//                currentItem.confidence < .25 -> itemView.itemPrecision.setTextColor(ContextCompat.getColor(context, R.color.red))
//                else -> itemView.itemPrecision.setTextColor(ContextCompat.getColor(context, R.color.yellow))
//            }
//            itemView.itemName.text = currentItem.label
//            itemView.itemPrecision.text = "Precision : ${(currentItem.confidence * 100).toInt()}%"
//        }

        fun bindDevice(currentItem: FirebaseVisionLabel) {
            when {
                currentItem.confidence > .75 -> itemView.itemPrecision.setTextColor(ContextCompat.getColor(context, R.color.green))
                currentItem.confidence < .25 -> itemView.itemPrecision.setTextColor(ContextCompat.getColor(context, R.color.red))
                else -> itemView.itemPrecision.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            }
            itemView.itemName.text = currentItem.label
            itemView.itemPrecision.text = "Precision : ${(currentItem.confidence * 100).toInt()}%"
        }

    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val currentItem = firebaseVisionList[position]
//        if (isCloud)
//            holder.bindCloud(currentItem as FirebaseVisionCloudLabel)
//        else
            holder.bindDevice(currentItem as FirebaseVisionLabel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        context = parent.context
        return ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount() = firebaseVisionList.size
}