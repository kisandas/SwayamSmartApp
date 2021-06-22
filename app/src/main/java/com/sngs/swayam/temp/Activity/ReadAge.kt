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
import kotlinx.android.synthetic.main.activity_read_age.*

class ReadAge : AppCompatActivity() {
    var np: NumberPicker? = null
    var db: DatabaseHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_age)
        db = DatabaseHandler(this)
        np = findViewById<View>(R.id.np) as NumberPicker
        val values = arrayOfNulls<String>(82)
        iv_back.setOnClickListener(View.OnClickListener { onBackPressed() })
        tv_next.setOnClickListener(View.OnClickListener {
            val sharedPreferences: SharedPreferences =
                getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
            val ContactNo = sharedPreferences.getString("User_no", "")
            if (age == null) {
                age = values[0]
            }
            db!!.UPDATE_UserAge(ContactNo, age)
            val intent = Intent(this@ReadAge, ReadHeight::class.java)
            startActivity(intent)
        })

        //Initializing a new string array with elements
        var j = 18
        for (i in 0..99) {
            if (j < 100) {
                values[i] = j.toString()
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
            age = values[newVal]
        }
    }

    companion object {
        @JvmField
        var age: String? = null
    }
}