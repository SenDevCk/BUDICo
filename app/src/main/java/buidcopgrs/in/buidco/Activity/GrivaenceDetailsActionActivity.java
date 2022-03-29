package buidcopgrs.in.buidco.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.asyncTasks.GetAGNUserLocationWIseService;
import buidcopgrs.in.buidco.asyncTasks.GetStatusService;
import buidcopgrs.in.buidco.asyncTasks.GetUserLocationWIseService;
import buidcopgrs.in.buidco.asyncTasks.GrivanceSolveService;
import buidcopgrs.in.buidco.asyncTasks.GrivanceTypeLoaderService;
import buidcopgrs.in.buidco.asyncTasks.SwapGrivanceService;
import buidcopgrs.in.buidco.entity.AGNUserByLocData;
import buidcopgrs.in.buidco.entity.GrivanceTypeData;
import buidcopgrs.in.buidco.entity.GrivanceUserWiseData;
import buidcopgrs.in.buidco.entity.StatusData;
import buidcopgrs.in.buidco.entity.UserByLocData;
import buidcopgrs.in.buidco.entity.UserDetails;
import buidcopgrs.in.buidco.interfaces.AGNUserLocBinder;
import buidcopgrs.in.buidco.interfaces.GrivanceBinder;
import buidcopgrs.in.buidco.interfaces.StatusBinder;
import buidcopgrs.in.buidco.interfaces.UserLocBinder;

public class GrivaenceDetailsActionActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar_gd;
    TextView text_name, text_id, text_type, text_mb, text_status, text_dec, text_up_date;
    Spinner sp_type_s, spn_action, spn_user_loc, spn_user_agn;
    ImageView img_grivance, btncaladob;
    RadioGroup rad_grp;
    GrivanceUserWiseData grivanceUserWiseData;
    private final static int CAMERA_PIC = 99;
    private int mYear, mMonth, mDay;
    RelativeLayout re_action, re_sts, lydob, re_agnuser;
    LinearLayout sp_type;
    private ArrayList<StatusData> statusDataarr = null;
    private StatusData statusData;
    private ArrayList<UserByLocData> userByLocDataarr = null;
    private ArrayList<AGNUserByLocData> agnuserByLocDataarr = null;
    private UserByLocData userByLocData1;
    private AGNUserByLocData AgnuserByLocData1;
    private UserDetails userDetails;
    private ArrayList<GrivanceTypeData> grivanceTypeDataarr;
    GrivanceTypeData grivanceTypeData;
    TextInputLayout lrem;
    TextInputEditText et_rem;
    Button but_proceed, btn_tk_img;
    String check = "P", latitude, longitude;
    private Bitmap im1;
    int ThumbnailSize = 500;
    DatePickerDialog datedialog;
    TextView tvdob;
    private byte[] imageData1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grivaence_details_action);
        userDetails = CommonPref.getUserDetails(GrivaenceDetailsActionActivity.this);
        grivanceUserWiseData = (GrivanceUserWiseData) getIntent().getSerializableExtra("object");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar_gd = (Toolbar) findViewById(R.id.toolbar_gd);
            //toolbar_gd.setNavigationIcon(getResources().getDrawable(R.drawable.leftarrow));
            toolbar_gd.setTitle("Grivaence Details");
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

        inIt();
    }

    private void inIt() {
        img_grivance = (ImageView) findViewById(R.id.img_grivance);
        Picasso.get().load("https://grievance.sspmis.in" + grivanceUserWiseData.getPhotoByte1()).into(img_grivance);
        text_name = (TextView) findViewById(R.id.text_name);
        text_id = (TextView) findViewById(R.id.text_id);
        text_mb = (TextView) findViewById(R.id.text_mb);
        text_type = (TextView) findViewById(R.id.text_type);
        text_status = (TextView) findViewById(R.id.text_status);
        text_dec = (TextView) findViewById(R.id.text_dec);
        text_up_date = (TextView) findViewById(R.id.text_up_date);
        sp_type_s = (Spinner) findViewById(R.id.sp_type_s);
        rad_grp = (RadioGroup) findViewById(R.id.rad_grp);
        re_sts = (RelativeLayout) findViewById(R.id.re_sts);
        re_agnuser = (RelativeLayout) findViewById(R.id.re_agnuser);
        lydob = (RelativeLayout) findViewById(R.id.lydob);
        re_action = (RelativeLayout) findViewById(R.id.re_action);
        sp_type = (LinearLayout) findViewById(R.id.sp_type);
        spn_action = (Spinner) findViewById(R.id.spn_action);
        spn_user_loc = (Spinner) findViewById(R.id.spn_user_loc);
        spn_user_agn = (Spinner) findViewById(R.id.spn_user_agn);
        lrem = (TextInputLayout) findViewById(R.id.lrem);
        et_rem = (TextInputEditText) findViewById(R.id.et_rem);
        but_proceed = (Button) findViewById(R.id.but_proceed);
        btn_tk_img = (Button) findViewById(R.id.btn_tk_img);
        btncaladob = (ImageView) findViewById(R.id.btncaladob);
        tvdob = (TextView) findViewById(R.id.tvdob);
        but_proceed.setOnClickListener(this);
        btn_tk_img.setOnClickListener(this);
        btncaladob.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        text_name.setText("" + grivanceUserWiseData.get_UserName());
        text_id.setText("" + grivanceUserWiseData.get_GrievanceId());
        text_mb.setText("" + grivanceUserWiseData.get_LandMark());
        text_type.setText("" + grivanceUserWiseData.get_GrievanceType());
        text_status.setText("" + grivanceUserWiseData.get_Status());
        text_dec.setText("" + grivanceUserWiseData.get_Description());
        text_up_date.setText("Date : " + grivanceUserWiseData.get_EntryDate());

        if (!userDetails.getRole().equals("PUB")) {
            rad_grp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.radio_proceed) {
                        check = "P";
                        re_sts.setVisibility(View.VISIBLE);
                        re_agnuser.setVisibility(View.VISIBLE);
                        re_action.setVisibility(View.VISIBLE);
                        sp_type.setVisibility(View.GONE);
                        text_type.setVisibility(View.VISIBLE);
                        lrem.setVisibility(View.VISIBLE);
                        btn_tk_img.setVisibility(View.GONE);
                        lydob.setVisibility(View.VISIBLE);

                        GetStatusService.bindStatus(new StatusBinder() {
                            @Override
                            public void bindStatus(ArrayList<StatusData> statusData) {
                                setStatusSpiner(statusData);

                            }
                        });

                        if (statusDataarr == null) {
                            new GetStatusService(GrivaenceDetailsActionActivity.this).execute(userDetails.getRole().trim());
                        }
                    } else if (i == R.id.radio_swap) {
                        check = "SWP";
                        re_sts.setVisibility(View.GONE);
                        re_agnuser.setVisibility(View.GONE);
                        re_action.setVisibility(View.GONE);
                        sp_type.setVisibility(View.VISIBLE);
                        text_type.setVisibility(View.GONE);
                        lrem.setVisibility(View.GONE);
                        btn_tk_img.setVisibility(View.GONE);
                        lydob.setVisibility(View.GONE);
                        spn_action.setSelection(0);
                        GrivanceTypeLoaderService.bindGTListener(new GrivanceBinder() {
                            @Override
                            public void grivanceLoaded(ArrayList<GrivanceTypeData> grivanceTypeData) {
                                loadGTSpinner(grivanceTypeData);
                            }

                            @Override
                            public void griTypeNotFound() {
                                sp_type_s.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, new String[]{}));
                            }
                        });
                        if (grivanceTypeData == null) {
                            new GrivanceTypeLoaderService(GrivaenceDetailsActionActivity.this).execute(userDetails.getCommID().trim());
                        }
                    }
                }
            });
            rad_grp.check(R.id.radio_proceed);
            if (userDetails.getRole().equalsIgnoreCase("AE") || userDetails.getRole().equalsIgnoreCase("JE") || userDetails.getRole().equalsIgnoreCase("AGN")) {
                rad_grp.setVisibility(View.GONE);
                re_agnuser.setVisibility(View.GONE);
                re_sts.setVisibility(View.GONE);
                lydob.setVisibility(View.GONE);
            }/*else if (userDetails.getRole().equalsIgnoreCase("SE")||userDetails.getRole().equalsIgnoreCase("EE")){
                re_agnuser.setVisibility(View.GONE);
            }*/
        } else {
            re_sts.setVisibility(View.GONE);
            re_agnuser.setVisibility(View.GONE);
            re_action.setVisibility(View.GONE);
            sp_type.setVisibility(View.GONE);
            text_type.setVisibility(View.VISIBLE);
            lrem.setVisibility(View.GONE);
            btn_tk_img.setVisibility(View.GONE);
            but_proceed.setVisibility(View.GONE);
            lydob.setVisibility(View.GONE);
            rad_grp.setVisibility(View.GONE);
        }
    }

    private void loadGTSpinner(ArrayList<GrivanceTypeData> grivanceTypeDataarr) {
        this.grivanceTypeDataarr = grivanceTypeDataarr;
        ArrayList<String> st_arr = new ArrayList<>();
        st_arr.add("--Select Grievance Type--");
        for (GrivanceTypeData grivanceTypeData1 : grivanceTypeDataarr) {
            st_arr.add(grivanceTypeData1.getDetail());
        }
        sp_type_s.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, st_arr));
        sp_type_s.setSelection(((ArrayAdapter) sp_type_s.getAdapter()).getPosition("" + grivanceUserWiseData.get_GrievanceType()));
        sp_type_s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    grivanceTypeData = grivanceTypeDataarr.get(i - 1);

                } else {
                    grivanceTypeData = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setUserLocSpinerDta(ArrayList<UserByLocData> userByLocData) {
        if (userByLocDataarr != null) {
            userByLocDataarr.clear();
        }
        this.userByLocDataarr = userByLocData;
        ArrayList<String> st_arr = new ArrayList<>();
        st_arr.add("--Select User--");
        for (UserByLocData userByLocData2 : userByLocDataarr) {
            st_arr.add(userByLocData2.get_UserName());
        }
        spn_user_loc.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, st_arr));
        spn_user_loc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    userByLocData1 = userByLocDataarr.get(i - 1);

                } else {
                    userByLocData1 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setAGNUserLocSpinerDta(ArrayList<AGNUserByLocData> agnuserByLocData) {
        if (agnuserByLocDataarr != null) {
            agnuserByLocDataarr.clear();
        }
        this.agnuserByLocDataarr = agnuserByLocData;
        ArrayList<String> st_arr = new ArrayList<>();
        st_arr.add("--Select User--");
        for (AGNUserByLocData userByLocData2 : agnuserByLocDataarr) {
            st_arr.add(userByLocData2.get_UserName());
        }
        spn_user_agn.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, st_arr));
        spn_user_agn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    AgnuserByLocData1 = agnuserByLocDataarr.get(i - 1);

                } else {
                    AgnuserByLocData1 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    private void setStatusSpiner(ArrayList<StatusData> statusDataarr) {
        this.statusDataarr = statusDataarr;
        ArrayList<String> st_arr = new ArrayList<>();
        st_arr.add("--Select Status--");
        for (StatusData statusData : statusDataarr) {
            st_arr.add(statusData.get_StatusDetail());
        }
        spn_action.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, st_arr));
        spn_action.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0) {
                    statusData = statusDataarr.get(i - 1);
                    if ((userDetails.getRole().equalsIgnoreCase("EE")) && statusData.get_StatusCode().equals("8")) {
                        check = "SOL";
                        re_sts.setVisibility(View.GONE);
                        re_agnuser.setVisibility(View.GONE);
                        re_action.setVisibility(View.VISIBLE);
                        sp_type.setVisibility(View.GONE);
                        text_type.setVisibility(View.VISIBLE);
                        lrem.setVisibility(View.VISIBLE);
                        btn_tk_img.setVisibility(View.VISIBLE);
                        spn_user_loc.setSelection(0);
                    } else if ((userDetails.getRole().equalsIgnoreCase("EE")) && statusData.get_StatusCode().equals("13")) {
                        check = "R";
                        re_sts.setVisibility(View.GONE);
                        re_agnuser.setVisibility(View.GONE);
                        re_action.setVisibility(View.VISIBLE);
                        sp_type.setVisibility(View.GONE);
                        text_type.setVisibility(View.VISIBLE);
                        lrem.setVisibility(View.VISIBLE);
                        btn_tk_img.setVisibility(View.GONE);
                        lydob.setVisibility(View.GONE);
                    } else if ((userDetails.getRole().equalsIgnoreCase("EE")) && (statusData.get_StatusCode().equals("20") || statusData.get_StatusCode().equals("4"))) {
                        check = "R";
                        if (statusData.get_StatusCode().equals("20")) {
                            re_sts.setVisibility(View.GONE);
                            re_agnuser.setVisibility(View.GONE);
                        } else {
                            re_sts.setVisibility(View.VISIBLE);
                            re_agnuser.setVisibility(View.GONE);
                            GetUserLocationWIseService.bindUserLocData(new UserLocBinder() {
                                @Override
                                public void bindUserLoc(ArrayList<UserByLocData> userByLocData) {
                                    setUserLocSpinerDta(userByLocData);
                                }

                                @Override
                                public void noDataFound() {
                                    if (userByLocDataarr != null) {
                                        userByLocDataarr.clear();
                                    }
                                    spn_user_loc.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, new String[]{}));
                                    userByLocData1 = null;
                                }

                            });
                            new GetUserLocationWIseService(GrivaenceDetailsActionActivity.this).execute(userDetails.getCommID().trim(), userDetails.getDistrictId().trim(), "SE");
                        }
                        re_action.setVisibility(View.VISIBLE);
                        sp_type.setVisibility(View.GONE);
                        text_type.setVisibility(View.VISIBLE);
                        lrem.setVisibility(View.VISIBLE);
                        btn_tk_img.setVisibility(View.GONE);
                        lydob.setVisibility(View.GONE);
                    } else if ((userDetails.getRole().equalsIgnoreCase("AE") || userDetails.getRole().equalsIgnoreCase("JE") || userDetails.getRole().equalsIgnoreCase("AGN")) && (statusData.get_StatusCode().equals("6") || statusData.get_StatusCode().equals("7") || statusData.get_StatusCode().equals("16"))) {
                        check = "SOL";
                        re_sts.setVisibility(View.GONE);
                        re_agnuser.setVisibility(View.GONE);
                        re_action.setVisibility(View.VISIBLE);
                        sp_type.setVisibility(View.GONE);
                        text_type.setVisibility(View.VISIBLE);
                        lrem.setVisibility(View.VISIBLE);
                        btn_tk_img.setVisibility(View.VISIBLE);
                        spn_user_loc.setSelection(0);
                    } else if ((userDetails.getRole().equalsIgnoreCase("AE") || userDetails.getRole().equalsIgnoreCase("JE") || userDetails.getRole().equalsIgnoreCase("AGN")) && (statusData.get_StatusCode().equals("11") || statusData.get_StatusCode().equals("12") || statusData.get_StatusCode().equals("17"))) {
                        check = "R";
                        re_sts.setVisibility(View.GONE);
                        re_agnuser.setVisibility(View.GONE);
                        re_action.setVisibility(View.VISIBLE);
                        sp_type.setVisibility(View.GONE);
                        text_type.setVisibility(View.VISIBLE);
                        lrem.setVisibility(View.VISIBLE);
                        btn_tk_img.setVisibility(View.GONE);
                        lydob.setVisibility(View.GONE);
                    } else {
                        check = "P";
                        re_sts.setVisibility(View.VISIBLE);
                        re_agnuser.setVisibility(View.VISIBLE);
                        re_action.setVisibility(View.VISIBLE);
                        sp_type.setVisibility(View.GONE);
                        text_type.setVisibility(View.VISIBLE);
                        lrem.setVisibility(View.VISIBLE);
                        btn_tk_img.setVisibility(View.GONE);
                        lydob.setVisibility(View.VISIBLE);
                        //spn_action.setSelection(0);
                        GetUserLocationWIseService.bindUserLocData(new UserLocBinder() {
                            @Override
                            public void bindUserLoc(ArrayList<UserByLocData> userByLocData) {
                                setUserLocSpinerDta(userByLocData);
                            }

                            @Override
                            public void noDataFound() {
                                if (userByLocDataarr != null) {
                                    userByLocDataarr.clear();
                                }
                                spn_user_loc.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, new String[]{}));
                                userByLocData1 = null;
                            }

                        });

                        GetAGNUserLocationWIseService.bindAGNUserLocData(new AGNUserLocBinder() {
                            @Override
                            public void bindAGNUserLoc(ArrayList<AGNUserByLocData> agnuserByLocData) {
                                setAGNUserLocSpinerDta(agnuserByLocData);

                            }

                            @Override
                            public void noDataFound() {
                                if (agnuserByLocDataarr != null) {
                                    agnuserByLocDataarr.clear();
                                }
                                spn_user_agn.setAdapter(new ArrayAdapter<String>(GrivaenceDetailsActionActivity.this, R.layout.dropdownlist, new String[]{}));
                                AgnuserByLocData1 = null;
                            }

                        });
                        if (userDetails.getRole().equals("EE")) {
                            String role = "";
                            if (statusData.get_StatusCode().equals("2")) {
                                role = "AE";
                            } else if (statusData.get_StatusCode().equals("3")) {
                                role = "JE";
                            } else if (statusData.get_StatusCode().equals("4")) {
                                role = "SE";
                            } else if (statusData.get_StatusCode().equals("16")) {
                                role = "AGN";
                            }
                            new GetUserLocationWIseService(GrivaenceDetailsActionActivity.this).execute(userDetails.getCommID().trim(), userDetails.getDistrictId().trim(), role);
                            new GetAGNUserLocationWIseService(GrivaenceDetailsActionActivity.this).execute(userDetails.getCommID().trim(), userDetails.getDistrictId().trim(), "AGN");
                        }
                    }

                } else {
                    statusData = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.but_proceed) {
            if (check.equals("P")) {
                if (statusData == null) {
                    Toast.makeText(this, "Please Select Action !", Toast.LENGTH_SHORT).show();
                } else if (userByLocData1 == null && !statusData.get_StatusCode().equals("13") && !statusData.get_StatusCode().equals("8") && !statusData.get_StatusCode().equals("20")) {
                    Toast.makeText(this, "Please Select User !", Toast.LENGTH_SHORT).show();
                } else if (AgnuserByLocData1 == null && !statusData.get_StatusCode().equals("13") && !statusData.get_StatusCode().equals("8") && !statusData.get_StatusCode().equals("20") && !statusData.get_StatusCode().equals("4")) {
                    Toast.makeText(this, "Please Select User !", Toast.LENGTH_SHORT).show();
                } else if (et_rem.getText().toString().trim().length() <= 5) {
                    Toast.makeText(this, "Invalid Remarks !", Toast.LENGTH_SHORT).show();
                    et_rem.setError("Invalid Remarks !");
                } else if (!Utiilties.isOnline(GrivaenceDetailsActionActivity.this)) {
                    Toast.makeText(this, "Go Online !", Toast.LENGTH_SHORT).show();
                } else {
                    new GrivanceSolveService(GrivaenceDetailsActionActivity.this).execute(grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), userByLocData1.get_UseId().trim(), statusData.get_StatusCode().trim(), tvdob.getText().toString().trim(), et_rem.getText().toString().trim(), "", AgnuserByLocData1.get_UseId().trim());
                    //new DataBaseHelper(GrivaenceDetailsActionActivity.this).saveSolvedGrivance(new String[]{grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), userByLocData1.get_UseId().trim(), statusData.get_StatusCode().trim(), tvdob.getText().toString().trim(), et_rem.getText().toString().trim()},null);
                }
            } else if (check.equals("SWP")) {
                new SwapGrivanceService(GrivaenceDetailsActionActivity.this).execute(grivanceUserWiseData.get_GrievanceId().trim(), grivanceTypeData.getCode().trim());
                //new DataBaseHelper(GrivaenceDetailsActionActivity.this).saveSwapGrivance(new String[]{grivanceUserWiseData.get_GrievanceId().trim(), grivanceTypeData.getCode().trim(),userDetails.getUserID().trim()});
            } else if (check.equals("SOL")) {
                if (et_rem.getText().toString().trim().length() <= 5) {
                    Toast.makeText(this, "Invalid Remarks !", Toast.LENGTH_SHORT).show();
                    et_rem.setError("Invalid Remarks !");
                } else if (im1 == null) {
                    Toast.makeText(this, "Take Image !", Toast.LENGTH_SHORT).show();
                } else {
                    if (userDetails.getRole().equalsIgnoreCase("AGN")) {
                        new GrivanceSolveService(GrivaenceDetailsActionActivity.this).execute(grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), grivanceUserWiseData.get_ForwardTo().trim(), statusData.get_StatusCode().trim(), "", et_rem.getText().toString().trim(), Utiilties.BitArrayToString(imageData1), userDetails.getUserID().trim());
                    } else {
                        new GrivanceSolveService(GrivaenceDetailsActionActivity.this).execute(grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), "", statusData.get_StatusCode().trim(), "", et_rem.getText().toString().trim(), Utiilties.BitArrayToString(imageData1), userDetails.getUserID().trim());
                    }
                    //new DataBaseHelper(GrivaenceDetailsActionActivity.this).saveSolvedGrivance(new String[]{grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), "", statusData.get_StatusCode().trim(), "", et_rem.getText().toString().trim()}, im1);
                }
            } else if (check.equals("R")) {
                if (et_rem.getText().toString().trim().length() <= 5) {
                    Toast.makeText(this, "Invalid Remarks !", Toast.LENGTH_SHORT).show();
                    et_rem.setError("Invalid Remarks !");
                } else {
                    if (userDetails.getRole().equalsIgnoreCase("AGN")) {
                        new GrivanceSolveService(GrivaenceDetailsActionActivity.this).execute(grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), grivanceUserWiseData.get_ForwardTo().trim(), statusData.get_StatusCode().trim(), "", et_rem.getText().toString().trim(), "", userDetails.getUserID().trim());
                    } else {
                        new GrivanceSolveService(GrivaenceDetailsActionActivity.this).execute(grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), "", statusData.get_StatusCode().trim(), "", et_rem.getText().toString().trim(), "", userDetails.getUserID().trim());
                    }
                    //new DataBaseHelper(GrivaenceDetailsActionActivity.this).saveSolvedGrivance(new String[]{grivanceUserWiseData.get_GrievanceId().trim(), userDetails.getUserID().trim(), "", statusData.get_StatusCode().trim(), "", et_rem.getText().toString().trim()}, null);
                }
            }
        } else if (view.getId() == R.id.btn_tk_img) {
            getImageDialog("1");
        } else if (view.getId() == R.id.btncaladob) {
            ShowDialog();
        }
    }
    private void getImageDialog(final String key_pic){
        String[] colors = {"Iamge With Location", "Iamge Without Location"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
                iCamera.putExtra("KEY_PIC", key_pic);
                if (which==0){
                    iCamera.putExtra("flag", "1");
                }else{
                    iCamera.putExtra("flag", "0");
                }
                startActivityForResult(iCamera, CAMERA_PIC);
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");
                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            im1 = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            img_grivance.setScaleType(ImageView.ScaleType.FIT_XY);
                            img_grivance.setImageBitmap(Utiilties.GenerateThumbnail(im1, ThumbnailSize, ThumbnailSize));
                            imageData1 = imgData;
                            latitude = data.getStringExtra("Lat");
                            longitude = data.getStringExtra("Lng");

                            break;


                    }


                }

        }

    }

    private void ShowDialog() {


        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(GrivaenceDetailsActionActivity.this,
                mDateSetListener, mYear, mMonth, mDay);
        datedialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datedialog.show();

    }

    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            try {
                if (mDay < 10) {
                    mDay = Integer.parseInt("0" + mDay);
                    tvdob.setText(new StringBuilder().append("0" + mDay).append("/").append(mMonth + 1).append("/").append(mYear));
                } else {
                    tvdob.setText(new StringBuilder().append(mDay).append("/").append(mMonth + 1).append("/").append(mYear));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
