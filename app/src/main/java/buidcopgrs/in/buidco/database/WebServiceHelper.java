package buidcopgrs.in.buidco.database;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import buidcopgrs.in.buidco.entity.AGNUserByLocData;
import buidcopgrs.in.buidco.entity.BlockData;
import buidcopgrs.in.buidco.entity.DistrictData;
import buidcopgrs.in.buidco.entity.GravanceReportData;
import buidcopgrs.in.buidco.entity.GrivanceTypeData;
import buidcopgrs.in.buidco.entity.GrivanceUserWiseData;
import buidcopgrs.in.buidco.entity.PanchayatData;
import buidcopgrs.in.buidco.entity.StatusData;
import buidcopgrs.in.buidco.entity.UserByLocData;
import buidcopgrs.in.buidco.entity.UserDetails;
import buidcopgrs.in.buidco.entity.Versioninfo;
import buidcopgrs.in.buidco.entity.VillageData;
import buidcopgrs.in.buidco.entity.Ward;

public class WebServiceHelper {

//        public static final String SERVICENAMESPACE = "https://grievance.sspmis.in/";
        public static final String SERVICENAMESPACE = "http://buidcopgrs.in/";

    //public static final String SERVICEURL ="https://grievance.sspmis.in/BuidcoPgrsWebService.asmx";
    public static final String SERVICEURL ="http://buidcopgrs.in/BuidcoPgrsWebService.asmx";

    public static final String APPVERSION_METHOD = "getAppLatest";
    public static final String AUTHENTICATE_METHOD = "Authenticate";
    public static final String REGISTER_METHOD = "RegisterCitizens";
    public static final String BLOCK_METHOD = "getCircle";
    public static final String WARD_METHOD = "getWard";
    public static final String PANCHAYAT_METHOD = "getPanchyat";
    public static final String VILLAGE_METHOD = "GetVillage";
    public static final String DIST_METHOD = "getDistrict";
    public static final String STATUS_METHOD = "getStatus";
    public static final String GRIVANCE_TYPE_METHOD = "getGrievanceType";
    public static final String GRIVANCE_USER_WISE_METHOD = "getUsersWiseGrievance";
    public static final String GRIVANCE_PUB_METHOD = "getGrievancePublic";
    public static final String GRIVANCE_ROLE_WISE_METHOD = "getGrievance";
    public static final String GRIVANCE_UPLOAD_METHOD = "RegisterGrievance";
    public static final String GRIVANCE_SWAP_METHOD = "SwapGrievance";
    public static final String GRIVANCE_SOLVE_METHOD = "GrivanceSolve";
    public static final String GET_USER_METHOD = "getUsersWithLocation";
    public static final String FORGET_PASS_METHOD = "ForgetPwd";
    public static final String REPORT_METHOD = "getGrievanceTypeWiseReport";
    private static final String GET_OTP = "GetOtp";
    private static final String CHANGE_PASS = "ChangePassword";


    static String rest;


    public static Versioninfo CheckVersion(String imei,String version) {
        Versioninfo versioninfo;
        SoapObject res1;
        try {

            //res1=getServerData(APPVERSION_METHOD,Versioninfo.Versioninfo_CLASS,"IMEI","Ver",imei,version);
            SoapObject request = new SoapObject(SERVICENAMESPACE, APPVERSION_METHOD);
            request.addProperty("Ver", version);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, Versioninfo.Versioninfo_CLASS.getSimpleName(), Versioninfo.Versioninfo_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + APPVERSION_METHOD, envelope);
            res1 = (SoapObject) envelope.getResponse();
            SoapObject final_object = (SoapObject) res1.getProperty(0);

            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return versioninfo;

    }




   /* public static String sendLocData(EntryDeatils data, String devicename, String Version) {
        SoapObject request = new SoapObject(SERVICENAMESPACE, UPLOAD_STDPHOTO);
        request.addProperty("_DeptId", data.getEntryBy());
        request.addProperty("_SchemeId", data.getAadharNo());
        request.addProperty("DistCode", data.getDistCode());
        request.addProperty("HostelCode", data.getAadharNo());
        request.addProperty("_BeneficieryId", data.getAadharNo());
        request.addProperty("_BenFH_Name",data.getBenFName());
        request.addProperty("_Latitude", data.getLatitude());
        request.addProperty("_Longitude", data.getLongitude());

        request.addProperty("_BenImage", Utiilties.BitArrayToString(data.getPhotoByte1()));
        request.addProperty("_BenCCImage", Utiilties.BitArrayToString(data.getPhotoByte1()));
        request.addProperty("_AppVersion", Version);
        request.addProperty("_DeviceType", devicename);



        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + UPLOAD_STDPHOTO,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();
            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }*/

    public static SoapObject getServerData(String methodName, Class bindClass, String param, String value )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param,value);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static ArrayList<PanchayatData> getPanchayat(String distCode, String blockCode) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                PANCHAYAT_METHOD);

        request.addProperty("distcode", distCode);
        request.addProperty("_BlockCode", blockCode);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, PanchayatData.PanchayatData_CLASS.getSimpleName(), PanchayatData.PanchayatData_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + PANCHAYAT_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<PanchayatData> pvmArrayList = new ArrayList<PanchayatData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PanchayatData block = new PanchayatData(final_object);
                    pvmArrayList.add(block);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }

    public static ArrayList<VillageData> getVillage(String PanchayatCode) {
        SoapObject res1;
        res1=getServerData(VILLAGE_METHOD, VillageData.VillageData_CLASS,"_PanchayatCode",PanchayatCode);
        int TotalProperty=0;
        if(res1!=null)
            TotalProperty= res1.getPropertyCount();
        ArrayList<VillageData> fieldList = new ArrayList<VillageData>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    VillageData sm = new VillageData(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<BlockData> getBlock(String... strings) {
        SoapObject res1;
        res1=getServerData(BLOCK_METHOD, BlockData.BlockData_CLASS,"DistCode",strings[0].trim());
        int TotalProperty=0;
        if(res1!=null)
            TotalProperty= res1.getPropertyCount();
        ArrayList<BlockData> fieldList = new ArrayList<BlockData>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    BlockData sm = new BlockData(final_object);
                    fieldList.add(sm);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<Ward> getward(String... strings) {



        SoapObject request = new SoapObject(SERVICENAMESPACE,WARD_METHOD);

        request.addProperty("DistCode", Integer.parseInt(strings[0]));
        request.addProperty("CircleCode", Integer.parseInt(strings[1]));

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, Ward.warddata.getSimpleName(), Ward.warddata);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + WARD_METHOD, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<Ward> pvmArrayList = new ArrayList<Ward>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Ward block = new Ward(final_object);
                    pvmArrayList.add(block);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }


//    public static ArrayList<Ward> getward(String pCode,String areaType) {
//        SoapObject res1;
//        res1=getServerData(WARD_METHOD,Ward.warddata,"_PanchayatCode",pCode,"_AreaType",areaType);
//        int TotalProperty=0;
//        if(res1!=null)
//            TotalProperty= res1.getPropertyCount();
//        ArrayList<Ward> fieldList = new ArrayList<Ward>();
//        for (int i = 0; i < TotalProperty; i++) {
//            if (res1.getProperty(i) != null) {
//                Object property = res1.getProperty(i);
//                if (property instanceof SoapObject) {
//                    SoapObject final_object = (SoapObject) property;
//                    Ward sm = new Ward(final_object);
//                    fieldList.add(sm);
//                }
//            } else
//                return fieldList;
//        }
//
//        return fieldList;
//    }

   public static UserDetails Login(String User_ID, String Pwd) {
       try {
           SoapObject res1;
           //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
           SoapObject request = new SoapObject(SERVICENAMESPACE, AUTHENTICATE_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
           request.addProperty("UserID", User_ID);
           request.addProperty("Password", Pwd);
           SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
           envelope.dotNet = true;
           envelope.setOutputSoapObject(request);
           envelope.addMapping(SERVICENAMESPACE, UserDetails.USER_CLASS.getSimpleName(), UserDetails.USER_CLASS);
           HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
           androidHttpTransport.call(SERVICENAMESPACE + AUTHENTICATE_METHOD, envelope);
           res1 = (SoapObject) envelope.getResponse();
           if (res1 != null) {
               return new UserDetails(res1);
           } else
               return null;

       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
   }

    public static String RegisterCitizens(String[] strings) {
        String response=null;
        try {

            //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            SoapObject request = new SoapObject(SERVICENAMESPACE, REGISTER_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
            request.addProperty("UserName", strings[0].trim());
            request.addProperty("MobileNo", strings[1].trim());
            request.addProperty("EmailId", strings[2].trim());
            request.addProperty("OTP", Integer.parseInt(strings[3].trim()));
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + REGISTER_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }


    public static ArrayList<DistrictData> distLoader() {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                DIST_METHOD);

        request.addProperty("comCode", "");

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, DistrictData.DISTRICT_CLASS.getSimpleName(),  DistrictData.DISTRICT_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + DIST_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<DistrictData> pvmArrayList = new ArrayList<DistrictData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    DistrictData districtData = new DistrictData(final_object);
                    pvmArrayList.add(districtData);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }

    public static ArrayList<StatusData> statusLoader(String[] arr) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                STATUS_METHOD);

        request.addProperty("Role", arr[0]);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, StatusData.STATUS_CLASS.getSimpleName(),  StatusData.STATUS_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + STATUS_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<StatusData> pvmArrayList = new ArrayList<StatusData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    StatusData districtData = new StatusData(final_object);
                    pvmArrayList.add(districtData);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }

    public static ArrayList<GrivanceTypeData> getGrivanceType(String[] arr) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                GRIVANCE_TYPE_METHOD);

        request.addProperty("comCode", ""+arr[0].trim());

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, GrivanceTypeData.GRIVANCE_TYPE_CLASS.getSimpleName(),  GrivanceTypeData.GRIVANCE_TYPE_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GRIVANCE_TYPE_METHOD,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<GrivanceTypeData> pvmArrayList = new ArrayList<GrivanceTypeData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    GrivanceTypeData districtData = new GrivanceTypeData(final_object);
                    pvmArrayList.add(districtData);
                }
            } else
                return pvmArrayList;
        }


        return pvmArrayList;
    }


    public static ArrayList<GrivanceUserWiseData> getGrivanceUserWise(String[] strings) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                GRIVANCE_USER_WISE_METHOD);

        request.addProperty("UserId", strings[0]);
        request.addProperty("Status", strings[1]);
        request.addProperty("Role", strings[2]);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, GrivanceUserWiseData.GRIVANCE_User_Wise_CLASS.getSimpleName(),  GrivanceUserWiseData.GRIVANCE_User_Wise_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GRIVANCE_USER_WISE_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<GrivanceUserWiseData> grivanceArrayList = new ArrayList<GrivanceUserWiseData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    GrivanceUserWiseData usergrivancedata = new GrivanceUserWiseData(final_object,strings[2].trim());
                    grivanceArrayList.add(usergrivancedata);
                }
            } else
                return grivanceArrayList;
        }


        return grivanceArrayList;
    }

    public static ArrayList<GrivanceUserWiseData> getGrivancePub(String[] strings) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                GRIVANCE_PUB_METHOD);

        request.addProperty("Flag", strings[0]);
        request.addProperty("status", Integer.parseInt(strings[1]));

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, GrivanceUserWiseData.GRIVANCE_User_Wise_CLASS.getSimpleName(),  GrivanceUserWiseData.GRIVANCE_User_Wise_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GRIVANCE_PUB_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<GrivanceUserWiseData> grivanceArrayList = new ArrayList<GrivanceUserWiseData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    GrivanceUserWiseData usergrivancedata = new GrivanceUserWiseData(final_object,"");
                    grivanceArrayList.add(usergrivancedata);
                }
            } else
                return grivanceArrayList;
        }


        return grivanceArrayList;
    }


    public static String UploadGrivance(String[] arr,String  Mob_Num,String User_Name) {
        String response=null;
        try {

            //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            SoapObject request = new SoapObject(SERVICENAMESPACE, GRIVANCE_UPLOAD_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
            request.addProperty("GrievanceType", Integer.parseInt(arr[0]));
            request.addProperty("Description", arr[1]);
            request.addProperty("CircleCode", arr[2]);
            request.addProperty("WardNo", Integer.parseInt(arr[3]));
            request.addProperty("LandMark", arr[4]);
         //   request.addProperty("Address", arr[5]);
            request.addProperty("Latitude", arr[6]);
            request.addProperty("Longitude", arr[7]);
            request.addProperty("DistCode", Integer.parseInt(arr[8]));
            request.addProperty("Status", Integer.parseInt(arr[9]));
            request.addProperty("UserId", arr[10]);
            request.addProperty("PhotoPath", arr[11]);
            request.addProperty("PhotoPath1", arr[12]);
            request.addProperty("MobileNo",Mob_Num);
            request.addProperty("Name",User_Name);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + GRIVANCE_UPLOAD_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }


    public static ArrayList<GrivanceUserWiseData> getGrivanceRoleWise(String[] strings) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                GRIVANCE_ROLE_WISE_METHOD);

        request.addProperty("zoneid", strings[0]);
        request.addProperty("commissionary", strings[1]);
        request.addProperty("dstcode", strings[2]);
        request.addProperty("status", strings[3]);
        request.addProperty("Gtype", strings[4]);
        request.addProperty("role", strings[5]);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, GrivanceUserWiseData.GRIVANCE_User_Wise_CLASS.getSimpleName(),  GrivanceUserWiseData.GRIVANCE_User_Wise_CLASS);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GRIVANCE_ROLE_WISE_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<GrivanceUserWiseData> grivanceArrayList = new ArrayList<GrivanceUserWiseData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    GrivanceUserWiseData usergrivancedata = new GrivanceUserWiseData(final_object,strings[5].trim());
                    grivanceArrayList.add(usergrivancedata);
                }
            } else
                return grivanceArrayList;
        }


        return grivanceArrayList;
    }

    public static String SwapGrivance(String[] arr) {
        String response=null;
        try {

            //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            SoapObject request = new SoapObject(SERVICENAMESPACE, GRIVANCE_SWAP_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
            request.addProperty("GrievanceId", arr[0]);
            request.addProperty("GrievanceType", arr[1]);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + GRIVANCE_SWAP_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;

    }

    public static String SolveGrivance(String[] arr) {
        String response=null;
        try {

            //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            SoapObject request = new SoapObject(SERVICENAMESPACE, GRIVANCE_SOLVE_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
            request.addProperty("GrievanceId", arr[0].trim());
            request.addProperty("ForwardBy", arr[1].trim());
            request.addProperty("ForwardTo", arr[2].trim());
            request.addProperty("Status", Integer.parseInt(arr[3].trim()));
            request.addProperty("AllotDate", arr[4].trim());
            request.addProperty("Remarks", arr[5].trim());
            request.addProperty("PhotoPath", arr[6].trim());
           request.addProperty("AgencyUserId", arr[7].trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + GRIVANCE_SOLVE_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<AGNUserByLocData> getAGNUserByLocation(String[] strings) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                GET_USER_METHOD);

        request.addProperty("comcode", Integer.parseInt(strings[0]));
        request.addProperty("dstcode", Integer.parseInt(strings[1]));
        request.addProperty("role", strings[2]);


        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, AGNUserByLocData.AGNUSER_LOC.getSimpleName(),  AGNUserByLocData.AGNUSER_LOC);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_USER_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<AGNUserByLocData> agnuserByLocData = new ArrayList<AGNUserByLocData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    AGNUserByLocData usergrivancedata = new AGNUserByLocData(final_object);
                    agnuserByLocData.add(usergrivancedata);
                }
            } else
                return agnuserByLocData;
        }


        return agnuserByLocData;
    }

    public static ArrayList<UserByLocData> getUserByLocation(String[] strings) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,
                GET_USER_METHOD);

        request.addProperty("comcode", Integer.parseInt(strings[0]));
        request.addProperty("dstcode", Integer.parseInt(strings[1]));
        request.addProperty("role", strings[2]);


        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, UserByLocData.USER_LOC.getSimpleName(),  UserByLocData.USER_LOC);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + GET_USER_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<UserByLocData> userByLocData = new ArrayList<UserByLocData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    UserByLocData usergrivancedata = new UserByLocData(final_object);
                    userByLocData.add(usergrivancedata);
                }
            } else
                return userByLocData;
        }


        return userByLocData;
    }

    public static String forgetPassword(String MB) {
        String response=null;
        try {

            //res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            SoapObject request = new SoapObject(SERVICENAMESPACE, FORGET_PASS_METHOD);
//           request.addProperty("_DeptId", Dept_Id);
            request.addProperty("MobileNo", MB.trim());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + FORGET_PASS_METHOD, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static ArrayList<GravanceReportData> getGrivanceReportData(String[] strings) {
        SoapObject request = new SoapObject(SERVICENAMESPACE,
                REPORT_METHOD);

        request.addProperty("ZonalId", Integer.parseInt(strings[0]));
        request.addProperty("ComCode", Integer.parseInt(strings[1]));
        request.addProperty("dstcode", Integer.parseInt(strings[2]));


        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE, GravanceReportData.getBlockData_CLASS().getSimpleName(),  GravanceReportData.getBlockData_CLASS());

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL);
            androidHttpTransport.call(SERVICENAMESPACE + REPORT_METHOD,envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<GravanceReportData> userByLocData = new ArrayList<GravanceReportData>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    GravanceReportData usergrivancedata = new GravanceReportData(final_object);
                    userByLocData.add(usergrivancedata);
                }
            } else
                return userByLocData;
        }


        return userByLocData;
    }

    public static String getOTP(String[] strings) {
        String response=null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, GET_OTP);
            request.addProperty("MobileNo", strings[0].trim());
            request.addProperty("Message", strings[1].trim());
            request.addProperty("Action", Integer.parseInt(strings[2].trim()));
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + GET_OTP, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }

    public static String ChangePassword(String[] strings) {
        String response=null;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE, CHANGE_PASS);
            request.addProperty("UserId", strings[0].trim());
            request.addProperty("OldPassword", strings[1].trim());
            request.addProperty("NewPassword", Integer.parseInt(strings[2].trim()));
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL);
            Log.d("TAG", envelope.toString());
            try {
                androidHttpTransport.call(SERVICENAMESPACE + CHANGE_PASS, envelope);
                response = envelope.getResponse().toString();

            } catch (Exception e) {
                response = e.toString();
                e.printStackTrace();
            }
        } catch (Exception e) {
            response = e.toString();
            e.printStackTrace();
        }
        return response;
    }
}
