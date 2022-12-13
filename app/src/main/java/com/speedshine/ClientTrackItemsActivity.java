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
import android.view.View;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ClientTrackItemsActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String userid = "";
	private  DatabaseReference appdb;
	private double index = 0;
	private  ChildEventListener appdblistener;
	private HashMap<String, Object> feedbackMap = new HashMap<>();
	private String feedbackid = "";
	
	private ArrayList<HashMap<String, Object>> appointment_lm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> productsLm = new ArrayList<>();
	private ArrayList<String> productsString = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private ListView lv;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;
	
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
	private DatabaseReference dbaccount = _firebase.getReference("account");
	private ChildEventListener _dbaccount_child_listener;
	private DatabaseReference dbFeedback = _firebase.getReference("feedback");
	private ChildEventListener _dbFeedback_child_listener;
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_track_items);
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
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
			}
		});
		
		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
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
		
		_dbFeedback_child_listener = new ChildEventListener() {
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
		dbFeedback.addChildEventListener(_dbFeedback_child_listener);
		
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
		appdb = _firebase.getReference("appointments/" + userid);
		_childEventListener();
		_loadFirebase();
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
		lv.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, productsString));
		((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _loadFirebase () {
		appdb.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				appointment_lm = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						appointment_lm.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				lv.setAdapter(new LvAdapter(appointment_lm));
				((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public void _childEventListener () {
		appdblistener = new ChildEventListener() {
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
				        _loadFirebase();
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
			    @Override
			    public void onChildMoved(DataSnapshot _param1, String _param2) {
				         
				    }
		};
		appdb.addChildEventListener(appdblistener);
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
				_view = _inflater.inflate(R.layout.lv_myitems, null);
			}
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final LinearLayout boxStatus = (LinearLayout) _view.findViewById(R.id.boxStatus);
			final TextView products = (TextView) _view.findViewById(R.id.products);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout boxControls = (LinearLayout) _view.findViewById(R.id.boxControls);
			final TextView status = (TextView) _view.findViewById(R.id.status);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView qty = (TextView) _view.findViewById(R.id.qty);
			final ImageView imageview3 = (ImageView) _view.findViewById(R.id.imageview3);
			final TextView date = (TextView) _view.findViewById(R.id.date);
			final Button btnFeedBack = (Button) _view.findViewById(R.id.btnFeedBack);
			final Button button2 = (Button) _view.findViewById(R.id.button2);
			
			// Design
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)10);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), box_design, null);
			box.setBackground(box_re);
			android.graphics.drawable.GradientDrawable boxStatus_design = new android.graphics.drawable.GradientDrawable();
			boxStatus_design.setColor(0xFFDD1D5E);
			boxStatus_design.setCornerRadius((float)10);
			boxStatus_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable boxStatus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxStatus_design, null);
			boxStatus.setBackground(boxStatus_re);
			android.graphics.drawable.GradientDrawable btnFeedBack_design = new android.graphics.drawable.GradientDrawable();
			btnFeedBack_design.setColor(0xFFDD1D5E);
			btnFeedBack_design.setCornerRadius((float)10);
			btnFeedBack_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnFeedBack_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnFeedBack_design, null);
			btnFeedBack.setBackground(btnFeedBack_re);
			android.graphics.drawable.GradientDrawable button2_design = new android.graphics.drawable.GradientDrawable();
			button2_design.setColor(0xFFDD1D5E);
			button2_design.setCornerRadius((float)10);
			button2_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable button2_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), button2_design, null);
			button2.setBackground(button2_re);
			boxControls.setVisibility(View.GONE);
			// Bind
			productsLm.clear();
			productsString.clear();
			productsLm = new Gson().fromJson(_data.get((int)_position).get("products").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			index = 0;
			for(int _repeat18 = 0; _repeat18 < (int)(productsLm.size()); _repeat18++) {
				products.setText(products.getText().toString() + " • " + productsLm.get((int)index).get("unit").toString().toUpperCase());
				index++;
			}
			if (_data.get((int)_position).get("status").toString().equals("Done")) {
				if (_data.get((int)_position).containsKey("isFeedback")) {
					if (_data.get((int)_position).get("isFeedback").toString().equals("1")) {
						boxControls.setVisibility(View.GONE);
					}
					else {
						boxControls.setVisibility(View.VISIBLE);
					}
				}
				else {
					boxControls.setVisibility(View.VISIBLE);
				}
			}
			else {
				boxControls.setVisibility(View.GONE);
			}
			status.setText(_data.get((int)_position).get("status").toString().toUpperCase());
			date.setText(_data.get((int)_position).get("created_at").toString());
			price.setText("₱ " + new DecimalFormat("###,###,###.##").format(Double.parseDouble(_data.get((int)_position).get("price").toString())));
			qty.setText(_data.get((int)_position).get("cart").toString());
			// Events
			btnFeedBack.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.putExtra("appointment_id", _data.get((int)_position).get("appointment_id").toString());
					i.setClass(getApplicationContext(), ClientFeedbackActivity.class);
					startActivity(i);
				}
			});
			button2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					appdb.child(_data.get((int)_position).get("appointment_id").toString()).child("isFeedback").setValue("0");
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