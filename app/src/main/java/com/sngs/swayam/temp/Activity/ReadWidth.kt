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
import com.sngs.swayam.temp.pademeter.pedemer.ui.Activity_Main
import kotlinx.android.synthetic.main.activity_read_width.*

class ReadWidth : AppCompatActivity() {
    var np: NumberPicker? = null
    var db: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_width)
        db = DatabaseHandler(this)

        np = findViewById<View>(R.id.npheight) as NumberPicker
        val values = arrayOfNulls<String>(100)
        iv_back.setOnClickListener(View.OnClickListener { onBackPressed() })
        tv_next.setOnClickListener(View.OnClickListener {

            if (weight == null) {
                weight = values[0]
            }
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
            val ContactNo = sharedPreferences.getString("User_no", "")
            db!!.UPDATE_UserWeight(ContactNo, weight)

            val intent = Intent(this@ReadWidth, Activity_Main::class.java)
            startActivity(intent)
        })
        var j = 30
        for (i in 0..99) {
            if (j < 131) {
                val temp = "$j Kg"
                Log.e("CUSTOM---", "onCreate: $temp")
                values[i] = temp
                j++
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
            weight = values[newVal]
        }
    }

    companion object {
        var weight: String? = null
    }



}