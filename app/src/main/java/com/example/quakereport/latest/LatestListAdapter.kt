package com.example.quakereport.latest

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.quakereport.R
import java.text.DecimalFormat

class LatestListAdapter(private val listener: QuakeItemClicked): RecyclerView.Adapter<QuakeViewHolder>()  {

    private val items: ArrayList<QuakeList> = ArrayList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuakeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quake_list_layout, parent, false)
        val viewHolder = QuakeViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.bindingAdapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QuakeViewHolder, position: Int) {
        val currentItem = items[position]
        val updatedMag = formatMagnitude(currentItem.mag.toDouble())
        holder.mag.text = updatedMag
        holder.location.text = currentItem.location

        val magnitudeCircle = holder.mag.background as GradientDrawable
        val magnitudeColor = getMagnitudeColor(holder.mag.context, currentItem.mag.toDouble())
        magnitudeCircle.setColor(magnitudeColor)

    }

    fun updateQuakeList(updateQuakeList: ArrayList<QuakeList>) {
        items.clear()
        items.addAll(updateQuakeList)

        notifyDataSetChanged()
    }
}

class QuakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val mag: TextView = itemView.findViewById(R.id.magnitude)
    val location: TextView = itemView.findViewById(R.id.location)
}

interface QuakeItemClicked {
    fun onItemClicked(item: QuakeList)
}



private fun getMagnitudeColor(context: Context, magnitude: Double): Int {
    val magnitudeColorResourceId: Int
    val magnitudeFloor = magnitude.toInt()
    magnitudeColorResourceId = when (magnitudeFloor) {
        0, 1 -> R.color.magnitude1
        2 -> R.color.magnitude2
        3 -> R.color.magnitude3
        4 -> R.color.magnitude4
        5 -> R.color.magnitude5
        6 -> R.color.magnitude6
        7 -> R.color.magnitude7
        8 -> R.color.magnitude8
        9 -> R.color.magnitude9
        else -> R.color.magnitude10plus
    }
    return ContextCompat.getColor(context, magnitudeColorResourceId)
}


private fun formatMagnitude(magnitude: Double): String {
    val magnitudeFormat = DecimalFormat("0.0")
    return magnitudeFormat.format(magnitude)
}


