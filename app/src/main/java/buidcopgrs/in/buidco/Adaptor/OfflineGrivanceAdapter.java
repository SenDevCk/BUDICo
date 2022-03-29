package buidcopgrs.in.buidco.Adaptor;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.entity.NewGrivanceData;

public class OfflineGrivanceAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<NewGrivanceData> grivanceDataArrayList;
    LayoutInflater layoutInflater;

    public OfflineGrivanceAdapter(Activity activity, ArrayList<NewGrivanceData> grivanceDataArrayList) {
        this.activity = activity;
        this.grivanceDataArrayList = grivanceDataArrayList;
        layoutInflater = activity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return grivanceDataArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View row, ViewGroup viewGroup) {
        ViewHolder holder = new ViewHolder();
        row = layoutInflater.inflate(R.layout.child_user_wise_grivance_list, null, false);
        holder.UserName = (TextView) row.findViewById(R.id.tv_user_name);
        holder.GrivamnceId = (TextView) row.findViewById(R.id.tv_Grivance_Id);
        holder.tv_Grivance_type = (TextView) row.findViewById(R.id.tv_Grivance_type);
        holder.tv_Grivance_sts = (TextView) row.findViewById(R.id.tv_Grivance_sts);
        holder.Date = (TextView) row.findViewById(R.id.tv_entrydate);
        holder.Image = (ImageView) row.findViewById(R.id.img_GrivanceImg);
        holder.UserName.setText(grivanceDataArrayList.get(pos).getGrievanceType());
        holder.GrivamnceId.setText(grivanceDataArrayList.get(pos).getAddress());
        holder.Image.setImageBitmap(Utiilties.StringToBitMap(grivanceDataArrayList.get(pos).getPhotoPath()));
        holder.Date.setText("");
        holder.tv_Grivance_sts.setText("");
        holder.tv_Grivance_type.setText("" + grivanceDataArrayList.get(pos).getDescription());
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                ab.setTitle("Delete");
                ab.setMessage("Would you like to delete ?");
                ab.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {

                        dialog.dismiss();

                    }
                });

                ab.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                    @TargetApi(Build.VERSION_CODES.CUPCAKE)
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //code for uploading data
                        long dc = new DataBaseHelper(activity).deleteNewGrivanceEntered(grivanceDataArrayList.get(pos).getId());
                        if (dc > 0) {
                            dialog.dismiss();
                            synchronized(OfflineGrivanceAdapter.this){
                                grivanceDataArrayList.remove(pos);
                                OfflineGrivanceAdapter.this.notify();
                            }
                        }
                    }
                });

                ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                ab.show();
                return false;
            }
        });
        return row;
    }

    private class ViewHolder {
        TextView UserName, GrivamnceId, Date, tv_Grivance_type, tv_Grivance_sts;
        ImageView Image;
    }
}
