package buidcopgrs.in.buidco.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import buidcopgrs.in.buidco.R;
import buidcopgrs.in.buidco.Utilitites.CommonPref;
import buidcopgrs.in.buidco.Utilitites.GlobalVariables;
import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.asyncTasks.BlockLoaderService;
import buidcopgrs.in.buidco.asyncTasks.DistLoaderService;
import buidcopgrs.in.buidco.asyncTasks.GrivanceTypeLoaderService;
import buidcopgrs.in.buidco.asyncTasks.GrivanceUploadService;
import buidcopgrs.in.buidco.asyncTasks.WardLoaderService;
import buidcopgrs.in.buidco.entity.BlockData;
import buidcopgrs.in.buidco.entity.DistrictData;
import buidcopgrs.in.buidco.entity.GrivanceTypeData;
import buidcopgrs.in.buidco.entity.UserDetails;
import buidcopgrs.in.buidco.entity.Ward;
import buidcopgrs.in.buidco.interfaces.BlockBinder;
import buidcopgrs.in.buidco.interfaces.DistrictBinder;
import buidcopgrs.in.buidco.interfaces.GrivanceBinder;
import buidcopgrs.in.buidco.interfaces.WardBinder;

public class GravanceActivity extends Activity implements View.OnClickListener {
    ImageView menu_inflater,img_get_loc;
    Bitmap im1,im2;
    byte[] imageData1,imageData2;
    String keyid = "";
    boolean edit = false;
    int ThumbnailSize = 500;
    private final static int CAMERA_PIC = 99;
    ImageView img_pic1,img_loc, viewIMG1,viewIMG2;
    LinearLayout ll_view_grivane;
    String latitude = "", longitude = "";
    Spinner spn_dist,spn_grt,spn_ward,spn_block;
  // AutoCompleteTextView spn_block;
    ArrayAdapter<String> dist_adp,grt_adp;
    private String dist_code = "", dist_name = "";
    private String gt_code = "", gt_name = "";
    ArrayList<DistrictData> district;
    ArrayList<GrivanceTypeData> grivance;
    TextView txt_UserName;
    boolean loaded;
    TextInputEditText etben_fmlyhis,etben_add,et_landmark;
    Button btn_save,btn_cancel;
    UserDetails userDetails;
    TextInputLayout lyadd;
    private ArrayList<BlockData> block_arr;
    private BlockData blockEntity;
    private ArrayList<Ward> ward_arr;
    private Ward wardEntity;
    LocationManager mlocManager = null;
    private final int UPDATE_ADDRESS = 1;
    private final int UPDATE_LATLNG = 2;
    ProgressBar progress_finding_location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravance);

        userDetails=CommonPref.getUserDetails(GravanceActivity.this);
        spn_dist = (Spinner) findViewById(R.id.spn_dist);
        spn_grt = (Spinner) findViewById(R.id.spn_grt);
        spn_block = (Spinner) findViewById(R.id.spn_blk);
       // spn_block = (AutoCompleteTextView) findViewById(R.id.spn_blk);
        spn_ward = (Spinner) findViewById(R.id.spn_ward);
        viewIMG1 = (ImageView) findViewById(R.id.viewIMG1);
        viewIMG2 = (ImageView) findViewById(R.id.viewIMG2);
        img_pic1 = (ImageView) findViewById(R.id.img_pic1);
        img_loc = (ImageView) findViewById(R.id.img_loc);
        img_get_loc = (ImageView) findViewById(R.id.img_get_loc);
        ll_view_grivane = (LinearLayout) findViewById(R.id.ll_view_grivane);
        etben_fmlyhis = (TextInputEditText) findViewById(R.id.etben_fmlyhis);
        etben_add = (TextInputEditText) findViewById(R.id.etben_add);
        et_landmark = (TextInputEditText) findViewById(R.id.et_landmark);
        txt_UserName = (TextView) findViewById(R.id.txt_UserName);

        lyadd = (TextInputLayout) findViewById(R.id.lyadd);
        lyadd.setVisibility(View.GONE);

        progress_finding_location = (ProgressBar) findViewById(R.id.progress_finding_location2);
        progress_finding_location.setVisibility(View.GONE);

        btn_save = (Button) findViewById(R.id.btn_save);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        menu_inflater = (ImageView) findViewById(R.id.menu_inflater);
        txt_UserName.setText(CommonPref.getUserDetails(GravanceActivity.this).getUserName());
        img_get_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress_finding_location.setVisibility(View.VISIBLE);
                locationManager();
            }
        });
        menu_inflater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(GravanceActivity.this, menu_inflater);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        //noinspection SimplifiableIfStatement
                        if (id == R.id.action_logout) {
                            CommonPref.clearLog(GravanceActivity.this);
                            Intent i = new Intent(getBaseContext(), MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            return true;
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });


        btn_save.setOnClickListener(this);
        img_pic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageDialog("1");


            }
        });
        img_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageDialog("3");


            }
        });
        ll_view_grivane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i = new Intent (GravanceActivity.this, ViewGrivanceList.class);
             startActivity(i);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void locationManager() {
        if(GlobalVariables.glocation==null){
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, mlistener);
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);
        }
        else {
        }
    }
    private final LocationListener mlistener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            if (Utiilties.isGPSEnabled(GravanceActivity.this)) {
                GlobalVariables.glocation = location;
                    if (location.getLatitude() > 0.0) {
                        if (location.getAccuracy() > 0 && location.getAccuracy() < 150) {
                            getLocationName();
                            progress_finding_location.setVisibility(View.GONE);
                        } else {
                            progress_finding_location.setVisibility(View.VISIBLE);
                        }



                } else {
                    GlobalVariables.glocation.setLatitude(0.0);
                    GlobalVariables.glocation.setLongitude(0.0);
                    GlobalVariables.glocation.setTime(0);
                    getLocationName();
                    progress_finding_location.setVisibility(View.GONE);
                    //takePhoto.setEnabled(true);
                }
            } else {
                GlobalVariables.glocation = location;
                getLocationName();
                progress_finding_location.setVisibility(View.GONE);
            }
            //Toast.makeText(getApplicationContext(), latitude + " WORKS offline " + longitude + "", Toast.LENGTH_LONG).show();

        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    };
    private void getLocationName(){
        try {

            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation( GlobalVariables.glocation.getLatitude(), GlobalVariables.glocation.getLongitude(), 1);
            if (addresses.isEmpty()) {
                et_landmark.setText("Waiting for Location");
            }
            else {
                if (addresses.size() > 0) {

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    et_landmark.setText(new StringBuilder().append(address+",").append(city+",").append(state+",").append(country+",").append(postalCode+",").append(knownName));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        DistLoaderService.bindDistListener(new DistrictBinder() {
            @Override
            public void distLoaded(final ArrayList<DistrictData> districtData) {
                runOnUiThread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        //implement
                        loaded=true;
                        loadDISTSpinnerData(districtData);
                        new GrivanceTypeLoaderService(GravanceActivity.this).execute(userDetails.getCommID().trim());
                    }
                });
            }

            @Override
            public void distNotFound() {
                spn_dist.setAdapter(new ArrayAdapter(GravanceActivity.this, R.layout.dropdownlist, new String[]{}));
            }
        });
        GrivanceTypeLoaderService.bindGTListener(new GrivanceBinder() {
            @Override
            public void grivanceLoaded(ArrayList<GrivanceTypeData> grivanceTypeData) {

                runOnUiThread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        //implement
                        loadGTSpinnerData(grivanceTypeData);
                    }
                });
            }

            @Override
            public void griTypeNotFound() {
                spn_grt.setAdapter( new ArrayAdapter(GravanceActivity.this, R.layout.dropdownlist, new String[]{}));
            }
        });
        if (!loaded) new DistLoaderService(GravanceActivity.this).execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");
                   // byte[] imgData1 = data.getByteArrayExtra("CapturedImage");

                    //imageData.add(imgData);
                    latitude = data.getStringExtra("Lat");
                    longitude = data.getStringExtra("Lng");

                    switch (data.getIntExtra("KEY_PIC", 0)) {

                        case 1:
                            im1 = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            img_pic1.setScaleType(ImageView.ScaleType.FIT_XY);
                            img_pic1.setImageBitmap(Utiilties.GenerateThumbnail(im1,
                                    ThumbnailSize, ThumbnailSize));
                            viewIMG1.setVisibility(View.VISIBLE);
                            imageData1 = imgData;
                            break;

                        case 3:
                            img_loc.setScaleType(ImageView.ScaleType.FIT_XY);
                            im2 = Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500,
                                    500);
                            viewIMG2.setVisibility(View.VISIBLE);
                            img_loc.setImageBitmap(im2);
                            imageData2 = imgData;
                            break;


                    }
                }

        }

    }



    public void onClick_ViewImg(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.viewimage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("- BUIDCo PGRS-");


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
        if (imageData1 != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageData1, 0, imageData1.length);


            imgview.setImageBitmap(bmp);

        }

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });


        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void onClick_ViewImg1(View v) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.viewimage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("- BUIDCo PGRS-");


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
        if (imageData2 != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(imageData2, 0, imageData2.length);


            imgview.setImageBitmap(bmp);

        }

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });


        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void loadDISTSpinnerData(ArrayList<DistrictData> dList) {
        this.district = dList;
        ArrayList<String> DistStringList = new ArrayList<String>();

        DistStringList.add("-- Select Division / Circle --");
        for (int i = 0; i < dList.size(); i++) {
            DistStringList.add(dList.get(i).get_DistNameEn());
        }
        dist_adp = new ArrayAdapter(this, R.layout.dropdownlist, DistStringList);
        spn_dist.setAdapter(dist_adp);
        spn_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    dist_code = dList.get(arg2-1).get_Distcode().trim();
                    dist_name = dList.get(arg2-1).get_DistNameEn().trim();
                    loadBlockData(dist_code);
                }else{
                    dist_code="";
                    dist_name="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void loadBlockData(String dist_code) {

            BlockLoaderService.bindBlockListener(new BlockBinder() {
                @Override
                public void bindBlock(ArrayList<BlockData> blockData) {
                    loadBlockSpinner(blockData);
                }

                @Override
                public void cancleBlockBinding() {
                    Toast.makeText(GravanceActivity.this, "Block Not Found !", Toast.LENGTH_SHORT).show();
                    spn_block.setAdapter(new ArrayAdapter<String>(GravanceActivity.this,R.layout.dropdownlist,new String[]{}));
                }
            });
            new BlockLoaderService(GravanceActivity.this).execute(dist_code.trim());

    }

    private void loadBlockSpinner(ArrayList<BlockData> block_arr) {
        this.block_arr = block_arr;
        ArrayList<String> block_list = new ArrayList<String>();

        block_list.add("-- Select Area --");
        for (BlockData blockData: block_arr) {
            block_list.add(blockData.getBlockname());
        }
      // spn_block.setThreshold(1);
        spn_block.setAdapter(new ArrayAdapter(this, R.layout.dropdownlist, block_list));

        spn_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                   blockEntity=(BlockData)block_arr.get(arg2-1);
                   loadWardData(blockEntity);

                }else{
                    blockEntity=null;
                    //spn_ward.invalidate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void loadWardData(BlockData blockEntity) {
        WardLoaderService.bindWardListener(new WardBinder() {
            @Override
            public void bindWard(ArrayList<Ward> wards) {
                bindWardSpinner(wards);
            }

            @Override
            public void cancleWardBinding() {
                Toast.makeText(GravanceActivity.this, "Ward Not Found !", Toast.LENGTH_SHORT).show();
                spn_ward.setAdapter(new ArrayAdapter<String>(GravanceActivity.this,R.layout.dropdownlist,new String[]{"--No Ward Found--"}));
            }
        });
        new WardLoaderService(GravanceActivity.this).execute(dist_code.trim(),blockEntity.getBlockcode().trim());
    }

    private void bindWardSpinner(ArrayList<Ward> ward_arr) {
        this.ward_arr = ward_arr;
        ArrayList<String> ward_list = new ArrayList<String>();

        ward_list.add("-- Select Ward --");
        for (Ward ward: ward_arr) {
            ward_list.add(ward.getWardname());
        }
        spn_ward.setAdapter(new ArrayAdapter(this, R.layout.dropdownlist, ward_list));
        spn_ward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    wardEntity=(Ward) ward_arr.get(arg2-1);

                }else{
                    wardEntity=null;
                    //spn_ward.invalidate();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void loadGTSpinnerData(ArrayList<GrivanceTypeData> gtList) {
        this.grivance = gtList;
        ArrayList<String> gt_arr = new ArrayList<String>();

        gt_arr.add("-- Select Grievance Type --");
        for (int i = 0; i < gtList.size(); i++) {
            gt_arr.add(gtList.get(i).getDetail());
        }
        grt_adp = new ArrayAdapter(this, R.layout.dropdownlist, gt_arr);
        spn_grt.setAdapter(grt_adp);

        spn_grt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    gt_code = gtList.get(arg2-1).getCode().trim();
                    gt_name = gtList.get(arg2-1).getDetail().trim();
                }else{
                    gt_code="";
                    gt_name="";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_save){
            if (validateForm()){
                UserDetails userDetails=CommonPref.getUserDetails(GravanceActivity.this);
             if (blockEntity==null){
                 if (im1!=null && im2!=null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), "0", "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), Utiilties.BitArrayToString(imageData1),Utiilties.BitArrayToString(imageData2)
                     );
                 }
                 else  if (im1!=null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), "0", "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), Utiilties.BitArrayToString(imageData1)
                     );
                 }
                 else  if (im2!=null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), "0", "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(),"", Utiilties.BitArrayToString(imageData2)
                     );
                 }
                 else{
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), "0", "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), "",""
                     );
                 }
             }else if (wardEntity==null){
                 if (im1!=null && im2!=null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), Utiilties.BitArrayToString(imageData1) ,Utiilties.BitArrayToString(imageData2)
                     );
                 }
                 else  if (im1!=null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), Utiilties.BitArrayToString(imageData1),""
                     );
                 }
                 else  if (im2!=null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(),"", Utiilties.BitArrayToString(imageData2)
                     );
                 }
                 else{
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), "0",
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), "",""
                     );
                 }
             }

             else {
                 if (im1!=null && im2!=null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), wardEntity.getWardCode().trim(),
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), Utiilties.BitArrayToString(imageData1), Utiilties.BitArrayToString(imageData2)
                     );
                 }
                 else if (im1!=null && im2==null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), wardEntity.getWardCode().trim(),
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), Utiilties.BitArrayToString(imageData1),""
                     );
                 }
                 else if (im2!=null && im1==null) {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), wardEntity.getWardCode().trim(),
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), "",Utiilties.BitArrayToString(imageData2)
                     );
                 }else {
                     new GrivanceUploadService(GravanceActivity.this,false).execute(
                             gt_code, etben_fmlyhis.getText().toString().trim(), blockEntity.getBlockcode().trim(), wardEntity.getWardCode().trim(),
                             et_landmark.getText().toString().trim(), et_landmark.getText().toString().trim(), latitude, longitude, dist_code.trim(), "1",
                             userDetails.getUserID().trim(), "",""
                     );
                 }
             }
            }
        }
    }

    private boolean validateForm() {
        boolean isvalid=false;
        if (dist_code.equals("")){
            isvalid=false;
            Toast.makeText(this, "Select Division / Circle !", Toast.LENGTH_SHORT).show();
        }
        else if (blockEntity==null){
            isvalid=false;
            Toast.makeText(this, "Select Block !", Toast.LENGTH_SHORT).show();
        }
       /* else if (wardEntity==null){
            isvalid=false;
            Toast.makeText(this, "Select Ward !", Toast.LENGTH_SHORT).show();
        }*/
        else if (gt_code.equals("")){
            isvalid=false;
            Toast.makeText(this, "Select Grivance Type !", Toast.LENGTH_SHORT).show();
        }else if (etben_fmlyhis.getText().toString().trim().length()<=0){
            isvalid=false;
            etben_fmlyhis.setError("Enter Grivance ! ");
            Toast.makeText(this, "Enter Grivance !", Toast.LENGTH_SHORT).show();
        }
        /*else if (etben_add.getText().toString().trim().length()<=0){
            isvalid=false;
            etben_add.setError("Enter Address ! ");
            Toast.makeText(this, "Enter Address !", Toast.LENGTH_SHORT).show();
        }*/
        else if (et_landmark.getText().toString().trim().length()<=0){
            isvalid=false;
            etben_add.setError("Enter Landmark ! ");
            Toast.makeText(this, "Enter Landmark !", Toast.LENGTH_SHORT).show();
        }

      /*  else if (im1==null){
            isvalid=false;
            Toast.makeText(this, "Take Image !", Toast.LENGTH_SHORT).show();
        }*/
        else{
            isvalid=true;
        }
        return isvalid;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Log Out?")
                .setMessage("Are you sure you want to Log Out?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                      //  GravanceActivity.super.onBackPressed();
                        Intent i =new Intent(GravanceActivity.this,MainActivity.class);
                        startActivity(i);
                    }

                }).create().show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
