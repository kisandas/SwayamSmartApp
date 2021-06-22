package com.sngs.swayam.temp.User

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sngs.swayam.temp.Activity.HomeActivity
import com.sngs.swayam.temp.Constant
import com.sngs.swayam.temp.DataBase.DatabaseHandler
import com.sngs.swayam.temp.Model.Model_Calories
import com.sngs.swayam.temp.Model.UserDetails
import com.sngs.swayam.temp.Network.WebUtlis.Links
import com.sngs.swayam.temp.R
import kotlinx.android.synthetic.main.activity_verification.*


class VerificationActivity : AppCompatActivity() {

    var otp: String = "1234"
    var page_type: String = "0"
    var db: DatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_verification)

        init()
        click_fun()
    }

    private fun init() {
        db = DatabaseHandler(this)
        otp = intent.getStringExtra("OTP").toString()
        otp_view.otp = otp
    }

    private fun click_fun() {

        llVerify.setOnClickListener {
            if (otp_view.otp.isEmpty()) {
                Links.snack_bar(
                    this@VerificationActivity,
                    main_layout,
                    "" + resources.getString(R.string.empty_otp).toString()
                )
            } else if (!otp_view.otp.equals(otp)) {
                Links.snack_bar(
                    this@VerificationActivity,
                    main_layout,
                    "" + resources.getString(R.string.valid_otp).toString()
                )
            } else {
                move_next_page()
            }
        }

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun move_next_page() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("Is_Login", "true")
        editor.commit()
        val UserContactNo = sharedPreferences.getString("User_no", "")
        val UserName = sharedPreferences.getString("User_name", "")

        funAdd_Calory();
        val contacts: List<UserDetails> = db?.GET_LOGIN_USER(UserContactNo) as List<UserDetails>
        if (contacts.size == 0) {
            db!!.Add_LOGIN(UserDetails(UserContactNo, otp_view.otp))
            db!!.Add_UserDetail(UserDetails(UserName, UserContactNo, "", "", ""))

            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } else {
            for (cn in contacts) {
                var StrMobile = cn?.getL_Mobile()
                if (StrMobile == "") {
                    val intent = Intent(this, SignupActivity::class.java)
                    startActivity(intent)
                } else {
                    db!!.Add_LOGIN(UserDetails(StrMobile, otp_view.otp))
                    db!!.Add_UserDetail(UserDetails(UserName, UserContactNo, "", "", "", ""))
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

            }
        }


    }

    private fun funAdd_Calory() {
        val Calory: List<Model_Calories> = db?.GET_CALORIES_CATEGORY() as List<Model_Calories>
                if (Calory.size == 0) {
            for (i in Constant.Categories.indices) {
                db!!.Add_Calories_Category(Model_Calories(Constant.Categories[i]))
            }
            for (i in Constant.Item_fast.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[0],
                        Constant.Item_fast[i],
                        Constant.Item_fast_QTY[i],
                        Constant.Item_fast_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Beverages.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[1],
                        Constant.Item_Beverages[i],
                        Constant.Item_Beverages_QTY[i],
                        Constant.Item_Beverages_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Breads.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[2],
                        Constant.Item_Breads[i],
                        Constant.Item_Breads_QTY[i],
                        Constant.Item_Breads_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Eggs.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[3],
                        Constant.Item_Eggs[i],
                        Constant.Item_Eggs_QTY[i],
                        Constant.Item_Eggs_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Fruits.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[4],
                        Constant.Item_Fruits[i],
                        Constant.Item_Fruits_QTY[i],
                        Constant.Item_Fruits_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Juice.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[5],
                        Constant.Item_Juice[i],
                        Constant.Item_Juice_QTY[i],
                        Constant.Item_Juice_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_BakedGoods.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[6],
                        Constant.Item_BakedGoods[i],
                        Constant.Item_BakedGoods_QTY[i],
                        Constant.Item_BakedGoods_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Dairy.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[7],
                        Constant.Item_Dairy[i],
                        Constant.Item_Dairy_QTY[i],
                        Constant.Item_Dairy_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Fast_Foods.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[8],
                        Constant.Item_Fast_Foods[i],
                        Constant.Item_Fast_Foods_QTY[i],
                        Constant.Item_Fast_Foods_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Fats_Oils.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[9],
                        Constant.Item_Fats_Oils[i],
                        Constant.Item_Fats_Oils_QTY[i],
                        Constant.Item_Fats_Oils_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Fish.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[10],
                        Constant.Item_Fish[i],
                        Constant.Item_Fish_QTY[i],
                        Constant.Item_Fish_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Fruit.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[11],
                        Constant.Item_Fruit[i],
                        Constant.Item_Fruit_QTY[i],
                        Constant.Item_Fruit_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Grain_Cereal.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[12],
                        Constant.Item_Grain_Cereal[i],
                        Constant.Item_Grain_QTY[i],
                        Constant.Item_Grain_Calories[i],
                        ""
                    )
                )
            }

            for (i in Constant.Item_Legumes_Nuts.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[13],
                        Constant.Item_Legumes_Nuts[i],
                        Constant.Item_Legume_QTY[i],
                        Constant.Item_Legume_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Meat_Poultry.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[14],
                        Constant.Item_Meat_Poultry[i],
                        Constant.Item_Meat_QTY[i],
                        Constant.Item_Meat_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Miscellaneous.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[15],
                        Constant.Item_Miscellaneous[i],
                        Constant.Item_Miscellaneous_QTY[i],
                        Constant.Item_Miscellaneous_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_MixedDishes.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[16],
                        Constant.Item_MixedDishes[i],
                        Constant.Item_MixedDishes_QTY[i],
                        Constant.Item_MixedDishes_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Snacks.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[17],
                        Constant.Item_Snacks[i],
                        Constant.Item_Snacks_QTY[i],
                        Constant.Item_Snacks_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Soups.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[18],
                        Constant.Item_Soups[i],
                        Constant.Item_Soups_QTY[i],
                        Constant.Item_Soups_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Sweets.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[19],
                        Constant.Item_Sweets[i],
                        Constant.Item_Sweets_QTY[i],
                        Constant.Item_Sweets_Calories[i],
                        ""
                    )
                )
            }
            for (i in Constant.Item_Vegetables.indices) {
                db!!.Add_Calories(
                    Model_Calories(
                        Constant.Categories[20],
                        Constant.Item_Vegetables[i],
                        Constant.Item_Vegetables_QTY[i],
                        Constant.Item_Vegetables_Calories[i],
                        ""
                    )
                )
            }


        }
    }
}
