package buidcopgrs.in.buidco.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import buidcopgrs.in.buidco.Utilitites.Utiilties;
import buidcopgrs.in.buidco.entity.BlockData;
import buidcopgrs.in.buidco.entity.DistrictData;
import buidcopgrs.in.buidco.entity.GrivanceTypeData;
import buidcopgrs.in.buidco.entity.NewGrivanceData;
import buidcopgrs.in.buidco.entity.SolveGraivenceData;
import buidcopgrs.in.buidco.entity.SwapData;
import buidcopgrs.in.buidco.entity.Ward;

;


public class DataBaseHelper extends SQLiteOpenHelper {
    // The Android's default system path of your application database.
    private static String DB_PATH = "";// "/data/data/com.bih.nic.app.biharmunicipalcorporation/databases/";
    //private static String DB_NAME = "chatrawasInsp.db";
    // private static String DB_NAME = "AlertMSGDB";

    private static String DB_NAME = "PACSDB1";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor Takes and keeps a reference of the passed context in order to
     * access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        if (Build.VERSION.SDK_INT >= 29) {
            DB_PATH = context.getDatabasePath(DB_NAME).getPath();
        } else if (Build.VERSION.SDK_INT >= 21) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
            Log.d("DataBase", "exist");
        } else {

            // By calling this method and empty database will be created into
            // the default system path
            // of your application so we are gonna be able to overwrite that
            // database with our database.
            Log.d("DataBase", "exist");
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        String myPath = null;
        try {
            if (Build.VERSION.SDK_INT >= 29) {
                myPath = DB_PATH;
            } else {
                myPath = DB_PATH + DB_NAME;
            }
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS
                            | SQLiteDatabase.OPEN_READWRITE);

        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public boolean databaseExist() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = null;
        if (Build.VERSION.SDK_INT >= 29) {
            outFileName = DB_PATH;
        } else {
            outFileName = DB_PATH + DB_NAME;
        }

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        // Open the database
        String myPath = null;
        if (Build.VERSION.SDK_INT >= 29) {
            myPath = DB_PATH;
        } else {
            myPath = DB_PATH + DB_NAME;
        }
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String CREATE_NEFT_TABLE = "CREATE TABLE NEFT (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,WALLET_ID TEXT,AMOUNT TEXT,UTR_NO TEXT,TOPUP_TIME TEXT,u_id TEXT);";
        String CREATE_STATEMENT_TABLE = "CREATE TABLE Statement (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,CON_ID TEXT,RCPT_NO TEXT,PAY_AMT TEXT,WALLET_BALANCE TEXT,WALLET_ID TEXT,RRFContactNo TEXT,ConsumerContactNo TEXT,transStatus TEXT,MESSAGE_STRING TEXT,Authenticated TEXT,payDate\tTEXT,BILL_NO TEXT,payMode TEXT,CNAME TEXT,IS_PRINTED TEXT,TRANS_ID TEXT,USER_ID TEXT);";
        String CREATE_BookNo_TABLE = "CREATE TABLE BookNo (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,BookNo TEXT,MessageString TEXT,USER_ID TEXT);";
        String CREATE_MRU_TABLE = "CREATE TABLE MRU (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,CON_ID TEXT,ACT_NO TEXT,OLD_CON_ID TEXT,CNAME TEXT,METER_NO TEXT,BOOK_NO TEXT,MOBILE_NO TEXT,PAYBLE_AMOUNT TEXT,BILL_NO TEXT,TARIFF_ID TEXT,MESSAGE_STRING TEXT,DATE_TIME TEXT,FA_HU_NAME TEXT,BILL_ADDR1 TEXT,USER_ID TEXT);";
        db.execSQL(CREATE_NEFT_TABLE);
        db.execSQL(CREATE_STATEMENT_TABLE);
        db.execSQL(CREATE_BookNo_TABLE);
        db.execSQL(CREATE_MRU_TABLE);*/
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;
        //ClearAllTable(db);
        onCreate(db);
        if (oldVersion == 1) {
            Log.d("New Version", "Data can be upgraded");
        }

        Log.d("Sample Data", "onUpgrade	: " + newVersion);
    }


    public long saveSolvedGrivance(String[] strings, Bitmap photo) {

        long c = 0;
        try {


            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("GrievanceId", strings[0]);
            values.put("ForwardBy", strings[1]);
            values.put("ForwardTo", strings[2]);
            values.put("Status", strings[3]);
            values.put("AllotDate", strings[4]);
            values.put("Remarks", strings[5]);
            if (photo != null) {
                values.put("PhotoPath", Utiilties.bitmaptoByte(photo));
            }
            String[] whereArgs = new String[]{strings[0].trim()};

            c = db.update("SolvedGrivance", values, "GrievanceId=? ", whereArgs);

            if (!(c > 0)) {

                c = db.insert("SolvedGrivance", null, values);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;

    }


    public ArrayList<SolveGraivenceData> getSolvedGrivance(String userid) {
        ArrayList<SolveGraivenceData> areadetail = new ArrayList<SolveGraivenceData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from SolvedGrivance where ForwardBy= '" + userid + "' ", null);
            while (cur.moveToNext()) {
                SolveGraivenceData solveGraivenceData = new SolveGraivenceData();
                solveGraivenceData.set_id(cur.getInt(cur.getColumnIndex("_id")));
                solveGraivenceData.setGrievanceId(cur.getString(cur.getColumnIndex("GrievanceId")));
                solveGraivenceData.setForwardBy((cur.getString(cur.getColumnIndex("ForwardBy"))));
                solveGraivenceData.setForwardTo((cur.getString(cur.getColumnIndex("ForwardTo"))));
                solveGraivenceData.setStatus((cur.getString(cur.getColumnIndex("Status"))));
                solveGraivenceData.setAllotDate((cur.getString(cur.getColumnIndex("AllotDate"))));
                solveGraivenceData.setRemarks((cur.getString(cur.getColumnIndex("Remarks"))));
                solveGraivenceData.setPhotoPath(cur.isNull(cur.getColumnIndex("PhotoPath")) == false ? Utiilties.BitArrayToString(cur.getBlob(cur.getColumnIndex("PhotoPath"))) : "");
                solveGraivenceData.setRemarks((cur.getString(cur.getColumnIndex("AgencyUserId"))));
                areadetail.add(solveGraivenceData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areadetail;

    }

    public long getPendingSolve_Count(String userid) {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select _id from SolvedGrivance where ForwardBy= '" + userid + "' ", null);
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return x;
    }


    public long deleteDataUploadedSolved(String grievanceId) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            c = db.delete("SolvedGrivance", "_id=?", new String[]{grievanceId.trim()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    public long saveSwapGrivance(String[] strings) {

        long c = 0;
        try {


            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("GrievanceId", strings[0]);
            values.put("GrievanceType", strings[1]);
            values.put("uid", strings[2]);

            String[] whereArgs = new String[]{strings[0].trim()};

            c = db.update("SwapGrivance", values, "GrievanceId=? ", whereArgs);

            if (!(c > 0)) {

                c = db.insert("SwapGrivance", null, values);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;

    }


    public ArrayList<SwapData> getSwapGrivance(String userid) {
        ArrayList<SwapData> areadetail = new ArrayList<SwapData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from SwapGrivance where uid= '" + userid + "' ", null);
            while (cur.moveToNext()) {
                SwapData solveGraivenceData = new SwapData();
                solveGraivenceData.set_id(cur.getInt(cur.getColumnIndex("_id")));
                solveGraivenceData.setGrievanceId(cur.getString(cur.getColumnIndex("GrievanceId")));
                solveGraivenceData.setGrievanceType((cur.getString(cur.getColumnIndex("GrievanceType"))));
                solveGraivenceData.setUid((cur.getString(cur.getColumnIndex("uid"))));
                areadetail.add(solveGraivenceData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areadetail;

    }

    public long getPendingSwap_Count(String userid) {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select _id from SwapGrivance where uid= '" + userid + "' ", null);
            x = cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return x;
    }


    public long deleteDataUploadedSwap(String grievanceId) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            c = db.delete("SwapGrivance", "_id=?", new String[]{grievanceId.trim()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }


    public long saveDistrict(ArrayList<DistrictData> districtDatas) {
        SQLiteDatabase db = null;
        long c = 0;
        try {
            db = this.getReadableDatabase();
            for (DistrictData districtData : districtDatas) {
                ContentValues values = new ContentValues();
                values.put("_Distcode", districtData.get_Distcode().trim());
                values.put("_DistNameEn", districtData.get_DistNameEn().trim());
                String[] whereArgs = new String[]{districtData.get_Distcode().trim()};
                c = db.update("District", values, "_Distcode=? ", whereArgs);
                if (!(c > 0)) {

                    c = db.insert("District", null, values);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            db.close();
        }
        return c;

    }

    public ArrayList<DistrictData> getDistrict() {
        ArrayList<DistrictData> areadetail = new ArrayList<DistrictData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from District", null);
            while (cur.moveToNext()) {
                DistrictData solveGraivenceData = new DistrictData();
                solveGraivenceData.set_Distcode(cur.getString(cur.getColumnIndex("_Distcode")));
                solveGraivenceData.set_DistNameEn(cur.getString(cur.getColumnIndex("_DistNameEn")));
                areadetail.add(solveGraivenceData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areadetail;

    }

    public long getDistrictCount() {
        long c = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select _Distcode from District", null);
            c=cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }

    public long saveGrievanceType(ArrayList<GrivanceTypeData> grivanceTypeDatas, String comcode) {
        SQLiteDatabase db = null;
        long c = 0;
        try {
            db = this.getReadableDatabase();
            for (GrivanceTypeData grivanceTypeData : grivanceTypeDatas) {
                ContentValues values = new ContentValues();
                values.put("code", grivanceTypeData.getCode().trim());
                values.put("detail", grivanceTypeData.getDetail().trim());
                values.put("Com_Code", comcode.trim());
                String[] whereArgs = new String[]{grivanceTypeData.getCode().trim()};
                c = db.update("GrievanceType", values, "code=?", whereArgs);
                if (!(c > 0)) {

                    c = db.insert("GrievanceType", null, values);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            db.close();
        }
        return c;

    }

    public ArrayList<GrivanceTypeData> getGrievanceType(String com_code) {
        ArrayList<GrivanceTypeData> areadetail = new ArrayList<GrivanceTypeData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from GrievanceType where Com_Code='" + com_code + "'", null);
            while (cur.moveToNext()) {
                GrivanceTypeData grivanceTypeData = new GrivanceTypeData();
                grivanceTypeData.setCode(cur.getString(cur.getColumnIndex("code")));
                grivanceTypeData.setDetail(cur.getString(cur.getColumnIndex("detail")));
                areadetail.add(grivanceTypeData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areadetail;

    }

    public long getGrievanceTypeCount(String com_code) {
        long c = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select code from GrievanceType where Com_Code='" + com_code + "'", null);
            c=cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }

    public long saveBlock(ArrayList<BlockData> blockDataArrayList, String dist_code) {
        SQLiteDatabase db = null;
        long c = 0;
        try {
            db = this.getReadableDatabase();
            for (BlockData blockData : blockDataArrayList) {
                ContentValues values = new ContentValues();
                values.put("Block_Code", blockData.getBlockcode().trim());
                values.put("Block_Name", blockData.getBlockname().trim());
                values.put("CType", blockData.get_CType().trim());
                values.put("Dist_Code", dist_code.trim());
                String[] whereArgs = new String[]{blockData.getBlockcode().trim()};
                c = db.update("Block", values, "Block_Code=?", whereArgs);
                if (!(c > 0)) {

                    c = db.insert("Block", null, values);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            db.close();
        }
        return c;

    }

    public ArrayList<BlockData> getBlock(String dist_code) {
        ArrayList<BlockData> areadetail = new ArrayList<BlockData>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from Block where Dist_Code='" + dist_code + "'", null);
            while (cur.moveToNext()) {
                BlockData grivanceTypeData = new BlockData();
                grivanceTypeData.setBlockcode(cur.getString(cur.getColumnIndex("Block_Code")));
                grivanceTypeData.setBlockname(cur.getString(cur.getColumnIndex("Block_Name")));
                grivanceTypeData.set_CType(cur.getString(cur.getColumnIndex("CType")));
                areadetail.add(grivanceTypeData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areadetail;
    }

    public long getBlockCount(String dist_code) {
        long c = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select Block_Code from Block where Dist_Code='" + dist_code + "'", null);
            c=cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }



    public long saveWard(ArrayList<Ward> wardArrayList, String block_code) {
        SQLiteDatabase db = null;
        long c = 0;
        try {
            db = this.getReadableDatabase();
            for (Ward ward : wardArrayList) {
                ContentValues values = new ContentValues();
                values.put("code", ward.getWardCode().trim());
                values.put("name", ward.getWardname().trim());
                values.put("bcode",block_code.trim());
                values.put("ccode", ward.get_CCode().trim());
                String[] whereArgs = new String[]{block_code.trim()};
                c = db.update("Ward", values, "bcode=? ", whereArgs);
                if (!(c > 0)) {

                    c = db.insert("Ward", null, values);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            db.close();
        }
        return c;

    }

    public ArrayList<Ward> getWard(String block_code) {
        ArrayList<Ward> areadetail = new ArrayList<Ward>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from Ward where bcode='" + block_code + "'", null);
            while (cur.moveToNext()) {
                Ward grivanceTypeData = new Ward();
                grivanceTypeData.setWardCode(cur.getString(cur.getColumnIndex("code")));
                grivanceTypeData.setWardname(cur.getString(cur.getColumnIndex("name")));
                grivanceTypeData.set_CCode(cur.getString(cur.getColumnIndex("ccode")));
                areadetail.add(grivanceTypeData);
            }

            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return areadetail;
    }

    public long getWardCount(String block_code) {
        long c = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select bcode from Ward where bcode='" + block_code + "'", null);
            c=cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }

    public long saveGrivanceData(String[] strings) {
        SQLiteDatabase db = null;
        long c = 0;
        try {
            db = this.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("GrievanceType", strings[0]);
                values.put("Description", strings[1]);
                values.put("CircleCode",strings[2]);
                values.put("WardNo", strings[3]);
                values.put("LandMark",strings[4]);
                values.put("Address", strings[5]);
                values.put("Latitude",strings[6]);
                values.put("Longitude", strings[7]);
                values.put("DistCode",strings[8]);
                values.put("Status", strings[9]);
                values.put("UserId", strings[10]);
                if (!strings[11].trim().equals("")) {
                    values.put("PhotoPath", Utiilties.bitmaptoByte(Utiilties.StringToBitMap(strings[11])));
                }
                if (!strings[12].trim().equals("")) {
                    values.put("PhotoPath1", Utiilties.bitmaptoByte(Utiilties.StringToBitMap(strings[12])));
                }
                //values.put("MobileNo", strings[12]);
                //values.put("Name", strings[13]);
                c = db.insert("NewGrievance", null, values);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        } finally {
            db.close();
        }
        return c;
    }

    public long getNewGriCount(String uid) {
        long c = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select UserId from NewGrievance where UserId='" + uid + "'", null);
            c=cur.getCount();
            cur.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;

    }

    public  ArrayList<NewGrivanceData>  getNewGrievance(String uid) {
       ArrayList<NewGrivanceData> grivanceDataArrayList=new ArrayList<>();
        SQLiteDatabase db=null;
        Cursor cur=null;
        try {
            db = this.getReadableDatabase();
            cur = db.rawQuery("Select * from NewGrievance where UserId='" + uid + "'", null);
            while (cur.moveToNext()) {
                NewGrivanceData newGrivanceData = new NewGrivanceData();
                newGrivanceData.setId(cur.getInt(cur.getColumnIndex("_id")));
                newGrivanceData.setGrievanceType(cur.getString(cur.getColumnIndex("GrievanceType")));
                newGrivanceData.setDescription((cur.getString(cur.getColumnIndex("Description"))));
                newGrivanceData.setCircleCode((cur.getString(cur.getColumnIndex("CircleCode"))));
                newGrivanceData.setWardNo((cur.getString(cur.getColumnIndex("WardNo"))));
                newGrivanceData.setLandMark((cur.getString(cur.getColumnIndex("LandMark"))));
                newGrivanceData.setAddress((cur.getString(cur.getColumnIndex("Address"))));
                newGrivanceData.setLatitude((cur.getString(cur.getColumnIndex("Latitude"))));
                newGrivanceData.setLongitude((cur.getString(cur.getColumnIndex("Longitude"))));
                newGrivanceData.setDistCode((cur.getString(cur.getColumnIndex("DistCode"))));
                newGrivanceData.setStatus((cur.getString(cur.getColumnIndex("Status"))));
                newGrivanceData.setUserId((cur.getString(cur.getColumnIndex("UserId"))));
                newGrivanceData.setPhotoPath(cur.isNull(cur.getColumnIndex("PhotoPath")) == false ? Utiilties.BitArrayToString(cur.getBlob(cur.getColumnIndex("PhotoPath"))) : "");
                newGrivanceData.setPhotoPath1(cur.isNull(cur.getColumnIndex("PhotoPath1")) == false ? Utiilties.BitArrayToString(cur.getBlob(cur.getColumnIndex("PhotoPath1"))) : "");
                grivanceDataArrayList.add(newGrivanceData);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            cur.close();
            db.close();
        }
        return grivanceDataArrayList;

    }

    public long deleteNewGrivanceEntered(int grievanceId) {
        long c = -1;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            c = db.delete("NewGrievance", "_id=?", new String[]{String.valueOf(grievanceId).trim()});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}