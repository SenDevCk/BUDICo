package buidcopgrs.in.buidco.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.entity.GrivanceUserWiseData;


/**
 * Created by nicsi on 11/23/2017.
 */
public class Adaptor_User_Wise_Grivance extends BaseAdapter {
    Context activity;

    ArrayList<GrivanceUserWiseData> UserWiseGrivanceList;

    public Adaptor_User_Wise_Grivance(Context context, ArrayList<GrivanceUserWiseData> rlist) {
        this.activity = context;
        this.UserWiseGrivanceList = rlist;

    }

    @Override
    public int getCount() {
        return UserWiseGrivanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;
        LayoutInflater inflater = ((Activity) activity).getLayoutInflater();
        row = inflater.inflate(R.layout.child_user_wise_grivance_list, parent, false);
        holder = new ViewHolder();
        holder.UserName = (TextView) row.findViewById(R.id.tv_user_name);
        holder.GrivamnceId = (TextView) row.findViewById(R.id.tv_Grivance_Id);
        holder.tv_Grivance_type = (TextView) row.findViewById(R.id.tv_Grivance_type);
        holder.tv_Grivance_sts = (TextView) row.findViewById(R.id.tv_Grivance_sts);
        holder.Date = (TextView) row.findViewById(R.id.tv_entrydate);
        holder.Image = (ImageView) row.findViewById(R.id.img_GrivanceImg);
        holder.UserName.setText("" + UserWiseGrivanceList.get(position).get_UserName());
        holder.GrivamnceId.setText(UserWiseGrivanceList.get(position).get_GrievanceId());
        holder.Date.setText("" + UserWiseGrivanceList.get(position).get_EntryDate());
        holder.tv_Grivance_type.setText(UserWiseGrivanceList.get(position).get_GrievanceType());
        holder.tv_Grivance_sts.setText(UserWiseGrivanceList.get(position).get_Status());
        holder.tv_Grivance_sts.setTextColor(activity.getResources().getColor(R.color.holo_red_dark));
        Picasso.get().load("https://grievance.sspmis.in" + UserWiseGrivanceList.get(position).getPhotoByte1()).into(holder.Image);

        row.setTag(holder);


        return row;
    }

    private class ViewHolder {
        TextView UserName, GrivamnceId, Date, tv_Grivance_type, tv_Grivance_sts;
        ImageView Image;
    }


}


