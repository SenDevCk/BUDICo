package buidcopgrs.in.buidco.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.WebServiceHelper;
import buidcopgrs.in.buidco.entity.GravanceReportData;
import buidcopgrs.in.buidco.interfaces.GrivanceReportBinder;

public class GrivanceReportServiceService extends AsyncTask<String,Void, ArrayList<GravanceReportData>> {
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    static GrivanceReportBinder grivanceReportBinder;
    boolean flag;
    public GrivanceReportServiceService(Activity activity,boolean flag){
       this.activity=activity;
       this.flag=flag;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (flag) {
            dialog1.setMessage("Getting Report...");
            dialog1.setCancelable(false);
            dialog1.show();
        }
    }

    @Override
    protected ArrayList<GravanceReportData> doInBackground(String... strings) {
        ArrayList<GravanceReportData> result = null;
        if (Utiilties.isOnline(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result = WebServiceHelper.getGrivanceReportData(strings);
            }else{
                alertDialog.setMessage("Your device must have atleast Kitkat or Above Version");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }else{
            Log.d("log", "No Internet Connection !");
        }
        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<GravanceReportData> res) {
        super.onPostExecute(res);
        if (dialog1.isShowing() && flag)dialog1.dismiss();
        if (res==null){
           grivanceReportBinder.cancleReportBinding();
        }else{
            if (res.size()>0){
                grivanceReportBinder.bindReport(res);
            }else{
                Toast.makeText(activity, "No Report Found !", Toast.LENGTH_SHORT).show();
                grivanceReportBinder.cancleReportBinding();
            }
        }
    }
   public static void bindGrivanceReport(GrivanceReportBinder grivanceReportBinder1){
       grivanceReportBinder=grivanceReportBinder1;
   }
}
