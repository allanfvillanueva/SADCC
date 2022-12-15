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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
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
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class NotificationsActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> AccountInfoMap = new HashMap<>();
	private String getAccountId = "";
	private double index = 0;
	private double length = 0;
	private double index1 = 0;
	private double length1 = 0;
	private HashMap<String, Object> notificationmap = new HashMap<>();
	private boolean isAdmin = false;
	private  DatabaseReference notifdb;
	private HashMap<String, Object> MarkAsReadNotificationMap = new HashMap<>();
	
	private ArrayList<HashMap<String, Object>> notificationml = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> newNotificationMl = new ArrayList<>();
	private ArrayList<String> notificationstring = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private ListView lv;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;
	
	private DatabaseReference dbnotification = _firebase.getReference("notification");
	private ChildEventListener _dbnotification_child_listener;
	private DatabaseReference dbaccount = _firebase.getReference("account");
	private ChildEventListener _dbaccount_child_listener;
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
	private DatabaseReference dbnotifadmin = _firebase.getReference("notif_admin");
	private ChildEventListener _dbnotifadmin_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.notifications);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		lv = (ListView) findViewById(R.id.lv);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		fbauth = FirebaseAuth.getInstance();
		
		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		_dbnotification_child_listener = new ChildEventListener() {
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
		dbnotification.addChildEventListener(_dbnotification_child_listener);
		
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
		
		_dbnotifadmin_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (isAdmin) {
					_LoadAllNotification(true, "0");
				}
				else {
					
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (isAdmin) {
					_LoadAllNotification(true, "0");
				}
				else {
					
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
		dbnotifadmin.addChildEventListener(_dbnotifadmin_child_listener);
		
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
		dbaccount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					//getAccountId = FirebaseAuth.getInstance().getCurrentUser().getUid();
					getAccountId = "uhrzwFpnfmWN6lQCWsu4HkPEee12"; //FirebaseAuth.getInstance().getCurrentUser().getUid();
				Map<String,Object> AccountInfoMap = (Map<String,Object>) _dataSnapshot.child(getAccountId).getValue();
				if (AccountInfoMap.containsKey("account_type")) {
					if (AccountInfoMap.get("account_type").toString().equals("3")) {
						SketchwareUtil.showMessage(getApplicationContext(), "Under maintenance");
					}
					if (AccountInfoMap.get("account_type").toString().equals("1")) {
						_LoadAllNotification(true, "0");
					}
				}
				else {
					SketchwareUtil.showMessage(getApplicationContext(), "Failed to load some of your data, Please try again.");
					finish();
				}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _LoadAllNotification (final boolean _isAdmin, final String _userid) {
		isAdmin = _isAdmin;
		if (_isAdmin) {
			dbnotifadmin.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					notificationml = new ArrayList<>();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : _dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							notificationml.add(_map);
						}
					}
					catch (Exception _e) {
						_e.printStackTrace();
					}
					lv.setAdapter(new LvAdapter(notificationml));
					((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
			});
		}
		else {
			notifdb = _firebase.getReference("notification/" + _userid);
			notifdb.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
					notificationml = new ArrayList<>();
					try {
						GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
						for (DataSnapshot _data : _dataSnapshot.getChildren()) {
							HashMap<String, Object> _map = _data.getValue(_ind);
							notificationml.add(_map);
						}
					}
					catch (Exception _e) {
						_e.printStackTrace();
					}
					lv.setAdapter(new LvAdapter(notificationml));
					((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
			});
		}
	}
	
	
	public void _design () {
		android.graphics.drawable.GradientDrawable btnExit_design = new android.graphics.drawable.GradientDrawable();
		btnExit_design.setColor(0xFFDD1D5E);
		btnExit_design.setCornerRadius((float)50);
		btnExit_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnExit_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnExit_design, null);
		btnExit.setBackground(btnExit_re);
	}
	
	
	public class LvAdapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public LvAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.lv_notification, null);
			}
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final TextView description = (TextView) _view.findViewById(R.id.description);
			final Button btnMarkAsRead = (Button) _view.findViewById(R.id.btnMarkAsRead);
			
			// Designs
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)8);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), box_design, null);
			box.setBackground(box_re);
			android.graphics.drawable.GradientDrawable btnMarkAsRead_design = new android.graphics.drawable.GradientDrawable();
			btnMarkAsRead_design.setColor(0xFFDD1D5E);
			btnMarkAsRead_design.setCornerRadius((float)8);
			btnMarkAsRead_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnMarkAsRead_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnMarkAsRead_design, null);
			btnMarkAsRead.setBackground(btnMarkAsRead_re);
			btnMarkAsRead.setVisibility(View.GONE);
			if (_data.get((int)_position).containsKey("unread")) {
				if (_data.get((int)_position).get("unread").toString().equals("true")) {
					btnMarkAsRead.setVisibility(View.VISIBLE);
				}
			}
			else {
				btnMarkAsRead.setVisibility(View.VISIBLE);
			}
			// Bind
			description.setText(_data.get((int)_position).get("message").toString());
			btnMarkAsRead.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					dbnotifadmin.child(_data.get((int)_position).get("logid").toString()).child("unread").setValue("false");
				}
			});
			
			return _view;
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
