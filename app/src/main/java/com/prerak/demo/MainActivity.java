package com.prerak.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.prerak.demo.model.Login;
import com.prerak.demo.services.APIService;
import com.prerak.demo.util.AppConstant;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // variable declaration
    EditText et_mobileNo, et_passwd;
    Button btn_submit;
    String sMobileNo, sPassword, str_userToken;
    ProgressBar progressBar;
    private APIService mAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // method initialization
        init();
    }

    private void init() {
        et_mobileNo = (EditText) findViewById(R.id.et_mobileNo);
        et_passwd = (EditText) findViewById(R.id.et_passwd);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View view) {
        if (AppConstant.isNetworkAvailable(this)) {
            if (isValidate()) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setClickable(true);
                str_userToken = Base64.encodeToString((sMobileNo + "-" + sPassword).getBytes(), Base64.DEFAULT);
                Map<String,String> loginMap = new HashMap<>();
                loginMap.put("contactNo",sMobileNo);
                loginMap.put("password",sPassword);
                loginMap.put("mobileToken","");
                mAPIService = AppConstant.setupRetrofit(AppConstant.baseURL);
                Call<Login> loginCall= mAPIService.getLoginCall(loginMap);
                Log.d("url", loginCall.request().url().toString());
                loginCall.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.body() != null) {
                            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Toast.makeText(this, "No Internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidate() {
        sMobileNo = et_mobileNo.getText().toString();
        sPassword = et_passwd.getText().toString();
        if (sMobileNo.equals("") && sMobileNo == null) {
            et_mobileNo.setError("Please enter mobile number");
            et_mobileNo.requestFocus();
            return false;
        } else if (sMobileNo.length() < 10) {
            et_mobileNo.setError("Mobile number must be 10 digit");
            et_mobileNo.requestFocus();
            return false;
        } else if (sPassword.equals("") && sPassword == null) {
            et_passwd.setError("Please enter password");
            et_passwd.requestFocus();
            return false;
        }
        return true;
    }
}
