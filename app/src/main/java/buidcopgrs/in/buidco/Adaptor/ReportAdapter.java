package buidcopgrs.in.buidco.Adaptor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.entity.GravanceReportData;

public class ReportAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<GravanceReportData> reportEntities;
    LayoutInflater layoutInflater;
    String save_date;
    public ReportAdapter(Activity activity,  ArrayList<GravanceReportData> reportEntities){
        this.activity=activity;
        this.reportEntities=reportEntities;
        layoutInflater=activity.getLayoutInflater();
        //Collections.reverse(this.reportEntities);
    }
    @Override
    public int getCount() {
        return (int) reportEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return reportEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=layoutInflater.inflate(R.layout.report_countwise_item, null, false);
        TextView text_name=(TextView)convertView.findViewById(R.id.text_name);
        TextView text_total=(TextView)convertView.findViewById(R.id.text_total);
        TextView text_solve=(TextView)convertView.findViewById(R.id.text_solve);
        TextView text_reject=(TextView)convertView.findViewById(R.id.text_reject);
        TextView text_pending=(TextView)convertView.findViewById(R.id.text_pending);
        GravanceReportData gravanceReportData=reportEntities.get(position);
        text_name.setText(gravanceReportData.get_GrievanceType());
        text_total.setText(gravanceReportData.get_Total());
        text_solve.setText(gravanceReportData.get_Resolved());
        text_reject.setText(gravanceReportData.get_Rejected());
        text_pending.setText(gravanceReportData.get_Pending());
        return convertView;
    }
}
