package buidcopgrs.in.buidco.Activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

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

public class ViewGrivanceList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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
    MenuItem m1, m2,m3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        dataBaseHelper = new DataBaseHelper(ViewGrivanceList.this);
        userDetails = CommonPref.getUserDetails(ViewGrivanceList.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_vgd);
            toolbar_gd.setTitle("Grivaence");
            toolbar_gd.setSubtitle("PGRS (BUIDCo)");
            toolbar_gd.setSubtitleTextColor(getResources().getColor(R.color.white));
            toolbar_gd.setTitleTextColor(getResources().getColor(R.color.white));
            setSupportActionBar(toolbar_gd);

            if (userDetails.getRole().equals("PUB") && !userDetails.getLogin().equals("yes")) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                toolbar_gd.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
            } else {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar_gd, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                try {
                    String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                    String code_v = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionCode);
                    TextView app_name_tip = (TextView) navigationView.findViewById(R.id.app_name_tip);
                    app_name_tip.setText("BUDCo ( " + code_v + "." + version + " ) V");
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                Menu menu = navigationView.getMenu();

                // find MenuItem you want to change
                m1 = menu.findItem(R.id.m1);
                // drawer_menu_up_sol.setTitle("Upload Solved ("+dataBaseHelper.getPendingSolve_Count(userDetails.getUserID().trim()+" )"));

                m2 = menu.findItem(R.id.m2);
                m3 = menu.findItem(R.id.m3);
                //drawer_menu_up_swap.setTitle("Upload Swap ("+dataBaseHelper.getPendingSwap_Count(userDetails.getUserID().trim()+" )"));
                if (userDetails.getRole().equals("PUB")) {
                    m2.setVisible(true);
                    m1.setVisible(false);
                    m3.setVisible(false);
                } else if (userDetails.getRole().equals("EE")){
                    m2.setVisible(false);
                    m1.setVisible(false);
                    m3.setVisible(true);
                }else{
                    m2.setVisible(false);
                    m1.setVisible(true);
                    m3.setVisible(false);
                }
                navigationView.setNavigationItemSelectedListener(this);

                //navigation header
                View header = navigationView.getHeaderView(0);
                text_header_name = (TextView) header.findViewById(R.id.text_header_name);
                text_header_mobile = (TextView) header.findViewById(R.id.text_header_mobile);
                imageViewheader = (ImageView) header.findViewById(R.id.imageViewheader);

                //imageViewheader.setOnClickListener(this);
                //header_image = (ImageView) findViewById(R.id.header_image);
                UserDetails userinfo = CommonPref.getUserDetails(ViewGrivanceList.this);
                text_header_name.setText("" + userinfo.getUserName()+" ( "+userinfo.getRole()+" )");
                text_header_mobile.setText("" + userinfo.getMobileNumber());
            }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        long count = new DataBaseHelper(ViewGrivanceList.this).getNewGriCount(userDetails.getUserID());
        if (id == R.id.drawer_up_new_gri) {
            //dialogNeft();

            if (count > 0) {
                DialogForUpload();
            } else {
                Toast.makeText(this, "No data For Upload !", Toast.LENGTH_SHORT).show();
            }

        }
        else if (id == R.id.dra_off_gri) {
            if (count > 0) {
                Intent intent2 = new Intent(ViewGrivanceList.this, OfflineGrievanceEnteredListActivity.class);
                Intent intent1 = new Intent(ViewGrivanceList.this, GravanceActivity.class);
                startActivities(new Intent[]{intent1,intent2});
            } else {
                Toast.makeText(this, "No data For Upload !", Toast.LENGTH_SHORT).show();
            }

        }else if (id == R.id.drawer_menu_up_swap){
            Intent intent1 = new Intent(ViewGrivanceList.this, GravanceActivity.class);
            startActivity(intent1);
        }else if (id == R.id.drawer_aj_cwr || id == R.id.drawer_ee_reg){
            Intent intent1 = new Intent(ViewGrivanceList.this, ReportCountwiseActivity.class);
            startActivity(intent1);
        }
        else if (id == R.id.drawer_change_pass){
            Intent intent1 = new Intent(ViewGrivanceList.this, ChangePasswordActivity.class);
            startActivity(intent1);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem logoutm = menu.findItem(R.id.action_logout);

        // show the button when some condition is true
        if (userDetails.getUserID().equals("")) {
            logoutm.setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_logout:
                CommonPref.clearLog(ViewGrivanceList.this);
                Intent i = new Intent(getBaseContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                new GetGrivanceRoleWiseService(ViewGrivanceList.this).execute(userDetails.getZoneID().trim(), userDetails.getCommID().trim(), userDetails.getDistrictId().trim(), sts_data, gt_data.trim(), userDetails.getRole().trim());
            }
        } else {
            if (userDetails.getRole().trim().equals("AE")) {
                sts_data = "2,16,17";
            } else if (userDetails.getRole().trim().equals("JE")) {
                sts_data = "3,16,17";
            } else if (userDetails.getRole().trim().equals("PUB")) {
                sts_data = "0";
            }
         else if (userDetails.getRole().trim().equals("AGN")) {
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
                    new GrivanceUserWiseLoaderService(ViewGrivanceList.this).execute(userDetails.getUserID().trim(), gt_data.trim(), userDetails.getRole().trim());
                }
                if (userDetails.getRole().trim().equals("PUB") && userDetails.getLogin().trim().equals("")) {
                    new GrivancePublicLoaderService(ViewGrivanceList.this).execute("0", "0");
                } else {
                    new GrivanceUserWiseLoaderService(ViewGrivanceList.this).execute(userDetails.getUserID().trim(), sts_data.trim(), userDetails.getRole().trim());
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
                    sp_sts.setAdapter(new ArrayAdapter<String>(ViewGrivanceList.this, R.layout.dropdownlist, new String[]{}));
                }
            });
            if (!onres) {
                new GrivanceTypeLoaderService(ViewGrivanceList.this).execute(userDetails.getCommID().trim());
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
            adaptor_user_wise_grivance = new Adaptor_User_Wise_Grivance(ViewGrivanceList.this, data);
            list_Grivance_Itm.setAdapter(adaptor_user_wise_grivance);
            list_Grivance_Itm.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    // TODO Auto-generated method stub
                    Intent intent = new Intent(ViewGrivanceList.this, GrivaenceDetailsActionActivity.class);
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
        sp_sts.setAdapter(new ArrayAdapter<String>(ViewGrivanceList.this, R.layout.dropdownlist, st_arr));
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
        if (userDetails.getRole().equalsIgnoreCase("AGN") || userDetails.getRole().equalsIgnoreCase("EE") || userDetails.getRole().equalsIgnoreCase("AE") || userDetails.getRole().equalsIgnoreCase("JE")|| (userDetails.getRole().equalsIgnoreCase("PUB") && userDetails.getLogin().equalsIgnoreCase("yes"))) {
            new AlertDialog.Builder(this)
                    .setTitle("Log Out?")
                    .setMessage("Are you sure you want to Log Out?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                            ViewGrivanceList.super.onBackPressed();
                        }

                    }).create().show();
        } else if (userDetails.getRole().equalsIgnoreCase("")) {
            ViewGrivanceList.super.onBackPressed();
        } else {
            ViewGrivanceList.super.onBackPressed();
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
                ViewGrivanceList.this);
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
                if (!Utiilties.isOnline(ViewGrivanceList.this)){
                    Toast.makeText(ViewGrivanceList.this, "Please Check Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                else if (dataProgress.size() > 0)
                {
                    for (NewGrivanceData data : dataProgress) {
                        new GrivanceUploadService(ViewGrivanceList.this, true,data.getId()).execute(data.getGrievanceType().trim(), data.getDescription().trim(), data.getCircleCode().trim(), data.getWardNo().trim(), data.getLandMark().trim(), data.getAddress().trim(),
                                data.getLatitude().trim(), data.getLongitude().trim(), data.getDistCode().trim(), data.getStatus().trim(), data.getUserId().trim(), data.getPhotoPath().trim(),data.getPhotoPath1().trim());
                    }
                    GlobalVariables.listSize = dataProgress.size();
                }else{
                    Toast.makeText(ViewGrivanceList.this, "No Data Found !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
        ab.show();
    }
}
