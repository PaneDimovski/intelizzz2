package uk.co.intelitrack.intelizzz.common.data.remote;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Alarm {
    private String guid;
    private String stm;
    private String etm;
    private String vid;

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
