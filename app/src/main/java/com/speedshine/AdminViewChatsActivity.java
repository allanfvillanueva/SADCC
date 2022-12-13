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
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AdminViewChatsActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private  DatabaseReference dbchats;
	private HashMap<String, Object> messageMap = new HashMap<>();
	private double i = 0;
	private String nameUser = "";
	private String chatjsonstr = "";
	private double mapindex = 0;
	private double maplength = 0;
	private  DataSnapshot chatSnapshot;
	private double LengthOfAllMessages = 0;
	private double IndexOfTheNewMessage = 0;
	
	private ArrayList<HashMap<String, Object>> chatLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> messageLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> usersLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> fromDbProductsLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> resultFromSearchMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> AllMessagesLM = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout boxTbSearch;
	private ListView lv;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;
	private ImageView imageview3;
	private EditText tbSearch;
	
	private DatabaseReference dbaccount = _firebase.getReference("account");
	private ChildEventListener _dbaccount_child_listener;
	private TimerTask tmr;
	private Intent intent = new Intent();
	private DatabaseReference dbchatroom = _firebase.getReference("chatroom");
	private ChildEventListener _dbchatroom_child_listener;
	private DatabaseReference dbchat = _firebase.getReference("chat");
	private ChildEventListener _dbchat_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.admin_view_chats);
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
		
		tbSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				fromDbProductsLm = new Gson().fromJson(chatjsonstr, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				mapindex = 0;
				maplength = fromDbProductsLm.size();
				resultFromSearchMap.clear();
				for(int _repeat18 = 0; _repeat18 < (int)(maplength); _repeat18++) {
					if (new Gson().toJson(fromDbProductsLm.get((int)(mapindex))).toLowerCase().contains(tbSearch.getText().toString().toLowerCase())) {
						resultFromSearchMap.add(fromDbProductsLm.get((int)(mapindex)));
					}
					mapindex++;
				}
				lv.setAdapter(new LvAdapter(resultFromSearchMap));
				((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
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
		
		_dbchatroom_child_listener = new ChildEventListener() {
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
		dbchatroom.addChildEventListener(_dbchatroom_child_listener);
		
		_dbchat_child_listener = new ChildEventListener() {
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
		dbchat.addChildEventListener(_dbchat_child_listener);
	}
	
	private void initializeLogic() {
		_design();
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
	
	
	public void _LoadNewMessage (final String _uid, final TextView _tb) {
		dbchats = _firebase.getReference("chat/" + _uid);
		dbchats.addListenerForSingleValueEvent(new ValueEventListener() {
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
				LengthOfAllMessages = AllMessagesLM.size();
				IndexOfTheNewMessage = LengthOfAllMessages - 1;
				if (IndexOfTheNewMessage < 0) {
					_tb.setText("No Message(s)");
				}
				else {
					if (AllMessagesLM.get((int)IndexOfTheNewMessage).get("type").toString().equals("text")) {
						if (AllMessagesLM.get((int)IndexOfTheNewMessage).get("userid").toString().equals("admin")) {
							_tb.setText("Admin : " + AllMessagesLM.get((int)IndexOfTheNewMessage).get("content").toString());
						}
						else {
							_tb.setText(AllMessagesLM.get((int)IndexOfTheNewMessage).get("content").toString());
						}
					}
					else {
						if (AllMessagesLM.get((int)IndexOfTheNewMessage).get("userid").toString().equals("admin")) {
							_tb.setText("Admin : Sent a image");
						}
						else {
							_tb.setText("Sent a image");
						}
					}
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public void _loadFirebase () {
		dbchatroom.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				chatLm = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						chatLm.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				chatjsonstr = new Gson().toJson(chatLm);
				lv.setAdapter(new LvAdapter(chatLm));
				((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
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
				_view = _inflater.inflate(R.layout.lv_user_chats, null);
			}
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final de.hdodenhof.circleimageview.CircleImageView cv = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.cv);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final TextView message = (TextView) _view.findViewById(R.id.message);
			
			// Design
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)10);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), box_design, null);
			box.setBackground(box_re);
			message.setVisibility(View.VISIBLE);
			// Logic
			_LoadNewMessage(_data.get((int)_position).get("uid").toString(), message);
			// Bind
			name.setText(_data.get((int)_position).get("lname").toString().toUpperCase() + " ," + _data.get((int)_position).get("fname").toString().toUpperCase() + " " + _data.get((int)_position).get("mname").toString().toUpperCase());
			// Events
			box.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					intent.putExtra("userid", _data.get((int)_position).get("uid").toString());
					intent.putExtra("name", _data.get((int)_position).get("lname").toString().toUpperCase() + " ," + _data.get((int)_position).get("fname").toString().toUpperCase() + " " + _data.get((int)_position).get("mname").toString().toUpperCase());
					intent.setClass(getApplicationContext(), ClientChatActivity.class);
					startActivity(intent);
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