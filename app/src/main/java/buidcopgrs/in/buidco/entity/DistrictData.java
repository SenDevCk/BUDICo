package buidcopgrs.in.buidco.entity;

import org.ksoap2.serialization.SoapObject;

public class DistrictData {

	private String _Distcode;
	private String _DistNameEn;
	private String _DistNameHn;
	public static Class<DistrictData> DISTRICT_CLASS=DistrictData.class;


	public DistrictData(SoapObject sobj)
	{

		this._Distcode=sobj.getProperty(0).toString();
		this._DistNameEn=sobj.getProperty(1).toString();
		//this._DistNameHn=sobj.getProperty(2).toString();
	}
	public DistrictData(){

	}

	public String get_Distcode() {
		return _Distcode;
	}

	public void set_Distcode(String _Distcode) {
		this._Distcode = _Distcode;
	}

	public String get_DistNameEn() {
		return _DistNameEn;
	}

	public void set_DistNameEn(String _DistNameEn) {
		this._DistNameEn = _DistNameEn;
	}

	public String get_DistNameHn() {
		return _DistNameHn;
	}

	public void set_DistNameHn(String _DistNameHn) {
		this._DistNameHn = _DistNameHn;
	}
}
