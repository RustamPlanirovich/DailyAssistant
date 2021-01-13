package com.nauka.dailyassistant.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.nauka.dailyassistant.R
import com.nauka.dailyassistant.util.CallResponse

class CallAdapter() : RecyclerView.Adapter<CallAdapter.ViewHolder>() {

    private var callList: List<CallResponse> = ArrayList()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val callCachedName: TextView = view.findViewById(R.id.callCachedName)
        val dateAndTimeSms: TextView = view.findViewById(R.id.dateAndTimeSms)
        val callNumber: TextView = view.findViewById(R.id.callNumber)
        val callDuration: TextView = view.findViewById(R.id.callDuration)
        val callType: TextView = view.findViewById(R.id.callType)

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.call_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.callCachedName.text = callList[position].callCachedName
        viewHolder.dateAndTimeSms.text = callList[position].dateAndTimeCall
        viewHolder.callNumber.text = callList[position].callNumber
        viewHolder.callDuration.text = callList[position].callDuration
        viewHolder.callType.text = callList[position].callType
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = callList.size

    fun refreshCallList(callList: List<CallResponse>) {
        this.callList = callList
        notifyDataSetChanged()
    }
}