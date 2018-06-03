package uk.co.intelitrack.intelizzz.common.data.remote;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Group {
    @SerializedName("ID")
    private String id;

    @SerializedName("Name")
    private String name;

    @SerializedName("CompanyID")
    private String companyId;

    @SerializedName("GroupName")
    private String groupName;

    @SerializedName("ParentCompanyID")
    private String parentCompanyId;

    @SerializedName("Vehicles")
    private Vehicle[] vehicles;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getParentCompanyId() {
        return parentCompanyId;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }
}
