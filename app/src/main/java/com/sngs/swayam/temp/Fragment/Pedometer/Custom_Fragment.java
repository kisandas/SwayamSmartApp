package com.sngs.swayam.temp.Fragment.Pedometer;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Custom_Fragment extends Fragment {
    View v;
    List<Model_User_Pedometer_data> Month_Of_List;
    DatabaseHandler db;
    TextView txtTotalCal, txt_calory_burn, txtGlass, txtGlass_Remain, txtStep, txtDist, txtCal, txtstep_per;
    int intcalori_minus = 1;
    BarChart barChart;
    BarDataSet barDataSet1, barDataSet2;
    ArrayList barEntries;
    ArrayList<String> days;
    SharedPreferences pref;
    TextView txtavavrange_per;
    ProgressBar progressBar;
    float Total_Per_Progress;
    LinearLayout linCustomView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragement_customstep, container, false);
        linCustomView = v.findViewById(R.id.linCustomView);
        Button btnselect = v.findViewById(R.id.btnselect);
        db = new DatabaseHandler(getContext());
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog mDialog;
                mDialog = new Dialog(getActivity());
                mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mDialog.setContentView(R.layout.dialog_calender);
                mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final CalendarPickerView calendar_view;
                calendar_view = (CalendarPickerView) mDialog.findViewById(R.id.calendar_view);
                Calendar nextYear = Calendar.getInstance();
                Calendar maxdate = Calendar.getInstance();
                nextYear.add(Calendar.YEAR, -1);
                maxdate.add(Calendar.DATE, 1);
                Date today = new Date();
                System.out.println("CUSTOM_maxdate" + maxdate + ",," + nextYear.getTime());
                calendar_view.init(nextYear.getTime(), maxdate.getTime())
                        .inMode(CalendarPickerView.SelectionMode.RANGE);
                calendar_view.scrollToDate(today);
                Button btn_show_dates = (Button) mDialog.findViewById(R.id.btn_show_dates);
                btn_show_dates.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Month_Of_List = new ArrayList<>();
                        int Last = calendar_view.getSelectedDates().size() - 1;
                        String dtStart = calendar_view.getSelectedDates().get(0).toString();
                        String dtlast = calendar_view.getSelectedDates().get(Last).toString();
                        SimpleDateFormat fmt = new SimpleDateFormat("dd MMM yyyy");
                        String firstdate = fmt.format(Date.parse(dtStart));
                        String lastdate = fmt.format(Date.parse(dtlast));
                        for (int i = 0; i < calendar_view.getSelectedDates().size(); i++) {
                            Month_Of_List = db.GET_Custom_Of_list(firstdate, lastdate);
                        }
                        for (int i = 0; i < Month_Of_List.size(); i++) {
                            System.out.println("Custom_Of_List" + Month_Of_List.get(i).getStrStepCount());
                        }
                        days = new ArrayList<>();
                        if (calendar_view.getSelectedDates().size() == 0) {
                        } else {
                            for (int i = 0; i < calendar_view.getSelectedDates().size(); i++) {
                                String string = calendar_view.getSelectedDates().get(i).toString();
                                String strDate = fmt.format(Date.parse(string));
                                days.add(strDate);
                            }
                            linCustomView.setVisibility(View.VISIBLE);
                            fun_Init();
                            fun_Assign();
                            fun_Bar_Chart_Assign();
                            fun_WHO_Avavrage();
                        }

                        mDialog.dismiss();

                    }
                });
                mDialog.show();
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
        int intStep = 0, intCal = 0, intGlass = 0;
        float intDist = 0;
        for (int i = 0; i < Month_Of_List.size(); i++) {
            intStep = intStep + Integer.parseInt(Month_Of_List.get(i).getStrStepCount());
            intCal = intCal + Integer.parseInt(Month_Of_List.get(i).getStrCalory());
            intGlass = intGlass + Integer.parseInt(Month_Of_List.get(i).getStrWater());
            intDist = intDist + Float.parseFloat(Month_Of_List.get(i).getStrDistanceCount());
            txtStep.setText(Integer.toString(intStep / Month_Of_List.size()));
            txtTotalCal.setText(Integer.toString(intCal));
            txtGlass.setText(Integer.toString(intGlass));
            String strintDist = String.format("%.2f", intDist / Month_Of_List.size());
            txtDist.setText(strintDist);
            txtCal.setText(Float.toString(intCal / Month_Of_List.size()));
            txtGlass_Remain.setText(getResources().getString(R.string.Glass_Of_Water1) + " " + Integer.toString(Constant.TotalGlass_Of_Water * Month_Of_List.size()) + " " + getResources().getString(R.string.Glass_Of_Water2) + " " + Integer.toString(Constant.TotalGlass_Of_Water * Month_Of_List.size() - intGlass));
            progressBar.setProgress(intStep / Month_Of_List.size());
            progressBar.setMax(Fragment_Settings.DEFAULT_GOAL);
            int Per = intStep / Month_Of_List.size() * 100 / Fragment_Settings.DEFAULT_GOAL;
            txtstep_per.setText(Integer.toString(Per) + "%");
            int burncaleries = intStep * intcalori_minus / 100;
            txt_calory_burn.setText("Total calories burn " + Integer.toString(burncaleries));
            pref = getContext().getApplicationContext().getSharedPreferences("Swayam Smart", 0); // 0 - for private mode
            SharedPreferences.Editor editors = pref.edit();
            editors.putString("Month_of_Step", Integer.toString(intStep / Month_Of_List.size()));
            editors.putString("Month_of_Cal", Integer.toString(intCal / Month_Of_List.size()));
            editors.commit();
            float Total_Of_3 = intStep + intDist + intCal / Month_Of_List.size();
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
        boolean barEnter = false;
        System.out.println("SIZE" + days.size() + ", " + Month_Of_List.size());
        for (int i = 0; i < days.size(); i++) {
            String strDATE = days.get(i).toString();
            for (int j = 0; j < Month_Of_List.size(); j++) {
                if (strDATE.equals(Month_Of_List.get(j).getStrDate())) {
                    barEnter = true;
                    barEntries.add(new BarEntry(i, Float.parseFloat(Month_Of_List.get(j).getStrStepCount())));
                }
            }
            if (barEnter == false) {
                barEntries.add(new BarEntry(i, 0));
            }

        }
        return barEntries;
    }

    private ArrayList<BarEntry> getBarEntriesTwo() {
        barEntries = new ArrayList<>();
        boolean barEnter = false;
        System.out.println("SIZE" + days.size() + ", " + Month_Of_List.size());
        for (int i = 0; i < days.size(); i++) {
            String strDATE = days.get(i).toString();
            for (int j = 0; j < Month_Of_List.size(); j++) {
                if (strDATE.equals(Month_Of_List.get(j).getStrDate())) {
                    barEnter = true;
                    barEntries.add(new BarEntry(i, Float.parseFloat(Month_Of_List.get(j).getStrCalory())));
                }
            }
            if (barEnter == false) {
                barEntries.add(new BarEntry(i, 0));
            }

        }
        return barEntries;
    }
}
