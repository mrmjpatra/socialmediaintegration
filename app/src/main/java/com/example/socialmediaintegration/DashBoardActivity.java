package com.example.socialmediaintegration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

public class DashBoardActivity extends AppCompatActivity {
    ImageView fbphoto;
    TextView fbname,fbemail;
    Button fblogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        fbphoto=findViewById(R.id.fbphoto);
        fbname=findViewById(R.id.fbname);
        fblogout=findViewById(R.id.fblogout);
        fbemail=findViewById(R.id.fbemail);
        AccessToken accessToken=AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Insert your code here
                        try {
                            String fullName= object.getString("name");
                            String url=object.getJSONObject("picture").getJSONObject("data").getString("url");
                            String email=object.getString("email");
                            fbemail.setText(email);
                            fbname.setText(fullName);
                            if (url!=null){
                                Glide.with(DashBoardActivity.this).load(url).into(fbphoto);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,picture.type(large),email");
        request.setParameters(parameters);
        request.executeAsync();
        fblogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                startActivity(new Intent(DashBoardActivity.this,LoginActivity.class));
                finish();
            }
        });
    }
}