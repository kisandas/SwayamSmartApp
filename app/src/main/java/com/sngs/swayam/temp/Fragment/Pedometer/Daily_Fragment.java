package com.sngs.swayam.temp.Fragment.Pedometer;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.sngs.swayam.temp.Activity.HomeActivity;
import com.sngs.swayam.temp.Adapter.Calery.Adapter_Calory_Category;
import com.sngs.swayam.temp.Adapter.Calery.Adapter_calery;
import com.sngs.swayam.temp.BuildConfig;
import com.sngs.swayam.temp.Constant;
import com.sngs.swayam.temp.DataBase.DatabaseHandler;
import com.sngs.swayam.temp.Model.Model_Calories;
import com.sngs.swayam.temp.Model.Model_User_Pedometer_data;
import com.sngs.swayam.temp.R;
import com.sngs.swayam.temp.UI.RelativeRadioGroup;
import com.sngs.swayam.temp.pademeter.Database;
import com.sngs.swayam.temp.pademeter.ui.Fragment_Settings;
import com.sngs.swayam.temp.pademeter.util.Logger;
import com.sngs.swayam.temp.pademeter.util.Util;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Daily_Fragment extends Fragment implements SensorEventListener {
    private TextView Txtdate, stepsView, txtGlass, txtGlass_Remain, txt_calory_burn, txtTotalCal, txtstep_per, txtStep, txtDist, txtCal;
    private PieModel sliceGoal, sliceCurrent;
    private PieChart pg;
    View v;
    private int todayOffset, total_start, goal, since_boot, total_days;
    public final static NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
    private boolean showSteps = true;
    ProgressBar progressBar;
    Button btnAdd_Cal, btnAddGlass;
    DatabaseHandler db;
    private ArrayList<Model_Calories> List_calories;
    private ArrayList<Model_Calories> List_calories_category = new ArrayList<>();
    String T_ItemName, T_Qty, T_Calories;
    String str_category;
    SharedPreferences pref;
    int intcalori_minus = 0; //Step 100 minus 1 calories
    String mobile, Today_Date;
    int Subcat_Value = 1;
    RadioButton radio_BF, radio_Lunch, radio_Snacks, radio_Dinner;
    Spinner spin_cal_category, spinner_cal_itemname;
    TextView txt_Quntity_with, txtQuntity, txtCalories;
    boolean isChecking = true;
    int mCheckedId = R.id.radio_BF;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragement_dailystep, container, false);
        fun_init();
        fun_User_PedomerDetail();
        fun_Progress_Assign();
        btnAdd_Cal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Show_Dialogue_Add_cal();
            }
        });
        btnAddGlass.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Show_Dialogue_Add_Water();
            }
        });
        return v;
    }

    public void fun_init() {
        db = new DatabaseHandler(getContext());
        pref = getActivity().getApplicationContext().getSharedPreferences("Swayam Smart", 0); // 0 - for private mode
        mobile = pref.getString("User_no", "");
        btnAdd_Cal = v.findViewById(R.id.btnAdd_Cal);
        txt_calory_burn = v.findViewById(R.id.txt_calory_burn);
        btnAddGlass = v.findViewById(R.id.btnAddGlass);
        Txtdate = (TextView) v.findViewById(R.id.Txtdate);
        stepsView = (TextView) v.findViewById(R.id.steps);
        txtGlass = (TextView) v.findViewById(R.id.txtGlass);
        txtDist = (TextView) v.findViewById(R.id.txtDist);
        txtCal = (TextView) v.findViewById(R.id.txtCal);
        txtStep = (TextView) v.findViewById(R.id.txtStep);
        txtstep_per = (TextView) v.findViewById(R.id.txtstep_per);
        txtTotalCal = (TextView) v.findViewById(R.id.txtTotalCal);
        txtGlass_Remain = (TextView) v.findViewById(R.id.txtGlass_Remain);
        progressBar = v.findViewById(R.id.pro_step);
        pg = (PieChart) v.findViewById(R.id.graph);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -0);
        Date Date7 = calendar.getTime();
        Today_Date = sdf.format(Date7);
        sdf = new SimpleDateFormat("EEEE");
        calendar.setTime(Date7);
        String Day7 = sdf.format(Date7);
        Txtdate.setText(Today_Date + " " + Day7);
        List<Model_User_Pedometer_data> listdate = db.GET_TodayDate(Today_Date);
        if (listdate.size() == 0) {
            db.Add_User_Pedometer_Detail(new Model_User_Pedometer_data(mobile, "0", "0", "0", "0", Today_Date));
        }
        txtGlass_Remain.setText(getResources().getString(R.string.Glass_Of_Water1) + " " + Integer.toString(Constant.Glass_Of_Water) + " " + getResources().getString(R.string.Glass_Of_Water2) + " " + Integer.toString(Constant.TotalGlass_Of_Water - Constant.Glass_Of_Water));


    }

    public void fun_User_PedomerDetail() {
        List<Model_User_Pedometer_data> model = db.GET_User_Pedometer_Detail();
        for (Model_User_Pedometer_data cn : model) {
            txtGlass.setText(cn.getStrWater());
            txtCal.setText(cn.getStrCalory());
            txtTotalCal.setText(cn.getStrCalory());
            txtStep.setText(cn.getStrStepCount());
            txtDist.setText(cn.getStrDistanceCount());
        }
    }

    public void fun_Progress_Assign() {
        sliceCurrent = new PieModel("", 0, Color.parseColor("#4CAF50"));
        pg.addPieSlice(sliceCurrent);
        sliceGoal = new PieModel("", Fragment_Settings.DEFAULT_GOAL, Color.parseColor("#FF7C1D"));
        pg.addPieSlice(sliceGoal);
        pg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                showSteps = !showSteps;
                stepsDistanceChanged();
            }
        });
        pg.setDrawValueInPie(false);
        pg.setUsePieRotation(true);
        pg.startAnimation();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Show_Dialogue_Add_Water() {
        final Dialog mDialog;
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_water);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        final EditText editGlass = mDialog.findViewById(R.id.txtGlass);
        Button btnADD_Glass = (Button) mDialog.findViewById(R.id.btnADD_Glass);
        btnADD_Glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.Glass_Of_Water == 0) {
                    Constant.Glass_Of_Water = Integer.parseInt(editGlass.getText().toString());
                } else {
                    Constant.Glass_Of_Water = Constant.Glass_Of_Water + Integer.parseInt(editGlass.getText().toString());
                }
                txtGlass.setText(Integer.toString(Constant.Glass_Of_Water));
                txtGlass_Remain.setText(getResources().getString(R.string.Glass_Of_Water1) + " " + Integer.toString(Constant.TotalGlass_Of_Water) + " " + getResources().getString(R.string.Glass_Of_Water2) + Integer.toString(Constant.TotalGlass_Of_Water - Constant.Glass_Of_Water));
                db.UPDATE_Today_Water(Today_Date, Integer.toString(Constant.Glass_Of_Water));
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Show_Dialogue_Add_cal() {
        final Dialog mDialog;
        mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_add_kal);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        spin_cal_category = (Spinner) mDialog.findViewById(R.id.spin_cal_category);
        spinner_cal_itemname = (Spinner) mDialog.findViewById(R.id.spinner_cal_itemname);
        txt_Quntity_with = (TextView) mDialog.findViewById(R.id.txt_Quntity_with);
        txtQuntity = (TextView) mDialog.findViewById(R.id.txtQuntity);
        txtCalories = (TextView) mDialog.findViewById(R.id.txtCalories);
        Button btnADD = (Button) mDialog.findViewById(R.id.btnADD);
        final RelativeRadioGroup RG1 = (RelativeRadioGroup) mDialog.findViewById(R.id.RG1);
        fun_Show_Cal_List();
        RG1.setOnCheckedChangeListener(new RelativeRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RelativeRadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio_BF:
                        fun_Show_Cal_List();
                        break;
                    case R.id.radio_Lunch:
                        fun_Show_Cal_List();
                        break;
                    case R.id.radio_Snacks:
                        fun_Show_Cal_List();
                        break;
                    case R.id.radio_Dinner:
                        fun_Show_Cal_List();
                        break;
                }
            }
        });


        btnADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (Constant.TotalCalory == 0) {
                        Constant.TotalCalory = Integer.parseInt(txtCalories.getText().toString());
                        txtTotalCal.setText(Integer.toString(Constant.TotalCalory));
                        txtCal.setText(Integer.toString(Constant.TotalCalory));
                    } else {
                        Constant.TotalCalory = Constant.TotalCalory + Integer.parseInt(txtCalories.getText().toString());
                        txtTotalCal.setText(Integer.toString(Constant.TotalCalory));
                        txtCal.setText(Integer.toString(Constant.TotalCalory));
                        System.out.println("DAILY__" + Constant.TotalCalory);

                    }
                    int burncaleries = Integer.parseInt(txtStep.getText().toString()) * intcalori_minus / 100;
                    txt_calory_burn.setText("Total calories burn" + Integer.toString(burncaleries));
                    db.UPDATE_Today_Calory(Today_Date, Integer.toString(Constant.TotalCalory));
                } catch (Exception e) {
                }
                mDialog.dismiss();
            }
        });

        mDialog.show();

    }

    public void fun_Show_Calories(String Categoryname) {
        List<Model_Calories> calories = db.GET_CALORIES_CATEGORY_DETAIL(Categoryname);
        List_calories = new ArrayList<>();
        for (Model_Calories cn : calories) {
            T_ItemName = cn.getT_ItemName();
            T_Qty = cn.getT_Qty();
            T_Calories = cn.getT_Calories();
            List_calories.add(new Model_Calories(T_ItemName, T_Qty, T_Calories, ""));
        }
    }

    public void fun_Show_Category() {
        List<Model_Calories> calories = db.GET_CALORIES_CATEGORY();
        for (Model_Calories cn : calories) {
            str_category = cn.getT_Categories();
            List_calories_category.add(new Model_Calories(str_category));
        }
    }

    public void fun_Show_Cal_List() {
        fun_Show_Category();
        Adapter_Calory_Category adapter = new Adapter_Calory_Category(getActivity(), List_calories_category);
        spin_cal_category.setAdapter(adapter);
        try {
            txtQuntity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count > 0) {
                        int multiply = Integer.parseInt(txtQuntity.getText().toString()) * Subcat_Value;
                        txtCalories.setText(Integer.toString(multiply));
                    } else {
                        txtCalories.setText("0");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } catch (Exception e) {
        }
        spin_cal_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                fun_Show_Calories(List_calories_category.get(position).getT_Categories());
                Adapter_calery adapter2 = new Adapter_calery(getActivity(), List_calories);
                spinner_cal_itemname.setAdapter(adapter2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_cal_itemname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String QTY = null;
                String QTY_With = null;
                try {
                    String str = List_calories.get(position).getT_Qty();
                    String[] part = str.split(" ");
                    QTY = part[0];
                    QTY_With = part[1];
                } catch (Exception e) {

                }
                try {
                    if (QTY_With.isEmpty()) {
                        txt_Quntity_with.setText("Peice");
                    } else {
                        txt_Quntity_with.setText(QTY_With);

                    }
                } catch (Exception e) {

                }
                txtQuntity.setText(QTY);
                Subcat_Value = Integer.parseInt(List_calories.get(position).getT_Calories());
                txtCalories.setText(Integer.toString(Subcat_Value));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Database db = Database.getInstance(getActivity());
        if (BuildConfig.DEBUG) db.logState();
        todayOffset = db.getSteps(Util.getToday());
        SharedPreferences prefs =
                getActivity().getSharedPreferences("pedometer", MODE_PRIVATE);
        goal = prefs.getInt("goal", Fragment_Settings.DEFAULT_GOAL);
        since_boot = db.getCurrentSteps();
        int pauseDifference = since_boot - prefs.getInt("pauseCount", since_boot);
        SensorManager sm = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor == null) {
            new AlertDialog.Builder(getActivity()).setTitle(R.string.no_sensor)
                    .setMessage(R.string.no_sensor_explain)
                    .setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(final DialogInterface dialogInterface) {
                            getActivity().finish();
                        }
                    }).setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(final DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        } else {
            sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI, 0);
        }
        since_boot -= pauseDifference;
        total_start = db.getTotalWithoutToday();
        total_days = db.getDays();
        db.close();
        stepsDistanceChanged();
    }


    private void stepsDistanceChanged() {

        if (showSteps) {
            ((TextView) getView().findViewById(R.id.unit)).setText(getString(R.string.steps));
        } else {
            String unit = getActivity().getSharedPreferences("pedometer", MODE_PRIVATE)
                    .getString("stepsize_unit", Fragment_Settings.DEFAULT_STEP_UNIT);
            if (unit.equals("cm")) {
                unit = "km";
            } else {
                unit = "mi";
            }
            ((TextView) getView().findViewById(R.id.unit)).setText(unit);
        }

        updatePie();
        UPDATE_STEP_DIST();


    }


    @Override
    public void onPause() {
        super.onPause();
        try {
            SensorManager sm =
                    (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
            sm.unregisterListener(this);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) Logger.log(e);
        }
        Database db = Database.getInstance(getActivity());
        db.saveCurrentSteps(since_boot);
        db.close();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }


    @Override
    public void onAccuracyChanged(final Sensor sensor, int accuracy) {
        // won't happen
    }

    @Override
    public void onSensorChanged(final SensorEvent event) {
        if (BuildConfig.DEBUG) Logger.log(
                "UI - sensorChanged | todayOffset: " + todayOffset + " since boot: " +
                        event.values[0]);
        if (event.values[0] > Integer.MAX_VALUE || event.values[0] == 0) {
            return;
        }
        if (todayOffset == Integer.MIN_VALUE) {
            todayOffset = -(int) event.values[0];
            Database db = Database.getInstance(getActivity());
            db.insertNewDay(Util.getToday(), (int) event.values[0]);
            db.close();
        }
        since_boot = (int) event.values[0];
        try {
            updatePie();
            UPDATE_STEP_DIST();
        } catch (Exception e) {

        }
    }

    private void updatePie() {
        if (BuildConfig.DEBUG) Logger.log("UI - update steps: " + since_boot);
        int steps_today = Math.max(todayOffset + since_boot, 0);
        sliceCurrent.setValue(steps_today);
        if (goal - steps_today > 0) {
            // goal not reached yet
            if (pg.getData().size() == 1) {
                pg.addPieSlice(sliceGoal);
            }
            sliceGoal.setValue(goal - steps_today);
        } else {
            // goal reached
            pg.clearChart();
            pg.addPieSlice(sliceCurrent);
        }
        pg.update();
        if (showSteps) {
            stepsView.setText(formatter.format(steps_today));

        } else {
            SharedPreferences prefs =
                    getActivity().getSharedPreferences("pedometer", MODE_PRIVATE);
            float stepsize = prefs.getFloat("stepsize_value", Fragment_Settings.DEFAULT_STEP_SIZE);
            float distance_today = steps_today * stepsize;
            float distance_total = (total_start + steps_today) * stepsize;
            if (prefs.getString("stepsize_unit", Fragment_Settings.DEFAULT_STEP_UNIT)
                    .equals("cm")) {
                distance_today /= 100000;
            } else {
                distance_today /= 5280;
            }
            stepsView.setText(formatter.format(distance_today));
        }

    }

    private void UPDATE_STEP_DIST() {
        if (BuildConfig.DEBUG) Logger.log("UI - update steps: " + since_boot);
        int steps_today = Math.max(todayOffset + since_boot, 0);
        sliceCurrent.setValue(steps_today);
        txtStep.setText(formatter.format(steps_today));
        progressBar.setProgress(steps_today);
        progressBar.setMax(Fragment_Settings.DEFAULT_GOAL);
        int Per = steps_today * 100 / Fragment_Settings.DEFAULT_GOAL;
        txtstep_per.setText(Integer.toString(Per) + "%");
        SharedPreferences prefs =
                getActivity().getSharedPreferences("pedometer", MODE_PRIVATE);
        float stepsize = prefs.getFloat("stepsize_value", Fragment_Settings.DEFAULT_STEP_SIZE);
        float distance_today = steps_today * stepsize;
        float distance_total = (total_start + steps_today) * stepsize;
        if (prefs.getString("stepsize_unit", Fragment_Settings.DEFAULT_STEP_UNIT)
                .equals("cm")) {
            distance_today /= 100000;
        } else {
            distance_today /= 5280;
        }
        txtDist.setText(formatter.format(distance_today));
        int burncaleries = steps_today * intcalori_minus / 100;
        txt_calory_burn.setText("Total calories burn " + Integer.toString(burncaleries));
        db.UPDATE_Today_StepCount(Today_Date, formatter.format(steps_today));
        db.UPDATE_Today_Distance(Today_Date, formatter.format(distance_today));


    }


}