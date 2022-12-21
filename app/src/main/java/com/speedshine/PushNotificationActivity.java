package com.speedshine;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.util.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.ImageView;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class PushNotificationActivity extends  AppCompatActivity {

    private Intent i = new Intent();
    private LinearLayout pushnotification;
    private Button button1;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.push_notification);
        initialize(_savedInstanceState);

        Log.d("av","PushNotificationActivity onCreate");

    }

    private void initialize(Bundle _savedInstanceState) {

        pushnotification = (LinearLayout) findViewById(R.id.pushnotification);
        button1 = (Button) findViewById(R.id.button1);
    }

}