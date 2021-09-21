package com.MRProject.nationalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class FacebookSharingActivity extends AppCompatActivity {

    Button shareBtn;
    CallbackManager callbackManager;
    ShareDialog shareDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_sharing);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        shareBtn = findViewById(R.id.shareBtn);

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);


        ShareLinkContent linkContent = new ShareLinkContent.Builder().setQuote(getResources().getString(R.string.finalScore) + "e")
                .setContentUrl(Uri.parse("https://play.google.com/store")).build();
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            shareDialog.show(linkContent);
        }


    }
}