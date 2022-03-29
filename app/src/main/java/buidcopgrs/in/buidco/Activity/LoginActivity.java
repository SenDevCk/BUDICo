package buidcopgrs.in.buidco.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.asyncTasks.ForgetPasswordService;
import buidcopgrs.in.buidco.asyncTasks.LoginService;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.interfaces.ForgetPasswordBinder;

public class LoginActivity extends AppCompatActivity {
    TextView tv_register, txtVersion, tv_fort_pass;
    TextInputEditText et_User_Name, et_pass;
    private DataBaseHelper localDBHelper;
    TelephonyManager tm;
    private static String imei = "";
    String version;
    RadioGroup rg_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        rg_log = (RadioGroup) findViewById(R.id.rg_log);
        et_User_Name = (TextInputEditText) findViewById(R.id.et_User_Name);
        et_pass = (TextInputEditText) findViewById(R.id.et_pass);
        txtVersion = (TextView) findViewById(R.id.txtVersion);
        tv_register = (TextView) findViewById(R.id.tv_register);
        tv_fort_pass = (TextView) findViewById(R.id.tv_fort_pass);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        tv_fort_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                final EditText input = new EditText(LoginActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                input.setHint("Enter Registered Mobile Number");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                builder.setView(input);
                builder.setMessage("Please Enter Your Mobile Number bellow")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (input.getText().toString().trim().equals("") || input.getText().toString().trim().length() < 10) {
                                    Toast.makeText(LoginActivity.this, "Enter valid Mobile ", Toast.LENGTH_SHORT).show();
                                } else if (!Utiilties.isOnline(LoginActivity.this)) {
                                    Toast.makeText(LoginActivity.this, "Please go online !", Toast.LENGTH_SHORT).show();
                                } else {
                                    ForgetPasswordService.bindForgetPassword(new ForgetPasswordBinder() {
                                        @Override
                                        public void bindForgetPASS(String res) {
                                            AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                            dialog.dismiss();
                                            alertDialog.setMessage("" + res);
                                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });
                                            alertDialog.show();

                                        }
                                    });
                                    new ForgetPasswordService(LoginActivity.this).execute(input.getText().toString());
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Forget Password");
                alert.show();
            }
        });
        init();
    }

    private void init() {
        localDBHelper = new DataBaseHelper(LoginActivity.this);
        try {
            localDBHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try {

            localDBHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }
        readPhoneState();
    }

    @SuppressLint("ObsoleteSdkInt")
    public void readPhoneState() {

        try {
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) ;
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                imei = tm.getDeviceId();
            } else {
                imei = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            version = LoginActivity.this.getPackageManager().getPackageInfo(LoginActivity.this.getPackageName(), 0).versionName;
            Log.e("App Version : ", "" + version + " ( " + imei + " )");
            txtVersion.setText("App Version" + version + " ( " + imei + " )");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void Login(View view) {
        if (et_User_Name.getText().toString().trim().length() <= 0) {
            et_User_Name.setError("Enter User ID !");
        } else if (et_pass.getText().toString().trim().length() <= 0) {
            et_pass.setError("Enter Password !");
        } else {
            if (Utiilties.isOnline(LoginActivity.this)) {
                new LoginService(LoginActivity.this).execute(et_User_Name.getText().toString().trim(), et_pass.getText().toString().trim(), imei);
            } else {
                Toast.makeText(this, "Turn On Internet Connection !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
