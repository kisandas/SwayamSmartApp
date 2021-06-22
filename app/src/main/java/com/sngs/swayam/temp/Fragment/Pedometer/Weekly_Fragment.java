package com.sngs.swayam.temp.Fragment.Pedometer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.sngs.swayam.temp.Constant;
import com.sngs.swayam.temp.DataBase.DatabaseHandler;
import com.sngs.swayam.temp.Model.Model_User_Pedometer_data;
import com.sngs.swayam.temp.R;
import com.sngs.swayam.temp.pademeter.ui.Fragment_Settings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Weekly_Fragment extends Fragment {
    BarChart barChart;
    BarDataSet barDataSet1, barDataSet2;
    ArrayList barEntries;
    DatabaseHandler db;
    ArrayList<String> days;
    ArrayList<String> Week_Of_Date;
    TextView txt_Weekdate, txtavavrange_per, txtstep_per;
    private ArrayList<Model_User_Pedometer_data> List_User_PedomerDetail;
    String strMobile, strStepCount, strDistanceCount, strCalory, strWater, strDate;
    TextView txt_calory_burn, txtGlass_Remain, txtTotalCal, txtGlass, txtStep, txtDist, txtCal;
    View v;
    List<Model_User_Pedometer_data> Week_Of_List, Finl_Week_Of_List;
    int intcalori_minus = 1; //Step 100 minus 1 calories
    float Total_Per_Progress;
    SharedPreferences pref;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragement_weeklystep, container, false);
        db = new DatabaseHandler(getContext());
        fun_init();
        fun_User_PedomerDetail();
        fun_Day_Assign();
        fun_Assign();
        fun_Week_Of_Total();
        fun_WHO_Avavrage();
        return v;
    }


    public void fun_init() {
        progressBar = v.findViewById(R.id.pro_step);
        txtstep_per = v.findViewById(R.id.txtstep_per);
        barChart = v.findViewById(R.id.idBarChart);
        txt_calory_burn = v.findViewById(R.id.txt_calory_burn);
        txtGlass_Remain = v.findViewById(R.id.txtGlass_Remain);
        txt_Weekdate = v.findViewById(R.id.txt_Weekdate);
        txtTotalCal = v.findViewById(R.id.txtTotalCal);
        txtGlass = v.findViewById(R.id.txtGlass);
        txtStep = v.findViewById(R.id.txtStep);
        txtDist = v.findViewById(R.id.txtDist);
        txtCal = v.findViewById(R.id.txtCal);
        txtavavrange_per = v.findViewById(R.id.txtavavrange_per);
    }

    public void fun_Week_Of_Total() {

        int intStep = 0, intCal = 0, intGlass = 0;
        float intDist = 0;
        for (int i = 0; i < Week_Of_List.size(); i++) {
            intStep = intStep + Integer.parseInt(Week_Of_List.get(i).getStrStepCount());
            intCal = intCal + Integer.parseInt(Week_Of_List.get(i).getStrCalory());
            intGlass = intGlass + Integer.parseInt(Week_Of_List.get(i).getStrWater());
            intDist = intDist + Float.parseFloat(Week_Of_List.get(i).getStrDistanceCount());
        }
        pref = getContext().getApplicationContext().getSharedPreferences("Swayam Smart", 0); // 0 - for private mode
        SharedPreferences.Editor editors = pref.edit();
        editors.putString("Week_of_Step", Integer.toString(intStep));
        editors.putString("Week_of_Cal", Integer.toString(intCal));
        editors.commit();
        txtStep.setText(Integer.toString(intStep / 7));
        txtTotalCal.setText(Integer.toString(intCal));
        txtGlass.setText(Integer.toString(intGlass));
        String strintDist = String.format("%.2f", intDist / 7);
        txtDist.setText(strintDist);
        txtCal.setText(Float.toString(intCal / 7));
        txtGlass_Remain.setText(getResources().getString(R.string.Glass_Of_Water1) + " " + Integer.toString(Constant.TotalGlass_Of_Water * 7) + " " + getResources().getString(R.string.Glass_Of_Water2) + " " + Integer.toString(Constant.TotalGlass_Of_Water * 7 - intGlass));
        progressBar.setProgress(intStep / 7);
        progressBar.setMax(Fragment_Settings.DEFAULT_GOAL);
        int Per = intStep / 7 * 100 / Fragment_Settings.DEFAULT_GOAL;
        txtstep_per.setText(Integer.toString(Per) + "%");
        int burncaleries = intStep * intcalori_minus / 100;
        txt_calory_burn.setText("Total calories burn " + Integer.toString(burncaleries));
        float Total_Of_3 = intStep + intDist + intCal / 7;
        float per = Total_Of_3;
        float Total_of_WHO = Constant.WHO_Step + Constant.WHO_Distance + Constant.WHO_Calory;
        float WHO_per = Total_of_WHO;
        Total_Per_Progress = per * 100 / WHO_per;
        String strTotal_Per_Progress = String.format("%.2f", Total_Per_Progress);
        txtavavrange_per.setText(strTotal_Per_Progress + " %");
    }

    public void fun_Assign() {
        barDataSet1 = new BarDataSet(getBarEntriesOne(), "Step");
        barDataSet1.setColor(getContext().getResources().getColor(R.color.color1));
        barDataSet2 = new BarDataSet(getBarEntriesTwo(), "Calories");
        barDataSet2.setColor(getContext().getResources().getColor(R.color.color10));
        BarData data = new BarData(barDataSet1, barDataSet2);
        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        float barSpace = 0.1f;
        float groupSpace = 0.5f;
        data.setBarWidth(0.15f);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.animate();
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }

    public void fun_Day_Assign() {

        days = new ArrayList<String>();
        Week_Of_Date = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        DateFormat df;
        for (int i = 0; i < 7; i++) {
            df = new SimpleDateFormat("dd MMM yyyy");
            c.add(Calendar.DATE, 1);
            Week_Of_Date.add(df.format(c.getTime()));
            df = new SimpleDateFormat("EEEE");
            days.add(df.format(c.getTime()));
        }
        try {
            df = new SimpleDateFormat("dd MMM ");
            Date Date1 = df.parse(Week_Of_Date.get(0).toString());
            Date Date2 = df.parse(Week_Of_Date.get(6).toString());
            txt_Weekdate.setText(df.format(Date1) + " - " + df.format(Date2) + " " + "walking record");

        } catch (ParseException e) {
            e.printStackTrace();
        }


        Week_Of_List = new ArrayList<>();
        Finl_Week_Of_List = new ArrayList<>();
        Week_Of_List = db.GET_Week_Of_list(Week_Of_Date.get(0).toString(), Week_Of_Date.get(6).toString());
        for (int i = 0; i < Week_Of_Date.size(); i++) {
            String sDate = Week_Of_Date.get(i).toString();
            System.out.println("Week_Of_List" + sDate);
            Finl_Week_Of_List.add(new Model_User_Pedometer_data("0", "0", sDate, "0", "0"));
            for (int j = 0; j < Week_Of_List.size(); j++) {
                String strStepCount, strCalory, StrDate, strWater, strDistanceCount;
                strStepCount = Week_Of_List.get(j).getStrStepCount();
                strCalory = Week_Of_List.get(j).getStrCalory();
                StrDate = Week_Of_List.get(j).getStrDate();
                strWater = Week_Of_List.get(j).getStrWater();
                strDistanceCount = Week_Of_List.get(j).getStrDistanceCount();
                if (Finl_Week_Of_List.get(i).getStrDate().equals(StrDate)) {
                    Finl_Week_Of_List.get(i).setStrCalory(strCalory);
                    Finl_Week_Of_List.get(i).setStrDistanceCount(strDistanceCount);
                    Finl_Week_Of_List.get(i).setStrStepCount(strStepCount);
                    Finl_Week_Of_List.get(i).setStrWater(strWater);
                }
            }
        }
    }

    private ArrayList<BarEntry> getBarEntriesOne() {
        barEntries = new ArrayList<>();
        for (int i = 0; i < Finl_Week_Of_List.size(); i++) {
            String Count = Finl_Week_Of_List.get(i).getStrStepCount();
            barEntries.add(new BarEntry(i, Float.parseFloat(Count)));
        }
        return barEntries;
    }

    private ArrayList<BarEntry> getBarEntriesTwo() {
        barEntries = new ArrayList<>();
        for (int i = 0; i < Finl_Week_Of_List.size(); i++) {
            String Count = Finl_Week_Of_List.get(i).getStrCalory();
            barEntries.add(new BarEntry(i, Float.parseFloat(Count)));
        }
        return barEntries;
    }

    public void fun_WHO_Avavrage() {
        CircularProgressBar circularProgressBar = v.findViewById(R.id.circularProgressBar);
        circularProgressBar.setProgress(Total_Per_Progress);
        circularProgressBar.setProgressWithAnimation(Total_Per_Progress, (long) 1000);
        circularProgressBar.setProgressMax(100f);
        circularProgressBar.setProgressBarWidth(7f); // in DP
        circularProgressBar.setBackgroundProgressBarWidth(3f); // in DP
        circularProgressBar.setRoundBorder(true);
        circularProgressBar.setStartAngle(180f);
        circularProgressBar.setProgressDirection(CircularProgressBar.ProgressDirection.TO_RIGHT);
    }

    public void fun_User_PedomerDetail() {
        List<Model_User_Pedometer_data> calories = db.GET_User_Pedometer_Detail();
        List_User_PedomerDetail = new ArrayList<>();
        for (Model_User_Pedometer_data cn : calories) {
            strMobile = cn.getStrMobile();
            strStepCount = cn.getStrStepCount();
            strDistanceCount = cn.getStrDistanceCount();
            strCalory = cn.getStrCalory();
            strWater = cn.getStrWater();
            strDate = cn.getStrDate();
            List_User_PedomerDetail.add(new Model_User_Pedometer_data(strMobile, strStepCount, strDistanceCount, strCalory, strWater, strDate));
        }


    }
}