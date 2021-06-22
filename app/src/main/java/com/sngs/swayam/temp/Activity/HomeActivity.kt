package com.sngs.swayam.temp.Activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dueeeke.tablayout.listener.OnTabSelectListener
import com.sngs.swayam.temp.Activity.datausage.DataUsagesActivity
import com.sngs.swayam.temp.Activity.editprofile.EditProfileActivity
import com.sngs.swayam.temp.Activity.offer.OfferActivity
import com.sngs.swayam.temp.Adapter.home.BannerListAdapter
import com.sngs.swayam.temp.Adapter.notes.NotesListAdapter
import com.sngs.swayam.temp.Adapter.offer.OfferListAdapter
import com.sngs.swayam.temp.DataBase.DatabaseHandler
import com.sngs.swayam.temp.Fragment.Pedometer.Daily_Fragment
import com.sngs.swayam.temp.Model.UserDetails
import com.sngs.swayam.temp.Network.WebUtlis.Links
import com.sngs.swayam.temp.Network.model.AdvertismentList.AdvertismentListBaseResponse
import com.sngs.swayam.temp.Network.model.BannerList.BannerListBaseResponse
import com.sngs.swayam.temp.Network.model.PromotionList.GetCustomerPromotionListBaseResponse
import com.sngs.swayam.temp.Network.model.UserDetail.UserDetailBaseResponse
import com.sngs.swayam.temp.Network.model.UserDetail.UserResult
import com.sngs.swayam.temp.Network.servicecall.ServiceCall
import com.sngs.swayam.temp.R
import com.sngs.swayam.temp.User.LoginActivity
import com.sngs.swayam.temp.pademeter.Broadcast_Save_Userdata
import com.sngs.swayam.temp.pademeter.pedemer.ui.Activity_Main
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.loading_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : AppCompatActivity() {

    var speedScroll: Long = 50000;

    lateinit var sharedPreferences: SharedPreferences
    var db: DatabaseHandler? = null
    val formatter =
        NumberFormat.getInstance(Locale.getDefault())

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        );
        setContentView(R.layout.activity_drawer)
        sharedPreferences = getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        init()
        set_drawer()
        NotificationCheckPoint();
        click_fun()
        profile_data()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun NotificationCheckPoint() {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 58
        calendar[Calendar.SECOND] = 0
        val MyIntent = Intent(applicationContext, Broadcast_Save_Userdata::class.java)
        val MyPendIntent = PendingIntent.getBroadcast(
            applicationContext, 100,
            MyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val MyAlarm =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        MyAlarm.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, MyPendIntent
        )
    }

    private fun profile_data() {
        val notes_rv = findViewById(R.id.notes_rv) as RecyclerView
        notes_rv.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

        var mb_used = Links.networkUsage(this@HomeActivity)
        tv_dataUsed.setText("" + mb_used + " MB")
    }

    private fun init() {
        db = DatabaseHandler(this)
        Links.dummy_list.clear()
        Links.dummy_list.add("1")
        Links.dummy_list.add("2")
        Links.dummy_list.add("3")
        tabLayout_1.setTabData(Links.mTitles);
        if (tabLayout_1.getCurrentTab() == 0) {
            var strstepcount: Int = 0
            var strcalcount: Int = 0
            val calDate = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd MMM yyyy")
            calDate.add(Calendar.DATE, -6)
            val today = Date()
            val FirstDate = sdf.format(today)
            val LastDate = sdf.format(calDate.time)
            val listdate = db!!.GET_User_MainCount(LastDate, FirstDate)
            for (i in listdate.indices) {
                strstepcount += formatter.format(listdate[i].strStepCount.toInt())
                    .toInt()
                strcalcount += listdate[i].strCalory.toInt()
            }
            txtTotalStep.text = Integer.toString(strstepcount / 7)
            txtTotalCal.text = Integer.toString(strcalcount / 7)
        }
        tabLayout_1.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {

                if (tabLayout_1.getCurrentTab() == 0) {
                    var strstepcount: Int = 0
                    var strcalcount: Int = 0
                    val calDate = Calendar.getInstance()
                    val sdf = SimpleDateFormat("dd MMM yyyy")
                    calDate.add(Calendar.DATE, -6)
                    val today = Date()
                    val FirstDate = sdf.format(today)
                    val LastDate = sdf.format(calDate.time)
                    val listdate = db!!.GET_User_MainCount(LastDate, FirstDate)
                    for (i in listdate.indices) {
                        strstepcount += formatter.format(listdate[i].strStepCount.toInt())
                            .toInt()
                        strcalcount += listdate[i].strCalory.toInt()
                    }
                    txtTotalStep.text = Integer.toString(strstepcount / 7)
                    txtTotalCal.text = Integer.toString(strcalcount / 7)
                } else if (tabLayout_1.getCurrentTab() == 1) {
                    var strstepcount: Int = 0
                    var strcalcount: Int = 0
                    val calDate = Calendar.getInstance()
                    val sdf = SimpleDateFormat("dd MMM yyyy")
                    calDate.add(Calendar.MONTH, -1)
                    val today = Date()
                    val FirstDate = sdf.format(today)
                    val LastDate = sdf.format(calDate.time)
                    val listdate = db!!.GET_User_MainCount(LastDate, FirstDate)
                    for (i in listdate.indices) {
                        strstepcount += formatter.format(listdate[i].strStepCount.toInt())
                            .toInt()
                        strcalcount += listdate[i].strCalory.toInt()
                    }
                    try {
                        txtTotalStep.text = Integer.toString(strstepcount / listdate.size)
                        txtTotalCal.text = Integer.toString(strcalcount / listdate.size)

                    } catch (e: Exception) {

                    }

                } else if (tabLayout_1.getCurrentTab() == 2) {
                    var strstepcount: Int = 0
                    var strcalcount: Int = 0
                    val calDate = Calendar.getInstance()
                    val sdf = SimpleDateFormat("dd MMM yyyy")
                    calDate.add(Calendar.MONTH, -6)
                    val today = Date()
                    val FirstDate = sdf.format(today)
                    val LastDate = sdf.format(calDate.time)
                    val listdate = db!!.GET_User_MainCount(LastDate, FirstDate)
                    System.out.println("GET_User_Week_Of_list_" + listdate.size + ", ");

                    for (i in listdate.indices) {
                        strstepcount += formatter.format(listdate[i].strStepCount.toInt())
                            .toInt()
                        strcalcount += listdate[i].strCalory.toInt()
                    }
                    try {
                        txtTotalStep.text = Integer.toString(strstepcount / listdate.size)
                        txtTotalCal.text = Integer.toString(strcalcount / listdate.size)

                    } catch (e: Exception) {

                    }
                } else if (tabLayout_1.getCurrentTab() == 3) {
                    var strstepcount: Int = 0
                    var strcalcount: Int = 0
                    val calDate = Calendar.getInstance()
                    val sdf = SimpleDateFormat("dd MMM yyyy")
                    calDate.add(Calendar.YEAR, 1)
                    val today = Date()
                    val FirstDate = sdf.format(today)
                    val LastDate = sdf.format(calDate.time)
                    val listdate = db!!.GET_User_MainCount(LastDate, FirstDate)
                    for (i in listdate.indices) {
                        strstepcount += formatter.format(listdate[i].strStepCount.toInt())
                            .toInt()
                        strcalcount += listdate[i].strCalory.toInt()
                    }
                    try {
                        txtTotalStep.text = Integer.toString(strstepcount / listdate.size)
                        txtTotalCal.text = Integer.toString(strcalcount / listdate.size)
                    } catch (e: Exception) {
                    }
                }
            }

            override fun onTabReselect(position: Int) {}
        })

        val your_offer_rv = findViewById(R.id.your_offer_rv) as RecyclerView
        your_offer_rv.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        val banner_rv = findViewById(R.id.banner_rv) as RecyclerView
        banner_rv.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

        api_calling_for_user_promotion_list()
        api_calling_for_banner_list()
        api_calling_for_advertisment_list()
        auto_swipe_promotion()
    }


    private fun click_fun() {
        logout_li1.setOnClickListener {
            logout()
        }

        profile_li.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        view_more_rel.setOnClickListener {
            val intent = Intent(this, OfferActivity::class.java)
            startActivity(intent)
        }

        layDataUsage.setOnClickListener {
            val intent = Intent(this, DataUsagesActivity::class.java)
            startActivity(intent)
        }

        lay_dailyAvg.setOnClickListener {

            val sharedPreferences: SharedPreferences =
                getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
            val UserContactNo = sharedPreferences.getString("User_no", "")

            val contacts: List<UserDetails> =
                db?.GET_USER_DETAIL(UserContactNo) as List<UserDetails>


            if (contacts.size == 0) {
                val intent = Intent(this, ReadAge::class.java)
                startActivity(intent)
            } else {
                for (cn in contacts) {
                    var StrUserAge = cn?.getUserAge()
                    var StrUserHeight = cn?.getUserHeight()
                    var StrUserWeight = cn?.getUserWeight()

                    if (StrUserAge == "") {
                        val intent = Intent(this, ReadAge::class.java)
                        startActivity(intent)
                    } else if (StrUserHeight == "") {
                        val intent = Intent(this, ReadHeight::class.java)
                        startActivity(intent)
                    } else if (StrUserWeight == "") {
                        val intent = Intent(this, ReadWidth::class.java)
                        startActivity(intent)
                    } else {
//                        val intent = Intent(this, StepCounterActivity::class.java)
                        val intent = Intent(this, Activity_Main::class.java)
                        startActivity(intent)
                    }

                }

            }

        }
    }


    override fun onBackPressed() {
        if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
            drawerlayout.closeDrawer(Gravity.LEFT)
        } else {
            back_dialog_box()
        }
    }

    private fun back_dialog_box() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(R.string.app_name)

        //set message for alert dialog
        builder.setMessage(R.string.back_mes)
        builder.setIcon(R.drawable.app_logo)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            finish()
        }

        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->

        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun set_drawer() {
        val actionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerlayout,
            R.string.open,
            R.string.close
        ) {
            private val scaleFactor = 10f
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX = drawerView.width * slideOffset
                content.translationX = slideX
                content.scaleX = 1 - slideOffset / scaleFactor
                content.scaleY = 1 - slideOffset / scaleFactor
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                btnMenu.setImageResource(R.drawable.ic_drawer)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                btnMenu.setImageResource(R.drawable.ic_back_arrow)
            }
        }

        drawerlayout.setScrimColor(Color.TRANSPARENT)
        drawerlayout.drawerElevation = 0f
        drawerlayout.addDrawerListener(actionBarDrawerToggle)

        btnMenu.setOnClickListener {
            drawerlayout.openDrawer(GravityCompat.START);
            setStatusBarGradient()
        }
    }

    fun setStatusBarGradient(color: Int = R.color.white) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                val window = window
                var flags = window.decorView.systemUiVisibility
                flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                window.decorView.systemUiVisibility = flags
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = ContextCompat.getColor(this, color)
            }
            else -> {
                window.statusBarColor = ContextCompat.getColor(
                    this,
                    R.color.view_color
                )
            }
        }
    }

    private fun logout() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("Auth_ID", "")
        editor.putString("Auth_Token", "")
        editor.putString("User_name", "")
        editor.putString("User_no", "")
        editor.putString("Is_Login", "false")
        editor.commit()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun api_calling_for_banner_list() {

        val auth_id = sharedPreferences.getString("Auth_ID", "")
        val auth_token = sharedPreferences.getString("Auth_Token", "")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callGetBannerList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<BannerListBaseResponse> {
                override fun onResponse(
                    call: Call<BannerListBaseResponse>,
                    response: Response<BannerListBaseResponse>
                ) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.Banner_list.clear()
                        if (success_v?.toInt() == 1) {
                            if (response.body()!!.bannerListResult != null) {
                                Links.Banner_list = response.body()!!.bannerListResult
                                banner_rv.adapter =
                                    BannerListAdapter(Links.Banner_list, this@HomeActivity)
                            }
                        } else {
                            Links.snack_bar(
                                this@HomeActivity,
                                content,
                                response.body()?.message.toString()
                            )
                        }
                    } else {
                        Links.snack_bar(
                            this@HomeActivity,
                            content,
                            response.body()?.message.toString()
                        )
                    }
                }

                override fun onFailure(call: Call<BannerListBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@HomeActivity, content, t.message.toString())
                }
            })
    }

    private fun api_calling_for_advertisment_list() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID", "")
        val auth_token = sharedPreferences.getString("Auth_Token", "")

        //  loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callAdvertismentList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<AdvertismentListBaseResponse> {
                override fun onResponse(
                    call: Call<AdvertismentListBaseResponse>,
                    response: Response<AdvertismentListBaseResponse>
                ) {
                    //     loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.Advertisment_list.clear()
                        if (success_v?.toInt() == 1) {
                            if (response.body()!!.promotionBannerListResult != null) {
                                Links.Advertisment_list =
                                    response.body()!!.promotionBannerListResult
                                notes_rv.adapter =
                                    NotesListAdapter(Links.Advertisment_list, this@HomeActivity)
                            }
                        } else {
                            Links.snack_bar(
                                this@HomeActivity,
                                content,
                                response.body()?.message.toString()
                            )
                        }
                    } else {
                        Links.snack_bar(
                            this@HomeActivity,
                            content,
                            response.body()?.message.toString()
                        )
                    }
                }

                override fun onFailure(call: Call<AdvertismentListBaseResponse>, t: Throwable) {
                    //   loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@HomeActivity, content, t.message.toString())
                }
            })
    }

    private fun api_calling_for_user_promotion_list() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID", "")
        val auth_token = sharedPreferences.getString("Auth_Token", "")

        //loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserPromotionList(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<GetCustomerPromotionListBaseResponse> {
                override fun onResponse(
                    call: Call<GetCustomerPromotionListBaseResponse>,
                    response: Response<GetCustomerPromotionListBaseResponse>
                ) {
                    //loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        Links.PromotionResult_list.clear()
                        if (success_v?.toInt() == 1) {
                            if (response.body()!!.promotionListResult != null) {
                                Links.PromotionResult_list = response.body()!!.promotionListResult
                                your_offer_rv.adapter =
                                    OfferListAdapter(Links.PromotionResult_list, this@HomeActivity)
                            }
                        } else {
                            //  Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                        }
                    } else {
                        // Links.snack_bar(this@HomeActivity,content,response.body()?.message.toString())
                    }
                }

                override fun onFailure(
                    call: Call<GetCustomerPromotionListBaseResponse>,
                    t: Throwable
                ) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@HomeActivity, content, t.message.toString())
                }
            })
    }

    private fun api_calling_for_user_detail() {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("Swayam Smart", Context.MODE_PRIVATE)
        val auth_id = sharedPreferences.getString("Auth_ID", "")
        val auth_token = sharedPreferences.getString("Auth_Token", "")

        loading_layout.setVisibility(View.VISIBLE)

        ServiceCall.callUserDetail(this, auth_id, auth_token, Links.User_Type)
            .enqueue(object : Callback<UserDetailBaseResponse> {
                override fun onResponse(
                    call: Call<UserDetailBaseResponse>,
                    response: Response<UserDetailBaseResponse>
                ) {
                    loading_layout.setVisibility(View.GONE)
                    if (response.isSuccessful()) {
                        val success_v = response.body()?.success
                        if (success_v?.toInt() == 1) {
                            if (response.body()!!.userResult != null) {
                                set_data(response.body()!!.userResult)
                            }
                        } else {
                            Links.snack_bar(
                                this@HomeActivity,
                                content,
                                response.body()?.message.toString()
                            )
                        }
                    } else {
                        Links.snack_bar(
                            this@HomeActivity,
                            content,
                            response.body()?.message.toString()
                        )
                    }
                }

                override fun onFailure(call: Call<UserDetailBaseResponse>, t: Throwable) {
                    loading_layout.setVisibility(View.GONE)
                    Links.snack_bar(this@HomeActivity, content, t.message.toString())
                }
            })
    }

    private fun set_data(userResult: UserResult?) {
        user_name_txt.setText("" + userResult!!.userName)
        tv_user_name.setText("" + userResult!!.userName)
        tv_user_mobile.setText("" + userResult!!.userContactNumber)
        coins.setText("" + userResult!!.getmUserAvailableCoins())
        Glide.with(applicationContext).load(userResult!!.userProfilePicure).placeholder(
            R.drawable.profile_icon
        ).into(profile_img);
        Glide.with(applicationContext).load(userResult!!.userProfilePicure)
            .placeholder(R.drawable.profile_icon)
            .into(profile_user_img);
    }

    override fun onResume() {
        super.onResume()
        api_calling_for_user_promotion_list()
        api_calling_for_banner_list()
        api_calling_for_advertisment_list()
        api_calling_for_user_detail()
    }

    private fun auto_swipe_promotion() {
        var handler_banner = Handler();
        val runnable = object : Runnable {
            override fun run() {
                handler_banner.postDelayed(this, speedScroll);
                api_calling_for_user_promotion_list()
            }
        }
        handler_banner.postDelayed(runnable, speedScroll);
    }

}


