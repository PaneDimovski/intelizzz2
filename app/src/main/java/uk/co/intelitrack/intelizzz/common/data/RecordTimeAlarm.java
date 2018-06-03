package uk.co.intelitrack.intelizzz.common.data;

import java.util.List;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class RecordTimeAlarm {
    List<TimerAlarm> alarms;

    public RecordTimeAlarm(List<TimerAlarm> alarms) {
        this.alarms = alarms;
    }

    public List<TimerAlarm> getAlarms() {
        return alarms;
    }

    public void setAlarms(List<TimerAlarm> alarms) {
        this.alarms = alarms;
    }
}
