package uk.co.intelitrack.intelizzz.common.data.remote;

import com.google.gson.annotations.SerializedName;



public class Alarm {
    @SerializedName("vehiIdnos")
    private String guid;

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCondiIdno() {
        return condiIdno;
    }

    public void setCondiIdno(String condiIdno) {
        this.condiIdno = condiIdno;
    }

    public String getTypeIdno() {
        return typeIdno;
    }

    public void setTypeIdno(String typeIdno) {
        this.typeIdno = typeIdno;
    }

    public String getSourceIdno() {
        return sourceIdno;
    }

    public void setSourceIdno(String sourceIdno) {
        this.sourceIdno = sourceIdno;
    }

    public String getVehiColor() {
        return vehiColor;
    }

    public void setVehiColor(String vehiColor) {
        this.vehiColor = vehiColor;
    }

    private String stm;
    private String etm;
    private String vid;

    @SerializedName("condiIdno")
    private String condiIdno;
    @SerializedName("typeIdno")
    private String typeIdno;
    @SerializedName("sourceIdno")
    private String sourceIdno;
    @SerializedName("vehiColor")
    private String vehiColor;

    private String smlng;
    private String smlat;

    private String emlng;
    private String emlat;
    private String did;

    public String getGuid() {
        return guid;
    }

    public String getStm() {
        return stm;
    }

    public String getDid() {
        return did;
    }

    public String getVid() {
        return vid;
    }
}
