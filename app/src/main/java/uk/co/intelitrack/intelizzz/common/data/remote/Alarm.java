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
    private String did;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getGuid() {
        return guid;
    }

    public String getStm() {
        return stm;
    }

    public void setStm(String stm) {
        this.stm = stm;
    }

    public String getEtm() {
        return etm;
    }

    public void setEtm(String etm) {
        this.etm = etm;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getSmlng() {
        return smlng;
    }

    public void setSmlng(String smlng) {
        this.smlng = smlng;
    }

    public String getSmlat() {
        return smlat;
    }

    public void setSmlat(String smlat) {
        this.smlat = smlat;
    }

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
}
