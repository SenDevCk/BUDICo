package buidcopgrs.in.buidco.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.asyncTasks.GrivanceReportServiceService;
import buidcopgrs.in.buidco.entity.GravanceReportData;
import buidcopgrs.in.buidco.interfaces.GrivanceReportBinder;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper mViewFlipper;
    private Context mContext;

    LinearLayout ll_Reg,ll_login,ll_grivane;

    private static String TAG = "MainActivity";
    PieChart pieChart;
    ArrayList<PieEntry> yEntrys = new ArrayList<>();
    ArrayList<String> xEntrys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        //sets auto flipping
        if (mViewFlipper!=null){
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(2000);
        mViewFlipper.startFlipping();
        }

        ll_Reg=(LinearLayout)findViewById(R.id.ll_Reg);
        ll_login=(LinearLayout)findViewById(R.id.ll_login);
        ll_grivane=(LinearLayout)findViewById(R.id.ll_grivane);


        pieChart = (PieChart) findViewById(R.id.idPieChart);

        //pieChart.setDescription(" ");
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Grievance");
        pieChart.setCenterTextSize(10);
        pieChart.setVisibility(View.GONE);


        //addDataSet();




        ll_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        ll_grivane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonPref.clearLog(MainActivity.this);
                Intent i = new Intent(MainActivity.this, ViewGrivanceList.class);
                startActivity(i);
            }
        });

        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (yEntrys.size()<=0){
            GrivanceReportServiceService.bindGrivanceReport(new GrivanceReportBinder() {
                @Override
                public void bindReport(ArrayList<GravanceReportData> gravanceReportDataArrayList) {
                    for (GravanceReportData griGravanceReportData:gravanceReportDataArrayList){
                        //yEntrys.add(new PieEntry(Float.parseFloat(griGravanceReportData.get_Total()),griGravanceReportData.get_GrievanceType()));
                        yEntrys.add(new PieEntry(Float.parseFloat(griGravanceReportData.get_Total()),griGravanceReportData));
                        xEntrys.add(griGravanceReportData.get_GrievanceType());
                        //create the data set
                        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Grievance Type Wise Records");
                        pieDataSet.setSliceSpace(2);
                        pieDataSet.setValueTextSize(12);

                        //add colors to dataset
                        ArrayList<Integer> colors = new ArrayList<>();
                        colors.add(Color.GRAY);
                        colors.add(Color.BLUE);
                        colors.add(Color.RED);
                        colors.add(Color.GREEN);
                        colors.add(Color.CYAN);
                        colors.add(Color.YELLOW);
                        colors.add(Color.MAGENTA);
                        colors.add(Color.DKGRAY);
                        colors.add(Color.WHITE);

                        pieDataSet.setColors(colors);

                        //add legend to chart
                        Legend legend = pieChart.getLegend();
                        legend.setForm(Legend.LegendForm.CIRCLE);
                        //legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

                        //create pie data object
                        PieData pieData = new PieData(pieDataSet);
                        pieChart.setData(pieData);
                        pieChart.invalidate();
                        pieChart.setVisibility(View.VISIBLE);
                        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @Override
                            public void onValueSelected(Entry e, Highlight h) {
                                Log.d(TAG, "onValueSelected: Value select from chart.");
                                Log.d(TAG, "onValueSelected: " + e.toString());
                                Log.d(TAG, "onValueSelected: " + h.toString());
                                GravanceReportData data=(GravanceReportData)e.getData();
                                AlertDialog alertDialog=new AlertDialog.Builder(MainActivity.this).create();
                                alertDialog.setMessage( data.get_GrievanceType()+"\n Total : " + data.get_Total()+"\n Pending : "+data.get_Pending()+"\n Resolved : "+data.get_Resolved()+"\n Rejected : "+data.get_Rejected());
                                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alertDialog.show();
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });
                    }
                }

                @Override
                public void cancleReportBinding() {
                    pieChart.setVisibility(View.GONE);
                }
            });
            new GrivanceReportServiceService(MainActivity.this,false).execute("0","0","0");
        }
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }

                }).create().show();

    }
}
