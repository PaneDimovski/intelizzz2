package uk.co.intelitrack.intelizzz.common.data.remote;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class UnassignedVehicles {
    @SerializedName("ID")
    private String mId;

    @SerializedName("Name")
    private String mName;

    private boolean mIsChecked;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
