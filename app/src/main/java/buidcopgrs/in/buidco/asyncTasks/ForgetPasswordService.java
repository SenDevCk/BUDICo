package buidcopgrs.in.buidco.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.WebServiceHelper;
import buidcopgrs.in.buidco.interfaces.ForgetPasswordBinder;

public class ForgetPasswordService extends AsyncTask<String, Void, String> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    EditText et_User_Name, et_pass;
    static ForgetPasswordBinder forgetPasswordBinder;

    public ForgetPasswordService(Activity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        alertDialog = new AlertDialog.Builder(this.activity).create();
        et_User_Name = (EditText) this.activity.findViewById(R.id.et_User_Name);
        et_pass = (EditText) this.activity.findViewById(R.id.et_pass);
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
                result = WebServiceHelper.forgetPassword(strings[0].trim());
            } else {
                alertDialog.setMessage("Your device must have atleast Kitkat or Above Version");
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        } else {
            Toast.makeText(activity, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog1.isShowing()) dialog1.dismiss();
        if (result == null) {
            alertDialog.setMessage("Something went wrong !");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        } else {
            forgetPasswordBinder.bindForgetPASS(result);
        }
    }

    public static void bindForgetPassword(ForgetPasswordBinder forgetPasswordBinder1) {
        forgetPasswordBinder = forgetPasswordBinder1;
    }
}
