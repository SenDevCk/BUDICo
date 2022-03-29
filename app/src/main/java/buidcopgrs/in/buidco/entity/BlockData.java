package buidcopgrs.in.buidco.entity;


import org.ksoap2.serialization.SoapObject;

public class BlockData {
    String _Block_Code="";
    String _Block_Name="";
    private String _CType="";



    String DistCode="";
    public static Class<BlockData> BlockData_CLASS=BlockData.class;


    public BlockData(SoapObject sobj)
    {

        this._Block_Code=sobj.getProperty(0).toString();
        this._Block_Name=sobj.getProperty(1).toString();
        this._CType=sobj.getProperty(2).toString();
    }
    public BlockData() {
        super();
    }

    public String getBlockcode() {
        return _Block_Code;
    }

    public void setBlockcode(String _blockcode) {
        _Block_Code = _blockcode;
    }

    public String getBlockname() {
        return _Block_Name;
    }

    public void setBlockname(String _blockname) {
        _Block_Name = _blockname;
    }
    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String _distCode) {
        DistCode = _distCode;
    }

    public String get_CType() {
        return _CType;
    }

    public void set_CType(String _CType) {
        this._CType = _CType;
    }
}
