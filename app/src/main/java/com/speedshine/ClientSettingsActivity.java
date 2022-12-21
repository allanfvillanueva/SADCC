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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import de.hdodenhof.circleimageview.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
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
import android.content.Intent;
import android.content.ClipData;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import android.view.View;
import android.widget.AdapterView;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class ClientSettingsActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_FP = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private String userid = "";
	private HashMap<String, Object> accountDataMap = new HashMap<>();
	
	private ArrayList<String> genderListString = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private ScrollView vsbody;
	private LinearLayout btnBack;
	private TextView textview1;
	private ImageView imageview1;
	private LinearLayout body;
	private LinearLayout boxOverview;
	private LinearLayout linear27;
	private LinearLayout linear5;
	private CircleImageView profile_picture_img;
	private LinearLayout btnChangeProfile;
	private ImageView imageview7;
	private LinearLayout linear6;
	private LinearLayout linear15;
	private Button btnSave;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private TextView textview4;
	private EditText tblastname;
	private TextView textview5;
	private EditText tbfirstname;
	private TextView textview6;
	private EditText tbmiddlename;
	private LinearLayout linear11;
	private LinearLayout linear17;
	private TextView textview3;
	private LinearLayout tbdatebox;
	private TextView tbdate;
	private LinearLayout linear16;
	private ImageView imageview3;
	private Spinner spinnerGenderList;
	private TextView textview7;
	private LinearLayout tbgenderbox;
	private TextView tbgender;
	private LinearLayout linear14;
	private ImageView imageview4;
	
	private DatabaseReference dbaccount = _firebase.getReference("account");
	private ChildEventListener _dbaccount_child_listener;
	private StorageReference fs = _firebase_storage.getReference("uploads/avatar");
	private OnCompleteListener<Uri> _fs_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fs_download_success_listener;
	private OnSuccessListener _fs_delete_success_listener;
	private OnProgressListener _fs_upload_progress_listener;
	private OnProgressListener _fs_download_progress_listener;
	private OnFailureListener _fs_failure_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
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
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_settings);
		initialize(_savedInstanceState);

		Log.d("av","ClientSettingsActivity onCreate");

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
		vsbody = (ScrollView) findViewById(R.id.vsbody);
		btnBack = (LinearLayout) findViewById(R.id.btnBack);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		body = (LinearLayout) findViewById(R.id.body);
		boxOverview = (LinearLayout) findViewById(R.id.boxOverview);
		linear27 = (LinearLayout) findViewById(R.id.linear27);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		profile_picture_img = (CircleImageView) findViewById(R.id.profile_picture_img);
		btnChangeProfile = (LinearLayout) findViewById(R.id.btnChangeProfile);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		btnSave = (Button) findViewById(R.id.btnSave);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		textview4 = (TextView) findViewById(R.id.textview4);
		tblastname = (EditText) findViewById(R.id.tblastname);
		textview5 = (TextView) findViewById(R.id.textview5);
		tbfirstname = (EditText) findViewById(R.id.tbfirstname);
		textview6 = (TextView) findViewById(R.id.textview6);
		tbmiddlename = (EditText) findViewById(R.id.tbmiddlename);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		textview3 = (TextView) findViewById(R.id.textview3);
		tbdatebox = (LinearLayout) findViewById(R.id.tbdatebox);
		tbdate = (TextView) findViewById(R.id.tbdate);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		spinnerGenderList = (Spinner) findViewById(R.id.spinnerGenderList);
		textview7 = (TextView) findViewById(R.id.textview7);
		tbgenderbox = (LinearLayout) findViewById(R.id.tbgenderbox);
		tbgender = (TextView) findViewById(R.id.tbgender);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		fbauth = FirebaseAuth.getInstance();
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btnChangeProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tblastname.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your last name");
					return;
				}
				if (tbfirstname.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your first name");
					return;
				}
				if (tbmiddlename.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your middle name");
					return;
				}
				accountDataMap = new HashMap<>();
				accountDataMap.put("lname", tblastname.getText().toString());
				accountDataMap.put("fname", tbfirstname.getText().toString());
				accountDataMap.put("mname", tbmiddlename.getText().toString());
				accountDataMap.put("bday", tbdate.getText().toString());
				accountDataMap.put("gender", tbgender.getText().toString());
				dbaccount.child(userid).updateChildren(accountDataMap);
				SketchwareUtil.showMessage(getApplicationContext(), "Successfully saved!");
			}
		});
		
		tbdatebox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_showDatePicker();
			}
		});
		
		spinnerGenderList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				tbgender.setText(genderListString.get((int)(_position)));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		tbgenderbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				spinnerGenderList.performClick();
			}
		});
		
		_dbaccount_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		dbaccount.addChildEventListener(_dbaccount_child_listener);
		
		_fs_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fs_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fs_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				dbaccount.child(userid).child("avatar").setValue(_downloadUrl);
				SketchwareUtil.showMessage(getApplicationContext(), "Image successfully uploaded...");
			}
		};
		
		_fs_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_fs_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fs_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
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
		_design();
		userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		dbaccount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
						Map<String,Object> accountDataMap = (Map<String,Object>) _dataSnapshot.child(userid).getValue();
				tbfirstname.setText(accountDataMap.get("fname").toString());
				tbmiddlename.setText(accountDataMap.get("mname").toString());
				tblastname.setText(accountDataMap.get("lname").toString());
				tbdate.setText(accountDataMap.get("bday").toString());
				tbgender.setText(accountDataMap.get("gender").toString());
				if (accountDataMap.containsKey("avatar")) {
					Glide.with(getApplicationContext()).load(Uri.parse(accountDataMap.get("avatar").toString())).into(profile_picture_img);
				}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
		genderListString.add("Male");
		genderListString.add("Female");
		spinnerGenderList.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, genderListString));
		((ArrayAdapter)spinnerGenderList.getAdapter()).notifyDataSetChanged();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				profile_picture_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(0)), 1024, 1024));
				fs.child("avatar-" + userid).putFile(Uri.fromFile(new File(_filePath.get((int)(0))))).addOnFailureListener(_fs_failure_listener).addOnProgressListener(_fs_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return fs.child("avatar-" + userid).getDownloadUrl();
					}}).addOnCompleteListener(_fs_upload_success_listener);
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
	}
	public void _design () {
		android.graphics.drawable.GradientDrawable tblastname_design = new android.graphics.drawable.GradientDrawable();
		tblastname_design.setColor(0xFFFFFFFF);
		tblastname_design.setCornerRadius((float)0);
		tblastname_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tblastname_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tblastname_design, null);
		tblastname.setBackground(tblastname_re);
		android.graphics.drawable.GradientDrawable tbfirstname_design = new android.graphics.drawable.GradientDrawable();
		tbfirstname_design.setColor(0xFFFFFFFF);
		tbfirstname_design.setCornerRadius((float)0);
		tbfirstname_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbfirstname_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbfirstname_design, null);
		tbfirstname.setBackground(tbfirstname_re);
		android.graphics.drawable.GradientDrawable tbmiddlename_design = new android.graphics.drawable.GradientDrawable();
		tbmiddlename_design.setColor(0xFFFFFFFF);
		tbmiddlename_design.setCornerRadius((float)0);
		tbmiddlename_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbmiddlename_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbmiddlename_design, null);
		tbmiddlename.setBackground(tbmiddlename_re);
		android.graphics.drawable.GradientDrawable tbdatebox_design = new android.graphics.drawable.GradientDrawable();
		tbdatebox_design.setColor(0xFFFFFFFF);
		tbdatebox_design.setCornerRadius((float)0);
		tbdatebox_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbdatebox_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbdatebox_design, null);
		tbdatebox.setBackground(tbdatebox_re);
		android.graphics.drawable.GradientDrawable tbgenderbox_design = new android.graphics.drawable.GradientDrawable();
		tbgenderbox_design.setColor(0xFFFFFFFF);
		tbgenderbox_design.setCornerRadius((float)0);
		tbgenderbox_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbgenderbox_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbgenderbox_design, null);
		tbgenderbox.setBackground(tbgenderbox_re);
		android.graphics.drawable.GradientDrawable btnSave_design = new android.graphics.drawable.GradientDrawable();
		btnSave_design.setColor(0xFFDD1D5E);
		btnSave_design.setCornerRadius((float)8);
		btnSave_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnSave_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnSave_design, null);
		btnSave.setBackground(btnSave_re);
		android.graphics.drawable.GradientDrawable btnBack_design = new android.graphics.drawable.GradientDrawable();
		btnBack_design.setColor(0xFFDD1D5E);
		btnBack_design.setCornerRadius((float)50);
		btnBack_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnBack_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnBack_design, null);
		btnBack.setBackground(btnBack_re);
		android.graphics.drawable.GradientDrawable btnChangeProfile_design = new android.graphics.drawable.GradientDrawable();
		btnChangeProfile_design.setColor(0xFFDD1D5E);
		btnChangeProfile_design.setCornerRadius((float)50);
		btnChangeProfile_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnChangeProfile_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnChangeProfile_design, null);
		btnChangeProfile.setBackground(btnChangeProfile_re);
	}
	
	
	public void _extra () {
	}
	
	public static class DatePickerFragment extends androidx.appcompat.app.AppCompatDialogFragment implements DatePickerDialog.OnDateSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		public void onDateSet(DatePicker view, int year, int month, int day) {
			int mon = month +1;
			String date = year + "-" + mon + "-" + day;
			TextView edittext1 = (TextView) getActivity().findViewById(R.id.tbdate);
			edittext1.setText(date);
		}
		
	}
	
	
	public void _showDatePicker () {
		androidx.appcompat.app.AppCompatDialogFragment newFragment = new DatePickerFragment();
		
		newFragment.show(getSupportFragmentManager(), "Date Picker");
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
