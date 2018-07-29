package uk.co.intelitrack.intelizzz.userdetails;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.intelitrack.intelizzz.R;
import uk.co.intelitrack.intelizzz.common.api.RestApi;
import uk.co.intelitrack.intelizzz.common.api.RestApi2;
import uk.co.intelitrack.intelizzz.common.data.remote.Company;
import uk.co.intelitrack.intelizzz.common.data.remote.Device;
import uk.co.intelitrack.intelizzz.components.login.LoginActivity;

public class UserDetailsActivity extends AppCompatActivity {

    public List<Device> device;
    String companyid;
    RestApi2 api;
    RestApi api1;
    Context context;
    @BindView(R.id.edit_full_name)
    EditText full_name;
    @BindView(R.id.edit_address)
    EditText adress;
    @BindView(R.id.edit_create_password)
    EditText create_password;
    @BindView(R.id.edit_confirm_password)
    EditText confirm_password;
    @BindView(R.id.wrong_pass_icon)
    ImageView wrong_pass_icon;
    @BindView(R.id.edit_email)
    EditText email;
    @BindView(R.id.edit_intelizzz_device_id)
    EditText device_id;



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
        Validation();


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






//
    }

    public void Validation() {
        device = null;
        api = new RestApi2(UserDetailsActivity.this);
        {
            String a = device_id.getText().toString();
            Call<List<Device>> call = api.getValidation(a);
            call.enqueue(new Callback<List<Device>>() {
                @Override
                public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                    if (response.isSuccessful()) {
                        //Toast.makeText(UserDetailsActivity.this, "Unit successfully added", Toast.LENGTH_SHORT).show();
                        device = response.body();
                        if (device!=null && device.size()>0){
                            createUser(device.get(0).getId());
                            String a ="";


                        }else {
                            Toast.makeText(UserDetailsActivity.this, "Not valid device ID", Toast.LENGTH_SHORT).show();
                        }
//                        if (device.size() > 0) {
//                            Toast.makeText(UserDetailsActivity.this, "Unit successfully added", Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(UserDetailsActivity.this, "Error please try again", Toast.LENGTH_SHORT).show();
//                        }
                    } else if (!response.isSuccessful()) {
                        Toast.makeText(UserDetailsActivity.this, "Error please try again", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Device>> call, Throwable t) {
                    Toast.makeText(UserDetailsActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void createUser(String id){
        String user = full_name.getText().toString();
        String address = "testKris2,123,St.test";
        String pass = create_password.getText().toString();
        String confirmpass = confirm_password.getText().toString();
        String legal = user; //"testKris2Account";
        String abbreviation = user; //"testKris2";
        String introduction = user + "@testDesktop.com"; //"Test+Desktop+Account testDesktop@testDesktop.com";
        String remark = "test+remark+from+testDesktop+Account";

//        SharedPreferencesUtils preferencesUtils = new SharedPreferencesUtils(this);
//        String bb = preferencesUtils.getSharedPreferencesString(Constants.DEVICEID);

        Company company = new Company();

        company.setName2(user);
        company.setAddress(address);
        company.setPassword(convertPassMd5(pass));
        company.setPassword(convertPassMd5(confirmpass));
        company.setLegal(legal);
        company.setAbbreviation(abbreviation);
        company.setEmail(introduction);
        company.setRemark(remark);
//        company.setId2(bb);

        //Gson gson = new Gson();
        //String com = gson.toJson(company);

        HashMap<String, Object> requestBody = new HashMap<>();
        requestBody.put("formData", company);


        if (!user.equals("") && pass.equals(confirmpass)) {
            confirm_password.setTextColor(getResources().getColor(R.color.black));
            wrong_pass_icon.setVisibility(View.INVISIBLE);
            api = new RestApi2(context);
            Call<Company> companyCall = api.creatUSer(requestBody);
            companyCall.enqueue(new Callback<Company>() {
                @Override
                public void onResponse(Call<Company> call, Response<Company> response) {
                    if (response.isSuccessful()) {
                        companyId(user,pass,id);
                        String a = "";
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
        if (user.equals("")){
            Toast.makeText(this, "Full name required", Toast.LENGTH_SHORT).show();
        }


        if (!pass.equals(confirmpass)){


            confirm_password.setTextColor(getResources().getColor(R.color.red));
            wrong_pass_icon.setVisibility(View.VISIBLE);


            Toast.makeText(this, "Password don't match", Toast.LENGTH_SHORT).show();
        }


    }

    String compID ;
    String devID ;

    public void companyId(String username,String password, String deviceid){
        companyid = "";
        api1 = new RestApi(UserDetailsActivity.this);
        Call<Device> getcompanyId = api1.getCompanyId(username,password);
        getcompanyId.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                Device user = new Device();
                user = response.body();
                if (user != null && user.getCompanyId()!=""){
                     compID = user.getCompanyId().toString();
                     devID = deviceid;
                    String aa ="";
                    moveVehicle();
                }

            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Toast.makeText(UserDetailsActivity.this, "Error when geting company ID", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void moveVehicle() {


        String[] addressesArr  = {devID};

        List<String[]> addresses = new ArrayList<String[]>();
        HashMap<String, Object> requestBody = new HashMap<>();
        for(int i=0; i < addressesArr.length; i++) {


            addresses.add(addressesArr);
        }


        Device device2 = new Device();
        String b = compID;
        Object[] object = {addressesArr};
        for (Object obj : object) {
            if (obj instanceof String[]) {
                String[] addressesArrRR = (String[]) obj;


                device2.setDeviceIds(addressesArrRR);
                device2.setCompanyId(b);


            }
        }


        requestBody.put("formData",device2 );
        api = new RestApi2(UserDetailsActivity.this);
        Call<Device> deviceCall = api.moveVehicle(requestBody);
        deviceCall.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {





            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {

            }
        });
    }


}
