package com.example.nationalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

public class FacebookSharingActivity extends AppCompatActivity {

    Button shareBtn;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_sharing);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        shareBtn=findViewById(R.id.shareBtn);

        callbackManager=CallbackManager.Factory.create();
        shareDialog=new ShareDialog(this);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent linkContent=new ShareLinkContent.Builder().setQuote("Objava rezultata").build();
                if(ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(linkContent);
                }
            }
        });


    }
}