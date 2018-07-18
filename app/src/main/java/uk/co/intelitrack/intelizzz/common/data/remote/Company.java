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

    @SerializedName("unassigned_vehicles")
    private Vehicle[] unassignedVehicles;

    @SerializedName("ID")
    private String id;

    @SerializedName("encryptPwd")
    private String password;

    public void setName(String name) {
        this.name = name;
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

    @SerializedName("address")
    private String address;

    @SerializedName("introduction")
    private String email;

    public Group[] getGroups() {
        return groups;
    }

    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

    public String getName() {
        return name;
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
}
