package com.sngs.swayam.temp.Model;

public class Model_Calories {

    String T_ID, T_Categories, T_ItemName, T_Qty, T_Calories, T_Imagename;

    public Model_Calories(String t_Categories, String t_ItemName, String t_Qty, String t_Calories, String t_Imagename) {
        T_Categories = t_Categories;
        T_ItemName = t_ItemName;
        T_Qty = t_Qty;
        T_Calories = t_Calories;
        T_Imagename = t_Imagename;
    }
    public Model_Calories() {
    }

    public Model_Calories(String t_Categories) {
        T_Categories = t_Categories;
    }
    public Model_Calories( String t_ItemName, String t_Qty, String t_Calories, String t_Imagename) {
        T_ItemName = t_ItemName;
        T_Qty = t_Qty;
        T_Calories = t_Calories;
        T_Imagename = t_Imagename;
    }
    public String getT_Imagename() {
        return T_Imagename;
    }

    public void setT_Imagename(String t_Imagename) {
        T_Imagename = t_Imagename;
    }


    public String getT_ID() {
        return T_ID;
    }

    public void setT_ID(String t_ID) {
        T_ID = t_ID;
    }

    public String getT_Categories() {
        return T_Categories;
    }

    public void setT_Categories(String t_Categories) {
        T_Categories = t_Categories;
    }

    public String getT_ItemName() {
        return T_ItemName;
    }

    public void setT_ItemName(String t_ItemName) {
        T_ItemName = t_ItemName;
    }

    public String getT_Qty() {
        return T_Qty;
    }

    public void setT_Qty(String t_Qty) {
        T_Qty = t_Qty;
    }

    public String getT_Calories() {
        return T_Calories;
    }

    public void setT_Calories(String t_Calories) {
        T_Calories = t_Calories;
    }
}
