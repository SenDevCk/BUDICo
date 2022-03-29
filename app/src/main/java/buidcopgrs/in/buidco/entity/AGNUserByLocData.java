package buidcopgrs.in.buidco.entity;

import org.ksoap2.serialization.SoapObject;

public class AGNUserByLocData {

	private String _UseId;
	private String _role;
	private String _UserName;
	private String _Mobileno;
	public static Class<AGNUserByLocData> AGNUSER_LOC= AGNUserByLocData.class;


	public AGNUserByLocData(SoapObject sobj)
	{

		this.set_UseId(sobj.getProperty(0).toString());
		this.set_role(sobj.getProperty(1).toString());
		this.set_UserName(sobj.getProperty(2).toString());
		this.set_Mobileno(sobj.getProperty(3).toString());
		//this._DistNameHn=sobj.getProperty(2).toString();
	}
	public AGNUserByLocData(){

	}

	public String get_UseId() {
		return _UseId;
	}

	public void set_UseId(String _UseId) {
		this._UseId = _UseId;
	}

	public String get_role() {
		return _role;
	}

	public void set_role(String _role) {
		this._role = _role;
	}

	public String get_UserName() {
		return _UserName;
	}

	public void set_UserName(String _UserName) {
		this._UserName = _UserName;
	}

	public String get_Mobileno() {
		return _Mobileno;
	}

	public void set_Mobileno(String _Mobileno) {
		this._Mobileno = _Mobileno;
	}
}
