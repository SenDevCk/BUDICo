package buidcopgrs.in.buidco.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;

import java.util.ArrayList;

import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.database.WebServiceHelper;
import buidcopgrs.in.buidco.entity.Ward;
import buidcopgrs.in.buidco.interfaces.WardBinder;

public class WardLoaderService extends AsyncTask<String,Void, ArrayList<Ward>> {
    static WardBinder wardBinder;
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    String block_code="";
    public WardLoaderService(Activity activity){
       this.activity=activity;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog1.setMessage("Loading Ward...");
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @Override
    protected ArrayList<Ward> doInBackground(String... strings) {
        block_code=strings[1].trim();
        ArrayList<Ward> result = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result=new DataBaseHelper(activity).getWard(block_code);
                if (Utiilties.isOnline(activity) && result.size()<=0) {
                    result = WebServiceHelper.getward(strings);
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
    protected void onPostExecute(ArrayList<Ward> res) {
        super.onPostExecute(res);
        //SUCCESS Your User Id is Mobile No and Password :- Gr@ba993123
        if (dialog1.isShowing())dialog1.dismiss();
        if (res==null){
            wardBinder.cancleWardBinding();
        }else{
           if (res.size()>0) {
               long wc=new DataBaseHelper(activity).getWardCount(block_code);
               if (Utiilties.isOnline(activity) && wc<=0){
                   new DataBaseHelper(activity).saveWard(res,block_code);
               }
               wardBinder.bindWard(res);
           }
           else{
               wardBinder.cancleWardBinding();
           }
        }
    }
    public static void bindWardListener(WardBinder listener) {
        wardBinder = listener;
    }
}
