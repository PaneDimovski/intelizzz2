package uk.co.intelitrack.intelizzz.common.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Device {
    private String did;
    private String type;
    private String vid;

    @SerializedName("id")
    private String id;

    @SerializedName("deviceIds")
    private String[] deviceIds;

    public void setDeviceIds(String[] deviceIds) {
        this.deviceIds = deviceIds;
    }

    @SerializedName("companyId")
    private String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {

        return id;
    }

    public String getDid() {
        return did;
    }

    public String getType() {
        return type;
    }

    public String getVid() {
        return vid;
    }
}
