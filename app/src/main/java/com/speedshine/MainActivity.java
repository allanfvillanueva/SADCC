package com.speedshine;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import android.util.*;
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

public class MainActivity extends  AppCompatActivity  {

    //uid: uhrzwFpnfmWN6lQCWsu4HkPEee12 allan.fvillanueva@gmail.com

    public final int REQ_CD_FP = 101;
    private Timer _timer = new Timer();

    private ArrayList<HashMap<String, Object>> lm = new ArrayList<>();

    private LinearLayout main;
    private ImageView imageview1;

    private TimerTask tmr;
    private Intent i = new Intent();
    private FirebaseAuth fbauth;
    private OnCompleteListener<Void> fbauth_updateEmailListener;
    private OnCompleteListener<Void> fbauth_updatePasswordListener;
    private OnCompleteListener<Void> fbauth_emailVerificationSentListener;
    private OnCompleteListener<Void> fbauth_deleteUserListener;
    private OnCompleteListener<Void> fbauth_updateProfileListener;
    private OnCompleteListener<AuthResult> fbauth_phoneAuthListener;
    private OnCompleteListener<AuthResult> fbauth_googleSignInListener;
    private OnCompleteListener<AuthResult> _fbauth_create_user_listener;
    private OnCompleteListener<AuthResult> _fbauth_sign_in_listener;
    private OnCompleteListener<Void> _fbauth_reset_password_listener;
    private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.main);
        initialize(_savedInstanceState);

        com.google.firebase.FirebaseApp.initializeApp(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
        }
        else {
            //initializeLogic();
            bypassLogic();
        }
    }

    private void bypassLogic() {
        main = (LinearLayout) findViewById(R.id.main);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        fp.setType("image/*");
        fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        tmr = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        i.setClass(getApplicationContext(), AdminDashboardActivity.class);
                        startActivity(i);
                        finish();

                        //i.setClass(getApplicationContext(), AccountActivity.class);
                        //startActivity(i);
                        //finish();

                    }
                });
            }
        };
        _timer.schedule(tmr, (int)(1000));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            initializeLogic();
        }
    }

    private void initialize(Bundle _savedInstanceState) {

        main = (LinearLayout) findViewById(R.id.main);
        imageview1 = (ImageView) findViewById(R.id.imageview1);
        fbauth = FirebaseAuth.getInstance();
        fp.setType("image/*");
        fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        fbauth_updateEmailListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        fbauth_updatePasswordListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        fbauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        fbauth_deleteUserListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        fbauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task){
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

            }
        };

        fbauth_updateProfileListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        fbauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task){
                final boolean _success = task.isSuccessful();
                final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";

            }
        };

        _fbauth_create_user_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _fbauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> _param1) {
                final boolean _success = _param1.isSuccessful();
                final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";

            }
        };

        _fbauth_reset_password_listener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> _param1) {
                final boolean _success = _param1.isSuccessful();

            }
        };
    }

    private void initializeLogic() {
        tmr = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fbauth = FirebaseAuth.getInstance();
                        if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
                            i.setClass(getApplicationContext(), ShopActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {
                            i.setClass(getApplicationContext(), AccountActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }
                });
            }
        };
        _timer.schedule(tmr, (int)(1000));
    }

    @Override
    protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {

        super.onActivityResult(_requestCode, _resultCode, _data);

        switch (_requestCode) {

            default:
                break;
        }
    }

    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }

    @Deprecated
    public int getLocationX(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[0];
    }

    @Deprecated
    public int getLocationY(View _v) {
        int _location[] = new int[2];
        _v.getLocationInWindow(_location);
        return _location[1];
    }

    @Deprecated
    public int getRandom(int _min, int _max) {
        Random random = new Random();
        return random.nextInt(_max - _min + 1) + _min;
    }

    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double)_arr.keyAt(_iIdx));
        }
        return _result;
    }

    @Deprecated
    public float getDip(int _input){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
    }

    @Deprecated
    public int getDisplayWidthPixels(){
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Deprecated
    public int getDisplayHeightPixels(){
        return getResources().getDisplayMetrics().heightPixels;
    }

}
