package com.sngs.swayam.temp.Fragment.Pedometer;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Yearly_Fragment extends Fragment {
    List<Model_User_Pedometer_data> Year_Of_List, GET_DB_Year_Of_List;
    DatabaseHandler db;
    TextView txtTotalCal, txtstep_per, txt_calory_burn, txtGlass, txtGlass_Remain, txtStep, txtDist, txtCal, txtavavrange_per;
    View v;
    int intcalori_minus = 1; //Step 100 minus 1 calories
    BarChart barChart;
    BarDataSet barDataSet1, barDataSet2;
    ArrayList barEntries;
    String[] days;
    String date;
    SharedPreferences pref;
    ProgressBar progressBar;
    float Total_Per_Progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragement_yearlystep, container, false);
        db = new DatabaseHandler(getContext());
        fun_Init();
        fun_Get_Year_List();
        fun_Assign();
        fun_Bar_Chart_Assign();
        fun_WHO_Avavrage();
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
        barChart = v.findViewById(R.id.idBarChart);
        txtTotalCal = v.findViewById(R.id.txtTotalCal);
        txt_calory_burn = v.findViewById(R.id.txt_calory_burn);
        txtGlass = v.findViewById(R.id.txtGlass);
        txtGlass_Remain = v.findViewById(R.id.txtGlass_Remain);
        txtStep = v.findViewById(R.id.txtStep);
        txtDist = v.findViewById(R.id.txtDist);
        txtCal = v.findViewById(R.id.txtCal);
        txtavavrange_per = v.findViewById(R.id.txtavavrange_per);
    }

    public void fun_Get_Year_List() {
        Year_Of_List = new ArrayList<>();
        days = new String[]{
                "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date Date1 = calendar.getTime();
        date = sdf.format(Date1);
        String[] part = date.split(" ");
        String year = part[2];
        for (int k = 0; k < days.length; k++) {
            if (days[k].equals("Jan")) {
                fun_Add_Value_Array("1 Jan " + year, "31 Jan " + year, "Jan");
            }
            if (days[k].equals("Feb")) {
                fun_Add_Value_Array("1 Feb " + year, "29 Feb " + year, "Feb");
            }
            if (days[k].equals("Mar")) {
                fun_Add_Value_Array("1 Mar " + year, "31 Mar " + year, "Mar");
            }
            if (days[k].equals("Apr")) {
                fun_Add_Value_Array("1 Apr " + year, "30 Apr " + year, "Apr");
            }
            if (days[k].equals("May")) {
                fun_Add_Value_Array("1 May " + year, "31 May " + year, "May");
            }
            if (days[k].equals("Jun")) {
                fun_Add_Value_Array("1 Jun " + year, "30 Jun " + year, "Jun");
            }
            if (days[k].equals("Jul")) {
                fun_Add_Value_Array("1 Jul " + year, "31 Jul " + year, "Jul");
            }
            if (days[k].equals("Aug")) {
                fun_Add_Value_Array("1 Aug " + year, "31 Aug " + year, "Aug");
            }
            if (days[k].equals("Sep")) {
                fun_Add_Value_Array("1 Sep " + year, "30 Sep " + year, "Sep");
            }
            if (days[k].equals("Oct")) {
                fun_Add_Value_Array("1 Oct " + year, "31 Oct " + year, "Oct");
            }
            if (days[k].equals("Nov")) {
                fun_Add_Value_Array("1 Nov " + year, "30 Nov " + year, "Nov");
            }
            if (days[k].equals("Dec")) {
                fun_Add_Value_Array("1 Dec " + year, "31 Dec " + year, "Dec");
            }
        }


    }

    public void fun_Add_Value_Array(String FirstDate, String LastDate, String MonthName) {
        GET_DB_Year_Of_List = new ArrayList<>();
        GET_DB_Year_Of_List = db.GET_User_Week_Of_list(FirstDate, LastDate);
        int totstep = 0, totalcal = 0, intGlass = 0, intDist = 0;
        String StrDate = null;
        for (int i = 0; i < GET_DB_Year_Of_List.size(); i++) {
            String[] strmonth = GET_DB_Year_Of_List.get(i).getStrDate().split(" ");
            StrDate = strmonth[1];
            System.out.println("YEAR" + GET_DB_Year_Of_List.get(i).getStrStepCount());
            totstep = totstep + Integer.parseInt(GET_DB_Year_Of_List.get(i).getStrStepCount());
            totalcal = totalcal + Integer.parseInt(GET_DB_Year_Of_List.get(i).getStrCalory());
            intGlass = intGlass + Integer.parseInt(GET_DB_Year_Of_List.get(i).getStrWater());
            intDist = intDist + Integer.parseInt(GET_DB_Year_Of_List.get(i).getStrCalory());
        }
        if (StrDate.equals(MonthName)) {
            Year_Of_List.add(new Model_User_Pedometer_data(Integer.toString(totstep), Integer.toString(totalcal), StrDate, Integer.toString(intGlass), Integer.toString(intDist)));
        }
    }

    public void fun_Assign() {
        int intStep = 0, intCal = 0, intGlass = 0;
        float intDist = 0;
        for (int i = 0; i < Year_Of_List.size(); i++) {
            intStep = intStep + Integer.parseInt(Year_Of_List.get(i).getStrStepCount());
            intCal = intCal + Integer.parseInt(Year_Of_List.get(i).getStrCalory());
            intGlass = intGlass + Integer.parseInt(Year_Of_List.get(i).getStrWater());
            intDist = intDist + Float.parseFloat(Year_Of_List.get(i).getStrDistanceCount());
            txtStep.setText(Integer.toString(intStep / 365));
            txtTotalCal.setText(Integer.toString(intCal));
            txtGlass.setText(Integer.toString(intGlass));
            String strintDist = String.format("%.2f", intDist / 365);
            txtDist.setText(strintDist);
            txtCal.setText(Float.toString(intCal / 365));
            txtGlass_Remain.setText(getResources().getString(R.string.Glass_Of_Water1) + " " + Integer.toString(Constant.TotalGlass_Of_Water * 7) + " " + getResources().getString(R.string.Glass_Of_Water2) + " " + Integer.toString(Constant.TotalGlass_Of_Water * 365 - intGlass));
            progressBar.setProgress(intStep / 365);
            progressBar.setMax(Fragment_Settings.DEFAULT_GOAL);
            int Per = intStep / 365 * 100 / Fragment_Settings.DEFAULT_GOAL;
            txtstep_per.setText(Integer.toString(Per) + "%");
            int burncaleries = intStep * intcalori_minus / 100;
            txt_calory_burn.setText("Total calories burn " + Integer.toString(burncaleries));
            pref = getContext().getApplicationContext().getSharedPreferences("Swayam Smart", 0); // 0 - for private mode
            SharedPreferences.Editor editors = pref.edit();
            editors.putString("Year_of_Step", Integer.toString(intStep / 365));
            editors.putString("Year_of_Cal", Integer.toString(intCal / 365));
            editors.commit();
            float Total_Of_3 = intStep + intDist + intCal / 365;
            float per = Total_Of_3;
            float Total_of_WHO = Constant.WHO_Step + Constant.WHO_Distance + Constant.WHO_Calory;
            float WHO_per = Total_of_WHO;
            Total_Per_Progress = per * 100 / WHO_per;
            String strTotal_Per_Progress = String.format("%.2f", Total_Per_Progress);
            txtavavrange_per.setText(strTotal_Per_Progress + " %");
        }
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

    private ArrayList<BarEntry> getBarEntriesOne() {
        barEntries = new ArrayList<>();
        for (int i = 0; i < days.length; i++) {
            String str = days[i].toString();
            for (int j = 0; j < Year_Of_List.size(); j++) {
                System.out.println("strmonth_MONTH" + str + ", " + Year_Of_List.get(j).getStrDate());
                String month = Year_Of_List.get(j).getStrDate();
                if (str.equals(month)) {
                    barEntries.add(new BarEntry(i, Float.parseFloat(Year_Of_List.get(j).getStrStepCount())));
                }
            }
            barEntries.add(new BarEntry(i, 0));
        }
        return barEntries;
    }

    private ArrayList<BarEntry> getBarEntriesTwo() {
        barEntries = new ArrayList<>();

        boolean barEnter = false;
        for (int i = 0; i < 12; i++) {
            String str = days[i].toString();
            for (int j = 0; j < Year_Of_List.size(); j++) {
                System.out.println("strmonth_MONTH" + str + ", " + Year_Of_List.get(j).getStrDate());
                String month = Year_Of_List.get(j).getStrDate();
                if (str.equals(month)) {
                    barEnter = true;
                    barEntries.add(new BarEntry(i, Float.parseFloat(Year_Of_List.get(j).getStrCalory())));
                    barEnter = false;
                }
            }
            if (barEnter == false) {
                barEntries.add(new BarEntry(i, 0));
            }
        }

        return barEntries;
    }
}