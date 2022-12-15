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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ClientMakeAppointmentActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String SelectedItemString = "";
	private double i1 = 0;
	private double totalOfSelectedItem = 0;
	private double l1 = 0;
	private  DatabaseReference appointmentdb;
	private String userid = "";
	private HashMap<String, Object> appointmentMap = new HashMap<>();
	private String appointment_str = "";
	private  ChildEventListener appointment_child_listener;
	private boolean newAppointment = false;
	private  DatabaseReference cartdb;
	private double dbcarts = 0;
	private double total = 0;
	private HashMap<String, Object> logsMap = new HashMap<>();
	private String logId = "";
	private HashMap<String, Object> LoggedAccountInfoMap = new HashMap<>();
	private String u_fname = "";
	private String u_lastname = "";
	private String u_middlename = "";
	private String u_fullname = "";
	private  DatabaseReference dbaccount;
	
	private ArrayList<HashMap<String, Object>> SelectedItemListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> selectedItemListMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout body;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;
	private TextView textview8;
	private EditText tbName;
	private TextView textview9;
	private EditText tbCnum;
	private TextView textview7;
	private LinearLayout boxDate;
	private TextView textview10;
	private LinearLayout boxTime;
	private TextView textview11;
	private ListView lv;
	private TextView textview12;
	private TextView totalPrice;
	private Button btnCreateAppointment;
	private TextView tbdate;
	private ImageView imageview5;
	private TextView time;
	private ImageView imageview6;
	
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
	private Calendar c = Calendar.getInstance();
	private DatabaseReference fbdbproducts = _firebase.getReference("products");
	private ChildEventListener _fbdbproducts_child_listener;
	private ProgressDialog pd;
	private TimerTask tmr;
	private DatabaseReference dbNotifAdmin = _firebase.getReference("notif_admin");
	private ChildEventListener _dbNotifAdmin_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_make_appointment);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		body = (LinearLayout) findViewById(R.id.body);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		textview8 = (TextView) findViewById(R.id.textview8);
		tbName = (EditText) findViewById(R.id.tbName);
		textview9 = (TextView) findViewById(R.id.textview9);
		tbCnum = (EditText) findViewById(R.id.tbCnum);
		textview7 = (TextView) findViewById(R.id.textview7);
		boxDate = (LinearLayout) findViewById(R.id.boxDate);
		textview10 = (TextView) findViewById(R.id.textview10);
		boxTime = (LinearLayout) findViewById(R.id.boxTime);
		textview11 = (TextView) findViewById(R.id.textview11);
		lv = (ListView) findViewById(R.id.lv);
		textview12 = (TextView) findViewById(R.id.textview12);
		totalPrice = (TextView) findViewById(R.id.totalPrice);
		btnCreateAppointment = (Button) findViewById(R.id.btnCreateAppointment);
		tbdate = (TextView) findViewById(R.id.tbdate);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		time = (TextView) findViewById(R.id.time);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		fbauth = FirebaseAuth.getInstance();
		
		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		boxDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_showDatePicker();
			}
		});
		
		boxTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				DialogFragment newFragment = new TimePickerFragment();
				
				
				newFragment.show(getSupportFragmentManager(), "timePicker");
			}
		});
		
		btnCreateAppointment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tbName.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your name");
					return;
				}
				if (tbCnum.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your contact number");
					return;
				}
				appointment_str = appointmentdb.push().getKey();
				newAppointment = true;
				appointmentMap = new HashMap<>();
				appointmentMap.put("appointment_id", appointment_str);
				appointmentMap.put("name", tbName.getText().toString());
				appointmentMap.put("cnum", tbCnum.getText().toString());
				appointmentMap.put("products", new Gson().toJson(selectedItemListMap));
				appointmentMap.put("date", tbdate.getText().toString());
				appointmentMap.put("time", time.getText().toString());
				appointmentMap.put("status", "Pending");
				appointmentMap.put("cart", String.valueOf((long)(selectedItemListMap.size())));
				appointmentMap.put("price", String.valueOf((long)(total)));
				appointmentMap.put("userid", userid);
				c = Calendar.getInstance();
				appointmentMap.put("created_at", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(c.getTime()));
				appointmentdb.child(appointment_str).updateChildren(appointmentMap);
				pd.setMessage("Saving your appointment, Please wait.");
				pd.setCancelable(false);
				pd.setCanceledOnTouchOutside(false);
				pd.show();
				_deleteSelectedItem();
				_Logs(u_fullname + " set an appointment " + "" + tbdate.getText().toString() + " " + time.getText().toString());
			}
		});
		
		_fbdbproducts_child_listener = new ChildEventListener() {
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
		fbdbproducts.addChildEventListener(_fbdbproducts_child_listener);
		
		_dbNotifAdmin_child_listener = new ChildEventListener() {
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
		dbNotifAdmin.addChildEventListener(_dbNotifAdmin_child_listener);
		
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
		pd = new ProgressDialog(ClientMakeAppointmentActivity.this);
		newAppointment = false;
		//userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
		userid = "uhrzwFpnfmWN6lQCWsu4HkPEee12"; //FirebaseAuth.getInstance().getCurrentUser().getUid();
		SelectedItemString = getIntent().getStringExtra("selected_item");
		selectedItemListMap = new Gson().fromJson(SelectedItemString, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
		_GetTotalItemSelected();
		lv.setAdapter(new LvAdapter(selectedItemListMap));
		((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
		appointmentdb = _firebase.getReference("appointments/" + userid);
		cartdb = _firebase.getReference("carts/" + userid);
		_firebaseChildEvent();
		_LoadLoggedAccountInfo(userid);
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _extraForTimePickerDialog () {
	}
	
	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			return new TimePickerDialog(getActivity(), this, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
		}
		
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			TextView textview101 = getActivity().findViewById(R.id.time);
			textview101.setText(hourOfDay + ":" + minute);
		}
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
	
	
	public void _design () {
		android.graphics.drawable.GradientDrawable btnExit_design = new android.graphics.drawable.GradientDrawable();
		btnExit_design.setColor(0xFFDD1D5E);
		btnExit_design.setCornerRadius((float)50);
		btnExit_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnExit_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnExit_design, null);
		btnExit.setBackground(btnExit_re);
		android.graphics.drawable.GradientDrawable tbName_design = new android.graphics.drawable.GradientDrawable();
		tbName_design.setColor(0xFFFFFFFF);
		tbName_design.setCornerRadius((float)10);
		tbName_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbName_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbName_design, null);
		tbName.setBackground(tbName_re);
		android.graphics.drawable.GradientDrawable tbCnum_design = new android.graphics.drawable.GradientDrawable();
		tbCnum_design.setColor(0xFFFFFFFF);
		tbCnum_design.setCornerRadius((float)10);
		tbCnum_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbCnum_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbCnum_design, null);
		tbCnum.setBackground(tbCnum_re);
		android.graphics.drawable.GradientDrawable boxDate_design = new android.graphics.drawable.GradientDrawable();
		boxDate_design.setColor(0xFFFFFFFF);
		boxDate_design.setCornerRadius((float)10);
		boxDate_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable boxDate_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxDate_design, null);
		boxDate.setBackground(boxDate_re);
		android.graphics.drawable.GradientDrawable boxTime_design = new android.graphics.drawable.GradientDrawable();
		boxTime_design.setColor(0xFFFFFFFF);
		boxTime_design.setCornerRadius((float)10);
		boxTime_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable boxTime_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxTime_design, null);
		boxTime.setBackground(boxTime_re);
		android.graphics.drawable.GradientDrawable btnCreateAppointment_design = new android.graphics.drawable.GradientDrawable();
		btnCreateAppointment_design.setColor(0xFFDD1D5E);
		btnCreateAppointment_design.setCornerRadius((float)10);
		btnCreateAppointment_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnCreateAppointment_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnCreateAppointment_design, null);
		btnCreateAppointment.setBackground(btnCreateAppointment_re);
	}
	
	
	public void _GetTotalItemSelected () {
		i1 = 0;
		totalOfSelectedItem = 0;
		l1 = selectedItemListMap.size();
		if (l1 == 0) {
			
		}
		else {
			for(int _repeat13 = 0; _repeat13 < (int)(l1); _repeat13++) {
				totalOfSelectedItem = totalOfSelectedItem + (Double.parseDouble(selectedItemListMap.get((int)i1).get("price").toString()) * Double.parseDouble(selectedItemListMap.get((int)i1).get("quantity").toString()));
				i1++;
			}
		}
		total = totalOfSelectedItem;
		totalPrice.setText("₱ ".concat(new DecimalFormat("###,###,###.##").format(totalOfSelectedItem)));
	}
	
	
	public void _firebaseChildEvent () {
		appointment_child_listener = new ChildEventListener() {
			        @Override
			    public void onChildAdded(DataSnapshot _param1, String _param2) {
				        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				        final String _childKey = _param1.getKey();
				        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				        if (newAppointment) {
					newAppointment = false;
					SketchwareUtil.showMessage(getApplicationContext(), "New appointment was succesfully created.");
					finish();
				}
				    }
			    @Override
			    public void onChildChanged(DataSnapshot _param1, String _param2) {
				        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				        final String _childKey = _param1.getKey();
				        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				         
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
		appointmentdb.addChildEventListener(appointment_child_listener);
	}
	
	
	public void _deleteSelectedItem () {
		i1 = 0;
		totalOfSelectedItem = 0;
		l1 = selectedItemListMap.size();
		for(int _repeat13 = 0; _repeat13 < (int)(l1); _repeat13++) {
			cartdb.child(selectedItemListMap.get((int)i1).get("product_id").toString()).removeValue();
			i1++;
		}
	}
	
	
	public void _Logs (final String _message) {
		logsMap = new HashMap<>();
		logId = dbNotifAdmin.push().getKey();
		logsMap.put("logid", logId);
		logsMap.put("message", _message);
		dbNotifAdmin.child(logId).updateChildren(logsMap);
	}
	
	
	public void _LoadLoggedAccountInfo (final String _uid) {
		dbaccount = _firebase.getReference("account");
		dbaccount.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
						Map<String,Object> LoggedAccountInfoMap = (Map<String,Object>) _dataSnapshot.child(_uid).getValue();
				u_fname = LoggedAccountInfoMap.get("fname").toString();
				u_lastname = LoggedAccountInfoMap.get("lname").toString();
				u_middlename = LoggedAccountInfoMap.get("mname").toString();
				u_fullname = u_lastname + ", " + u_fname + " " + u_middlename;
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
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
				_view = _inflater.inflate(R.layout.lv_user_cart, null);
			}
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final TextView selected_item = (TextView) _view.findViewById(R.id.selected_item);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout realBoxControl = (LinearLayout) _view.findViewById(R.id.realBoxControl);
			final CheckBox cb = (CheckBox) _view.findViewById(R.id.cb);
			final ImageView thumbnail = (ImageView) _view.findViewById(R.id.thumbnail);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear7 = (LinearLayout) _view.findViewById(R.id.linear7);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final LinearLayout linear8 = (LinearLayout) _view.findViewById(R.id.linear8);
			final LinearLayout boxStatus = (LinearLayout) _view.findViewById(R.id.boxStatus);
			final TextView unit = (TextView) _view.findViewById(R.id.unit);
			final TextView brand = (TextView) _view.findViewById(R.id.brand);
			final TextView statusText = (TextView) _view.findViewById(R.id.statusText);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final LinearLayout boxControl = (LinearLayout) _view.findViewById(R.id.boxControl);
			final Button btnRemove = (Button) _view.findViewById(R.id.btnRemove);
			final Button btnAddQty = (Button) _view.findViewById(R.id.btnAddQty);
			final TextView qty = (TextView) _view.findViewById(R.id.qty);
			final Button btnDiminishQty = (Button) _view.findViewById(R.id.btnDiminishQty);
			
			// Design
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)10);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFFFFFF }), box_design, null);
			box.setBackground(box_re);
			cb.setVisibility(View.GONE);
			boxStatus.setVisibility(View.GONE);
			realBoxControl.setVisibility(View.GONE);
			// Bind
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(thumbnail);
			unit.setText(_data.get((int)_position).get("unit").toString().toUpperCase());
			brand.setText(_data.get((int)_position).get("brand").toString().toUpperCase());
			qty.setText(_data.get((int)_position).get("quantity").toString().toUpperCase());
			price.setText("₱ ".concat(new DecimalFormat("###,###,###.##").format(Double.parseDouble(_data.get((int)_position).get("price").toString()))));
			
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
