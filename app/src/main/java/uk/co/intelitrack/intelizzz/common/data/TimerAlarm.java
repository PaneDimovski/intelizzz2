package uk.co.intelitrack.intelizzz.common.data;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class TimerAlarm {
    private String id;
    private long date;

    public TimerAlarm(String id, long date) {
        this.id = id;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public long getDate() {
        return date;
    }
}
