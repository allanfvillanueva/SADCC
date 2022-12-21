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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ManageProductsActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double mapindex = 0;
	private double maplength = 0;
	private String productsListMapJson = "";
	
	private ArrayList<HashMap<String, Object>> productsListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> fromDbProductsLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> resultFromSearchMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout body;
	private LinearLayout btnBack;
	private TextView textview1;
	private ImageView imageview1;
	private LinearLayout boxAction;
	private LinearLayout boxFilter;
	private ListView lv;
	private Button btnAdd;
	private LinearLayout linear4;
	private EditText tbSearch;
	private ImageView imageview2;
	private TextView textview2;
	
	private Intent i = new Intent();
	private DatabaseReference fbdb = _firebase.getReference("products");
	private ChildEventListener _fbdb_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.manage_products);
		initialize(_savedInstanceState);

		Log.d("av","ManageProductActivity onCreate");

		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		body = (LinearLayout) findViewById(R.id.body);
		btnBack = (LinearLayout) findViewById(R.id.btnBack);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		boxAction = (LinearLayout) findViewById(R.id.boxAction);
		boxFilter = (LinearLayout) findViewById(R.id.boxFilter);
		lv = (ListView) findViewById(R.id.lv);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		tbSearch = (EditText) findViewById(R.id.tbSearch);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		textview2 = (TextView) findViewById(R.id.textview2);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), AddProductActivity.class);
				startActivity(i);
			}
		});
		
		tbSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				fromDbProductsLm = new Gson().fromJson(productsListMapJson, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				mapindex = 0;
				maplength = fromDbProductsLm.size();
				resultFromSearchMap.clear();
				for(int _repeat15 = 0; _repeat15 < (int)(maplength); _repeat15++) {
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
		
		_fbdb_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						productsListMap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								productsListMap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						productsListMapJson = new Gson().toJson(productsListMap);
						lv.setAdapter(new LvAdapter(productsListMap));
						((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
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
	}
	
	private void initializeLogic() {
		_design();
		fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				productsListMap = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						productsListMap.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				productsListMapJson = new Gson().toJson(productsListMap);
				lv.setAdapter(new LvAdapter(productsListMap));
				((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
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
	
	@Override
	public void onResume() {
		super.onResume();
		fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				productsListMap = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						productsListMap.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				productsListMapJson = new Gson().toJson(productsListMap);
				lv.setAdapter(new LvAdapter(productsListMap));
				((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	public void _design () {
		// TB
		android.graphics.drawable.GradientDrawable tbSearch_design = new android.graphics.drawable.GradientDrawable();
		tbSearch_design.setColor(0xFFFFFFFF);
		tbSearch_design.setCornerRadius((float)3);
		tbSearch_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbSearch_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbSearch_design, null);
		tbSearch.setBackground(tbSearch_re);
		// Boxes
		boxFilter.setElevation((float)5);
		android.graphics.drawable.GradientDrawable boxFilter_design = new android.graphics.drawable.GradientDrawable();
		boxFilter_design.setColor(0xFFFFFFFF);
		boxFilter_design.setCornerRadius((float)5);
		boxFilter_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable boxFilter_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxFilter_design, null);
		boxFilter.setBackground(boxFilter_re);
		boxAction.setElevation((float)5);
		android.graphics.drawable.GradientDrawable boxAction_design = new android.graphics.drawable.GradientDrawable();
		boxAction_design.setColor(0xFFFFFFFF);
		boxAction_design.setCornerRadius((float)5);
		boxAction_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable boxAction_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxAction_design, null);
		boxAction.setBackground(boxAction_re);
		// Buttons
		android.graphics.drawable.GradientDrawable btnBack_design = new android.graphics.drawable.GradientDrawable();
		btnBack_design.setColor(0xFFDD1D5E);
		btnBack_design.setCornerRadius((float)100);
		btnBack_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable btnBack_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnBack_design, null);
		btnBack.setBackground(btnBack_re);
		android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
		btnAdd_design.setColor(0xFFDD1D5E);
		btnAdd_design.setCornerRadius((float)8);
		btnAdd_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAdd_design, null);
		btnAdd.setBackground(btnAdd_re);
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
				_view = _inflater.inflate(R.layout.lv_products, null);
			}
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final de.hdodenhof.circleimageview.CircleImageView circleimageview1 = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.circleimageview1);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView unit = (TextView) _view.findViewById(R.id.unit);
			final TextView brand = (TextView) _view.findViewById(R.id.brand);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final TextView stock_count = (TextView) _view.findViewById(R.id.stock_count);
			final TextView textview6 = (TextView) _view.findViewById(R.id.textview6);
			final TextView rating = (TextView) _view.findViewById(R.id.rating);
			
			// Design
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)5);
			box_design.setStroke((int)0,0xFFFFFFFF);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), box_design, null);
			box.setBackground(box_re);
			// Bind
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(circleimageview1);
			brand.setText(_data.get((int)_position).get("brand").toString());
			unit.setText(_data.get((int)_position).get("unit").toString());
			stock_count.setText(_data.get((int)_position).get("stock").toString());
			price.setText(new DecimalFormat("###,###,###.##").format(Double.parseDouble(_data.get((int)_position).get("price").toString())));
			// Events
			box.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.putExtra("unique_id", _data.get((int)_position).get("unique_id").toString());
					i.setClass(getApplicationContext(), ViewProductActivity.class);
					startActivity(i);
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
