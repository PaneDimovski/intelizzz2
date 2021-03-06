package uk.co.intelitrack.intelizzz.common.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Company {
    @SerializedName("Groups")
    private Group[] groups;

    @SerializedName("Name")
    private String name;

    @SerializedName("name")
    private String name2;

    public void setName2(String name2) {
        this.name2 = name2;
    }

    @SerializedName("unassigned_vehicles")
    private Vehicle[] unassignedVehicles;

    @SerializedName("ID")
    private String id;

    @SerializedName("id")
    private String id2;

    @SerializedName("encryptPwd")
    private String password;

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("abbreviation")
    private String abbreviation;
    @SerializedName("legal")
    private String legal;
    @SerializedName("address")
    private String address;
    @SerializedName("introduction")
    private String email;
    @SerializedName("remark")
    private String remark;

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setLegal(String legal) {
        this.legal = legal;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Group[] getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vehicle[] getUnassignedVehicles() {
        return unassignedVehicles;
    }

    public String getId() {
        return id;
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Group group : groups) {
            for (Vehicle vehicle : group.getVehicles()) {
                vehicles.add(vehicle);
            }
        }

        return vehicles;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }
}
