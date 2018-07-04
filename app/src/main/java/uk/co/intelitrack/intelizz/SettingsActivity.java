package uk.co.intelitrack.intelizz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.intelitrack.intelizzz.R;

public class SettingsActivity extends AppCompatActivity {

   @BindView(R.id.rvSelect)
    RecyclerView select;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_select);


        ButterKnife.bind(this);



    }




}
