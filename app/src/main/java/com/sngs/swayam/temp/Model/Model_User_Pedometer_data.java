package com.sngs.swayam.temp.Model;

public class Model_User_Pedometer_data {
    String strMobile, strStepCount, strDistanceCount, strCalory, strWater, strDate;

    public Model_User_Pedometer_data() {
    }

    public Model_User_Pedometer_data(String strStepCount, String strCalory, String StrDate, String strWater, String strDistanceCount) {
        this.strStepCount = strStepCount;
        this.strCalory = strCalory;
        this.strDate = StrDate;
        this.strWater = strWater;
        this.strDistanceCount = strDistanceCount;


    }

    public Model_User_Pedometer_data(String strMobile, String strStepCount, String strDistanceCount, String strCalory, String strWater, String strDate) {
        this.strMobile = strMobile;
        this.strStepCount = strStepCount;
        this.strDistanceCount = strDistanceCount;
        this.strCalory = strCalory;
        this.strWater = strWater;
        this.strDate = strDate;
    }

    public String getStrMobile() {
        return strMobile;
    }

    public void setStrMobile(String strMobile) {
        this.strMobile = strMobile;
    }

    public String getStrStepCount() {
        return strStepCount;
    }

    public void setStrStepCount(String strStepCount) {
        this.strStepCount = strStepCount;
    }

    public String getStrDistanceCount() {
        return strDistanceCount;
    }

    public void setStrDistanceCount(String strDistanceCount) {
        this.strDistanceCount = strDistanceCount;
    }

    public String getStrCalory() {
        return strCalory;
    }

    public void setStrCalory(String strCalory) {
        this.strCalory = strCalory;
    }

    public String getStrWater() {
        return strWater;
    }

    public void setStrWater(String strWater) {
        this.strWater = strWater;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }
}
