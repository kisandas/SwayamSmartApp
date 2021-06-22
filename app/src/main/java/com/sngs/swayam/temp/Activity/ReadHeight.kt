package com.sngs.swayam.temp.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.sngs.swayam.temp.DataBase.DatabaseHandler
import com.sngs.swayam.temp.R
import kotlinx.android.synthetic.main.activity_read_height.*

class ReadHeight : AppCompatActivity() {
    var db: DatabaseHandler? = null
    var np: NumberPicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_height)
        db = DatabaseHandler(this)

        np = findViewById<View>(R.id.npheight) as NumberPicker

        val values = arrayOfNulls<String>(21)
        iv_back.setOnClickListener(View.OnClickListener { onBackPressed() })
        tv_next.setOnClickListener(View.OnClickListener {

            if (height == null) {
                height = values[0]
            }
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
            val ContactNo = sharedPreferences.getString("User_no", "")
            db!!.UPDATE_UserHeight(ContactNo, height)

            val intent = Intent(this@ReadHeight, ReadWidth::class.java)
            startActivity(intent)
        })
        var j = 100
        for (i in 0..20) {
            if (j < 220) {
                val temp = "$j cm"
                Log.e("CUSTOM---", "onCreate: $temp")
                values[i] = temp
                j = j
            } else {
                break
            }
        }

        //Populate NumberPicker values from String array values
        //Set the minimum value of NumberPicker
        np!!.minValue = 0 //from array first value
        //Specify the maximum value/number of NumberPicker
        np!!.maxValue = values.size - 1 //to array last value

        //Specify the NumberPicker data source as array elements
        np!!.displayedValues = values

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np!!.wrapSelectorWheel = false

        //Set a value change listener for NumberPicker
        np!!.setOnValueChangedListener { picker, oldVal, newVal ->
            Log.e("CUSTOM---->", "onValueChange: $oldVal----$newVal")
            height = values[newVal]
        }
    }

    companion object {
        @JvmField
        var height: String? = null
    }
}