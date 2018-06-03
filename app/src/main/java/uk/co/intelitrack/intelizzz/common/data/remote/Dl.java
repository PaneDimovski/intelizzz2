package uk.co.intelitrack.intelizzz.common.data.remote;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class Dl {
    private String id;
    private String tn;
    private String ic;
    private String md;
    private String tc;
    private String cn;
    private String pid;
    private String cc;
    private String io;
    private String sim;

    public Dl(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getIo() {
        return io;
    }

    public String getSim() {
        return sim;
    }
}
