package com.nauka.dailyassistant.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import androidx.loader.content.CursorLoader
import com.nauka.dailyassistant.util.SmsResponse
import java.text.SimpleDateFormat

class smsData(var application: Context?) {


    @SuppressLint("SimpleDateFormat")
    fun getSMS(): ArrayList<SmsResponse> {
        val dataSetSms: ArrayList<SmsResponse> = ArrayList()

        val uriSms: Uri = Uri.parse("content://sms/")
        val c = CursorLoader(application!!, uriSms, null, null, null, null)
        var cur: Cursor? = c.loadInBackground()
        var format1 = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")

        if (cur?.count!! > 0) {
            while (cur.moveToNext()) {
                dataSetSms.add(
                    SmsResponse(
                        format1.format(cur.getLong(4)).toString(),
                        cur.getString(2).toString(),
                        cur.getString(12).toString(),
                        cur.getString(7).toString()
                    )
                )
            }
        }
        return dataSetSms
    }
}

