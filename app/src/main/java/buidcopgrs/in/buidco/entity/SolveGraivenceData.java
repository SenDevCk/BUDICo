package buidcopgrs.in.buidco.entity;

public class SolveGraivenceData {

    private int _id;
    private String GrievanceId;
    private String ForwardBy;
    private String ForwardTo;
    private String Status;
    private String AllotDate;
    private String Remarks;
    private String PhotoPath;
    private String AgencyUserId;

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

    public String getForwardBy() {
        return ForwardBy;
    }

    public void setForwardBy(String forwardBy) {
        ForwardBy = forwardBy;
    }

    public String getForwardTo() {
        return ForwardTo;
    }

    public void setForwardTo(String forwardTo) {
        ForwardTo = forwardTo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getAllotDate() {
        return AllotDate;
    }

    public void setAllotDate(String allotDate) {
        AllotDate = allotDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        PhotoPath = photoPath;
    }

    public String getAgencyUserId() {
        return AgencyUserId;
    }

    public void setAgencyUserId(String agencyUserId) {
        AgencyUserId = agencyUserId;
    }
}
