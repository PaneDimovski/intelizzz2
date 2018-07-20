package uk.co.intelitrack.intelizzz.components.timerSettings;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizz.SettingsActivity;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.data.Constants;
import uk.co.intelitrack.intelizzz.common.utils.SharedPreferencesUtils;

public class TimerSettingsActivity extends AppCompatActivity {
    Context context;
    @BindView(R.id.btn_alarm_one_2)
    ImageView alarm_one_2;
    @BindView(R.id.btn_alarm_one)
    ImageView alarm_one;
    @BindView(R.id.one_on)
    ImageView one_on;
    @BindView(R.id.one_off)
    ImageView one_off;
    @BindView(R.id.btn_alarm_two_2)
    ImageView alarm_two_2;
    @BindView(R.id.btn_alarm_two)
    ImageView alarm_two;
    @BindView(R.id.two_on)
    ImageView two_on;
    @BindView(R.id.two_off)
    ImageView two_off;
    @BindView(R.id.btn_alarm_three_2)
    ImageView alarm_three_2;
    @BindView(R.id.btn_alarm_three)
    ImageView alarm_three;
    @BindView(R.id.three_on)
    ImageView three_on;
    @BindView(R.id.three_off)
    ImageView three_off;
    @BindView(R.id.btn_alarm_four_2)
    ImageView alarm_four_2;
    @BindView(R.id.btn_alarm_four)
    ImageView alarm_four;
    @BindView(R.id.four_on)
    ImageView four_on;
    @BindView(R.id.four_off)
    ImageView four_off;
    @BindView(R.id.alarm_text)
    EditText textView;
    SharedPreferencesUtils sharedPreferencesUtils;
    LinearLayout linear11,linear22,linear33,linear44;
    @BindView(R.id.btn_ok)
    ImageView button_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context=this;
        ButterKnife.bind(this);

        linear11 =(LinearLayout)findViewById(R.id.linear1);
         linear22 =(LinearLayout)findViewById(R.id.linear2);
        linear33 =(LinearLayout)findViewById(R.id.linear3);
        linear44 =(LinearLayout)findViewById(R.id.linear4);

//        final int sdk = android.os.Build.VERSION.SDK_INT;
//        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//            linear1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.time_chouse_form) );
//        } else {
//            linear1.setBackground(ContextCompat.getDrawable(context, R.drawable.time_chouse_form));
//        }

    }
    @OnClick(R.id.btn_alarm_one_2)
    public void click(View view){
        alarm_one_2.setVisibility(View.INVISIBLE);
        alarm_one.setVisibility(View.VISIBLE);
        one_off.setVisibility(View.INVISIBLE);
        one_on.setVisibility(View.VISIBLE);
        String firstAlarm = textView.getText().toString();
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        sharedPreferencesUtils.setSharedPreferencesString(Constants.FIRST_ALARM,firstAlarm);
//
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linear11.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.time_chouse_form) );
        } else {
            linear11.setBackground(ContextCompat.getDrawable(context, R.drawable.time_chouse_form));
        }

    }
    @OnClick(R.id.btn_alarm_two_2)
    public void click2(View view){
        alarm_two_2.setVisibility(View.INVISIBLE);
        alarm_two.setVisibility(View.VISIBLE);
        two_off.setVisibility(View.INVISIBLE);
        two_on.setVisibility(View.VISIBLE);
        String secondAlarm = textView.getText().toString();
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        sharedPreferencesUtils.setSharedPreferencesString(Constants.SECOND_ALARM,secondAlarm);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linear22.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.time_chouse_form) );
        } else {
            linear22.setBackground(ContextCompat.getDrawable(context, R.drawable.time_chouse_form));
        }

    }
    @OnClick(R.id.btn_alarm_three_2)
    public void click3(View view){
        alarm_three_2.setVisibility(View.INVISIBLE);
        alarm_three.setVisibility(View.VISIBLE);
        three_off.setVisibility(View.INVISIBLE);
        three_on.setVisibility(View.VISIBLE);
        String thirdAlarm = textView.getText().toString();
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        sharedPreferencesUtils.setSharedPreferencesString(Constants.THIRD_ALARM,thirdAlarm);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linear33.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.time_chouse_form) );
        } else {
            linear33.setBackground(ContextCompat.getDrawable(context, R.drawable.time_chouse_form));
        }

    }
    @OnClick(R.id.btn_alarm_four_2)
    public void click4(View view){
        alarm_four_2.setVisibility(View.INVISIBLE);
        alarm_four.setVisibility(View.VISIBLE);
        four_off.setVisibility(View.INVISIBLE);
        four_on.setVisibility(View.VISIBLE);
        String fourthAlarm = textView.getText().toString();
        SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getApplicationContext());
        sharedPreferencesUtils.setSharedPreferencesString(Constants.FOURTH_ALARM,fourthAlarm);
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linear44.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.time_chouse_form) );
        } else {
            linear44.setBackground(ContextCompat.getDrawable(context, R.drawable.time_chouse_form));
        }

    }
    @OnClick(R.id.btn_ok)
    void setings1Click() {


        SettingsActivity.start(this, true);


    }

    @OnClick(R.id.btn_user_back)
    void onBackClick() {
        onBackPressed();
    }
}
