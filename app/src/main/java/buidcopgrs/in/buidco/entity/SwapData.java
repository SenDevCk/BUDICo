package buidcopgrs.in.buidco.entity;

public class SwapData {
    private int _id;
    private String GrievanceId;
    private String GrievanceType;
    private String uid;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getGrievanceId() {
        return GrievanceId;
    }

    public void setGrievanceId(String grievanceId) {
        GrievanceId = grievanceId;
    }

    public String getGrievanceType() {
        return GrievanceType;
    }

    public void setGrievanceType(String grievanceType) {
        GrievanceType = grievanceType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
