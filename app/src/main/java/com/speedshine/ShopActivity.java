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
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
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
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ShopActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_GL = 101;
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private  GoogleSignInOptions gso;
	private String getAccountId = "";
	private HashMap<String, Object> AccountInfoMap = new HashMap<>();
	private double a = 0;
	
	private ArrayList<HashMap<String, Object>> accountslistmap = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> hotWheelsMapList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> bestPriceMapList = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> trendingMapList = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout body;
	private TextView textview1;
	private LinearLayout linear3;
	private LinearLayout btnSearch;
	private LinearLayout btnCart;
	private LinearLayout btnNotifications;
	private LinearLayout btnProfile;
	private ImageView imageview3;
	private ImageView imageview4;
	private ImageView imageview5;
	private ImageView imageview6;
	private RecyclerView rv;
	private TextView textview2;
	private TextView textview3;
	private RecyclerView rv_bestprice;
	private TextView textview4;
	private RecyclerView rv_trending;
	
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
	private Intent i = new Intent();
	private GoogleSignInClient gl;
	private DatabaseReference fbdb = _firebase.getReference("account");
	private ChildEventListener _fbdb_child_listener;
	private ProgressDialog pd;
	private DatabaseReference dbdb_products = _firebase.getReference("products");
	private ChildEventListener _dbdb_products_child_listener;
	private TimerTask tmr;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.shop);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		body = (LinearLayout) findViewById(R.id.body);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		btnSearch = (LinearLayout) findViewById(R.id.btnSearch);
		btnCart = (LinearLayout) findViewById(R.id.btnCart);
		btnNotifications = (LinearLayout) findViewById(R.id.btnNotifications);
		btnProfile = (LinearLayout) findViewById(R.id.btnProfile);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		rv = (RecyclerView) findViewById(R.id.rv);
		textview2 = (TextView) findViewById(R.id.textview2);
		textview3 = (TextView) findViewById(R.id.textview3);
		rv_bestprice = (RecyclerView) findViewById(R.id.rv_bestprice);
		textview4 = (TextView) findViewById(R.id.textview4);
		rv_trending = (RecyclerView) findViewById(R.id.rv_trending);
		fbauth = FirebaseAuth.getInstance();
		
		btnSearch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ProductListActivity.class);
				startActivity(i);
			}
		});
		
		btnCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ClientViewCartActivity.class);
				startActivity(i);
			}
		});
		
		btnNotifications.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				SketchwareUtil.showMessage(getApplicationContext(), "Working on it!!!");
			}
		});
		
		btnProfile.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ClientProfileActivity.class);
				startActivity(i);
				finish();
			}
		});
		
		_fbdb_child_listener = new ChildEventListener() {
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
				SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
			}
		};
		fbdb.addChildEventListener(_fbdb_child_listener);
		
		_dbdb_products_child_listener = new ChildEventListener() {
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
				_loadHotWheelsDB();
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
		dbdb_products.addChildEventListener(_dbdb_products_child_listener);
		
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
		// Initialize Progress Dialog
		pd = new ProgressDialog(ShopActivity.this);
		pd.setMessage("Retrieving your account data, Please wait.");
		pd.setCancelable(false);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		// Firebase Load
		fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
						getAccountId = FirebaseAuth.getInstance().getCurrentUser().getUid();
				Map<String,Object> AccountInfoMap = (Map<String,Object>) _dataSnapshot.child(getAccountId).getValue();
				if (AccountInfoMap.containsKey("account_type")) {
					if (AccountInfoMap.get("account_type").toString().equals("3") && (AccountInfoMap.get("verified").toString().equals("0") || AccountInfoMap.get("verified").toString().equals("1"))) {
						i.setClass(getApplicationContext(), VerificationAreaActivity.class);
						startActivity(i);
						finish();
					}
					if (AccountInfoMap.get("account_type").toString().equals("1")) {
						i.setClass(getApplicationContext(), AdminDashboardActivity.class);
						startActivity(i);
						finish();
					}
					pd.dismiss();
				}
				else {
					fbdb.child(getAccountId).child("account_type").setValue("3");
				}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
		// Initialize GoogleLogin
		fbauth = FirebaseAuth.getInstance();
		GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().requestProfile().requestIdToken("1090849595737-8p185b3f3pru1oc1c68tlqp6so2r027v.apps.googleusercontent.com").build();
		gl = GoogleSignIn.getClient(this,gso);
		_design();
		_loadHotWheelsDB();
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
		android.graphics.drawable.GradientDrawable btnSearch_design = new android.graphics.drawable.GradientDrawable();
		btnSearch_design.setColor(0xFFDD1D5E);
		btnSearch_design.setCornerRadius((float)100);
		btnSearch_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnSearch_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnSearch_design, null);
		btnSearch.setBackground(btnSearch_re);
		android.graphics.drawable.GradientDrawable btnCart_design = new android.graphics.drawable.GradientDrawable();
		btnCart_design.setColor(0xFFDD1D5E);
		btnCart_design.setCornerRadius((float)100);
		btnCart_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnCart_design, null);
		btnCart.setBackground(btnCart_re);
		android.graphics.drawable.GradientDrawable btnNotifications_design = new android.graphics.drawable.GradientDrawable();
		btnNotifications_design.setColor(0xFFDD1D5E);
		btnNotifications_design.setCornerRadius((float)100);
		btnNotifications_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnNotifications_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnNotifications_design, null);
		btnNotifications.setBackground(btnNotifications_re);
		android.graphics.drawable.GradientDrawable btnProfile_design = new android.graphics.drawable.GradientDrawable();
		btnProfile_design.setColor(0xFFDD1D5E);
		btnProfile_design.setCornerRadius((float)100);
		btnProfile_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnProfile_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnProfile_design, null);
		btnProfile.setBackground(btnProfile_re);
	}
	
	
	public void _loadHotwheelsRv () {
		rv.setAdapter(new RvAdapter(hotWheelsMapList));
		rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		SketchwareUtil.sortListMap(bestPriceMapList, "price", true, true);
		rv_bestprice.setAdapter(new Rv_bestpriceAdapter(bestPriceMapList));
		rv_bestprice.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
		SketchwareUtil.sortListMap(trendingMapList, "likes", true, false);
		rv_trending.setAdapter(new Rv_trendingAdapter(trendingMapList));
		rv_trending.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
	}
	
	
	public void _loadHotWheelsDB () {
		dbdb_products.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				hotWheelsMapList = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						hotWheelsMapList.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				trendingMapList = new Gson().fromJson(new Gson().toJson(hotWheelsMapList), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				bestPriceMapList = new Gson().fromJson(new Gson().toJson(hotWheelsMapList), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
				_loadHotwheelsRv();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}
	
	
	public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public RvAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.rv_hotwheels, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final RelativeLayout background = (RelativeLayout) _view.findViewById(R.id.background);
			final ImageView picture = (ImageView) _view.findViewById(R.id.picture);
			final LinearLayout info = (LinearLayout) _view.findViewById(R.id.info);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final LinearLayout linear4 = (LinearLayout) _view.findViewById(R.id.linear4);
			final TextView unit = (TextView) _view.findViewById(R.id.unit);
			final TextView textview2 = (TextView) _view.findViewById(R.id.textview2);
			
			//Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(picture);
			linear3.setBackgroundColor(0xB5FFFFFF);
			unit.setText(_data.get((int)_position).get("unit").toString().toUpperCase());
			textview2.setText(_data.get((int)_position).get("brand").toString());
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
	
	public class Rv_bestpriceAdapter extends RecyclerView.Adapter<Rv_bestpriceAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Rv_bestpriceAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.rv_recommended, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final ImageView picture = (ImageView) _view.findViewById(R.id.picture);
			final TextView unit = (TextView) _view.findViewById(R.id.unit);
			final TextView brand = (TextView) _view.findViewById(R.id.brand);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView like = (TextView) _view.findViewById(R.id.like);
			final ImageView imageview3 = (ImageView) _view.findViewById(R.id.imageview3);
			final TextView dislike = (TextView) _view.findViewById(R.id.dislike);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			//Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(picture);
			box.setElevation((float)8);
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
	
	public class Rv_trendingAdapter extends RecyclerView.Adapter<Rv_trendingAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Rv_trendingAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.rv_recommended, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final ImageView picture = (ImageView) _view.findViewById(R.id.picture);
			final TextView unit = (TextView) _view.findViewById(R.id.unit);
			final TextView brand = (TextView) _view.findViewById(R.id.brand);
			final TextView price = (TextView) _view.findViewById(R.id.price);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final ImageView imageview2 = (ImageView) _view.findViewById(R.id.imageview2);
			final TextView like = (TextView) _view.findViewById(R.id.like);
			final ImageView imageview3 = (ImageView) _view.findViewById(R.id.imageview3);
			final TextView dislike = (TextView) _view.findViewById(R.id.dislike);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			//Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("thumbnail").toString())).into(picture);
			box.setElevation((float)8);
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
