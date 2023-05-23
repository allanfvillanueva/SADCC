package com.speedshine;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import com.google.android.gms.common.SignInButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;



public class AccountActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_GL = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map_account = new HashMap<>();
	private String genderString = "";
	private String current_date = "";
	private String current_dat = "";
	private  GoogleSignInOptions gop;
	private  GoogleSignInAccount gsia;
	private String getUID = "";
	
	private ArrayList<String> genderListString = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> d = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout loginForm;
	private LinearLayout registerform;
	private ImageView imageview3;
	private TextView textview6;
	private EditText tbLoginEmail;
	private EditText tbLoginPassword;
	private Button btnLogin;
	private TextView textview7;
	private SignInButton btnGoogleLogin;
	private Button btnToRegister;
	private ImageView imageview1;
	private TextView textview1;
	private LinearLayout linear2;
	private EditText tb_email;
	private EditText tb_password;
	private EditText tb_repassword;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private Button btnRegister;
	private TextView textview5;
	private Button btnToLoginForm;
	private Spinner spinnerGender;
	private EditText tb_fname;
	private EditText tb_mname;
	private EditText tb_lname;
	private TextView textview3;
	private LinearLayout boxGender;
	private TextView lbGender;
	private LinearLayout linear21;
	private ImageView imageview4;
	private TextView textview4;
	private LinearLayout boxBday;
	private TextView tb_bday;
	private LinearLayout linear5;
	private ImageView imageview2;
	
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
	private ProgressDialog pd;
	private DatabaseReference fbregister = _firebase.getReference("account");
	private ChildEventListener _fbregister_child_listener;
	private Calendar c = Calendar.getInstance();
	private GoogleSignInClient gl;
	private Intent i = new Intent();

	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.account);
		initialize(_savedInstanceState);

		Log.d("av","AccountActivity onCreate");

		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		loginForm = (LinearLayout) findViewById(R.id.loginForm);
		registerform = (LinearLayout) findViewById(R.id.registerform);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		textview6 = (TextView) findViewById(R.id.textview6);
		tbLoginEmail = (EditText) findViewById(R.id.tbLoginEmail);
		tbLoginPassword = (EditText) findViewById(R.id.tbLoginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		textview7 = (TextView) findViewById(R.id.textview7);
		btnGoogleLogin = (SignInButton) findViewById(R.id.btnGoogleLogin);
		btnToRegister = (Button) findViewById(R.id.btnToRegister);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		tb_email = (EditText) findViewById(R.id.tb_email);
		tb_password = (EditText) findViewById(R.id.tb_password);
		tb_repassword = (EditText) findViewById(R.id.tb_repassword);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		textview5 = (TextView) findViewById(R.id.textview5);
		btnToLoginForm = (Button) findViewById(R.id.btnToLoginForm);
		spinnerGender = (Spinner) findViewById(R.id.spinnerGender);
		tb_fname = (EditText) findViewById(R.id.tb_fname);
		tb_mname = (EditText) findViewById(R.id.tb_mname);
		tb_lname = (EditText) findViewById(R.id.tb_lname);
		textview3 = (TextView) findViewById(R.id.textview3);
		boxGender = (LinearLayout) findViewById(R.id.boxGender);
		lbGender = (TextView) findViewById(R.id.lbGender);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview4 = (TextView) findViewById(R.id.textview4);
		boxBday = (LinearLayout) findViewById(R.id.boxBday);
		tb_bday = (TextView) findViewById(R.id.tb_bday);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		fbauth = FirebaseAuth.getInstance();
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {

				if (tbLoginEmail.getText().toString().trim().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your email");
					return;
				}
				if (tbLoginPassword.getText().toString().trim().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your password");
					return;
				}
				fbauth.signInWithEmailAndPassword(tbLoginEmail.getText().toString(), tbLoginPassword.getText().toString()).addOnCompleteListener(AccountActivity.this, _fbauth_sign_in_listener);
				pd.setMessage("Logging to your account.");
				pd.setCancelable(false);
				pd.setCanceledOnTouchOutside(false);
				pd.show();

			}
		});
		
		btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				Log.d("av","btnGoogleLogin.setOnClickListener");

				Intent signInIntent = gl.getSignInIntent();

				startActivityForResult(signInIntent, REQ_CD_GL);
			}
		});
		
		btnToRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				loginForm.setVisibility(View.GONE);
				registerform.setVisibility(View.VISIBLE);
			}
		});
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tb_fname.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please type your first name.");
					return;
				}
				if (tb_mname.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please type your middle name.");
					return;
				}
				if (tb_lname.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please type your last name.");
					return;
				}
				if (tb_email.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your email.");
					return;
				}
				if (tb_password.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your password");
					return;
				}
				if (!tb_repassword.getText().toString().equals(tb_password.getText().toString())) {
					SketchwareUtil.showMessage(getApplicationContext(), "Passwords doesn't match.");
					return;
				}
				if (tb_bday.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter/select your birthday.");
					return;
				}
				fbauth.createUserWithEmailAndPassword(tb_email.getText().toString(), tb_password.getText().toString()).addOnCompleteListener(AccountActivity.this, _fbauth_create_user_listener);
				pd.setMessage("Registering your account please wait.");
				pd.setCancelable(false);
				pd.setCanceledOnTouchOutside(false);
				pd.show();
			}
		});
		
		btnToLoginForm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				registerform.setVisibility(View.GONE);
				loginForm.setVisibility(View.VISIBLE);
			}
		});
		
		spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				genderString = genderListString.get((int)(_position));
				lbGender.setText(genderString);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		boxGender.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				spinnerGender.performClick();
			}
		});
		
		boxBday.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_showDatePicker();
			}
		});
		
		_fbregister_child_listener = new ChildEventListener() {
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
		fbregister.addChildEventListener(_fbregister_child_listener);
		
		fbauth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {

				Log.d("av","fbauth_updateEmailListener");

				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {

				Log.d("av","fbauth_updatePasswordListener");

				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {

				Log.d("av","fbauth_emailVerificationSentListener");

				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {

				Log.d("av","fbauth_deleteUserListener");

				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){

				Log.d("av","fbauth_phoneAuthListener");

				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		fbauth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {

				Log.d("av","fbauth_updateProfileListener");

				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		fbauth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){

				Log.d("av","fbauth_googleSignInListener");

				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_fbauth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {

				Log.d("av","_fbauth_create_user_listener");

				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				pd.dismiss();
				if (_success) {
					map_account = new HashMap<>();
					map_account.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					map_account.put("fname", tb_fname.getText().toString());
					map_account.put("mname", tb_mname.getText().toString());
					map_account.put("lname", tb_lname.getText().toString());
					map_account.put("email", tb_email.getText().toString());
					map_account.put("bday", tb_bday.getText().toString());
					map_account.put("gender", genderString);
					map_account.put("verified", "0");
					map_account.put("account_type", "3");
					fbregister.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map_account);
					SketchwareUtil.showMessage(getApplicationContext(), "Your account was successfully created.");
					i.setClass(getApplicationContext(), ShopActivity.class);
					startActivity(i);
					finish();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_fbauth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {

				Log.d("av","_fbauth_sign_in_listener");

				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				pd.dismiss();
				if (_success) {
					i.setClass(getApplicationContext(), ShopActivity.class);
					startActivity(i);
					finish();
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_fbauth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {

				Log.d("av","_fbauth_reset_password_listener");

				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		pd = new ProgressDialog(AccountActivity.this);
		_design();
		_loadGender();
		_getTheDate();
		GoogleSignInOptions gop = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken("1090849595737-8p185b3f3pru1oc1c68tlqp6so2r027v.apps.googleusercontent.com").build();
		gl = GoogleSignIn.getClient(AccountActivity.this,gop);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);

		Log.d("av","AccountActivity onActivityResult");
		
		switch (_requestCode) {
			case REQ_CD_GL:

				Log.d("av","_resultCode == REQ_CD_GL");

				Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(_data);

				try {
					GoogleSignInAccount account = task.getResult(ApiException.class);
					Log.d("av","AccountActivity google sign-in success");

					Task<GoogleSignInAccount> _task = GoogleSignIn.getSignedInAccountFromIntent(_data);

					pd.setMessage("Logging to your account.");
					pd.setCancelable(false);
					pd.setCanceledOnTouchOutside(false);
					pd.show();
					try{
						gsia = _task.getResult(ApiException.class);
						AuthCredential credential = GoogleAuthProvider.getCredential(gsia.getIdToken(), null);
						Log.d("av","GoogleAuthProvider.getCredential(gsia.getIdToken()");
						Log.d("av","GoogleAuthProvider.getCredential(gsia.getIdToken()");

						fbauth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {

								fbregister.addListenerForSingleValueEvent(new ValueEventListener() {
									@Override
									public void onDataChange(DataSnapshot _dataSnapshot) {
										getUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

										Log.d("av",getUID);

										if (_dataSnapshot.hasChild(getUID)) {

											Log.d("av","GUID exists, loading ShopActivity");

											i.setClass(getApplicationContext(), ShopActivity.class);
											startActivity(i);
											finish();
										}
										else {

											Log.d("av","GUID not exists, saving details to Firebase");

											map_account = new HashMap<>();
											map_account.put("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
											map_account.put("fname", gsia.getGivenName());
											map_account.put("mname", "");
											map_account.put("lname", gsia.getFamilyName());
											map_account.put("email", gsia.getEmail());
											map_account.put("bday", tb_bday.getText().toString());
											map_account.put("gender", "");
											map_account.put("verified", "0");
											map_account.put("account_type", "3");
											fbregister.child(getUID).updateChildren(map_account);

											Log.d("av","Details saved to Firebase, continuing to ShopActivity");

											i.setClass(getApplicationContext(), ShopActivity.class);
											startActivity(i);
											finish();
										}
									}
									@Override
									public void onCancelled(DatabaseError _databaseError) {
									}
								});
							}
						});
					}
					catch(ApiException e){
						String Error = e.getMessage();
						SketchwareUtil.showMessage(getApplicationContext(), "Failed to login to your google account, Please try again later.");
					}

				} catch (ApiException e) {
					// The ApiException status code indicates the detailed failure reason.
					// Please refer to the GoogleSignInStatusCodes class reference for more information.
					Log.d("av","LoginActivity signInResult:failed code=" + e.getStatusCode());

				}
			break;
			default:
			break;
		}
	}
	
	private void updateFirebaseUserData() {
		Log.d("av","trying to update user data");

		DatabaseReference dbAccounts = _firebase.getReference("account");

		map_account = new HashMap<>();
		map_account.put("uid", "uhrzwFpnfmWN6lQCWsu4HkPEee12");
		map_account.put("fname", gsia.getGivenName());
		map_account.put("mname", "");
		map_account.put("lname", gsia.getFamilyName());
		map_account.put("email", gsia.getEmail());
		map_account.put("bday", tb_bday.getText().toString());
		map_account.put("gender", "Male");
		map_account.put("verified", "2");
		map_account.put("account_type", "1");
		dbAccounts.child(getUID).updateChildren(map_account);

		Log.d("av","update finished for user account");

	}

	public void _loadGender () {
		genderListString.add("Male");
		genderListString.add("Female");
		spinnerGender.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, genderListString));
		((ArrayAdapter)spinnerGender.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _showDatePicker () {
		androidx.appcompat.app.AppCompatDialogFragment newFragment = new DatePickerFragment();
		
		newFragment.show(getSupportFragmentManager(), "Date Picker");
		
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
			TextView tb_bday = (TextView) getActivity().findViewById(R.id.tb_bday);
			tb_bday.setText(date);
		}
	}
	
	
	public void _getTheDate () {
		c = Calendar.getInstance();
		current_date = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		current_dat = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		tb_bday.setText(new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()));
	}
	
	
	public void _design () {
		android.graphics.drawable.GradientDrawable boxGender_design = new android.graphics.drawable.GradientDrawable();
		boxGender_design.setColor(Color.TRANSPARENT);
		boxGender_design.setCornerRadius((float)0);
		boxGender_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable boxGender_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxGender_design, null);
		boxGender.setBackground(boxGender_re);
		android.graphics.drawable.GradientDrawable boxBday_design = new android.graphics.drawable.GradientDrawable();
		boxBday_design.setColor(Color.TRANSPARENT);
		boxBday_design.setCornerRadius((float)0);
		boxBday_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable boxBday_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxBday_design, null);
		boxBday.setBackground(boxBday_re);
		android.graphics.drawable.GradientDrawable tb_fname_design = new android.graphics.drawable.GradientDrawable();
		tb_fname_design.setColor(Color.TRANSPARENT);
		tb_fname_design.setCornerRadius((float)0);
		tb_fname_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tb_fname_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tb_fname_design, null);
		tb_fname.setBackground(tb_fname_re);
		android.graphics.drawable.GradientDrawable tb_mname_design = new android.graphics.drawable.GradientDrawable();
		tb_mname_design.setColor(Color.TRANSPARENT);
		tb_mname_design.setCornerRadius((float)0);
		tb_mname_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tb_mname_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tb_mname_design, null);
		tb_mname.setBackground(tb_mname_re);
		android.graphics.drawable.GradientDrawable tb_lname_design = new android.graphics.drawable.GradientDrawable();
		tb_lname_design.setColor(Color.TRANSPARENT);
		tb_lname_design.setCornerRadius((float)0);
		tb_lname_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tb_lname_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tb_lname_design, null);
		tb_lname.setBackground(tb_lname_re);
		android.graphics.drawable.GradientDrawable tb_email_design = new android.graphics.drawable.GradientDrawable();
		tb_email_design.setColor(Color.TRANSPARENT);
		tb_email_design.setCornerRadius((float)0);
		tb_email_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tb_email_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tb_email_design, null);
		tb_email.setBackground(tb_email_re);
		android.graphics.drawable.GradientDrawable tb_password_design = new android.graphics.drawable.GradientDrawable();
		tb_password_design.setColor(Color.TRANSPARENT);
		tb_password_design.setCornerRadius((float)0);
		tb_password_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tb_password_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tb_password_design, null);
		tb_password.setBackground(tb_password_re);
		android.graphics.drawable.GradientDrawable tb_repassword_design = new android.graphics.drawable.GradientDrawable();
		tb_repassword_design.setColor(Color.TRANSPARENT);
		tb_repassword_design.setCornerRadius((float)0);
		tb_repassword_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tb_repassword_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tb_repassword_design, null);
		tb_repassword.setBackground(tb_repassword_re);
		android.graphics.drawable.GradientDrawable tbLoginEmail_design = new android.graphics.drawable.GradientDrawable();
		tbLoginEmail_design.setColor(Color.TRANSPARENT);
		tbLoginEmail_design.setCornerRadius((float)0);
		tbLoginEmail_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbLoginEmail_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbLoginEmail_design, null);
		tbLoginEmail.setBackground(tbLoginEmail_re);
		android.graphics.drawable.GradientDrawable tbLoginPassword_design = new android.graphics.drawable.GradientDrawable();
		tbLoginPassword_design.setColor(Color.TRANSPARENT);
		tbLoginPassword_design.setCornerRadius((float)0);
		tbLoginPassword_design.setStroke((int)1,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbLoginPassword_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbLoginPassword_design, null);
		tbLoginPassword.setBackground(tbLoginPassword_re);
		android.graphics.drawable.GradientDrawable btnRegister_design = new android.graphics.drawable.GradientDrawable();
		btnRegister_design.setColor(0xFFDD1D5E);
		btnRegister_design.setCornerRadius((float)8);
		btnRegister_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnRegister_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnRegister_design, null);
		btnRegister.setBackground(btnRegister_re);
		android.graphics.drawable.GradientDrawable btnLogin_design = new android.graphics.drawable.GradientDrawable();
		btnLogin_design.setColor(0xFFDD1D5E);
		btnLogin_design.setCornerRadius((float)8);
		btnLogin_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnLogin_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnLogin_design, null);
		btnLogin.setBackground(btnLogin_re);
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
