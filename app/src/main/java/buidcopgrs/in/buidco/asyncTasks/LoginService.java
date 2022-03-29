package buidcopgrs.in.buidco.asyncTasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import buidcopgrs.in.buidco.Activity.ViewGrivanceList;
import buidcopgrs.in.buidco.Activity.GravanceActivity;
import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.Utilitites.GlobalVariables;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.WebServiceHelper;
import buidcopgrs.in.buidco.entity.UserDetails;

public class LoginService extends AsyncTask<String,Void, UserDetails> {

    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    String userName,password;
    EditText et_User_Name,et_pass;
    private String imei;

    public LoginService(Activity activity){
       this.activity=activity;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();
        et_User_Name=(EditText)this.activity.findViewById(R.id.et_User_Name);
        et_pass=(EditText)this.activity.findViewById(R.id.et_pass);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog1.setMessage("Loading...");
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @Override
    protected UserDetails doInBackground(String... strings) {
        UserDetails result = null;
        userName=strings[0].trim();
        password=strings[1].trim();
        imei=strings[2].trim();
        if (Utiilties.isOnline(activity)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result = WebServiceHelper.Login(strings[0].trim(),strings[1].trim() );
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
    protected void onPostExecute(UserDetails result) {
        super.onPostExecute(result);
        if (dialog1.isShowing())dialog1.dismiss();
        if (result==null){
            alertDialog.setMessage("Something went wrong !");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }else if (result.is_isAuthenticated()==false){
            alertDialog.setMessage("Authentication Failed ");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
        else{
            Intent cPannel=null;
            Intent[] intents=null;
            if (result.getRole().equals("PUB")){
                intents=new Intent[]{new Intent(activity, ViewGrivanceList.class),new Intent(activity, GravanceActivity.class)};
            }
            else {
                cPannel = new Intent(activity, ViewGrivanceList.class);
            }
            if (Utiilties.isOnline(activity)) {
                if (result != null) {
                    if (result.is_isAuthenticated() && result.getIsLocked().equals("N")) {
                        CommonPref.setUserDetails(activity,result);
                        if (result.getRole().equals("PUB")){
                            activity.startActivities(intents);
                        }
                        else {
                            activity.startActivity(cPannel);
                        }
                        activity.finish();
                    }
                    else if (result.getIsLocked().equals("Y")) {
                        alertDialog.setTitle("Failed");
                        alertDialog.setMessage("Login Failed ! Account Locked");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                et_User_Name.setFocusable(true);
                            }
                        });
                        alertDialog.show();
                    }
                    else {
                        alertDialog.setTitle("Failed");
                        alertDialog.setMessage("Login Failed !");
                        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                et_User_Name.setFocusable(true);
                            }
                        });
                        alertDialog.show();
                    }
                }else {
                    alertDialog.setTitle("Failed");
                    alertDialog.setMessage("Login Failed ! UserID or Password Error !");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            et_User_Name.setFocusable(true);
                        }
                    });
                    alertDialog.show();
                }
            } else {
                if (CommonPref.getUserDetails(activity) != null) {
                    GlobalVariables.LoggedUser = result;
                    if (GlobalVariables.LoggedUser.getUserID().equalsIgnoreCase(userName.trim()) && GlobalVariables.LoggedUser.getUserPassword().equalsIgnoreCase(password.trim())) {
                        if (result.getRole().equals("PUB")){
                            activity.startActivities(intents);
                        }
                        else {
                            activity.startActivity(cPannel);
                        }
                        activity.finish();
                    } else {
                        Toast.makeText(activity, "User name and password not matched !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(activity, "Please enable internet connection for first time login.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
