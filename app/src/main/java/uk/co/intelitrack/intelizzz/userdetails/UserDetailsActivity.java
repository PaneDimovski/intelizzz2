package uk.co.intelitrack.intelizzz.userdetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.ApiInterface;
import uk.co.intelitrack.intelizzz.common.api.RestApi;
import uk.co.intelitrack.intelizzz.common.api.RestApi2;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;

public class UserDetailsActivity extends AppCompatActivity {


    Company company;
    RestApi2 api;
    Context context;
    ApiInterface inter;

    @BindView(R.id.edit_full_name)
    EditText full_name;
    @BindView(R.id.edit_address)
    EditText adress;
    @BindView(R.id.edit_create_password)
    EditText create_password;
    @BindView(R.id.edit_email)
    EditText email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_user_back)
    public void click(View view) {
        Intent intent = new Intent(UserDetailsActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btn_ok)
    public void onClick2() {

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("name", full_name.getText().toString())
//                .addFormDataPart("address", adress.getText().toString())
//                .addFormDataPart("password", create_password.getText().toString())
//                .addFormDataPart("email", email.getText().toString())
//                .build();
//
//        inter.createUser(requestBody).enqueue(new Callback<Company>() {
//                                                  @Override
//                                                  public void onResponse(Call<Company> call, Response<Company> response) {
//                                                      if (response.isSuccessful()) {
//
//
//                                                          Toast.makeText(context, "uspesno", Toast.LENGTH_LONG).show();
//
//                                                      }else if (!response.isSuccessful()){
//                                                          Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
//                                                      }
//                                                  }
//
//                                                  @Override
//                                                  public void onFailure(Call<Company> call, Throwable t) {
//
//                                                  }
//                                              }
//
//        );

        String user = full_name.getText().toString();
        String address = "testKris2,123,St.test";
        String pass = create_password.getText().toString();
        String legal = user; //"testKris2Account";
        String abbreviation = user; //"testKris2";
        String introduction = user + "@testDesktop.com"; //"Test+Desktop+Account testDesktop@testDesktop.com";
        String remark = "test+remark+from+testDesktop+Account";


        Company company = new Company();

        company.setName2(user);
        company.setAddress(address);
        company.setPassword(convertPassMd5(pass));
        company.setLegal(legal);
        company.setAbbreviation(abbreviation);
        company.setEmail(introduction);
        company.setRemark(remark);


        //Gson gson = new Gson();
        //String com = gson.toJson(company);



        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("formData", company);



        api = new RestApi2(context);
        Call<Company> companyCall = api.creatUSer(requestBody);
        companyCall.enqueue(new Callback<Company>() {
            @Override
            public void onResponse(Call<Company> call, Response<Company> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UserDetailsActivity.this, "User successfully created", Toast.LENGTH_SHORT).show();
                } else if (!response.isSuccessful()) {
                    Toast.makeText(UserDetailsActivity.this, "Error something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Company> call, Throwable t) {

            }
        });


    }


    public static String convertPassMd5(String pass) {
        String password = null;
        MessageDigest mdEnc;
        try {
            mdEnc = MessageDigest.getInstance("MD5");
            mdEnc.update(pass.getBytes(), 0, pass.length());
            pass = new BigInteger(1, mdEnc.digest()).toString(16);
            while (pass.length() < 32) {
                pass = "0" + pass;
            }
            password = pass;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        return password;
    }


}
