package com.sngs.swayam.temp.Fragment.Pedometer;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.sngs.swayam.temp.Constant;
import com.sngs.swayam.temp.DataBase.DatabaseHandler;
import com.sngs.swayam.temp.Model.Model_User_Pedometer_data;
import com.sngs.swayam.temp.R;
import com.sngs.swayam.temp.pademeter.ui.Fragment_Settings;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Monthly_Fragment extends Fragment {
    TabLayout tabLayout;
    View v;

    List<Model_User_Pedometer_data> Finl_Month_Of_List;
    TextView txtTotalCal, txt_calory_burn, txtGlass, txtGlass_Remain, txtStep, txtDist, txtCal, txtstep_per;
    int intcalori_minus = 1;
    BarChart barChart;
    BarDataSet barDataSet1, barDataSet2;
    ArrayList barEntries;
    ArrayList<String> days;
    ProgressBar progressBar;
    SharedPreferences pref;
    TextView txtavavrange_per;
    float Total_Per_Progress = 0;
    int intStep = 0, intCal = 0, intGlass = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragement_monthlystep, container, false);
        tabLayout = v.findViewById(R.id.tabLayout);

        Calendar c = Calendar.getInstance();
        final SimpleDateFormat df = new SimpleDateFormat("MMM");
        int Intmonth = 0;
        for (int i = 0; i < 12; i++) {
            c.set(Calendar.MONTH, i);
            String month_name = df.format(c.getTime());
            tabLayout.addTab(tabLayout.newTab().setText(month_name));
            Date date = new Date();
            String strMonth = df.format(date);
            if (strMonth.equals(month_name)) {
                Intmonth = i;
            }

        }


        final int finalIntmonth = Intmonth;
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        tabLayout.getTabAt(finalIntmonth).select();
                    }
                }, 10);
        fun_Init();
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
//        viewPager.setAdapter(viewPagerAdapter);
////        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//            }
//        });
//

//        tabLayout.setScrollPosition(5, 0f, true);

//        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, 1);
//        Date Date1 = calendar.getTime();
//        FirstDate = sdf.format(Date1);
//        String[] part = FirstDate.split(" ");
        final String year = "2021";
//        final String Month = part[1];
//        for (int i = 0; i < 12; i++) {
//            String TabString = tabLayout.getTabAt(i).getText().toString();
//            if (TabString.equals(Month)) {
//                TabLayout.Tab tab = tabLayout.getTabAt(i);
//                tab.select();
//                tabLayout.setScrollPosition(i, 0f, true);
//                fun_Add_Value_Array("01 " + Month + " " + year, "31 " + Month + " " + year, Month);
//            }
//        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        fun_Add_Value_Array("1 Jan " + year, "31 Jan " + year, "Jan");
                        break;
                    case 1:
                        fun_Add_Value_Array("1 Feb " + year, "29 Feb " + year, "Feb");
                        break;
                    case 2:
                        fun_Add_Value_Array("1 Mar " + year, "31 Mar " + year, "Mar");
                        break;
                    case 3:
                        fun_Add_Value_Array("1 Apr " + year, "30 Apr " + year, "Apr");
                        break;
                    case 4:
                        fun_Add_Value_Array("1 May " + year, "31 May " + year, "May");
                        break;
                    case 5:
                        fun_Add_Value_Array("1 Jun " + year, "30 Jun " + year, "Jun");
                        break;
                    case 6:
                        fun_Add_Value_Array("1 Jul " + year, "31 Jul " + year, "Jul");
                        break;
                    case 7:
                        fun_Add_Value_Array("1 Aug " + year, "31 Aug " + year, "Aug");
                        break;
                    case 8:
                        fun_Add_Value_Array("1 Sep " + year, "30 Sep " + year, "Sep");
                        break;
                    case 9:
                        fun_Add_Value_Array("1 Oct " + year, "31 Oct " + year, "Oct");
                        break;
                    case 10:
                        fun_Add_Value_Array("1 Nov " + year, "30 Nov " + year, "Nov");
                        break;
                    case 11:
                        fun_Add_Value_Array("1 Dec " + year, "31 Dec " + year, "Dec");
                        break;

                }
            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return v;
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

    public void fun_Init() {
        progressBar = v.findViewById(R.id.pro_step);
        txtstep_per = v.findViewById(R.id.txtstep_per);
        txtavavrange_per = v.findViewById(R.id.txtavavrange_per);
        barChart = v.findViewById(R.id.idBarChart);
        txtTotalCal = v.findViewById(R.id.txtTotalCal);
        txt_calory_burn = v.findViewById(R.id.txt_calory_burn);
        txtGlass = v.findViewById(R.id.txtGlass);
        txtGlass_Remain = v.findViewById(R.id.txtGlass_Remain);
        txtStep = v.findViewById(R.id.txtStep);
        txtDist = v.findViewById(R.id.txtDist);
        txtCal = v.findViewById(R.id.txtCal);

    }


    private void fun_Add_Value_Array(String s, String s1, String dec) {
        DatabaseHandler db = new DatabaseHandler(getContext());
        List<Model_User_Pedometer_data> Month_Of_List = new ArrayList<>();
        Finl_Month_Of_List = new ArrayList<>();
        Month_Of_List = db.GET_Custom_Of_list(s, s1);
        System.out.println("GET_Month_Of_List=" + Month_Of_List.size() + s + s1);

        days = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            String string = "";
            if (i <= 9) {
                string = "0" + i + " " + dec;
            } else {
                string = i + " " + dec;
            }
            days.add(string);
        }
        for (int i = 0; i < days.size(); i++) {
            String sDate = days.get(i).toString() + " 2021";
            Finl_Month_Of_List.add(new Model_User_Pedometer_data("0", "0", sDate, "0", "0"));
            for (int j = 0; j < Month_Of_List.size(); j++) {
                String strStepCount, strCalory, StrDate, strWater, strDistanceCount;
                strStepCount = Month_Of_List.get(j).getStrStepCount();
                strCalory = Month_Of_List.get(j).getStrCalory();
                StrDate = Month_Of_List.get(j).getStrDate();
                strWater = Month_Of_List.get(j).getStrWater();
                strDistanceCount = Month_Of_List.get(j).getStrDistanceCount();
                System.out.println("GET_Finl_Month_Of_List" + Finl_Month_Of_List.get(i).getStrDate() + StrDate);
                if (Finl_Month_Of_List.get(i).getStrDate().equals(StrDate)) {
                    Finl_Month_Of_List.get(i).setStrCalory(strCalory);
                    Finl_Month_Of_List.get(i).setStrDistanceCount(strDistanceCount);
                    Finl_Month_Of_List.get(i).setStrStepCount(strStepCount);
                    Finl_Month_Of_List.get(i).setStrWater(strWater);
                }
            }
        }
        for (int i = 0; i < Finl_Month_Of_List.size(); i++) {
//            System.out.println("GET_Finl_Month_Of_List" + Finl_Month_Of_List.get(i).getStrDate());
        }
        fun_Assign();
        fun_Bar_Chart_Assign();
        fun_WHO_Avavrage();

    }

    public void fun_Bar_Chart_Assign() {
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

    public void fun_Assign() {
        float intDist = 0;
        intStep = 0;
        intCal = 0;
        intGlass = 0;
        txtStep.setText("");
        txtTotalCal.setText("");
        txtGlass.setText("");
        txtDist.setText("");
        txtCal.setText("");
        txtGlass_Remain.setText(getResources().getString(R.string.Glass_Of_Water1) + " " + Integer.toString(Constant.TotalGlass_Of_Water * 30) + " " + getResources().getString(R.string.Glass_Of_Water2) + " 0 ");
        progressBar.setProgress(0);
        txtstep_per.setText("");
        txt_calory_burn.setText("Total calories burn " + Integer.toString(0));
        txtavavrange_per.setText("0 %");
        for (int i = 0; i < Finl_Month_Of_List.size(); i++) {
            intStep = intStep + Integer.parseInt(Finl_Month_Of_List.get(i).getStrStepCount());
            intCal = intCal + Integer.parseInt(Finl_Month_Of_List.get(i).getStrCalory());
            intGlass = intGlass + Integer.parseInt(Finl_Month_Of_List.get(i).getStrWater());
            intDist = intDist + Float.parseFloat(Finl_Month_Of_List.get(i).getStrDistanceCount());
            txtStep.setText(Integer.toString(intStep / 30));
            txtTotalCal.setText(Integer.toString(intCal));
            txtGlass.setText(Integer.toString(intGlass));
            String strintDist = String.format("%.2f", intDist / 30);
            txtDist.setText(strintDist);
            txtCal.setText(Float.toString(intCal / 30));
            txtGlass_Remain.setText(getResources().getString(R.string.Glass_Of_Water1) + " " + Integer.toString(Constant.TotalGlass_Of_Water * 30) + " " + getResources().getString(R.string.Glass_Of_Water2) + " " + Integer.toString(Constant.TotalGlass_Of_Water * 30 - intGlass));
            progressBar.setProgress(intStep / 30);
            progressBar.setMax(Fragment_Settings.DEFAULT_GOAL);
            int Per = intStep / 30 * 100 / Fragment_Settings.DEFAULT_GOAL;
            txtstep_per.setText(Integer.toString(Per) + "%");
            int burncaleries = intStep * intcalori_minus / 100;
            txt_calory_burn.setText("Total calories burn " + Integer.toString(burncaleries));
            pref = getContext().getApplicationContext().getSharedPreferences("Swayam Smart", 0); // 0 - for private mode
            SharedPreferences.Editor editors = pref.edit();
            editors.putString("Month_of_Step", Integer.toString(intStep / 30));
            editors.putString("Month_of_Cal", Integer.toString(intCal / 30));
            editors.commit();
            float Total_Of_3 = intStep + intDist + intCal / 30;
            float per = Total_Of_3;
            float Total_of_WHO = Constant.WHO_Step + Constant.WHO_Distance + Constant.WHO_Calory;
            float WHO_per = Total_of_WHO;
            Total_Per_Progress = per * 100 / WHO_per;
            String strTotal_Per_Progress = String.format("%.2f", Total_Per_Progress);
            txtavavrange_per.setText(strTotal_Per_Progress + " %");
        }
    }

    private ArrayList<BarEntry> getBarEntriesOne() {
        barEntries = new ArrayList<>();
        for (int i = 0; i < Finl_Month_Of_List.size(); i++) {
            String Count = Finl_Month_Of_List.get(i).getStrStepCount();
            barEntries.add(new BarEntry(i, Float.parseFloat(Count)));
        }
        return barEntries;
    }

    private ArrayList<BarEntry> getBarEntriesTwo() {
        barEntries = new ArrayList<>();
        for (int i = 0; i < Finl_Month_Of_List.size(); i++) {
            String Count = Finl_Month_Of_List.get(i).getStrCalory();
            barEntries.add(new BarEntry(i, Float.parseFloat(Count)));
        }
        return barEntries;
    }
}
