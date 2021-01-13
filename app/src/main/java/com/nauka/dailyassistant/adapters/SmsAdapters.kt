package com.nauka.dailyassistant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.util.SmsResponse
import com.nauka.dailyassistant.viewModels.SmsListViewModel

class SmsAdapters() : RecyclerView.Adapter<SmsAdapters.ViewHolder>() {

    private var smsList: List<SmsResponse> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val numberSms: TextView = view.findViewById(R.id.callCachedName)
        val dateAndTimeSms: TextView = view.findViewById(R.id.dateAndTimeSms)
        val smsText: TextView = view.findViewById(R.id.callNumber)
        val readSms: TextView = view.findViewById(R.id.readSms)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.sms_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.numberSms.text = smsList[position].dateAndTimeSms
        viewHolder.dateAndTimeSms.text = smsList[position].numberSms
        viewHolder.smsText.text = smsList[position].smsText
        //viewHolder.readSms.text = smsList[position].readSms

        if (smsList[position].readSms == "0") {
            viewHolder.readSms.visibility = View.VISIBLE
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = smsList.size

    fun refreshSmsList(smsList: List<SmsResponse>) {
        this.smsList = smsList
        notifyDataSetChanged()
    }

}
