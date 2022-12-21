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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import java.io.File;
import android.content.ClipData;
import java.util.Timer;
import java.util.TimerTask;
import android.provider.MediaStore;
import android.os.Build;
import androidx.core.content.FileProvider;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class VerificationAreaActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_GL = 101;
	public final int REQ_CD_FP = 102;
	public final int REQ_CD_C = 103;
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private  GoogleSignInOptions gso;
	private String getAccountId = "";
	private HashMap<String, Object> AccountInfoMap = new HashMap<>();
	private String file = "";
	private String file_name = "";
	private HashMap<String, Object> mapFromDb = new HashMap<>();
	private boolean UploadedValidBoolean = false;
	private boolean takeASelfieBoolean = false;
	private String selfie_file = "";
	private String selfie_file_name = "";
	
	private LinearLayout linear1;
	private LinearLayout header;
	private LinearLayout linear4;
	private ScrollView vs;
	private LinearLayout step2_processing_box;
	private LinearLayout step3_successfully_box;
	private TextView textview1;
	private TextView textview19;
	private TextView tbEmail;
	private Button btnLogout;
	private LinearLayout step1_verification_box;
	private TextView textview2;
	private TextView textview3;
	private LinearLayout linear6;
	private TextView textview21;
	private LinearLayout valid_id_preview_box;
	private LinearLayout btnUploadID;
	private TextView textview22;
	private LinearLayout selfie_preview_box;
	private LinearLayout btnTookAPicture;
	private LinearLayout btnDone;
	private LinearLayout linear10;
	private LinearLayout linear13;
	private LinearLayout linear11;
	private LinearLayout linear12;
	private TextView textview9;
	private TextView textview4;
	private TextView textview10;
	private TextView textview5;
	private LinearLayout linear14;
	private LinearLayout linear15;
	private TextView textview11;
	private TextView textview6;
	private TextView textview12;
	private TextView textview7;
	private LinearLayout valid_id_preview_text;
	private ImageView valid_id_preview_img;
	private ImageView imageview5;
	private TextView textview15;
	private ImageView imageview1;
	private TextView textview8;
	private LinearLayout selfie_preview_text;
	private ImageView selfie_preview_img;
	private ImageView imageview6;
	private TextView textview23;
	private ImageView imageview4;
	private TextView textview20;
	private TextView textview24;
	private ImageView imageview2;
	private TextView textview16;
	private TextView textview17;
	private ImageView imageview3;
	private TextView textview18;
	private Button btnShopNow;
	
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
	private GoogleSignInClient gl;
	private DatabaseReference fbdb = _firebase.getReference("account");
	private ChildEventListener _fbdb_child_listener;
	private Intent i = new Intent();
	private AlertDialog.Builder d;
	private StorageReference fs = _firebase_storage.getReference("uploads/ids");
	private OnCompleteListener<Uri> _fs_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fs_download_success_listener;
	private OnSuccessListener _fs_delete_success_listener;
	private OnProgressListener _fs_upload_progress_listener;
	private OnProgressListener _fs_download_progress_listener;
	private OnFailureListener _fs_failure_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private ProgressDialog pd;
	private TimerTask tmr;
	private Intent c = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	private File _file_c;
	private StorageReference fs1 = _firebase_storage.getReference("uploads/selfies");
	private OnCompleteListener<Uri> _fs1_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fs1_download_success_listener;
	private OnSuccessListener _fs1_delete_success_listener;
	private OnProgressListener _fs1_upload_progress_listener;
	private OnProgressListener _fs1_download_progress_listener;
	private OnFailureListener _fs1_failure_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.verification_area);
		initialize(_savedInstanceState);

		Log.d("av","VerificationAreaActivity onCreate");

		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
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
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		header = (LinearLayout) findViewById(R.id.header);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		vs = (ScrollView) findViewById(R.id.vs);
		step2_processing_box = (LinearLayout) findViewById(R.id.step2_processing_box);
		step3_successfully_box = (LinearLayout) findViewById(R.id.step3_successfully_box);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview19 = (TextView) findViewById(R.id.textview19);
		tbEmail = (TextView) findViewById(R.id.tbEmail);
		btnLogout = (Button) findViewById(R.id.btnLogout);
		step1_verification_box = (LinearLayout) findViewById(R.id.step1_verification_box);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview21 = (TextView) findViewById(R.id.textview21);
		valid_id_preview_box = (LinearLayout) findViewById(R.id.valid_id_preview_box);
		btnUploadID = (LinearLayout) findViewById(R.id.btnUploadID);
		textview22 = (TextView) findViewById(R.id.textview22);
		selfie_preview_box = (LinearLayout) findViewById(R.id.selfie_preview_box);
		btnTookAPicture = (LinearLayout) findViewById(R.id.btnTookAPicture);
		btnDone = (LinearLayout) findViewById(R.id.btnDone);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear13 = (LinearLayout) findViewById(R.id.linear13);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear12 = (LinearLayout) findViewById(R.id.linear12);
		textview9 = (TextView) findViewById(R.id.textview9);
		textview4 = (TextView) findViewById(R.id.textview4);
		textview10 = (TextView) findViewById(R.id.textview10);
		textview5 = (TextView) findViewById(R.id.textview5);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		textview11 = (TextView) findViewById(R.id.textview11);
		textview6 = (TextView) findViewById(R.id.textview6);
		textview12 = (TextView) findViewById(R.id.textview12);
		textview7 = (TextView) findViewById(R.id.textview7);
		valid_id_preview_text = (LinearLayout) findViewById(R.id.valid_id_preview_text);
		valid_id_preview_img = (ImageView) findViewById(R.id.valid_id_preview_img);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview15 = (TextView) findViewById(R.id.textview15);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview8 = (TextView) findViewById(R.id.textview8);
		selfie_preview_text = (LinearLayout) findViewById(R.id.selfie_preview_text);
		selfie_preview_img = (ImageView) findViewById(R.id.selfie_preview_img);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		textview23 = (TextView) findViewById(R.id.textview23);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview20 = (TextView) findViewById(R.id.textview20);
		textview24 = (TextView) findViewById(R.id.textview24);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview16 = (TextView) findViewById(R.id.textview16);
		textview17 = (TextView) findViewById(R.id.textview17);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		textview18 = (TextView) findViewById(R.id.textview18);
		btnShopNow = (Button) findViewById(R.id.btnShopNow);
		fbauth = FirebaseAuth.getInstance();
		d = new AlertDialog.Builder(this);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		_file_c = FileUtil.createNewPictureFile(getApplicationContext());
		Uri _uri_c = null;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			_uri_c= FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", _file_c);
		}
		else {
			_uri_c = Uri.fromFile(_file_c);
		}
		c.putExtra(MediaStore.EXTRA_OUTPUT, _uri_c);
		c.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("Logout?");
				d.setMessage("Do you want to logout?");
				d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						fbauth.signOut();
						
						gl.signOut().addOnCompleteListener(VerificationAreaActivity.this, new OnCompleteListener<Void>() {
								
								@Override
								
								public void onComplete(@NonNull Task<Void> task) {
										i.setClass(getApplicationContext(), AccountActivity.class);
								startActivity(i);
								finish();
								}
						});
					}
				});
				d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		
		btnUploadID.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		btnTookAPicture.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(c, REQ_CD_C);
			}
		});
		
		btnDone.setOnLongClickListener(new View.OnLongClickListener() {
			 @Override
				public boolean onLongClick(View _view) {
				
				return true;
				}
			 });
		
		btnDone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (UploadedValidBoolean && takeASelfieBoolean) {
					btnDone.setEnabled(false);
					d.setTitle("Submit");
					d.setMessage("Do you want to submit your valid id?");
					d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							fs.child("valid-id-".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).putFile(Uri.fromFile(new File(file))).addOnFailureListener(_fs_failure_listener).addOnProgressListener(_fs_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return fs.child("valid-id-".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).getDownloadUrl();
								}}).addOnCompleteListener(_fs_upload_success_listener);
							fs1.child("selfie-picture-".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).putFile(Uri.fromFile(new File(file_name))).addOnFailureListener(_fs1_failure_listener).addOnProgressListener(_fs1_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
								@Override
								public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
									return fs1.child("selfie-picture-".concat(FirebaseAuth.getInstance().getCurrentUser().getUid())).getDownloadUrl();
								}}).addOnCompleteListener(_fs1_upload_success_listener);
							pd.setTitle("Uploading");
							pd.setMessage("Please wait uploading your id.");
							pd.setProgress((int)0);
							pd.setCancelable(false);
							pd.setCanceledOnTouchOutside(false);
							pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
							pd.show();
						}
					});
					d.setNegativeButton("No", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					d.create().show();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "You must select/attach any kind of valid id.");
					btnDone.setEnabled(false);
					android.graphics.drawable.GradientDrawable btnDone_design = new android.graphics.drawable.GradientDrawable();
					btnDone_design.setColor(0xFF898F99);
					btnDone_design.setCornerRadius((float)10);
					btnDone_design.setStroke((int)0,Color.TRANSPARENT);
					android.graphics.drawable.RippleDrawable btnDone_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDone_design, null);
					btnDone.setBackground(btnDone_re);
					tmr = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									btnDone.setEnabled(true);
									android.graphics.drawable.GradientDrawable btnDone_design = new android.graphics.drawable.GradientDrawable();
									btnDone_design.setColor(0xFFDD1D5E);
									btnDone_design.setCornerRadius((float)10);
									btnDone_design.setStroke((int)0,Color.TRANSPARENT);
									android.graphics.drawable.RippleDrawable btnDone_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDone_design, null);
									btnDone.setBackground(btnDone_re);
								}
							});
						}
					};
					_timer.schedule(tmr, (int)(1000));
				}
			}
		});
		
		btnShopNow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ShopActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		_fbdb_child_listener = new ChildEventListener() {
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
				mapFromDb = new Gson().fromJson(new Gson().toJson(_childValue), new TypeToken<HashMap<String, Object>>(){}.getType());
				if (mapFromDb.get("verified").toString().equals("0")) {
					vs.setVisibility(View.VISIBLE);
					step2_processing_box.setVisibility(View.GONE);
					step3_successfully_box.setVisibility(View.GONE);
				}
				if (mapFromDb.get("verified").toString().equals("1")) {
					vs.setVisibility(View.GONE);
					step2_processing_box.setVisibility(View.VISIBLE);
					step3_successfully_box.setVisibility(View.GONE);
				}
				if (mapFromDb.get("verified").toString().equals("2")) {
					vs.setVisibility(View.GONE);
					step2_processing_box.setVisibility(View.GONE);
					step3_successfully_box.setVisibility(View.VISIBLE);
				}
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
		fbdb.addChildEventListener(_fbdb_child_listener);
		
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
				pd.dismiss();
				fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
								fbdb.child(getAccountId).child("valid_id").setValue(_downloadUrl);
						fbdb.child(getAccountId).child("verified").setValue("1");
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
				});
				d.setTitle("Success");
				d.setMessage("Your id was successfully submitted, \n\nNote: Please wait atleast 3 bussiness days for us validate your uploaded id and get verifed.");
				d.setPositiveButton("Okay i get it", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
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
		
		_fs1_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fs1_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fs1_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				fbdb.child(getAccountId).child("selfie_id").setValue(_downloadUrl);
			}
		};
		
		_fs1_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_fs1_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fs1_failure_listener = new OnFailureListener() {
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
		// Functions To Load
		_design();
		takeASelfieBoolean = false;
		UploadedValidBoolean = false;
		// Retrieve Account Data
		fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					getAccountId = FirebaseAuth.getInstance().getCurrentUser().getUid();
				Map<String,Object> AccountInfoMap = (Map<String,Object>) _dataSnapshot.child(getAccountId).getValue();
				tbEmail.setText(AccountInfoMap.get("email").toString());
				if (_dataSnapshot.child(getAccountId).child("verified").getValue().toString().equals("1")) {
					vs.setVisibility(View.GONE);
					step2_processing_box.setVisibility(View.VISIBLE);
					step3_successfully_box.setVisibility(View.GONE);
				}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
		// Init Google Login
		fbauth = FirebaseAuth.getInstance();
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken("1090849595737-8p185b3f3pru1oc1c68tlqp6so2r027v.apps.googleusercontent.com").build();
		gl = GoogleSignIn.getClient(this,gso);
		// Init Progress Dialog
		pd = new ProgressDialog(VerificationAreaActivity.this);
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
				file = _filePath.get((int)(0));
				file_name = Uri.parse(file).getLastPathSegment();
				valid_id_preview_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(file, 1024, 1024));
				valid_id_preview_text.setVisibility(View.GONE);
				valid_id_preview_img.setVisibility(View.VISIBLE);
				UploadedValidBoolean = true;
			}
			else {
				
			}
			break;
			
			case REQ_CD_C:
			if (_resultCode == Activity.RESULT_OK) {
				 String _filePath = _file_c.getAbsolutePath();
				
				file_name = _filePath;
				selfie_preview_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath, 1024, 1024));
				selfie_preview_text.setVisibility(View.GONE);
				selfie_preview_img.setVisibility(View.VISIBLE);
				takeASelfieBoolean = true;
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	public void _design () {
		// Defaults
		valid_id_preview_img.setVisibility(View.GONE);
		selfie_preview_img.setVisibility(View.GONE);
		// Ripples and Effects
		android.graphics.drawable.GradientDrawable btnLogout_design = new android.graphics.drawable.GradientDrawable();
		btnLogout_design.setColor(0xFFDD1D5E);
		btnLogout_design.setCornerRadius((float)10);
		btnLogout_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnLogout_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnLogout_design, null);
		btnLogout.setBackground(btnLogout_re);
		android.graphics.drawable.GradientDrawable btnShopNow_design = new android.graphics.drawable.GradientDrawable();
		btnShopNow_design.setColor(0xFFDD1D5E);
		btnShopNow_design.setCornerRadius((float)10);
		btnShopNow_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnShopNow_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnShopNow_design, null);
		btnShopNow.setBackground(btnShopNow_re);
		android.graphics.drawable.GradientDrawable btnUploadID_design = new android.graphics.drawable.GradientDrawable();
		btnUploadID_design.setColor(0xFFDD1D5E);
		btnUploadID_design.setCornerRadius((float)10);
		btnUploadID_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnUploadID_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnUploadID_design, null);
		btnUploadID.setBackground(btnUploadID_re);
		android.graphics.drawable.GradientDrawable btnTookAPicture_design = new android.graphics.drawable.GradientDrawable();
		btnTookAPicture_design.setColor(0xFFDD1D5E);
		btnTookAPicture_design.setCornerRadius((float)10);
		btnTookAPicture_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnTookAPicture_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnTookAPicture_design, null);
		btnTookAPicture.setBackground(btnTookAPicture_re);
		android.graphics.drawable.GradientDrawable valid_id_preview_box_design = new android.graphics.drawable.GradientDrawable();
		valid_id_preview_box_design.setColor(0xFFF5F5F5);
		valid_id_preview_box_design.setCornerRadius((float)10);
		valid_id_preview_box_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable valid_id_preview_box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), valid_id_preview_box_design, null);
		valid_id_preview_box.setBackground(valid_id_preview_box_re);
		android.graphics.drawable.GradientDrawable selfie_preview_box_design = new android.graphics.drawable.GradientDrawable();
		selfie_preview_box_design.setColor(0xFFF5F5F5);
		selfie_preview_box_design.setCornerRadius((float)10);
		selfie_preview_box_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable selfie_preview_box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), selfie_preview_box_design, null);
		selfie_preview_box.setBackground(selfie_preview_box_re);
		android.graphics.drawable.GradientDrawable btnDone_design = new android.graphics.drawable.GradientDrawable();
		btnDone_design.setColor(0xFFDD1D5E);
		btnDone_design.setCornerRadius((float)10);
		btnDone_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnDone_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDone_design, null);
		btnDone.setBackground(btnDone_re);
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
