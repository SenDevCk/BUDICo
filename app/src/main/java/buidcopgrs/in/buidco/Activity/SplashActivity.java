package buidcopgrs.in.buidco.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.Utilitites.MarshmallowPermission;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.database.WebServiceHelper;
import buidcopgrs.in.buidco.entity.Versioninfo;


public class SplashActivity extends Activity {
    Context context;
    MarshmallowPermission permission;
    long isDataDownloaded = -1;
    public static SharedPreferences prefs;
    @SuppressLint("NewApi")
    ActionBar actionBar;
    @SuppressLint("NewApi")
    DataBaseHelper localDBHelper;
    private static int SPLASH_TIME_OUT = 1000;
    boolean initse;
    private static final int MY_PERMISSIONS_REQUEST_ACCOUNTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }


    private void checkAppUseMode() {
        if (!Utiilties.isGPSEnabled(SplashActivity.this)) {
            Utiilties.displayPromptForEnablingGPS(SplashActivity.this);
        } else {
            boolean net = false;

            permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_NETWORK_STATE);
            if (permission.result == -1 || permission.result == 0)
                net = Utiilties.isOnline(SplashActivity.this);
            if (net) {

                new CheckUpdate().execute();

            } else {
                start();
            }
        }
    }

    private void start() {


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (CommonPref.getUserDetails(SplashActivity.this).getLogin().equalsIgnoreCase("yes")) {
                    if (CommonPref.getUserDetails(SplashActivity.this).getRole().equals("PUB")) {
                        startActivities(new Intent[]{new Intent(SplashActivity.this, ViewGrivanceList.class), new Intent(SplashActivity.this, GravanceActivity.class)});
                    } else {
                        startActivity(new Intent(SplashActivity.this, ViewGrivanceList.class));
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


    private void showDailog(AlertDialog.Builder ab,
                            final Versioninfo versioninfo) {

        if (versioninfo.isVerUpdated()) {
            if (versioninfo.getPriority() == 0) {
                start();
            } else if (versioninfo.getPriority() == 1) {

                ab.setTitle(versioninfo.getAdminTitle());
                ab.setMessage(versioninfo.getAdminMsg());

                ab.setPositiveButton("update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {


                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package

                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri
                                        .parse("market://details?id="
                                                + getApplicationContext()
                                                .getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                            }


                        });
                ab.setNegativeButton("ignore",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                dialog.dismiss();

                                start();
                            }

                        });

                ab.show();

            } else if (versioninfo.getPriority() == 2) {

                ab.setTitle(versioninfo.getAdminTitle());
                ab.setMessage(versioninfo.getAdminMsg());
                ab.setPositiveButton("update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package

                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri
                                        .parse("market://details?id="
                                                + getApplicationContext()
                                                .getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                                // finish();
                            }
                        });
                ab.show();
            }
        } else {

            start();
        }

    }


    private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo> {


        CheckUpdate() {


        }


        @Override
        protected void onPreExecute() {

        }

        @SuppressLint("MissingPermission")
        @Override
        protected Versioninfo doInBackground(Void... Params) {

            TelephonyManager tm = null;
            String imei = null;

            permission = new MarshmallowPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE);
            if (permission.result == -1 || permission.result == 0) {
                try {
                    tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                    if (tm != null) imei = tm.getDeviceId();
                } catch (Exception e) {
                }
            }

            String version = null;
            try {
                version = getPackageManager().getPackageInfo(getPackageName(),
                        0).versionName;
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Versioninfo versioninfo = WebServiceHelper.CheckVersion(imei, version);

            return versioninfo;

        }

        @Override
        protected void onPostExecute(final Versioninfo versioninfo) {

            final AlertDialog.Builder ab = new AlertDialog.Builder(
                    SplashActivity.this);
            ab.setCancelable(false);
            if (versioninfo != null) {

                CommonPref.setCheckUpdate(getApplicationContext(),
                        System.currentTimeMillis());

                if (versioninfo.getAdminMsg().trim().length() > 0
                        && !versioninfo.getAdminMsg().trim().equalsIgnoreCase("anyType{}")) {


                    showDailog(ab, versioninfo);

                } else {
                    showDailog(ab, versioninfo);
                }
            } else {
                if (versioninfo != null) {
                    Toast.makeText(getApplicationContext(), "wrong_device_text",
                            Toast.LENGTH_LONG).show();

                } else {
                    start();

                }
            }

        }
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Call some material design APIs here
            if (checkAndRequestPermissions()) {
                //init2();
                if (!initse) init();
            }

        } else {
            // Implement this feature without material design
            //init2();
            if (!initse) init();
        }
    }

    private void init() {
        initse = true;
        localDBHelper = new DataBaseHelper(SplashActivity.this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        context = this;
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
        checkAppUseMode();

    }

    private boolean checkAndRequestPermissions() {

        int camra = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        //int read_phone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int write_external = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camra != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (write_external != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (location != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
      /*  if (read_phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_PHONE_STATE);
        }*/
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), MY_PERMISSIONS_REQUEST_ACCOUNTS);
            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCOUNTS:
                //if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //init();
                    if (!initse) init();
                } else {
                    //Toast.makeText(this, "Please enable all permissions !", Toast.LENGTH_SHORT).show();
                    if (!initse) init();
                }
                break;
        }
    }
}
