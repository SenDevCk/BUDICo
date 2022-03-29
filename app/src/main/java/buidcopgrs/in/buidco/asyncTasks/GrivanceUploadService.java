package buidcopgrs.in.buidco.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import buidcopgrs.in.buidco.Activity.OfflineGrievanceEnteredListActivity;
import buidcopgrs.in.buidco.Activity.ViewGrivanceList;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.database.WebServiceHelper;

public class GrivanceUploadService extends AsyncTask<String,Void, String> {
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    boolean flag;
    int id;
    public GrivanceUploadService(Activity activity, boolean b){
       this.activity=activity;
       this.flag=b;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();
    }
    public GrivanceUploadService(Activity activity, boolean b,int id){
        this.activity=activity;
        this.flag=b;
        this.id=id;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog1.setMessage("Uploading Grivance Type...");
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (Utiilties.isOnline(activity)) {
                    result = WebServiceHelper.UploadGrivance(strings, CommonPref.getUserDetails(this.activity).getMobileNumber(), CommonPref.getUserDetails(this.activity).getUserName());
                }else{
                    if (!flag) {
                        long c = new DataBaseHelper(activity).saveGrivanceData(strings);
                        if (c > 0) {
                            result = "SUCCESS";
                        }else{
                            result = "Failure";
                        }
                    }else {
                        result = "Failure";
                    }
                }
            }else{
                Log.e("log","Device Must Be Kitkat");
            }
        return result;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
        //SUCCESS Your User Id is Mobile No and Password :- Gr@ba993123
        if (dialog1.isShowing())dialog1.dismiss();
        if (res==null){
            alertDialog.setMessage("Something went wrong !");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }else{
            if (res.contains("SUCCESS")){

                Toast.makeText(activity, "SUCCESS", Toast.LENGTH_SHORT).show();
                if (flag) {
                    new DataBaseHelper(activity).deleteNewGrivanceEntered(id);
                    Intent intent = new Intent(activity, OfflineGrievanceEnteredListActivity.class);
                    activity.startActivity(intent);
                }else{
                    Intent intent = new Intent(activity, ViewGrivanceList.class);
                    activity.startActivity(intent);
                }
                activity.finish();
            }else{
                Toast.makeText(activity, ""+res, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
