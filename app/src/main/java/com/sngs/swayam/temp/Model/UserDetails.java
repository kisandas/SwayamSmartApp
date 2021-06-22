package com.sngs.swayam.temp.Model;

import java.util.Date;

public class UserDetails {


    String UserID, UserName, UserMobile, UserHeight, UserWeight, UserAge,UserStepCount,UserDistanceCount,UserCalory,UserWater,UserDate;
    String S_Id, S_Name, S_Mobile, S_Email, S_Dob, S_State, S_Sender;
    Date date;
    String L_Mobile, L_Code;

    public UserDetails() {
    }


    public UserDetails(String userName, String Mobile, String height, String weight, String age) {
        UserName = userName;
        UserMobile = Mobile;
        UserHeight = height;
        UserWeight = weight;
        UserAge = age;

    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserMobile(String userMobile) {
        UserMobile = userMobile;
    }

    public void setUserHeight(String userHeight) {
        UserHeight = userHeight;
    }

    public void setUserWeight(String userWeight) {
        UserWeight = userWeight;
    }

    public void setUserAge(String userAge) {
        UserAge = userAge;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserMobile() {
        return UserMobile;
    }

    public String getUserHeight() {
        return UserHeight;
    }

    public String getUserWeight() {
        return UserWeight;
    }

    public String getUserAge() {
        return UserAge;
    }


    public String getUserStepCount() {
        return UserStepCount;
    }

    public void setUserStepCount(String userStepCount) {
        UserStepCount = userStepCount;
    }

    public String getUserDistanceCount() {
        return UserDistanceCount;
    }

    public void setUserDistanceCount(String userDistanceCount) {
        UserDistanceCount = userDistanceCount;
    }

    public String getUserCalory() {
        return UserCalory;
    }

    public void setUserCalory(String userCalory) {
        UserCalory = userCalory;
    }

    public String getUserWater() {
        return UserWater;
    }

    public void setUserWater(String userWater) {
        UserWater = userWater;
    }

    public String getUserDate() {
        return UserDate;
    }

    public void setUserDate(String userDate) {
        UserDate = userDate;
    }

    public Date getDate() {
        return date;
    }

    public UserDetails(String Mobile, String Code) {
        L_Mobile = Mobile;
        L_Code = Code;
    }


    public String getL_Mobile() {
        return L_Mobile;
    }

    public String getL_Code() {
        return L_Code;
    }

    public void setS_Id(String s_Id) {
        S_Id = s_Id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setL_Mobile(String l_Mobile) {
        L_Mobile = l_Mobile;
    }

    public void setL_Code(String l_Code) {
        L_Code = l_Code;
    }


    public UserDetails(String s_Name, String s_Mobile, String s_Email, String s_Dob, String s_State, String s_Sender) {
        S_Name = s_Name;
        S_Mobile = s_Mobile;
        S_Email = s_Email;
        S_Dob = s_Dob;
        S_State = s_State;
        S_Sender = s_Sender;
    }


    public void setS_Name(String s_Name) {
        S_Name = s_Name;
    }

    public void setS_Mobile(String s_Mobile) {
        S_Mobile = s_Mobile;
    }

    public void setS_Email(String s_Email) {
        S_Email = s_Email;
    }

    public void setS_Dob(String s_Dob) {
        S_Dob = s_Dob;
    }

    public void setS_State(String s_State) {
        S_State = s_State;
    }

    public void setS_Sender(String s_Sender) {
        S_Sender = s_Sender;
    }


    public String getS_Name() {
        return S_Name;
    }

    public String getS_Mobile() {
        return S_Mobile;
    }

    public String getS_Email() {
        return S_Email;
    }

    public String getS_Dob() {
        return S_Dob;
    }

    public String getS_State() {
        return S_State;
    }

    public String getS_Sender() {
        return S_Sender;
    }
}
