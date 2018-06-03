package uk.co.intelitrack.intelizzz.common.broadcastreceivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.components.timer.TimerActivity;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class SmsSentBroadcastReceiver extends BroadcastReceiver {

    private String id;

    public SmsSentBroadcastReceiver(String id) {
        this.id = id;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (getResultCode()) {
            case Activity.RESULT_OK:

                Toast.makeText(context, "SMS sent successfully", Toast.LENGTH_SHORT).show();
                Intent intentTimer = new Intent(context, TimerActivity.class);
                intentTimer.putExtra(Constants.ID, id);
                context.startActivity(intentTimer);
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Toast.makeText(context, "Generic failure cause", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Toast.makeText(context, "Service is currently unavailable", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Toast.makeText(context, "No pdu provided", Toast.LENGTH_SHORT).show();
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Toast.makeText(context, "Radio was explicitly turned off", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
