package uk.co.intelitrack.intelizzz.userdetails;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;

public class UserDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
    }

    
    @OnClick(R.id.btn_user_back)
    public void click (View view) {
        Intent intent = new Intent(UserDetailsActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
