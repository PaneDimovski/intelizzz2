package uk.co.intelitrack.intelizzz.common.data.remote;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.Comparator;
import java.util.List;

import uk.co.intelitrack.intelizz.MultiCheckGengre;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class ParentVehicle extends ExpandableGroup<Vehicle> {
    private String mId;
    private String mName;
    private List<Vehicle> mVehicles;
    private boolean mIsCompany;
    private boolean mIsChecked;
    private MultiCheckGengre multi;


    public ParentVehicle(String name, List<Vehicle> items, String id, boolean isCompany) {
        super(name, items);
        this.mName = name;
        this.mVehicles = items;
        this.mId = id;
        this.mIsCompany = isCompany;

    }

    public boolean ismIsChecked() {
        return mIsChecked;
    }

    public void setmIsChecked(boolean mIsChecked) {
        this.mIsChecked = mIsChecked;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Vehicle> getVehicles() {
        return mVehicles;
    }

    public boolean isCompany() {
        return mIsCompany;
    }

    private boolean isGeo(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            return true;
        }
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.hasGeofenceAlarm()) {
                return false;
            }
        }
        return true;
    }

    private boolean isWarning(List<Vehicle> vehicles) {
        if (vehicles == null || vehicles.isEmpty()) {
            return false;
        }
        for (Vehicle vehicle : vehicles) {
            if (!vehicle.isWarning()) {
                return false;
            }
        }
        return true;
    }

    private int getActiveDaysNumber() {
        int result = 0;
        for (int i = 0; i <= 6; i++) {
            if (isActiveDay(getVehicles(), i)) {
                result++;
            }
        }
        return result;
    }

    private boolean isActiveDay(List<Vehicle> vehicles, int day) {
        if (vehicles == null || vehicles.isEmpty()) {
            return true;
        }
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getDays() == null || vehicle.getDays().length == 0 || !vehicle.getDays()[day]) {
                return false;
            }
        }
        return true;
    }

    public static class NameComparator implements Comparator<ParentVehicle> {
        public int compare(ParentVehicle p1, ParentVehicle p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    }

    public static class IdComparator implements Comparator<ParentVehicle> {
        public int compare(ParentVehicle p1, ParentVehicle p2) {
            return p1.getId().compareToIgnoreCase(p2.getId());
        }
    }

    public static class GeoComparator implements Comparator<ParentVehicle> {
        public int compare(ParentVehicle p1, ParentVehicle p2) {
            return Boolean.compare(p1.isGeo(p1.getVehicles()), p2.isGeo(p2.getVehicles()));
        }
    }

    public static class WarningComparator implements Comparator<ParentVehicle> {
        public int compare(ParentVehicle p1, ParentVehicle p2) {
            return Boolean.compare(p1.isWarning(p1.getVehicles()), p2.isWarning(p2.getVehicles()));
        }
    }

    public static class DaysComparator implements Comparator<ParentVehicle> {
        public int compare(ParentVehicle p1, ParentVehicle p2) {
            int age1 = p1.getActiveDaysNumber();
            int age2 = p2.getActiveDaysNumber();

            if (age1 == age2)
                return 0;
            else if (age1 > age2)
                return 1;
            else
                return -1;
        }
    }
}
