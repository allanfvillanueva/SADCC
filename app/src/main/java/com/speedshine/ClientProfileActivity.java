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
import de.hdodenhof.circleimageview.*;
import android.widget.ImageView;
import android.app.Activity;
import android.content.SharedPreferences;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import android.view.View;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ClientProfileActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_GL = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> AccountInfoMap = new HashMap<>();
	private String getAccountId = "";
	private  GoogleSignInOptions gop;
	
	private LinearLayout drawer_main;
	private LinearLayout linear1;
	private ScrollView vsnavs;
	private TextView textview1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear6;
	private CircleImageView profile_picture;
	private LinearLayout linear5;
	private TextView name;
	private TextView textview3;
	private LinearLayout btnHome;
	private LinearLayout btnTrackItems;
	private LinearLayout btnMessages;
	private LinearLayout btnSettings;
	private LinearLayout btnAboutUs;
	private LinearLayout btnLogout;
	private ImageView imageview5;
	private TextView textview11;
	private ImageView imageview6;
	private TextView textview12;
	private ImageView imageview1;
	private TextView textview10;
	private ImageView imageview2;
	private TextView textview5;
	private ImageView imageview3;
	private TextView textview7;
	private ImageView imageview4;
	private TextView textview6;
	
	private SharedPreferences sp;
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
	private DatabaseReference fbdb = _firebase.getReference("account");
	private ChildEventListener _fbdb_child_listener;
	private Intent i = new Intent();
	private GoogleSignInClient gl;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_profile);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		drawer_main = (LinearLayout) findViewById(R.id.drawer_main);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		vsnavs = (ScrollView) findViewById(R.id.vsnavs);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		profile_picture = (CircleImageView) findViewById(R.id.profile_picture);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		name = (TextView) findViewById(R.id.name);
		textview3 = (TextView) findViewById(R.id.textview3);
		btnHome = (LinearLayout) findViewById(R.id.btnHome);
		btnTrackItems = (LinearLayout) findViewById(R.id.btnTrackItems);
		btnMessages = (LinearLayout) findViewById(R.id.btnMessages);
		btnSettings = (LinearLayout) findViewById(R.id.btnSettings);
		btnAboutUs = (LinearLayout) findViewById(R.id.btnAboutUs);
		btnLogout = (LinearLayout) findViewById(R.id.btnLogout);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview11 = (TextView) findViewById(R.id.textview11);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		textview12 = (TextView) findViewById(R.id.textview12);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview10 = (TextView) findViewById(R.id.textview10);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview5 = (TextView) findViewById(R.id.textview5);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		textview7 = (TextView) findViewById(R.id.textview7);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview6 = (TextView) findViewById(R.id.textview6);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
		fbauth = FirebaseAuth.getInstance();
		
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ShopActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		btnTrackItems.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ClientTrackItemsActivity.class);
				startActivity(i);
			}
		});
		
		btnMessages.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ClientChatActivity.class);
				startActivity(i);
			}
		});
		
		btnSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ClientSettingsActivity.class);
				startActivity(i);
			}
		});
		
		btnAboutUs.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), AboutusActivity.class);
				startActivity(i);
			}
		});
		
		btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().signOut();
				fbauth.signOut();
				
				gl.signOut().addOnCompleteListener(ClientProfileActivity.this, new OnCompleteListener<Void>() {
						
						@Override
						
						public void onComplete(@NonNull Task<Void> task) {
								SketchwareUtil.showMessage(getApplicationContext(), "Logout successfully!");
						i.setClass(getApplicationContext(), AccountActivity.class);
						startActivity(i);
						finish();
						}
				});
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
		_loadData();
		GoogleSignInOptions gop = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken("1090849595737-8p185b3f3pru1oc1c68tlqp6so2r027v.apps.googleusercontent.com").build();
		gl = GoogleSignIn.getClient(this,gop);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _loadData () {
		fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
						getAccountId = FirebaseAuth.getInstance().getCurrentUser().getUid();
				Map<String,Object> AccountInfoMap = (Map<String,Object>) _dataSnapshot.child(getAccountId).getValue();
				name.setText(AccountInfoMap.get("lname").toString().concat(" , ").concat(AccountInfoMap.get("fname").toString().concat(" ").concat(AccountInfoMap.get("mname").toString())));
				if (AccountInfoMap.containsKey("avatar")) {
					Glide.with(getApplicationContext()).load(Uri.parse(AccountInfoMap.get("avatar").toString())).into(profile_picture);
				}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
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
