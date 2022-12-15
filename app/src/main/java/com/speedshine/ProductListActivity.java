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
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.EditText;
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
import android.text.Editable;
import android.text.TextWatcher;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ProductListActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double index = 0;
	private HashMap<String, Object> brandMap = new HashMap<>();
	private String productsLMJson = "";
	private double mapindex = 0;
	private double maplength = 0;
	private double currentPos = 0;
	
	private ArrayList<HashMap<String, Object>> productsLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> brandLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> fromDbProductsLm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> resultFromSearchMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout boxTbSearch;
	private TextView textview4;
	private RecyclerView rv;
	private TextView textview5;
	private ListView lv;
	private LinearLayout boxEmpty;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;
	private ImageView imageview3;
	private EditText tbSearch;
	private ImageView imageview5;
	private TextView textview6;
	
	private DatabaseReference fbdbProducts = _firebase.getReference("products");
	private ChildEventListener _fbdbProducts_child_listener;
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.product_list);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		boxTbSearch = (LinearLayout) findViewById(R.id.boxTbSearch);
		textview4 = (TextView) findViewById(R.id.textview4);
		rv = (RecyclerView) findViewById(R.id.rv);
		textview5 = (TextView) findViewById(R.id.textview5);
		lv = (ListView) findViewById(R.id.lv);
		boxEmpty = (LinearLayout) findViewById(R.id.boxEmpty);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		tbSearch = (EditText) findViewById(R.id.tbSearch);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview6 = (TextView) findViewById(R.id.textview6);
		
		boxEmpty.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
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
				fromDbProductsLm = new Gson().fromJson(productsLMJson, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				mapindex = 0;
				maplength = fromDbProductsLm.size();
				resultFromSearchMap.clear();
				for(int _repeat16 = 0; _repeat16 < (int)(maplength); _repeat16++) {
					if (new Gson().toJson(fromDbProductsLm.get((int)(mapindex))).toLowerCase().contains(tbSearch.getText().toString().toLowerCase())) {
						resultFromSearchMap.add(fromDbProductsLm.get((int)(mapindex)));
					}
					mapindex++;
				}
				if (resultFromSearchMap.size() == 0) {
					lv.setVisibility(View.GONE);
					boxEmpty.setVisibility(View.VISIBLE);
				}
				else {
					lv.setVisibility(View.VISIBLE);
					boxEmpty.setVisibility(View.GONE);
					lv.setAdapter(new LvAdapter(resultFromSearchMap));
					((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		_fbdbProducts_child_listener = new ChildEventListener() {
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
		fbdbProducts.addChildEventListener(_fbdbProducts_child_listener);
	}
	
	private void initializeLogic() {
		_design();
		fbdbProducts.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				productsLm = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						productsLm.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				productsLMJson = new Gson().toJson(productsLm);
				_loadRecycleView();
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
	
	public void _design () {
		boxTbSearch.setElevation((float)5);
		android.graphics.drawable.GradientDrawable boxTbSearch_design = new android.graphics.drawable.GradientDrawable();
		boxTbSearch_design.setColor(0xFFFFFFFF);
		boxTbSearch_design.setCornerRadius((float)50);
		boxTbSearch_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable boxTbSearch_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxTbSearch_design, null);
		boxTbSearch.setBackground(boxTbSearch_re);
	}
	
	
	public void _loadRecycleView () {
		index = 0;
		for(int _repeat13 = 0; _repeat13 < (int)(productsLm.size()); _repeat13++) {
			brandMap = new HashMap<>();
			brandMap.put("brand", productsLm.get((int)index).get("brand").toString());
			brandLm.add(brandMap);
			index++;
		}
		LinkedHashSet<HashMap<String, Object>> set = new LinkedHashSet<HashMap<String, Object>>(brandLm);
		ArrayList<HashMap<String, Object>> brandWithoutDuplicate = new ArrayList<HashMap<String, Object>>(set);
		rv.setAdapter(new RvAdapter(brandWithoutDuplicate));
		rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		if (productsLm.size() == 0) {
			lv.setVisibility(View.GONE);
			boxEmpty.setVisibility(View.VISIBLE);
		}
		else {
			lv.setVisibility(View.VISIBLE);
			boxEmpty.setVisibility(View.GONE);
			lv.setAdapter(new LvAdapter(productsLm));
			((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
		}
	}
	
	
	public void _SearchProductsByBrand (final String _keyword) {
		tbSearch.setText(_keyword);
	}
	
	
	public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public RvAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.rv_brand, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final LinearLayout brandbg = (LinearLayout) _view.findViewById(R.id.brandbg);
			final TextView brandName = (TextView) _view.findViewById(R.id.brandName);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			brandName.setText(_data.get((int)_position).get("brand").toString().toUpperCase());
			android.graphics.drawable.GradientDrawable brandbg_design = new android.graphics.drawable.GradientDrawable();
			brandbg_design.setColor(0xFFDD1D5E);
			brandbg_design.setCornerRadius((float)8);
			brandbg_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable brandbg_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), brandbg_design, null);
			brandbg.setBackground(brandbg_re);
			brandbg.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_SearchProductsByBrand(_data.get((int)_position).get("brand").toString());
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
		
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
				_view = _inflater.inflate(R.layout.lv_shop_products, null);
			}
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final ImageView picture = (ImageView) _view.findViewById(R.id.picture);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView unit = (TextView) _view.findViewById(R.id.unit);
			final TextView brand = (TextView) _view.findViewById(R.id.brand);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView like = (TextView) _view.findViewById(R.id.like);
			final ImageView imageview3 = (ImageView) _view.findViewById(R.id.imageview3);
			final TextView dislike = (TextView) _view.findViewById(R.id.dislike);
			
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(picture);
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)8);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), box_design, null);
			box.setBackground(box_re);
			unit.setText(_data.get((int)_position).get("unit").toString().toUpperCase());
			brand.setText(_data.get((int)_position).get("brand").toString().toUpperCase());
			price.setText("₱ ".concat(new DecimalFormat("###,###,###.##").format(Double.parseDouble(_data.get((int)_position).get("price").toString()))));
			like.setText(_data.get((int)_position).get("likes").toString());
			dislike.setText(_data.get((int)_position).get("dislikes").toString());
			box.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					i.putExtra("unique_id", _data.get((int)_position).get("unique_id").toString());
					i.setClass(getApplicationContext(), ClientViewProductActivity.class);
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
