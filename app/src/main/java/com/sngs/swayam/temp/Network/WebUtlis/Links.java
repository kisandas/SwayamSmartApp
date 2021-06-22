package com.sngs.swayam.temp.Network.WebUtlis;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.TrafficStats;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.sngs.swayam.temp.Model.DataUsage;
import com.sngs.swayam.temp.Network.model.AdvertismentList.PromotionBannerListResult;
import com.sngs.swayam.temp.Network.model.Area.AreaListDatum;
import com.sngs.swayam.temp.Network.model.BannerList.BannerListResult;
import com.sngs.swayam.temp.Network.model.City.CityListDatum;

import com.sngs.swayam.temp.Network.model.PromotionDetail.PromotionListResult;
import com.sngs.swayam.temp.Network.model.State.StateListDatum;
import com.sngs.swayam.temp.Network.model.UserDetail.UserResult;
import com.sngs.swayam.temp.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class Links {

    public static final String SERVER_URL = "https://www.digisysindiatech.com/swayam/app/API/";
    //  public  static final String SERVER_URL = "https://sohumngs.com/swayam/app/API/";
    public static final String User_Type = "5";

    public static List<String> dummy_list = new ArrayList<>();
    public static List<PromotionListResult> promotion_list = new ArrayList<>();
    public static String[] mTitles = {"7 Days", "30 Days", "6 Months", "1 Year"};
    public static String[] mTitles_Tab = {"Daily", "Weekly", "Monthly", "Yearly", "Custom"};
    public static String[] mTitles_Month = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};


    //Swayam Smart
    public static final String User_SignUp = "userSignUp.php";
    public static final String User_SignIn = "signIn.php";
    public static final String Mobile_Verify = "mobileVerify.php";
    public static final String Edit_User_Profile = "editUserProfile.php";


    //select state , city , area
    public static final String Get_State_List = "getStateList.php";
    public static final String Get_City_List = "getCityList.php";
    public static final String Get_Area_List = "getAreaList.php";


    //Promotion Detail List
    public static final String Get_UserPromotionDetail_List = "getUserPromotionDetailList.php";

    //Advertisement
    public static final String Get_Advertisement_List = "getAdvertisementList.php";

    //Banner
    public static final String Get_Banner_List = "getBannerList.php";

    //user detail
    public static final String Get_User_Detail = "getUserDetail.php";

    //Query
    public static final String Add_Promotion_Query = "addPromotionQuery.php";
    public static final String Promotion_Like_Dislike = "promotionLikeDislike.php";


    //purchase promotion
    public static final String User_Purchase_Promotion = "userPurchasePromotion.php";
    public static final String View_Promotion = "viewPromotion.php";
    public static final String Get_Customer_Detail = "getCustomerDetail.php";


    public static ArrayList<String> selected_image_array_list = new ArrayList<String>();


    public static class Header {
        public static final String Auth_ID = "authId";
        public static final String Auth_Token = "authToken";
        public static final String User_Type = "userType";
        public static final String Reg_Id = "regId";
        public static final String Device_Id = "device_id";
    }


    //Swayam Smart
    public static class UserSignUp {
        public static final String User_Type = "userType";
        public static final String User_Name = "userName";
        public static final String User_Contact_Number = "userContactNumber";
        public static final String User_Email = "userEmail";
        public static final String User_Gender = "userGender";
        public static final String User_BirthDate = "userBirthDate";
        public static final String User_State = "userState";
        public static final String User_City = "userCity";
        public static final String User_Area = "userArea";
    }

    //Swayam Smart
    public static class UserSignIn {
        public static final String User_Type = "userType";
        public static final String User_Contact_Number = "userContactNumber";
    }

    public static class MobileVerifyDetail {
        public static final String User_Type = "userType";
        public static final String Contact_Number = "contactNumber";
    }

    public static class EditUserProfile {
        public static final String User_Name = "userName";
        public static final String User_ContactNumber = "userContactNumber";
        public static final String User_Email = "userEmail";
        public static final String User_Gender = "userGender";
        public static final String User_BirthDate = "userBirthDate";
        public static final String User_State = "userState";
        public static final String User_City = "userCity";
        public static final String User_Area = "userArea";
        public static final String User_Profile_Pic = "userProfilePicture";
    }

    public static class City_List_Detail {
        public static final String state_Id = "stateId";
    }

    public static class Area_List_Detail {
        public static final String state_Id = "stateId";
        public static final String city_Id = "cityId";
    }

    public static class Purchase_Promotion_Detail {
        public static final String Promotion_Id = "promotionId";
    }

    public static class ViewPromotion {
        public static final String Promotion_Id = "promotionId";
    }

    public static class CustomerDetail {
        public static final String Customer_Id = "customerId";
    }

    public static class PromotionQueryDetail {
        public static final String Promotion_Id = "promotionId";
        public static final String Customer_Id = "customerId";
        public static final String Query = "query";
    }

    public static class PromotionLikeDislikeDetail {
        public static final String Promotion_Id = "promotionId";
        public static final String Like_Dislike = "likeDislike";
    }


    public static void snack_bar(Context context, RelativeLayout main_layout, String mes) {
        TSnackbar snackbar = TSnackbar.make(main_layout, "" + mes, TSnackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    public static List<StateListDatum> state_list = new ArrayList<>();
    public static List<CityListDatum> city_list = new ArrayList<>();
    public static List<AreaListDatum> area_list = new ArrayList<>();


    public static String selected_state_name = "";
    public static String selected_state_id = "";

    public static String selected_city_name = "";
    public static String selected_city_id = "";

    public static String selected_servies_name = "";
    public static String selected_servies_id = "";


    //baner list
    public static List<BannerListResult> Banner_list = new ArrayList<>();
    public static List<PromotionBannerListResult> Advertisment_list = new ArrayList<>();
    public static List<com.sngs.swayam.temp.Network.model.PromotionList.PromotionListResult>
            PromotionResult_list = new ArrayList<>();


    //User list
    public static UserResult userResult = new UserResult();

    //data_usage_type
    public static List<DataUsage> data_usage_type = new ArrayList();


    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));


        Log.e("App_Data_Usage", " " + new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups));
    }

    public static float networkUsage(Context context) {
        float fileSizeInKB = 0;
        float fileSizeInMB = 0;
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningApp : runningApps) {
            // Get UID of the selected process
            int uid = 0;
            try {
                uid = context.getPackageManager().getApplicationInfo("com.sngs.swayam.smartapp", 0).uid;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            // Get traffic data
            long received = TrafficStats.getUidRxBytes(uid);
            long send = TrafficStats.getUidTxBytes(uid);
            long total_data = received + send;
            fileSizeInKB = total_data / 1024;
            fileSizeInMB = fileSizeInKB / 1024;
            Log.e("Naimee" + uid, "Send :" + send + ", Received :" + received);
            Log.e("Naimee" + uid, "KB :" + fileSizeInKB);
            Log.e("Naimee" + uid, "MB :" + fileSizeInMB);
        }
        return fileSizeInKB;
    }
}
