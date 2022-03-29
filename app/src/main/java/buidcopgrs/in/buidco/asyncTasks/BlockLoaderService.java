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
import buidcopgrs.in.buidco.entity.BlockData;
import buidcopgrs.in.buidco.interfaces.BlockBinder;

public class BlockLoaderService extends AsyncTask<String,Void, ArrayList<BlockData>> {
    static BlockBinder blockBinder;
    Activity activity;
    private ProgressDialog dialog1;
    private AlertDialog alertDialog;
    String dist_code="";
    public BlockLoaderService(Activity activity){
       this.activity=activity;
        dialog1= new ProgressDialog(this.activity);
        alertDialog=new AlertDialog.Builder(this.activity).create();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog1.setMessage("Loading Block...");
        dialog1.setCancelable(false);
        dialog1.show();
    }

    @Override
    protected ArrayList<BlockData> doInBackground(String... strings) {
        dist_code=strings[0].trim();
        ArrayList<BlockData> result = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                result=new DataBaseHelper(activity).getBlock(dist_code);
                if (Utiilties.isOnline(activity) && result.size()<=0) {
                    result = WebServiceHelper.getBlock(strings);
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
    protected void onPostExecute(ArrayList<BlockData> res) {
        super.onPostExecute(res);
        //SUCCESS Your User Id is Mobile No and Password :- Gr@ba993123
        if (dialog1.isShowing())dialog1.dismiss();
        if (res==null){
            blockBinder.cancleBlockBinding();
        }else{
           if (res.size()>0) {
               long bc=new DataBaseHelper(activity).getBlockCount(dist_code);
               if (Utiilties.isOnline(activity) && bc<=0) {
                   new DataBaseHelper(activity).saveBlock(res,dist_code);
               }
               blockBinder.bindBlock(res);
           }
           else{
               blockBinder.cancleBlockBinding();
           }
        }
    }
    public static void bindBlockListener(BlockBinder listener) {
        blockBinder = listener;
    }
}
