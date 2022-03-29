package buidcopgrs.in.buidco.Utilitites;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import buidcopgrs.in.buidco.entity.UserDetails;


public class CommonPref {

	static Context context;

	CommonPref() {

	}

	CommonPref(Context context) {
		CommonPref.context = context;
	}



	public static void setUserDetails(Context context, UserDetails userInfo) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		editor.putString("DistrictId", userInfo.getDistrictId());
		//editor.putString("DistrictId", "230");
		editor.putString("BlockCode", userInfo.getBlockCode());
		editor.putString("District_name", userInfo.getDistrict_name());
		editor.putString("Block_Name", userInfo.getBlock_Name());
		editor.putString("MobileNumber", userInfo.getMobileNumber());
		editor.putString("ZoneID", userInfo.getZoneID());
		editor.putString("ZoneName", userInfo.getZoneName());
		editor.putString("CommID", userInfo.getCommID());
		editor.putString("CommName", userInfo.getCommName());

		editor.putString("SubDistrict_name", userInfo.getSubDistrict_name());
		editor.putString("SubDistrictCode", userInfo.getSubDistrictCode());
		editor.putString("UserID", userInfo.getUserID());
		editor.putString("UserPassword", userInfo.getUserPassword());

		editor.putString("Role", userInfo.getRole());
		editor.putString("UserName", userInfo.getUserName());
		editor.putString("login", "yes");

		editor.commit();

	}

	public static UserDetails getUserDetails(Context context) {

		String key = "_USER_DETAILS";
		UserDetails userInfo = new UserDetails();
		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

	userInfo.setDistrictId(prefs.getString("DistrictId", ""));
		userInfo.setBlockCode(prefs.getString("BlockCode", ""));
		userInfo.setDistrict_name(prefs.getString("District_name", ""));
		userInfo.setBlock_Name(prefs.getString("Block_Name", ""));
		userInfo.setSubDistrict_name(prefs.getString("SubDistrict_name", ""));
		userInfo.setSubDistrictCode(prefs.getString("SubDistrictCode", ""));
		userInfo.setUserID(prefs.getString("UserID", ""));
		userInfo.setUserPassword(prefs.getString("UserPassword", ""));
		userInfo.setRole(prefs.getString("Role", ""));
		userInfo.setUserName(prefs.getString("UserName", ""));
		userInfo.setCommID(prefs.getString("CommID", ""));
		userInfo.setCommName(prefs.getString("CommName", ""));
		userInfo.setMobileNumber(prefs.getString("MobileNumber", ""));
		userInfo.setZoneName(prefs.getString("ZoneName", ""));
		userInfo.setZoneID(prefs.getString("ZoneID", ""));
		userInfo.setLogin(prefs.getString("login", ""));
		return userInfo;
	}

	

	public static void setCheckUpdate(Context context, long dateTime) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();

		
		dateTime=dateTime+1*3600000;
		editor.putLong("LastVisitedDate", dateTime);

		editor.commit();

	}

	public static int getCheckUpdate(Context context) {

		String key = "_CheckUpdate";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		long a = prefs.getLong("LastVisitedDate", 0);

		
		if(System.currentTimeMillis()>a)
			return 1;
		else
			return 0;
	}

	public static void setAwcId(Activity activity, String awcid){
		String key = "_Awcid";
		SharedPreferences prefs = activity.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("code2", awcid);
		editor.commit();
	}
	public static void clearLog(Context context) {

		String key = "_USER_DETAILS";

		SharedPreferences prefs = context.getSharedPreferences(key,
				Context.MODE_PRIVATE);

		Editor editor = prefs.edit();
		editor.putString("DistrictId", "");
		editor.putString("BlockCode", "");
		editor.putString("District_name", "");
		editor.putString("Block_Name", "");
		editor.putString("MobileNumber", "");
		editor.putString("ZoneID", "");
		editor.putString("ZoneName", "");
		editor.putString("CommID", "");
		editor.putString("CommName", "");

		editor.putString("SubDistrict_name","");
		editor.putString("SubDistrictCode","");
		editor.putString("UserID", "");
		editor.putString("UserPassword", "");

		editor.putString("Role", "PUB");
		editor.putString("UserName","");
		editor.putString("login", "");
		editor.commit();

	}

}
