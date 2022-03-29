package buidcopgrs.in.buidco.entity;

import org.ksoap2.serialization.SoapObject;

public class VillageData {

    String villcode="";
    String villname="";
    String panCode="";
    public static Class<VillageData> VillageData_CLASS=VillageData.class;

    public VillageData(SoapObject sobj)
    {

        this.villcode=sobj.getProperty(0).toString();
        this.villname=sobj.getProperty(1).toString();
        this.panCode=sobj.getProperty(2).toString();


        this.setVillcode(sobj.getProperty("VillageCode").toString());
        this.setVillname(sobj.getProperty("VillageNm").toString());
        this.setPanCode(sobj.getProperty("PanchayatCode").toString());
        //this.setPanCode(sobj.getProperty("UserName").toString());
    }
    public VillageData() {
        super();
    }



    public String getVillcode() {
        return villcode;
    }

    public void setVillcode(String villcode) {
        this.villcode = villcode;
    }

    public String getVillname() {
        return villname;
    }

    public void setVillname(String villname) {
        this.villname = villname;
    }

    public String getPanCode() {
        return panCode;
    }

    public void setPanCode(String panCode) {
        this.panCode = panCode;
    }
}
