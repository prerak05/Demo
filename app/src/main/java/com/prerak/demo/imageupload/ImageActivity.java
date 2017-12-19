package com.prerak.demo.imageupload;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.prerak.demo.R;
import com.prerak.demo.imageupload.model.Image;
import com.prerak.demo.services.APIService;
import com.prerak.demo.util.AppConstant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.gson.internal.bind.TypeAdapters.URL;

public class ImageActivity extends AppCompatActivity {
    // variable declaration
    private Button btn_upload,btn_show;
    private APIService mApiService;
    private ProgressBar mProgressBar;
    private static final int INTENT_REQUEST_CODE = 100;
    private String mImageUrl = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        btn_show= (Button) findViewById(R.id.btn_show);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_show.setVisibility(View.GONE);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                try {
                    startActivityForResult(intent, INTENT_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mImageUrl));
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                try {
                    InputStream is = getContentResolver().openInputStream(data.getData());
                    uploadImage(getBytes(is));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void uploadImage(byte[] bytes) {
      mApiService= AppConstant.setupRetrofit(AppConstant.baseURL);
        final RequestBody requestFile= RequestBody.create(MediaType.parse("image/jpeg"),bytes);
        MultipartBody.Part body= MultipartBody.Part.createFormData("image","image.jpg",requestFile);
        Call<Image> imageCall= mApiService.uploadImage(body);
        Log.d("url",imageCall.request().url().toString());
        imageCall.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    Image responseBody = response.body();
                    btn_show.setVisibility(View.VISIBLE);
                    mImageUrl = URL + responseBody.getPath();
                    Toast.makeText(ImageActivity.this, ""+ responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Gson gson = new Gson();
                    try {
                        Image errorResponse = gson.fromJson(errorBody.string(), Image.class);
                        Toast.makeText(ImageActivity.this, "" + errorResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.d("Failure", "onFailure: "+t.getLocalizedMessage());
            }
        });
    }

    private byte[] getBytes(InputStream is) throws IOException{
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];
        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
    }
}
