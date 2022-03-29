package buidcopgrs.in.buidco.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;

import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.database.WebServiceHelper;
import buidcopgrs.in.buidco.entity.GrivanceTypeData;
import buidcopgrs.in.buidco.interfaces.GrivanceBinder;

public class GrivanceTypeLoaderService extends AsyncTask<String,Void, ArrayList<GrivanceTypeData>> {
    static GrivanceBinder grivanceBinder;
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    String comcode="";
    public GrivanceTypeLoaderService(Activity activity){
       this.activity=activity;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog1.setMessage("Loading Grivance Type...");
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @Override
    protected ArrayList<GrivanceTypeData> doInBackground(String... strings) {
        comcode=strings[0].trim();
        ArrayList<GrivanceTypeData> result = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                 result=new DataBaseHelper(activity).getGrievanceType(comcode);
                if (Utiilties.isOnline(activity) && result.size()<=0) {
                    result = WebServiceHelper.getGrivanceType(strings);
                }
            }else{
                alertDialog.setMessage("Your device must have atleast Kitkat or Above Version");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }

        return result;
    }

    @Override
    protected void onPostExecute(ArrayList<GrivanceTypeData> res) {
        super.onPostExecute(res);
        //SUCCESS Your User Id is Mobile No and Password :- Gr@ba993123
        if (dialog1.isShowing())dialog1.dismiss();
        if (res==null){
            Toast.makeText(activity, "Please Enable Internet", Toast.LENGTH_SHORT).show();
            grivanceBinder.griTypeNotFound();
        }else{
           if (res.size()>0) {
               long c=new DataBaseHelper(activity).getGrievanceTypeCount(comcode);
               if (Utiilties.isOnline(activity) && c<=0){
                   new DataBaseHelper(activity).saveGrievanceType(res,comcode);
               }
               grivanceBinder.grivanceLoaded(res);
           }
           else{
               Toast.makeText(activity, "Please Enable Internet ! Grievance Type Not Found !", Toast.LENGTH_SHORT).show();
               grivanceBinder.griTypeNotFound();
           }
        }
    }
    public static void bindGTListener(GrivanceBinder listener) {
        grivanceBinder = listener;
    }
}
