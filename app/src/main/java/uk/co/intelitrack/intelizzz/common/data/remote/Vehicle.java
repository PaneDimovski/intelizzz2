package uk.co.intelitrack.intelizzz.common.data.remote;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Vehicle implements Parcelable {
    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };
    @SerializedName("ID")
    private String idAlternative;
    @SerializedName("Name")
    private String nameAlternative;
    private String id;
    private String name;
    private String ic;
    private Dl[] dl;
    private Dl device;
    private String pid;
    private String nm;
    private String phone;
    private boolean isTamper;
    private boolean hasGeofenceAlarm;
    private boolean mWarning;
    private boolean[] days = new boolean[7];
    private boolean mIsChecked;

    public Vehicle() {
        days = new boolean[7];
    }

    protected Vehicle(Parcel in) {
        idAlternative = in.readString();
        nameAlternative = in.readString();
        id = in.readString();
        name = in.readString();
        ic = in.readString();
        pid = in.readString();
        nm = in.readString();
        phone = in.readString();
        isTamper = in.readByte() != 0;
        hasGeofenceAlarm = in.readByte() != 0;
        mWarning = in.readByte() != 0;
        days = in.createBooleanArray();
        mIsChecked = in.readByte() != 0;
    }

    public String getId() {
        if (TextUtils.isEmpty(id)) {
            return idAlternative;
        }
        return id;
    }

    public String getIc() {
        return ic;
    }

    public Dl[] getDl() {
        return dl;
    }

    public void setDl(Dl dl) {
        this.dl = new Dl[1];
        this.dl[0] = dl;
    }

    public String setId(String id) {
        this.id = String.valueOf(id);
        return id;
    }

    public String getPid() {
        return pid;
    }

    public String getNm() {
        return nm;
    }

    public boolean isTamper() {
        return isTamper;
    }

    public void setTamper(boolean tamper) {
        isTamper = tamper;
    }

    public String getName() {
        if (TextUtils.isEmpty(name)) {
            if (TextUtils.isEmpty(nameAlternative)) {
                return nm;
            }
            return nameAlternative;
        }
        return name;
    }

    public boolean hasGeofenceAlarm() {
        return hasGeofenceAlarm;
    }

    public void setHasGeofenceAlarm(boolean hasGeofenceAlarm) {
        this.hasGeofenceAlarm = hasGeofenceAlarm;
    }

    public boolean isWarning() {
        return mWarning;
    }

    public void setWarning(boolean mWarning) {
        this.mWarning = mWarning;
    }

    public boolean[] getDays() {
        return days;
    }

    public int getDaysNumber() {
        int i = 0;
        for (boolean day : days) {
            if (day) {
                i++;
            }
        }
        return i;
    }

    public String getPhone() {
        return phone;
    }

    public Dl getDevice() {
        return device;
    }

    public void setDevice(Dl device) {
        this.device = device;
    }


    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }


    public static class NameComparator implements Comparator<Vehicle> {
        public int compare(Vehicle p1, Vehicle p2) {
            return p1.getName().compareToIgnoreCase(p2.getName());
        }
    }

    public static class IdComparator implements Comparator<Vehicle> {
        public int compare(Vehicle p1, Vehicle p2) {
            int age1 = Integer.parseInt(p1.getId());
            int age2 = Integer.parseInt(p2.getId());

            if (age1 == age2)
                return 0;
            else if (age1 > age2)
                return 1;
            else
                return -1;
        }
    }

    public static class GeoComparator implements Comparator<Vehicle> {
        public int compare(Vehicle p1, Vehicle p2) {
            return Boolean.compare(p1.hasGeofenceAlarm, p2.hasGeofenceAlarm());
        }
    }

    public static class WarningComparator implements Comparator<Vehicle> {
        public int compare(Vehicle p1, Vehicle p2) {
            return Boolean.compare(p1.isWarning(), p2.isWarning());
        }
    }

    public static class DaysComparator implements Comparator<Vehicle> {
        public int compare(Vehicle p1, Vehicle p2) {
            int age1 = p1.getDaysNumber();
            int age2 = p2.getDaysNumber();

            if (age1 == age2)
                return 0;
            else if (age1 > age2)
                return 1;
            else
                return -1;
        }
    }
}
