package buidcopgrs.in.buidco.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class UserDetails implements KvmSerializable {
	public static Class<UserDetails> USER_CLASS = UserDetails.class;
	private boolean _isAuthenticated = false;
	private String DistrictId = "";
	private String BlockCode = "";
	private String District_name = "";
	private String Block_Name = "";
	private String SubDistrict_name="";
	private String SubDistrictCode="";
	private String UserID="";
	private String UserPassword="";
	private String Role="";
	private String UserName="";
	private String MobileNumber="";

	//not done
	private String ZoneID="";
	private String ZoneName="";
	private String CommID="";
	private String CommName="";
	private String isLocked="";
	private String login="";

	public UserDetails(SoapObject obj) {


		
		this.set_isAuthenticated(Boolean.parseBoolean(obj.getProperty("isAuthenticated").toString()));
		if (_isAuthenticated) {
			this.setDistrictId(obj.getProperty("DistrictCode").toString());
			this.setBlockCode(obj.getProperty("BlockCode").toString());
			this.setDistrict_name(obj.getProperty("DistName").toString());
			this.setBlock_Name(obj.getProperty("BlockName").toString());
			//this.setSubDistrict_name(obj.getProperty("SubDistrict_name").toString());
			//this.setSubDistrictCode(obj.getProperty("SubDistrictCode").toString());
			this.setUserID(obj.getProperty("UserId").toString());
			//this.setUserPassword(obj.getProperty("UserPwd").toString());
			this.setRole(obj.getProperty("UserRole").toString());
			this.setUserName(obj.getProperty("UserName").toString());
			this.setMobileNumber(obj.getProperty("MobileNumber").toString());
			this.setZoneID(obj.getProperty("ZoneID").toString());
			this.setZoneName(obj.getProperty("ZoneName").toString());
			this.setCommID(obj.getProperty("CommID").toString());
			this.setCommName(obj.getProperty("CommName").toString());
			this.setIsLocked(obj.getProperty("isLocked").toString());
		}
	}



	public UserDetails() {
	}

	public static Class<UserDetails> getUserClass() {
		return USER_CLASS;
	}

	public static void setUserClass(Class<UserDetails> userClass) {
		USER_CLASS = userClass;
	}


	@Override
	public Object getProperty(int i) {
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 0;
	}

	@Override
	public void setProperty(int i, Object o) {

	}

	@Override
	public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

	}

	public boolean is_isAuthenticated() {
		return _isAuthenticated;
	}

	public void set_isAuthenticated(boolean _isAuthenticated) {
		this._isAuthenticated = _isAuthenticated;
	}

	public String getDistrictId() {
		return DistrictId;
	}

	public void setDistrictId(String districtId) {
		DistrictId = districtId;
	}

	public String getBlockCode() {
		return BlockCode;
	}

	public void setBlockCode(String blockCode) {
		BlockCode = blockCode;
	}

	public String getDistrict_name() {
		return District_name;
	}

	public void setDistrict_name(String district_name) {
		District_name = district_name;
	}

	public String getBlock_Name() {
		return Block_Name;
	}

	public void setBlock_Name(String block_Name) {
		Block_Name = block_Name;
	}

	public String getSubDistrict_name() {
		return SubDistrict_name;
	}

	public void setSubDistrict_name(String subDistrict_name) {
		SubDistrict_name = subDistrict_name;
	}

	public String getSubDistrictCode() {
		return SubDistrictCode;
	}

	public void setSubDistrictCode(String subDistrictCode) {
		SubDistrictCode = subDistrictCode;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getUserPassword() {
		return UserPassword;
	}

	public void setUserPassword(String userPassword) {
		UserPassword = userPassword;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getZoneID() {
		return ZoneID;
	}

	public void setZoneID(String zoneID) {
		ZoneID = zoneID;
	}

	public String getZoneName() {
		return ZoneName;
	}

	public void setZoneName(String zoneName) {
		ZoneName = zoneName;
	}

	public String getCommID() {
		return CommID;
	}

	public void setCommID(String commID) {
		CommID = commID;
	}

	public String getCommName() {
		return CommName;
	}

	public void setCommName(String commName) {
		CommName = commName;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
}
