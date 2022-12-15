package com.speedshine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Continuation;
import android.net.Uri;
import java.io.File;
import android.view.View;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class ViewAssetsActivity extends  AppCompatActivity  { 
	
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private String image_view_str = "";
	private String fileName = "";
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout linear2;
	private LinearLayout btnExit;
	private TextView textview3;
	private LinearLayout btnDownload;
	private ImageView imageview4;
	private ImageView imageview5;
	private ImageView viewasset;
	
	private StorageReference fbstorage = _firebase_storage.getReference("uploads/chats/attachments");
	private OnCompleteListener<Uri> _fbstorage_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fbstorage_download_success_listener;
	private OnSuccessListener _fbstorage_delete_success_listener;
	private OnProgressListener _fbstorage_upload_progress_listener;
	private OnProgressListener _fbstorage_download_progress_listener;
	private OnFailureListener _fbstorage_failure_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view_assets);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
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
		header = (LinearLayout) findViewById(R.id.header);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		textview3 = (TextView) findViewById(R.id.textview3);
		btnDownload = (LinearLayout) findViewById(R.id.btnDownload);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		viewasset = (ImageView) findViewById(R.id.viewasset);
		
		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btnDownload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				fileName = "image-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(1000000), (int)(9999999)))));
				_firebase_storage.getReferenceFromUrl(image_view_str).getFile(new File(FileUtil.getPublicDir(Environment.DIRECTORY_PICTURES) + "/SADCC Saved Images/" + fileName + ".png")).addOnSuccessListener(_fbstorage_download_success_listener).addOnFailureListener(_fbstorage_failure_listener).addOnProgressListener(_fbstorage_download_progress_listener);
			}
		});
		
		_fbstorage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fbstorage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fbstorage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				
			}
		};
		
		_fbstorage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				SketchwareUtil.showMessage(getApplicationContext(), "Image successfully saved");
			}
		};
		
		_fbstorage_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fbstorage_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
	}
	
	private void initializeLogic() {
		_design();
		image_view_str = getIntent().getStringExtra("picture_url");
		Glide.with(getApplicationContext()).load(Uri.parse(image_view_str)).into(viewasset);
		btnDownload.setVisibility(View.GONE);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _design () {
		android.graphics.drawable.GradientDrawable btnExit_design = new android.graphics.drawable.GradientDrawable();
		btnExit_design.setColor(0xFFDD1D5E);
		btnExit_design.setCornerRadius((float)50);
		btnExit_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnExit_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnExit_design, null);
		btnExit.setBackground(btnExit_re);
		android.graphics.drawable.GradientDrawable btnDownload_design = new android.graphics.drawable.GradientDrawable();
		btnDownload_design.setColor(0xFFDD1D5E);
		btnDownload_design.setCornerRadius((float)50);
		btnDownload_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnDownload_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDownload_design, null);
		btnDownload.setBackground(btnDownload_re);
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
