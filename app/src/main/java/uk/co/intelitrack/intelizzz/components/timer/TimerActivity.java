package uk.co.intelitrack.intelizzz.components.timer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.data.TimerAlarm;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

/**
 * Created by Filip Stojanovski (filip100janovski@gmail.com).
 */

public class TimerActivity extends AppCompatActivity {

    @BindView(R.id.txtTimerValue)
    TextView mTimerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra(Constants.ID);

        setTimer(id, true);
    }

    private void setTimer(String id, boolean isFirstTime) {
        Calendar current = Calendar.getInstance();
        Calendar tillAlarm = Calendar.getInstance();
        if (current.get(Calendar.HOUR_OF_DAY) == 8 && current.get(Calendar.MINUTE) <= 59) {
            tillAlarm.set(Calendar.HOUR_OF_DAY, 9);
            mTimerValue.setTextColor(getResources().getColor(R.color.red));
            isFirstTime = false;
        } else {
            mTimerValue.setTextColor(getResources().getColor(R.color.text_white));
            if (current.get(Calendar.HOUR_OF_DAY) > 8) {
                tillAlarm.add(Calendar.DAY_OF_MONTH, 1);
            }
            tillAlarm.set(Calendar.HOUR_OF_DAY, 8);
        }

        tillAlarm.set(Calendar.MINUTE, 0);
        tillAlarm.set(Calendar.SECOND, 0);
        tillAlarm.set(Calendar.MILLISECOND, 0);

        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(this);
        if (!TextUtils.isEmpty(id)) {
            sharedPreferencesUtils.addTimer(new TimerAlarm(id, tillAlarm.getTimeInMillis()));
        }

        long milliseconds = tillAlarm.getTimeInMillis() - current.getTimeInMillis();

        CountDownTimer countDownTimer = new MyTimer(milliseconds, 1000, isFirstTime, id);

        countDownTimer.start();
    }

    @OnClick(R.id.btn_back)
    public void onBackClicked() {
        onBackPressed();
    }

    private class MyTimer extends CountDownTimer {

        private boolean isFirstTime;
        private String id;

        public MyTimer(long millisInFuture, long countDownInterval, boolean isFirstTime, String id) {
            super(millisInFuture, countDownInterval);
            this.isFirstTime = isFirstTime;
            this.id = id;
        }

        @Override
        public void onTick(long millis) {
            String timesString = String.format(Locale.getDefault(), "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(millis) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            mTimerValue.setText(timesString);
        }

        @Override
        public void onFinish() {
            String timesString = "00:00:00";
            mTimerValue.setText(timesString);
            if (isFirstTime) {
                setTimer(id, false);
            } else {
                finish();
            }
        }
    }
}
