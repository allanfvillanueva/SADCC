package com.speedshine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
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
import android.widget.TextView;
import android.widget.ScrollView;
import de.hdodenhof.circleimageview.*;
import android.widget.ImageView;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
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
import android.view.View;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AdminDashboardActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_FGL = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private DrawerLayout _drawer;
	private HashMap<String, Object> AccountInfoMap = new HashMap<>();
	private String getAccountId = "";
	private String adminUid = "";
	private  GoogleSignInOptions gop;
	private double index = 0;
	private double length = 0;
	private double index1 = 0;
	private double length1 = 0;
	private double NoReplyMessagesCount = 0;
	private double length3 = 0;
	private double index3 = 0;
	private double unreadAdminNotification = 0;
	private double index4 = 0;
	private double length4 = 0;
	
	private ArrayList<HashMap<String, Object>> accountsLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> productsListmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> AllMessagesLM = new ArrayList<>();
	private ArrayList<String> AllMessagesStr = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> AllMessagesFromUserLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> AllNotificationLM = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout boxUsers;
	private LinearLayout boxProducts;
	private TextView textview1;
	private TextView users;
	private TextView textview2;
	private TextView products;
	private LinearLayout _drawer_drawer_main;
	private LinearLayout _drawer_linear1;
	private ScrollView _drawer_vsnavs;
	private TextView _drawer_textview1;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear3;
	private LinearLayout _drawer_linear8;
	private LinearLayout _drawer_btnManageAccounts;
	private LinearLayout _drawer_btnManageProducts;
	private LinearLayout _drawer_btnAppointmentList;
	private LinearLayout _drawer_btnSettings;
	private LinearLayout _drawer_btnLogout;
	private CircleImageView _drawer_profile_picture;
	private TextView _drawer_name;
	private TextView _drawer_textview3;
	private LinearLayout _drawer_boxNotification;
	private LinearLayout _drawer_boxMessage;
	private TextView _drawer_lblNotificationCount;
	private ImageView _drawer_imageview1;
	private TextView _drawer_textview8;
	private TextView _drawer_lbNoReplyMessagesCount;
	private ImageView _drawer_imageview2;
	private TextView _drawer_textview9;
	private TextView _drawer_textview10;
	private TextView _drawer_textview5;
	private TextView _drawer_textview4;
	private TextView _drawer_textview11;
	private TextView _drawer_textview7;
	
	private Intent i = new Intent();
	private DatabaseReference fb = _firebase.getReference("account");
	private ChildEventListener _fb_child_listener;
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
	private GoogleSignInClient fgl;
	private DatabaseReference dbauth = _firebase.getReference("account");
	private ChildEventListener _dbauth_child_listener;
	private DatabaseReference dbproducts = _firebase.getReference("products");
	private ChildEventListener _dbproducts_child_listener;
	private DatabaseReference dbmessage = _firebase.getReference("chat");
	private ChildEventListener _dbmessage_child_listener;
	private DatabaseReference dbAdminNotif = _firebase.getReference("notif_admin");
	private ChildEventListener _dbAdminNotif_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.admin_dashboard);
		initialize(_savedInstanceState);

		Log.d("av","AdminDashboardActivity onCreate");

		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();

		//bypassLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_drawer = (DrawerLayout) findViewById(R.id._drawer);
		ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(AdminDashboardActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		main = (LinearLayout) findViewById(R.id.main);
		boxUsers = (LinearLayout) findViewById(R.id.boxUsers);
		boxProducts = (LinearLayout) findViewById(R.id.boxProducts);
		textview1 = (TextView) findViewById(R.id.textview1);
		users = (TextView) findViewById(R.id.users);
		textview2 = (TextView) findViewById(R.id.textview2);
		products = (TextView) findViewById(R.id.products);
		_drawer_drawer_main = (LinearLayout) _nav_view.findViewById(R.id.drawer_main);
		_drawer_linear1 = (LinearLayout) _nav_view.findViewById(R.id.linear1);
		_drawer_vsnavs = (ScrollView) _nav_view.findViewById(R.id.vsnavs);
		_drawer_textview1 = (TextView) _nav_view.findViewById(R.id.textview1);
		_drawer_linear2 = (LinearLayout) _nav_view.findViewById(R.id.linear2);
		_drawer_linear3 = (LinearLayout) _nav_view.findViewById(R.id.linear3);
		_drawer_linear8 = (LinearLayout) _nav_view.findViewById(R.id.linear8);
		_drawer_btnManageAccounts = (LinearLayout) _nav_view.findViewById(R.id.btnManageAccounts);
		_drawer_btnManageProducts = (LinearLayout) _nav_view.findViewById(R.id.btnManageProducts);
		_drawer_btnAppointmentList = (LinearLayout) _nav_view.findViewById(R.id.btnAppointmentList);
		_drawer_btnSettings = (LinearLayout) _nav_view.findViewById(R.id.btnSettings);
		_drawer_btnLogout = (LinearLayout) _nav_view.findViewById(R.id.btnLogout);
		_drawer_profile_picture = (CircleImageView) _nav_view.findViewById(R.id.profile_picture);
		_drawer_name = (TextView) _nav_view.findViewById(R.id.name);
		_drawer_textview3 = (TextView) _nav_view.findViewById(R.id.textview3);
		_drawer_boxNotification = (LinearLayout) _nav_view.findViewById(R.id.boxNotification);
		_drawer_boxMessage = (LinearLayout) _nav_view.findViewById(R.id.boxMessage);
		_drawer_lblNotificationCount = (TextView) _nav_view.findViewById(R.id.lblNotificationCount);
		_drawer_imageview1 = (ImageView) _nav_view.findViewById(R.id.imageview1);
		_drawer_textview8 = (TextView) _nav_view.findViewById(R.id.textview8);
		_drawer_lbNoReplyMessagesCount = (TextView) _nav_view.findViewById(R.id.lbNoReplyMessagesCount);
		_drawer_imageview2 = (ImageView) _nav_view.findViewById(R.id.imageview2);
		_drawer_textview9 = (TextView) _nav_view.findViewById(R.id.textview9);
		_drawer_textview10 = (TextView) _nav_view.findViewById(R.id.textview10);
		_drawer_textview5 = (TextView) _nav_view.findViewById(R.id.textview5);
		_drawer_textview4 = (TextView) _nav_view.findViewById(R.id.textview4);
		_drawer_textview11 = (TextView) _nav_view.findViewById(R.id.textview11);
		_drawer_textview7 = (TextView) _nav_view.findViewById(R.id.textview7);
		fbauth = FirebaseAuth.getInstance();
		
		_fb_child_listener = new ChildEventListener() {
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
		fb.addChildEventListener(_fb_child_listener);
		
		_dbauth_child_listener = new ChildEventListener() {
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
		dbauth.addChildEventListener(_dbauth_child_listener);
		
		_dbproducts_child_listener = new ChildEventListener() {
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
		dbproducts.addChildEventListener(_dbproducts_child_listener);
		
		_dbmessage_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				_getAllNoReplyMessages();
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
		dbmessage.addChildEventListener(_dbmessage_child_listener);
		
		_dbAdminNotif_child_listener = new ChildEventListener() {
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
		dbAdminNotif.addChildEventListener(_dbAdminNotif_child_listener);
		
		_drawer_btnManageAccounts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ManageAccountsActivity.class);
				startActivity(i);
			}
		});
		
		_drawer_btnManageProducts.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ManageProductsActivity.class);
				startActivity(i);
			}
		});
		
		_drawer_btnAppointmentList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ManageAppointmentActivity.class);
				startActivity(i);
			}
		});
		
		_drawer_btnSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ClientSettingsActivity.class);
				startActivity(i);
			}
		});
		
		_drawer_btnLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FirebaseAuth.getInstance().signOut();
				fbauth.signOut();
				
				fgl.signOut().addOnCompleteListener(AdminDashboardActivity.this, new OnCompleteListener<Void>() {
						
						@Override
						
						public void onComplete(@NonNull Task<Void> task) {
								SketchwareUtil.showMessage(getApplicationContext(), "Successfully logout.");
						i.setClass(getApplicationContext(), AccountActivity.class);
						startActivity(i);
						finish();
						}
				});
			}
		});
		
		_drawer_boxNotification.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), NotificationsActivity.class);
				startActivity(i);
			}
		});
		
		_drawer_boxMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), AdminViewChatsActivity.class);
				startActivity(i);
			}
		});
		
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
		_loadData();
		_loadAnalytics();
		_getAllNoReplyMessages();
		_getAllNotifications();
		GoogleSignInOptions gop = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken("1090849595737-8p185b3f3pru1oc1c68tlqp6so2r027v.apps.googleusercontent.com").build();
		fgl = GoogleSignIn.getClient(this,gop);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		_loadAnalytics();
		_getAllNotifications();
		_getAllNoReplyMessages();
	}
	
	@Override
	public void onBackPressed() {
		if (_drawer.isDrawerOpen(GravityCompat.START)) {
			_drawer.closeDrawer(GravityCompat.START);
		}
		else {
			super.onBackPressed();
		}
	}
	public void _design () {
		// Drawer
		_drawer_boxNotification.setElevation((float)5);
		android.graphics.drawable.GradientDrawable _drawer_boxNotification_design = new android.graphics.drawable.GradientDrawable();
		_drawer_boxNotification_design.setColor(0xFFFFFFFF);
		_drawer_boxNotification_design.setCornerRadius((float)8);
		_drawer_boxNotification_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable _drawer_boxNotification_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), _drawer_boxNotification_design, null);
		_drawer_boxNotification.setBackground(_drawer_boxNotification_re);
		_drawer_boxMessage.setElevation((float)5);
		android.graphics.drawable.GradientDrawable _drawer_boxMessage_design = new android.graphics.drawable.GradientDrawable();
		_drawer_boxMessage_design.setColor(0xFFFFFFFF);
		_drawer_boxMessage_design.setCornerRadius((float)8);
		_drawer_boxMessage_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable _drawer_boxMessage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), _drawer_boxMessage_design, null);
		_drawer_boxMessage.setBackground(_drawer_boxMessage_re);
		boxUsers.setElevation((float)5);
		android.graphics.drawable.GradientDrawable boxUsers_design = new android.graphics.drawable.GradientDrawable();
		boxUsers_design.setColor(0xFFFFFFFF);
		boxUsers_design.setCornerRadius((float)8);
		boxUsers_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable boxUsers_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxUsers_design, null);
		boxUsers.setBackground(boxUsers_re);
		boxProducts.setElevation((float)5);
		android.graphics.drawable.GradientDrawable boxProducts_design = new android.graphics.drawable.GradientDrawable();
		boxProducts_design.setColor(0xFFFFFFFF);
		boxProducts_design.setCornerRadius((float)8);
		boxProducts_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable boxProducts_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxProducts_design, null);
		boxProducts.setBackground(boxProducts_re);
		android.graphics.drawable.GradientDrawable _drawer_lbNoReplyMessagesCount_design = new android.graphics.drawable.GradientDrawable();
		_drawer_lbNoReplyMessagesCount_design.setColor(0xFFDD1D5E);
		_drawer_lbNoReplyMessagesCount_design.setCornerRadius((float)8);
		_drawer_lbNoReplyMessagesCount_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable _drawer_lbNoReplyMessagesCount_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), _drawer_lbNoReplyMessagesCount_design, null);
		_drawer_lbNoReplyMessagesCount.setBackground(_drawer_lbNoReplyMessagesCount_re);
		android.graphics.drawable.GradientDrawable _drawer_lblNotificationCount_design = new android.graphics.drawable.GradientDrawable();
		_drawer_lblNotificationCount_design.setColor(0xFFDD1D5E);
		_drawer_lblNotificationCount_design.setCornerRadius((float)8);
		_drawer_lblNotificationCount_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable _drawer_lblNotificationCount_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), _drawer_lblNotificationCount_design, null);
		_drawer_lblNotificationCount.setBackground(_drawer_lblNotificationCount_re);
	}
	
	
	public void _loadData () {
		fb.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				getAccountId = FirebaseAuth.getInstance().getCurrentUser().getUid();
				adminUid = getAccountId;
				Map<String,Object> AccountInfoMap = (Map<String,Object>) _dataSnapshot.child(getAccountId).getValue();
				_drawer_name.setText(AccountInfoMap.get("lname").toString().concat(" , ").concat(AccountInfoMap.get("fname").toString().concat(" ").concat(AccountInfoMap.get("mname").toString())));
				if (AccountInfoMap.containsKey("avatar")) {
					Glide.with(getApplicationContext()).load(Uri.parse(AccountInfoMap.get("avatar").toString())).into(_drawer_profile_picture);
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});

//		fb.addListenerForSingleValueEvent(new ValueEventListener() {
//				@Override
//				public void onDataChange(DataSnapshot _dataSnapshot) {
//						getAccountId = FirebaseAuth.getInstance().getCurrentUser().getUid();
//				Map<String,Object> AccountInfoMap = (Map<String,Object>) _dataSnapshot.child(getAccountId).getValue();
//				_drawer_name.setText(AccountInfoMap.get("lname").toString().concat(" , ").concat(AccountInfoMap.get("fname").toString().concat(" ").concat(AccountInfoMap.get("mname").toString())));
//				if (AccountInfoMap.containsKey("avatar")) {
//					Glide.with(getApplicationContext()).load(Uri.parse(AccountInfoMap.get("avatar").toString())).into(_drawer_profile_picture);
//				}
//				}
//				@Override
//				public void onCancelled(DatabaseError _databaseError) {
//				}
//		});
	}
	
	
	public void _loadAnalytics () {
		dbauth.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				accountsLm = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						accountsLm.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				users.setText(String.valueOf((long)(accountsLm.size())));
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		dbproducts.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				productsListmap = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						productsListmap.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				products.setText(String.valueOf((long)(productsListmap.size())));
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public void _getAllNoReplyMessages () {
		dbmessage = _firebase.getReference("chatroom");
		dbmessage.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				AllMessagesLM = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						AllMessagesLM.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				index = 0;
				NoReplyMessagesCount = AllMessagesLM.size();
				AllMessagesFromUserLm.clear();
				Log.d("av","_getAllNoReplyMessages: NoReplyMessagesCount=" + String.valueOf(NoReplyMessagesCount));
				length = AllMessagesLM.size();
				for(int _repeat17 = 0; _repeat17 < (int)(length); _repeat17++) {
					SketchwareUtil.getAllKeysFromMap(AllMessagesLM.get((int)(index)), AllMessagesStr);
					Log.d("av","_getAllNoReplyMessages: AllMessagesStr.size()=" + String.valueOf(AllMessagesStr.size()));
					index1 = 0;
					length1 = AllMessagesStr.size();
					for(int _repeat26 = 0; _repeat26 < (int)(length1); _repeat26++) {


						AllMessagesFromUserLm.add(AllMessagesLM.get((int)(index)));
						//Log.d("av","_getAllNoReplyMessages: AllMessagesFromUserLm.get((int)index).get(\"userid\")=" + String.valueOf(AllMessagesFromUserLm.get((int)index).get("userid").toString()));
						index1++;
					}
					index++;
				}

				index3 = 0;
				//NoReplyMessagesCount = 0; //AllMessagesFromUserLm.size();
				Log.d("av","_getAllNoReplyMessages: NoReplyMessagesCount=" + String.valueOf(NoReplyMessagesCount));
				length3 = AllMessagesFromUserLm.size();
				for(int _repeat66 = 0; _repeat66 < (int)(length3); _repeat66++) {
					//Log.d("av","_getAllNoReplyMessages: AllMessagesFromUserLm.get((int)index3).get(\"noreply\") " + String.valueOf(AllMessagesFromUserLm.get((int)index3).get("noreply").toString()));
					//Log.d("av","_getAllNoReplyMessages: AllMessagesFromUserLm.get((int)index3).get(\"userid\") " + String.valueOf(AllMessagesFromUserLm.get((int)index3).get("userid").toString()));

					//if (AllMessagesFromUserLm.get((int)index3).get("noreply").toString().equals("true") && !AllMessagesFromUserLm.get((int)index3).get("userid").toString().equals("admin")) {
						//NoReplyMessagesCount++;
					//}
					//else {
						//NoReplyMessagesCount--;
						//Log.d("av","_getAllNoReplyMessages: NoReplyMessagesCount--");
					//}
				}
				Log.d("av","_getAllNoReplyMessages: NoReplyMessagesCount=" + String.valueOf(NoReplyMessagesCount));

				if (NoReplyMessagesCount > 9) {
					_drawer_lbNoReplyMessagesCount.setText("9+");
				}
				else {
					_drawer_lbNoReplyMessagesCount.setText(String.valueOf((long)(NoReplyMessagesCount)));
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public void _getAllNotifications () {
		dbAdminNotif.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				AllNotificationLM = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						AllNotificationLM.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				unreadAdminNotification = AllNotificationLM.size();
				index4 = 0;
				length4 = AllNotificationLM.size();
				for(int _repeat15 = 0; _repeat15 < (int)(length4); _repeat15++) {
					if (AllNotificationLM.get((int)index4).containsKey("unread")) {
						if (AllNotificationLM.get((int)index4).get("unread").toString().equals("false")) {
							unreadAdminNotification--;
						}
						else {
							//unreadAdminNotification--;
						}
					}
					else {
						//unreadAdminNotification++;
					}
					index4++;
				}
				_drawer_lblNotificationCount.setText(String.valueOf((long)(unreadAdminNotification)));
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
