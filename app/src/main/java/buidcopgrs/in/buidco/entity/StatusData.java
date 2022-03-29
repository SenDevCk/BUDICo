package buidcopgrs.in.buidco.entity;

import org.ksoap2.serialization.SoapObject;

public class StatusData {

	private String _StatusCode;
	private String _StatusDetail;
	public static Class<StatusData> STATUS_CLASS= StatusData.class;


	public StatusData(SoapObject sobj)
	{

		this.set_StatusCode(sobj.getProperty(0).toString());
		this.set_StatusDetail(sobj.getProperty(1).toString());
		//this._DistNameHn=sobj.getProperty(2).toString();
	}
	public StatusData(){

	}


	public String get_StatusCode() {
		return _StatusCode;
	}

	public void set_StatusCode(String _StatusCode) {
		this._StatusCode = _StatusCode;
	}

	public String get_StatusDetail() {
		return _StatusDetail;
	}

	public void set_StatusDetail(String _StatusDetail) {
		this._StatusDetail = _StatusDetail;
	}
}
