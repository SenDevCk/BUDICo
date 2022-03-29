package buidcopgrs.in.buidco.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import buidcopgrs.in.buidco.Adaptor.ReportAdapter;
import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.asyncTasks.GrivanceReportServiceService;
import buidcopgrs.in.buidco.entity.GravanceReportData;
import buidcopgrs.in.buidco.entity.UserDetails;
import buidcopgrs.in.buidco.interfaces.GrivanceReportBinder;

public class ReportCountwiseActivity extends AppCompatActivity {
    ListView list_report;
    TextView  text_report_not_found;
    Toolbar toolbar_report;
    LinearLayout ll_tag;
    double grand_total = 0.00;
    ArrayList<GravanceReportData> reportEntities;
    UserDetails userDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_countwise);
        toolbar_report = (Toolbar) findViewById(R.id.toolbar_report);
        toolbar_report.setTitle("Report");
        setSupportActionBar(toolbar_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        userDetails= CommonPref.getUserDetails(ReportCountwiseActivity.this);
        ll_tag = (LinearLayout) findViewById(R.id.ll_tag);
        text_report_not_found = (TextView) findViewById(R.id.text_report_not_found);
        list_report = (ListView) findViewById(R.id.list_report);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utiilties.isOnline(ReportCountwiseActivity.this) && reportEntities==null) {
            GrivanceReportServiceService.bindGrivanceReport(new GrivanceReportBinder() {
                @Override
                public void bindReport(ArrayList<GravanceReportData> gravanceReportDataArrayList) {
                    reportEntities=gravanceReportDataArrayList;
                    list_report.setVisibility(View.VISIBLE);
                    text_report_not_found.setVisibility(View.GONE);
                    list_report.setAdapter(new ReportAdapter(ReportCountwiseActivity.this,reportEntities));
                }

                @Override
                public void cancleReportBinding() {
                    reportEntities.clear();
                    list_report.invalidate();
                    list_report.setVisibility(View.GONE);
                    text_report_not_found.setVisibility(View.VISIBLE);
                }
            });
            new GrivanceReportServiceService(ReportCountwiseActivity.this,true).execute(userDetails.getZoneID().trim(),userDetails.getCommID().trim(),userDetails.getDistrictId().trim());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
