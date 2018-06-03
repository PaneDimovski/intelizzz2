package uk.co.intelitrack.intelizzz.common.data.remote;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Response {
    private String result;
    private Pagination pagination;

    private Alarm[] alarms;
    private Device[] devices;
    private Online[] onlines;
    private Vehicle[] vehicles;
    private Track[] tracks;
    private Status[] status;
    private Group[] Groups;

    public String getResult() {
        return result;
    }

    public Alarm[] getAlarms() {
        return alarms;
    }

    public Device[] getDevices() {
        return devices;
    }

    public Online[] getOnlines() {
        return onlines;
    }

    public Vehicle[] getVehicles() {
        return vehicles;
    }

    public Track[] getTracks() {
        return tracks;
    }

    public Status[] getStatuses() {
        return status;
    }

    public Group[] getGroups() {
        return Groups;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
