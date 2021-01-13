package com.nauka.dailyassistant.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.CallLog
import androidx.loader.content.CursorLoader
import com.nauka.dailyassistant.util.CallResponse
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class callData(var context: Context?) {

    private lateinit var typeOutput: String
    private lateinit var nameContact: String

    @SuppressLint("SimpleDateFormat")
    fun getCallList(): ArrayList<CallResponse> {

        val dataSetCall: ArrayList<CallResponse> = ArrayList()

        val c = CursorLoader(context!!, CallLog.Calls.CONTENT_URI, null, null, null, null)
        var cur: Cursor? = c.loadInBackground()
        val format1 = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
        if (cur?.count!! > 0) {
            while (cur.moveToNext()) {
                when (cur.getInt(cur.getColumnIndex(CallLog.Calls.TYPE))) {
                    CallLog.Calls.OUTGOING_TYPE -> typeOutput = "OUTGOING"
                    CallLog.Calls.INCOMING_TYPE -> typeOutput = "INCOMING"
                    CallLog.Calls.MISSED_TYPE -> typeOutput = "MISSED"
                }
                if(cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME))!=null) {
                    nameContact = cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME))
                }else{
                    nameContact = "Unknown"
                }

                dataSetCall.add(
                    CallResponse(
                        format1.format(cur.getLong(cur.getColumnIndex(CallLog.Calls.DATE))),
                        cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER)),
                        nameContact,
                        convertMillis1(cur.getLong(cur.getColumnIndex(CallLog.Calls.DURATION))),
                        typeOutput
                    )
                )
            }
        }
        return dataSetCall
    }

    @SuppressLint("DefaultLocale")
    private fun convertMillis1(milliseconds2: Long): String {
        return java.lang.String.format(
            "%02d:%02d:%02d",
            TimeUnit.SECONDS.toHours(milliseconds2) % 60,
            TimeUnit.SECONDS.toMinutes(milliseconds2) % 60,
            TimeUnit.SECONDS.toSeconds(milliseconds2) % 60
        )
    }
}