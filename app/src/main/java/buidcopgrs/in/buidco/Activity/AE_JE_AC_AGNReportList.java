package buidcopgrs.in.buidco.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import buidcopgrs.in.buidco.Adaptor.Adaptor_User_Wise_Grivance;
import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.Utilitites.GlobalVariables;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.asyncTasks.GetGrivanceRoleWiseService;
import buidcopgrs.in.buidco.asyncTasks.GrivancePublicLoaderService;
import buidcopgrs.in.buidco.asyncTasks.GrivanceTypeLoaderService;
import buidcopgrs.in.buidco.asyncTasks.GrivanceUploadService;
import buidcopgrs.in.buidco.asyncTasks.GrivanceUserWiseLoaderService;
import buidcopgrs.in.buidco.database.DataBaseHelper;
import buidcopgrs.in.buidco.entity.GrivanceTypeData;
import buidcopgrs.in.buidco.entity.GrivanceUserWiseData;
import buidcopgrs.in.buidco.entity.NewGrivanceData;
import buidcopgrs.in.buidco.entity.UserDetails;
import buidcopgrs.in.buidco.interfaces.GrivanceBinder;
import buidcopgrs.in.buidco.interfaces.GrivanceRoleWiseBinder;
import buidcopgrs.in.buidco.interfaces.GrivanceUserWiseBinder;

public class AE_JE_AC_AGNReportList extends AppCompatActivity {
    ListView list_Grivance_Itm;
    Adaptor_User_Wise_Grivance adaptor_user_wise_grivance;
    ArrayList<GrivanceUserWiseData> data;
    UserDetails userDetails;
    SwipeRefreshLayout swipeRefreshLayout;
    static private boolean onres = false;
    Toolbar toolbar_gd;
    Spinner sp_sts;
    private ArrayList<GrivanceTypeData> grivanceTypeDataArrayList = null;
    private String gt_data = "0", sts_data = "0";
    RelativeLayout re_filter;
    ImageView imageViewheader;
    TextView text_header_name, text_header_mobile;
    ImageView header_image;
    DataBaseHelper dataBaseHelper;
    MenuItem m1, m2, m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dataBaseHelper = new DataBaseHelper(AE_JE_AC_AGNReportList.this);
        userDetails = CommonPref.getUserDetails(AE_JE_AC_AGNReportList.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_vgd);
            toolbar_gd.setTitle("Grivaence");
            toolbar_gd.setSubtitle("PGRS (BUIDCo)");
            toolbar_gd.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_gd.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_gd);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar_gd.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        re_filter = (RelativeLayout) findViewById(R.id.re_filter);
        list_Grivance_Itm = (ListView) findViewById(R.id.list_Grivance_Itm);
        sp_sts = (Spinner) findViewById(R.id.sp_sts);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onres = false;
                loadpageData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    private void loadpageData() {

        if (userDetails.getRole().trim().equals("EE")) {
            sts_data = "1";
            GetGrivanceRoleWiseService.bindGTListener(new GrivanceRoleWiseBinder() {
                @Override
                public void rolegrivanceLoaded(ArrayList<GrivanceUserWiseData> grivanceUserWiseData) {
                    ShowUserWiseGrivanceListData(grivanceUserWiseData);
                }

                @Override
                public void grivanceNotFound() {
                    if (data != null) {
                        data.clear();
                        list_Grivance_Itm.invalidate();
                    } else {
                        list_Grivance_Itm.invalidate();
                    }
                }
            });
            if (!onres) {
                new GetGrivanceRoleWiseService(AE_JE_AC_AGNReportList.this).execute(userDetails.getZoneID().trim(), userDetails.getCommID().trim(), userDetails.getDistrictId().trim(), sts_data, gt_data.trim(), userDetails.getRole().trim());
            }
        } else {
            if (userDetails.getRole().trim().equals("AE")) {
                sts_data = "2,16,17";
            } else if (userDetails.getRole().trim().equals("JE")) {
                sts_data = "3,16,17";
            } else if (userDetails.getRole().trim().equals("PUB")) {
                sts_data = "0";
            } else if (userDetails.getRole().trim().equals("AGN")) {
                sts_data = "2,3";
            }
            GrivanceUserWiseLoaderService.bindGTListener(new GrivanceUserWiseBinder() {
                @Override
                public void usergrivanceLoaded(ArrayList<GrivanceUserWiseData> grivanceUserWiseData) {
                    ShowUserWiseGrivanceListData(grivanceUserWiseData);
                }

                @Override
                public void grivanceNotFound() {
                    if (data != null) {
                        data.clear();
                        adaptor_user_wise_grivance.notifyDataSetChanged();
                    }
                }
            });
            GrivancePublicLoaderService.bindGTListener(new GrivanceUserWiseBinder() {
                @Override
                public void usergrivanceLoaded(ArrayList<GrivanceUserWiseData> grivanceUserWiseData) {
                    ShowUserWiseGrivanceListData(grivanceUserWiseData);
                }

                @Override
                public void grivanceNotFound() {
                    if (data != null) {
                        data.clear();
                        adaptor_user_wise_grivance.notifyDataSetChanged();
                    }
                }
            });
            if (!onres) {
                if (userDetails.getRole().trim().equals("PUB") && userDetails.getLogin().trim().equals("yes")) {
                    new GrivanceUserWiseLoaderService(AE_JE_AC_AGNReportList.this).execute(userDetails.getUserID().trim(), gt_data.trim(), userDetails.getRole().trim());
                }
                if (userDetails.getRole().trim().equals("PUB") && userDetails.getLogin().trim().equals("")) {
                    new GrivancePublicLoaderService(AE_JE_AC_AGNReportList.this).execute("0", "0");
                } else {
                    new GrivanceUserWiseLoaderService(AE_JE_AC_AGNReportList.this).execute(userDetails.getUserID().trim(), sts_data.trim(), userDetails.getRole().trim());
                }
            }

        }
        onres = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (userDetails.getRole().equals("EE")) {
            GrivanceTypeLoaderService.bindGTListener(new GrivanceBinder() {
                @Override
                public void grivanceLoaded(ArrayList<GrivanceTypeData> grivanceTypeDataArrayList) {
                    setGrivanceTypeSpiner(grivanceTypeDataArrayList);
                }

                @Override
                public void griTypeNotFound() {
                    sp_sts.setAdapter(new ArrayAdapter<String>(AE_JE_AC_AGNReportList.this, R.layout.dropdownlist, new String[]{}));
                }
            });
            if (!onres) {
                new GrivanceTypeLoaderService(AE_JE_AC_AGNReportList.this).execute(userDetails.getCommID().trim());
            }
        } else {
            re_filter.setVisibility(View.GONE);
            loadpageData();
        }

    }

    private void ShowUserWiseGrivanceListData(ArrayList<GrivanceUserWiseData> grivanceUserWiseData) {
        // data=helper.getEntryListForEdit();
        if (data != null) {
            data.clear();
        }
        data = grivanceUserWiseData;
        if (!data.isEmpty()) {
            list_Grivance_Itm.invalidate();
            adaptor_user_wise_grivance = new Adaptor_User_Wise_Grivance(AE_JE_AC_AGNReportList.this, data);
            list_Grivance_Itm.setAdapter(adaptor_user_wise_grivance);
            list_Grivance_Itm.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(AE_JE_AC_AGNReportList.this, GrivaenceDetailsActionActivity.class);
                    intent.putExtra("object", data.get(position));
                    startActivityForResult(intent, 10);

                }


            });
        } else {
            Toast.makeText(this, "Sorry! no record found. !", Toast.LENGTH_SHORT).show();
        }
    }

    private void setGrivanceTypeSpiner(ArrayList<GrivanceTypeData> grivanceTypeDataArrayList) {
        this.grivanceTypeDataArrayList = grivanceTypeDataArrayList;
        ArrayList<String> st_arr = new ArrayList<>();
        st_arr.add("-- Select Grievance Type --");
        for (GrivanceTypeData grivanceTypeData : grivanceTypeDataArrayList) {
            st_arr.add(grivanceTypeData.getDetail());
        }
        sp_sts.setAdapter(new ArrayAdapter<String>(AE_JE_AC_AGNReportList.this, R.layout.dropdownlist, st_arr));
        sp_sts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    gt_data = grivanceTypeDataArrayList.get(i - 1).getCode();
                    onres = false;
                    loadpageData();
                } else {
                    gt_data = "0";
                    onres = false;
                    loadpageData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sp_sts.setSelection(0);
    }

    @Override
    public void onBackPressed() {
        if (userDetails.getRole().equalsIgnoreCase("EE") || userDetails.getRole().equalsIgnoreCase("AE") || userDetails.getRole().equalsIgnoreCase("JE")) {
            new AlertDialog.Builder(this)
                    .setTitle("Log Out?")
                    .setMessage("Are you sure you want to Log Out?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            AE_JE_AC_AGNReportList.super.onBackPressed();
                        }

                    }).create().show();
        } else if (userDetails.getRole().equalsIgnoreCase("")) {
            AE_JE_AC_AGNReportList.super.onBackPressed();
        } else {
            AE_JE_AC_AGNReportList.super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        onres = false;
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "SUCCESS", Toast.LENGTH_SHORT).show();
                list_Grivance_Itm.invalidate();
                if (this.data != null) {
                    this.data.clear();
                    adaptor_user_wise_grivance.notifyDataSetChanged();
                }
                onres = false;
                loadpageData();
            }
        }
    }


    private void DialogForUpload() {
        AlertDialog.Builder ab = new AlertDialog.Builder(
                AE_JE_AC_AGNReportList.this);
        ab.setTitle("Upload");
        ab.setMessage("Would you like to upload ?");
        ab.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();

            }
        });

        ab.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.CUPCAKE)
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                //code for uploading data
                ArrayList<NewGrivanceData> dataProgress = dataBaseHelper.getNewGrievance(userDetails.getUserID());
                if (!Utiilties.isOnline(AE_JE_AC_AGNReportList.this)) {
                    Toast.makeText(AE_JE_AC_AGNReportList.this, "Please Check Internet Connection !", Toast.LENGTH_SHORT).show();
                } else if (dataProgress.size() > 0) {
                    for (NewGrivanceData data : dataProgress) {
                        new GrivanceUploadService(AE_JE_AC_AGNReportList.this, true, data.getId()).execute(data.getGrievanceType().trim(), data.getDescription().trim(), data.getCircleCode().trim(), data.getWardNo().trim(), data.getLandMark().trim(), data.getAddress().trim(),
                                data.getLatitude().trim(), data.getLongitude().trim(), data.getDistCode().trim(), data.getStatus().trim(), data.getUserId().trim(), data.getPhotoPath().trim());
                    }
                    GlobalVariables.listSize = dataProgress.size();
                } else {
                    Toast.makeText(AE_JE_AC_AGNReportList.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        ab.show();
    }
}
