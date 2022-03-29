package buidcopgrs.in.buidco.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import buidcopgrs.in.buidco.Activity.GravanceActivity;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.WebServiceHelper;

public class SignUpService extends AsyncTask<String,Void, String> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    public SignUpService(Activity activity){
       this.activity=activity;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog1.setMessage("Loading...");
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;
        if (Utiilties.isOnline(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result = WebServiceHelper.RegisterCitizens(strings);
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
            Toast.makeText(activity, "No Internet Connection !", Toast.LENGTH_SHORT).show();
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
                alertDialog.setTitle("Success");
                alertDialog.setMessage("Signup Successful ! \n"+res);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        activity.finish();
                        Intent i = new Intent(activity, GravanceActivity.class);
                        activity.startActivity(i);

                    }
                });
                alertDialog.show();
            }else{
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Signup Unsuccessful !\n"+res);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }
    }
}
