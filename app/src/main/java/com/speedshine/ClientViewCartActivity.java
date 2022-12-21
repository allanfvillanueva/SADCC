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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Button;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import com.google.gson.reflect.TypeToken;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ClientViewCartActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private  DatabaseReference cartdb;
	private String userid = "";
	private HashMap<String, Object> selectedMap = new HashMap<>();
	private double i = 0;
	private double l = 0;
	private double i1 = 0;
	private double l1 = 0;
	private double totalOfSelectedItem = 0;
	private String cartItemsString = "";
	private double mapindex = 0;
	private double maplength = 0;
	private  ChildEventListener cartListener;
	private double productQty = 0;
	private double availableQty = 0;
	private  DataSnapshot productsSnapShot;
	private  DataSnapshot cartSnapShot;
	private double productFromStockQty = 0;
	private double newProductStock = 0;
	private String product_id_str = "";
	
	private ArrayList<HashMap<String, Object>> cartListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> selectedItemListMap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> resultFromSearchMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout boxCartList;
	private LinearLayout boxEmpty;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout boxTbSearch;
	private ListView lv;
	private ImageView imageview3;
	private EditText tbSearch;
	private LinearLayout linear4;
	private Button btnMakeAnAppointment;
	private TextView textview7;
	private TextView totalPrice;
	private ImageView imageview5;
	private TextView textview6;
	
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
	private DatabaseReference productsdb = _firebase.getReference("products");
	private ChildEventListener _productsdb_child_listener;
	private Intent intent = new Intent();
	private AlertDialog.Builder d;
	private TimerTask tmr;
	private TimerTask tmrDelay;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_view_cart);
		initialize(_savedInstanceState);

		Log.d("av","ClientViewCartActivity onCreate");

		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		boxCartList = (LinearLayout) findViewById(R.id.boxCartList);
		boxEmpty = (LinearLayout) findViewById(R.id.boxEmpty);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		boxTbSearch = (LinearLayout) findViewById(R.id.boxTbSearch);
		lv = (ListView) findViewById(R.id.lv);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		tbSearch = (EditText) findViewById(R.id.tbSearch);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		btnMakeAnAppointment = (Button) findViewById(R.id.btnMakeAnAppointment);
		textview7 = (TextView) findViewById(R.id.textview7);
		totalPrice = (TextView) findViewById(R.id.totalPrice);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview6 = (TextView) findViewById(R.id.textview6);
		fbauth = FirebaseAuth.getInstance();
		d = new AlertDialog.Builder(this);
		
		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				
				return true;
			}
		});
		
		tbSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				cartListMap = new Gson().fromJson(cartItemsString, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				mapindex = 0;
				maplength = cartListMap.size();
				resultFromSearchMap.clear();
				for(int _repeat16 = 0; _repeat16 < (int)(maplength); _repeat16++) {
					if (new Gson().toJson(cartListMap.get((int)(mapindex))).toLowerCase().contains(tbSearch.getText().toString().toLowerCase())) {
						resultFromSearchMap.add(cartListMap.get((int)(mapindex)));
					}
					mapindex++;
				}
				if (resultFromSearchMap.size() == 0) {
					
				}
				else {
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
		
		btnMakeAnAppointment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.putExtra("selected_item", new Gson().toJson(selectedItemListMap));
				intent.setClass(getApplicationContext(), ClientMakeAppointmentActivity.class);
				startActivity(intent);
				finish();
			}
		});
		
		_productsdb_child_listener = new ChildEventListener() {
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
		productsdb.addChildEventListener(_productsdb_child_listener);
		
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
		cartdb = _firebase.getReference("carts/" + userid);
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
		boxEmpty.setVisibility(View.GONE);
		boxCartList.setVisibility(View.GONE);
	}
	
	
	public void _GetTotalItemSelected () {
		i1 = 0;
		totalOfSelectedItem = 0;
		l1 = selectedItemListMap.size();
		if (l1 == 0) {
			btnMakeAnAppointment.setEnabled(false);
			android.graphics.drawable.GradientDrawable btnMakeAnAppointment_design = new android.graphics.drawable.GradientDrawable();
			btnMakeAnAppointment_design.setColor(0xFF898F99);
			btnMakeAnAppointment_design.setCornerRadius((float)10);
			btnMakeAnAppointment_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnMakeAnAppointment_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnMakeAnAppointment_design, null);
			btnMakeAnAppointment.setBackground(btnMakeAnAppointment_re);
		}
		else {
			btnMakeAnAppointment.setEnabled(true);
			android.graphics.drawable.GradientDrawable btnMakeAnAppointment_design = new android.graphics.drawable.GradientDrawable();
			btnMakeAnAppointment_design.setColor(0xFFDD1D5E);
			btnMakeAnAppointment_design.setCornerRadius((float)10);
			btnMakeAnAppointment_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnMakeAnAppointment_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnMakeAnAppointment_design, null);
			btnMakeAnAppointment.setBackground(btnMakeAnAppointment_re);
			for(int _repeat13 = 0; _repeat13 < (int)(l1); _repeat13++) {
				totalOfSelectedItem = totalOfSelectedItem + (Double.parseDouble(selectedItemListMap.get((int)i1).get("price").toString()) * Double.parseDouble(selectedItemListMap.get((int)i1).get("quantity").toString()));
				i1++;
			}
		}
		totalPrice.setText("₱ ".concat(new DecimalFormat("###,###,###.##").format(totalOfSelectedItem)));
	}
	
	
	public void _childEventListener () {
		cartListener = new ChildEventListener() {
			        @Override
			    public void onChildChanged(DataSnapshot _param1, String _param2) {
				        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				        final String _childKey = _param1.getKey();
				        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				        _loadFirebase();
				    }
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
				        _loadFirebase();
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
		cartdb.addChildEventListener(cartListener);
	}
	
	
	public void _updateCartQty (final String _id, final boolean _isAdd) {
		selectedItemListMap.clear();
		availableQty = Double.parseDouble(productsSnapShot.child(_id).child("stock").getValue().toString());
		productQty = Double.parseDouble(cartSnapShot.child(_id).child("quantity").getValue().toString());
		if (_isAdd) {
			if (availableQty == 1) {
				SketchwareUtil.showMessage(getApplicationContext(), "This item is out of stock you cant ad d anymore.");
			}
			else {
				productQty++;
				availableQty--;
			}
		}
		else {
			if (productQty == 1) {
				SketchwareUtil.showMessage(getApplicationContext(), "You've reach the minimum limit.");
			}
			else {
				availableQty++;
				productQty--;
			}
		}
		cartdb.child(_id).child("quantity").setValue(String.valueOf((long)(productQty)));
		productsdb.child(_id).child("stock").setValue(String.valueOf((long)(availableQty)));
	}
	
	
	public void _returnProject (final String _product_id, final String _qty) {
		selectedItemListMap.clear();
		product_id_str = _product_id;
		cartdb.child(_product_id).removeValue();
		productFromStockQty = Double.parseDouble(productsSnapShot.child(_product_id).child("stock").getValue().toString());
		newProductStock = productFromStockQty + Double.parseDouble(_qty);
		productsdb.child(_product_id).child("stock").setValue(String.valueOf((long)(newProductStock)));
	}
	
	
	public void _loadFirebase () {
		cartdb.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				cartListMap = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						cartListMap.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				cartSnapShot = _dataSnapshot;
				cartItemsString = new Gson().toJson(cartListMap);
				if (cartListMap.size() == 0) {
					boxCartList.setVisibility(View.GONE);
					boxEmpty.setVisibility(View.VISIBLE);
				}
				else {
					_GetTotalItemSelected();
					boxCartList.setVisibility(View.VISIBLE);
					boxEmpty.setVisibility(View.GONE);
					lv.setAdapter(new LvAdapter(cartListMap));
					((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		_childEventListener();
		productsdb.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
						productsSnapShot = _dataSnapshot;
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
	}
	
	
	public void _protectButtons (final View _button1, final View _button2, final View _button3) {
		
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
			cb.setVisibility(View.VISIBLE);
			boxStatus.setVisibility(View.GONE);
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)10);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFFFFFF }), box_design, null);
			box.setBackground(box_re);
			android.graphics.drawable.GradientDrawable boxStatus_design = new android.graphics.drawable.GradientDrawable();
			boxStatus_design.setColor(0xFFDD1D5E);
			boxStatus_design.setCornerRadius((float)10);
			boxStatus_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable boxStatus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxStatus_design, null);
			boxStatus.setBackground(boxStatus_re);
			android.graphics.drawable.GradientDrawable qty_design = new android.graphics.drawable.GradientDrawable();
			qty_design.setColor(0xFFFFFFFF);
			qty_design.setCornerRadius((float)10);
			qty_design.setStroke((int)2,0xFFDD1D5E);
			android.graphics.drawable.RippleDrawable qty_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), qty_design, null);
			qty.setBackground(qty_re);
			android.graphics.drawable.GradientDrawable btnAddQty_design = new android.graphics.drawable.GradientDrawable();
			btnAddQty_design.setColor(0xFFDD1D5E);
			btnAddQty_design.setCornerRadius((float)10);
			btnAddQty_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnAddQty_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddQty_design, null);
			btnAddQty.setBackground(btnAddQty_re);
			android.graphics.drawable.GradientDrawable btnRemove_design = new android.graphics.drawable.GradientDrawable();
			btnRemove_design.setColor(0xFFDD1D5E);
			btnRemove_design.setCornerRadius((float)10);
			btnRemove_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnRemove_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnRemove_design, null);
			btnRemove.setBackground(btnRemove_re);
			android.graphics.drawable.GradientDrawable btnDiminishQty_design = new android.graphics.drawable.GradientDrawable();
			btnDiminishQty_design.setColor(0xFFDD1D5E);
			btnDiminishQty_design.setCornerRadius((float)10);
			btnDiminishQty_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnDiminishQty_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDiminishQty_design, null);
			btnDiminishQty.setBackground(btnDiminishQty_re);
			// Bind
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(thumbnail);
			unit.setText(_data.get((int)_position).get("unit").toString().toUpperCase());
			brand.setText(_data.get((int)_position).get("brand").toString().toUpperCase());
			qty.setText(_data.get((int)_position).get("quantity").toString().toUpperCase());
			price.setText("₱ ".concat(new DecimalFormat("###,###,###.##").format(Double.parseDouble(_data.get((int)_position).get("price").toString()))));
			// Login
			if (_data.get((int)_position).containsKey("status")) {
				cb.setVisibility(View.GONE);
				boxStatus.setVisibility(View.VISIBLE);
				statusText.setText(_data.get((int)_position).get("status").toString().toUpperCase());
			}
			else {
				statusText.setText("PENDING");
				boxStatus.setVisibility(View.GONE);
			}
			// Events
			cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
					if (isChecked) {
						selectedMap = new HashMap<>();
						selectedMap.put("product_id", _data.get((int)_position).get("product_id").toString());
						selectedMap.put("price", _data.get((int)_position).get("price").toString());
						selectedMap.put("quantity", _data.get((int)_position).get("quantity").toString());
						selectedMap.put("unit", _data.get((int)_position).get("unit").toString());
						selectedMap.put("brand", _data.get((int)_position).get("brand").toString());
						selectedMap.put("thumbnail", _data.get((int)_position).get("thumbnail").toString());
						selectedItemListMap.add(selectedMap);
					}
					else {
						l = selectedItemListMap.size();
						i = 0;
						while(true) {
							if (selectedItemListMap.get((int)i).get("product_id").toString().equals(_data.get((int)_position).get("product_id").toString())) {
								selectedItemListMap.remove((int)(i));
								break;
							}
							i++;
						}
					}
					_GetTotalItemSelected();
				}});
			btnAddQty.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_updateCartQty(_data.get((int)_position).get("product_id").toString(), true);

				}
			});
			btnRemove.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					d.setTitle("REMOVE");
					d.setMessage("Do you want to remove this product from your cart?");
					d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							_returnProject(_data.get((int)_position).get("product_id").toString(), _data.get((int)_position).get("quantity").toString());
						}
					});
					d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					d.create().show();
				}
			});
			btnDiminishQty.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					_updateCartQty(_data.get((int)_position).get("product_id").toString(), false);
				}
			});
			
			return _view;
		}
	}

	private void sendPushNotification(String title, String message) {
		String channel_id = "notification_channel";

		// Create a Builder object using NotificationCompat
		// class. This will allow control over all the flags
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channel_id)
				.setSmallIcon(R.drawable.app_icon)
				.setAutoCancel(true)
				.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
				.setOnlyAlertOnce(true);

		builder.setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.app_icon);
		// Create an object of NotificationManager class to
		// notify the
		// user of events that happen in the background.
		NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

		// Check if the Android Version is greater than Oreo
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			NotificationChannel notificationChannel = new NotificationChannel(channel_id, "web_app", NotificationManager.IMPORTANCE_HIGH);
			notificationManager.createNotificationChannel(notificationChannel);
		}

		notificationManager.notify(0, builder.build());
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
