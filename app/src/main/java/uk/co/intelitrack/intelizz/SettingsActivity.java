package uk.co.intelitrack.intelizz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;

public class SettingsActivity extends AppCompatActivity {

//    @BindView(R.id.linear1)
//    Layout linear1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Context context = this;
        ButterKnife.bind(this);


        LinearLayout linear1 =(LinearLayout)findViewById(R.id.linear1);
        LinearLayout linear2 =(LinearLayout)findViewById(R.id.linear2);
        LinearLayout linear3 =(LinearLayout)findViewById(R.id.linear3);
        LinearLayout linear4 =(LinearLayout)findViewById(R.id.linear4);

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            linear1.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.time_chouse_form) );
        } else {
            linear1.setBackground(ContextCompat.getDrawable(context, R.drawable.time_chouse_form));
        }

        //   Ova e za da se menja pozadinata na odbereniot layout , koga ke se odbere kopceto eden, t.e. prviot timer

    }
}
