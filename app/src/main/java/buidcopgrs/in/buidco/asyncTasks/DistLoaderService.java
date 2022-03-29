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
import buidcopgrs.in.buidco.entity.DistrictData;
import buidcopgrs.in.buidco.interfaces.DistrictBinder;

public class DistLoaderService extends AsyncTask<String, Void, ArrayList<DistrictData>> {
    static DistrictBinder districtBinder;
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;

    public DistLoaderService(Activity activity) {
        this.activity = activity;
        dialog1 = new ProgressDialog(this.activity);
        alertDialog = new AlertDialog.Builder(this.activity).create();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog1.setMessage("Loading District...");
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @Override
    protected ArrayList<DistrictData> doInBackground(String... strings) {
        ArrayList<DistrictData> result = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result = new DataBaseHelper(activity).getDistrict();
                if (Utiilties.isOnline(activity) && result.size() <= 0) {
                    result = WebServiceHelper.distLoader();
                }
            } else {
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
    protected void onPostExecute(ArrayList<DistrictData> res) {
        super.onPostExecute(res);
        //SUCCESS Your User Id is Mobile No and Password :- Gr@ba993123
        if (dialog1.isShowing()) dialog1.dismiss();
        if (res == null) {
            Toast.makeText(activity, "District Not Found ! Enable Internet Connection !", Toast.LENGTH_SHORT).show();
            districtBinder.distNotFound();
        } else {
            if (res.size() > 0) {
                long dc = new DataBaseHelper(activity).getDistrictCount();
                if (Utiilties.isOnline(activity) && dc <= 0) {
                    new DataBaseHelper(activity).saveDistrict(res);
                }
                districtBinder.distLoaded(res);
            } else {
                Toast.makeText(activity, "District Not Found ! Enable Internet Connection !", Toast.LENGTH_SHORT).show();
                districtBinder.distNotFound();
            }
        }
    }

    public static void bindDistListener(DistrictBinder listener) {
        districtBinder = listener;
    }
}
