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
import android.widget.EditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ManageAppointmentActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double index = 0;
	private double length = 0;
	private double index1 = 0;
	private double length1 = 0;
	private HashMap<String, Object> appointmentmap = new HashMap<>();
	private  DatabaseReference appdb;
	private  DatabaseReference dbnotification;
	private HashMap<String, Object> createNotificationMap = new HashMap<>();
	private String notificationid = "";
	private  ChildEventListener AppointmentCE;
	
	private ArrayList<HashMap<String, Object>> appointmentslm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> allappointmentlm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> appointmentlm = new ArrayList<>();
	private ArrayList<String> appointmapliststring = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout boxTbSearch;
	private ListView lv;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;
	private ImageView imageview3;
	private EditText tbSearch;
	
	private DatabaseReference fbappointments = _firebase.getReference("appointments");
	private ChildEventListener _fbappointments_child_listener;
	private Calendar c = Calendar.getInstance();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.manage_appointment);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		boxTbSearch = (LinearLayout) findViewById(R.id.boxTbSearch);
		lv = (ListView) findViewById(R.id.lv);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		tbSearch = (EditText) findViewById(R.id.tbSearch);
		
		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		_fbappointments_child_listener = new ChildEventListener() {
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
		fbappointments.addChildEventListener(_fbappointments_child_listener);
	}
	
	private void initializeLogic() {
		_design();
		_loadAllAppointments();
		appdb = _firebase.getReference("appointments");
		_AppointmentChildEvent();
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
		boxTbSearch.setElevation((float)5);
		android.graphics.drawable.GradientDrawable boxTbSearch_design = new android.graphics.drawable.GradientDrawable();
		boxTbSearch_design.setColor(0xFFFFFFFF);
		boxTbSearch_design.setCornerRadius((float)50);
		boxTbSearch_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable boxTbSearch_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxTbSearch_design, null);
		boxTbSearch.setBackground(boxTbSearch_re);
		android.graphics.drawable.GradientDrawable btnExit_design = new android.graphics.drawable.GradientDrawable();
		btnExit_design.setColor(0xFFDD1D5E);
		btnExit_design.setCornerRadius((float)50);
		btnExit_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnExit_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnExit_design, null);
		btnExit.setBackground(btnExit_re);
	}
	
	
	public void _loadAllAppointments () {
		fbappointments.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				appointmentslm = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						appointmentslm.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				allappointmentlm.clear();
				index = 0;
				length = appointmentslm.size();
				for(int _repeat14 = 0; _repeat14 < (int)(length); _repeat14++) {
					appointmapliststring.clear();
					SketchwareUtil.getAllKeysFromMap(appointmentslm.get((int)(index)), appointmapliststring);
					index1 = 0;
					length1 = appointmapliststring.size();
					for(int _repeat31 = 0; _repeat31 < (int)(length1); _repeat31++) {
						allappointmentlm.add((HashMap<String,Object>)appointmentslm.get((int)(index)).get(appointmapliststring.get((int)(index1))));
						index1++;
					}
					index++;
				}
				lv.setAdapter(new LvAdapter(allappointmentlm));
				((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public void _UpdateStatus (final String _userid, final String _appointment_id, final String _status) {
		appdb = _firebase.getReference("appointments/" + _userid);
		appdb.child(_appointment_id).child("status").setValue(_status);
	}
	
	
	public void _CreateNotifcation (final String _userid, final String _title, final String _description) {
		dbnotification = _firebase.getReference("notification/" + _userid);
		notificationid = dbnotification.push().getKey();
		createNotificationMap = new HashMap<>();
		createNotificationMap.put("userid", _userid);
		createNotificationMap.put("notificationid", notificationid);
		createNotificationMap.put("title", _title);
		createNotificationMap.put("description", _description);
		c = Calendar.getInstance();
		createNotificationMap.put("date", new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a").format(c.getTime()));
		dbnotification.child(notificationid).updateChildren(createNotificationMap);
	}
	
	
	public void _AppointmentChildEvent () {
		AppointmentCE = new ChildEventListener() {
			        @Override
			    public void onChildAdded(DataSnapshot _param1, String _param2) {
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
			    @Override
			    public void onChildChanged(DataSnapshot _param1, String _param2) {
				        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				        final String _childKey = _param1.getKey();
				        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				        _loadAllAppointments();
				    }
		};
		appdb.addChildEventListener(AppointmentCE);
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
				_view = _inflater.inflate(R.layout.lv_appointments, null);
			}
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final LinearLayout boxbuttons = (LinearLayout) _view.findViewById(R.id.boxbuttons);
			final TextView appointmentid = (TextView) _view.findViewById(R.id.appointmentid);
			final TextView status = (TextView) _view.findViewById(R.id.status);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			final TextView datetime = (TextView) _view.findViewById(R.id.datetime);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView cart = (TextView) _view.findViewById(R.id.cart);
			final ImageView imageview3 = (ImageView) _view.findViewById(R.id.imageview3);
			final TextView total = (TextView) _view.findViewById(R.id.total);
			final ImageView imageview4 = (ImageView) _view.findViewById(R.id.imageview4);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final ImageView imageview5 = (ImageView) _view.findViewById(R.id.imageview5);
			final TextView contact = (TextView) _view.findViewById(R.id.contact);
			final LinearLayout boxApproveDecline = (LinearLayout) _view.findViewById(R.id.boxApproveDecline);
			final Button btnProcess = (Button) _view.findViewById(R.id.btnProcess);
			final Button btnDone = (Button) _view.findViewById(R.id.btnDone);
			final Button btnApprove = (Button) _view.findViewById(R.id.btnApprove);
			final Button btnDecline = (Button) _view.findViewById(R.id.btnDecline);
			
			// Design
			android.graphics.drawable.GradientDrawable btnApprove_design = new android.graphics.drawable.GradientDrawable();
			btnApprove_design.setColor(0xFFE91E63);
			btnApprove_design.setCornerRadius((float)10);
			btnApprove_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnApprove_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnApprove_design, null);
			btnApprove.setBackground(btnApprove_re);
			android.graphics.drawable.GradientDrawable btnDecline_design = new android.graphics.drawable.GradientDrawable();
			btnDecline_design.setColor(0xFFE91E63);
			btnDecline_design.setCornerRadius((float)10);
			btnDecline_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnDecline_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDecline_design, null);
			btnDecline.setBackground(btnDecline_re);
			android.graphics.drawable.GradientDrawable btnDone_design = new android.graphics.drawable.GradientDrawable();
			btnDone_design.setColor(0xFFE91E63);
			btnDone_design.setCornerRadius((float)10);
			btnDone_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnDone_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDone_design, null);
			btnDone.setBackground(btnDone_re);
			android.graphics.drawable.GradientDrawable btnProcess_design = new android.graphics.drawable.GradientDrawable();
			btnProcess_design.setColor(0xFFE91E63);
			btnProcess_design.setCornerRadius((float)10);
			btnProcess_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnProcess_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnProcess_design, null);
			btnProcess.setBackground(btnProcess_re);
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)5);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), box_design, null);
			box.setBackground(box_re);
			boxApproveDecline.setVisibility(View.GONE);
			btnProcess.setVisibility(View.GONE);
			btnDone.setVisibility(View.GONE);
			// BInding
			appointmentid.setText(_data.get((int)_position).get("appointment_id").toString());
			datetime.setText(_data.get((int)_position).get("date").toString().toUpperCase() + " " + _data.get((int)_position).get("time").toString().toUpperCase());
			total.setText(_data.get((int)_position).get("price").toString());
			cart.setText(_data.get((int)_position).get("cart").toString());
			name.setText(_data.get((int)_position).get("name").toString().toUpperCase());
			contact.setText(_data.get((int)_position).get("cnum").toString());
			status.setText(_data.get((int)_position).get("status").toString());
			if (status.getText().toString().equals("Approve")) {
				btnProcess.setVisibility(View.VISIBLE);
			}
			if (status.getText().toString().equals("Pending")) {
				boxApproveDecline.setVisibility(View.VISIBLE);
			}
			if (status.getText().toString().equals("Process")) {
				btnDone.setVisibility(View.VISIBLE);
			}
			// Events
			btnApprove.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_UpdateStatus(_data.get((int)_position).get("userid").toString(), _data.get((int)_position).get("appointment_id").toString(), "Approve");
					_CreateNotifcation(_data.get((int)_position).get("userid").toString(), "APPOINTMENT", "Your appointment has been approved.");
				}
			});
			btnDecline.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_UpdateStatus(_data.get((int)_position).get("userid").toString(), _data.get((int)_position).get("appointment_id").toString(), "Decline");
					_CreateNotifcation(_data.get((int)_position).get("userid").toString(), "APPOINTMENT", "Your appointment has been declined. We are sorry about that.");
				}
			});
			btnProcess.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_UpdateStatus(_data.get((int)_position).get("userid").toString(), _data.get((int)_position).get("appointment_id").toString(), "Process");
					_CreateNotifcation(_data.get((int)_position).get("userid").toString(), "APPOINTMENT", "Your appointment was on process.");
				}
			});
			btnDone.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_UpdateStatus(_data.get((int)_position).get("userid").toString(), _data.get((int)_position).get("appointment_id").toString(), "Done");
					_CreateNotifcation(_data.get((int)_position).get("userid").toString(), "APPOINTMENT", "Your appointment is done.");
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