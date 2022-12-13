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
import android.widget.Button;
import android.widget.ScrollView;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import java.text.DecimalFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ClientViewProductActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private double navigationIndex = 0;
	private HashMap<String, Object> productsInfoMap = new HashMap<>();
	private String unique_id = "";
	private double currentPos = 0;
	private String userid = "";
	private HashMap<String, Object> reactsInfoMap = new HashMap<>();
	private HashMap<String, Object> reactItemMap = new HashMap<>();
	private  DatabaseReference reactsdb;
	private double productLikes = 0;
	private double productDislikes = 0;
	private String newLikeCount = "";
	private String newDisLikeCount = "";
	private  DatabaseReference cartdb;
	private HashMap<String, Object> addItemsToCartMap = new HashMap<>();
	private String cart_id = "";
	private String priceStr = "";
	private String brandStr = "";
	private String unitStr = "";
	private String thumbnailStr = "";
	private double currentQtyItem = 0;
	private double newCurrentQty = 0;
	private  ChildEventListener _cartdb_child_listener;
	private boolean addModuleBoolean = false;
	private String u_fname = "";
	private String u_lastname = "";
	private String u_middlename = "";
	private  DatabaseReference dbaccount;
	private HashMap<String, Object> LoggedAccountInfoMap = new HashMap<>();
	private String u_fullname = "";
	private HashMap<String, Object> logsMap = new HashMap<>();
	private String logId = "";
	private String product_name = "";
	
	private ArrayList<HashMap<String, Object>> gallerListMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout body;
	private LinearLayout btnBack;
	private TextView textview1;
	private ImageView imageview1;
	private LinearLayout background;
	private LinearLayout linear8;
	private LinearLayout linear10;
	private LinearLayout linear9;
	private RecyclerView rv;
	private LinearLayout boxNavMenu;
	private LinearLayout navMenu;
	private Button btnImage1;
	private Button btnImage2;
	private Button btnImage3;
	private Button btnImage4;
	private Button btnImage5;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private TextView price;
	private TextView unit_name;
	private TextView brand;
	private LinearLayout boxStock;
	private LinearLayout btnLike;
	private LinearLayout btnDislike;
	private ImageView imageview2;
	private TextView stock;
	private ImageView imageview3;
	private TextView like;
	private ImageView imageview4;
	private TextView dislike;
	private ScrollView vscroll1;
	private LinearLayout linear6;
	private TextView textview2;
	private TextView spec;
	private LinearLayout btnViewCart;
	private LinearLayout boxQty;
	private Button btnAddToCart;
	private ImageView imageview5;
	private TextView textview3;
	private Button btnMinus;
	private EditText tbQty;
	private Button btnAdd;
	
	private DatabaseReference fbdb = _firebase.getReference("products");
	private ChildEventListener _fbdb_child_listener;
	private TimerTask tmr;
	private Intent i = new Intent();
	private AlertDialog.Builder d;
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
	private DatabaseReference dbNotifAdmin = _firebase.getReference("notif_admin");
	private ChildEventListener _dbNotifAdmin_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_view_product);
		initialize(_savedInstanceState);
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
		background = (LinearLayout) findViewById(R.id.background);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		rv = (RecyclerView) findViewById(R.id.rv);
		boxNavMenu = (LinearLayout) findViewById(R.id.boxNavMenu);
		navMenu = (LinearLayout) findViewById(R.id.navMenu);
		btnImage1 = (Button) findViewById(R.id.btnImage1);
		btnImage2 = (Button) findViewById(R.id.btnImage2);
		btnImage3 = (Button) findViewById(R.id.btnImage3);
		btnImage4 = (Button) findViewById(R.id.btnImage4);
		btnImage5 = (Button) findViewById(R.id.btnImage5);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		price = (TextView) findViewById(R.id.price);
		unit_name = (TextView) findViewById(R.id.unit_name);
		brand = (TextView) findViewById(R.id.brand);
		boxStock = (LinearLayout) findViewById(R.id.boxStock);
		btnLike = (LinearLayout) findViewById(R.id.btnLike);
		btnDislike = (LinearLayout) findViewById(R.id.btnDislike);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		stock = (TextView) findViewById(R.id.stock);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		like = (TextView) findViewById(R.id.like);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		dislike = (TextView) findViewById(R.id.dislike);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		textview2 = (TextView) findViewById(R.id.textview2);
		spec = (TextView) findViewById(R.id.spec);
		btnViewCart = (LinearLayout) findViewById(R.id.btnViewCart);
		boxQty = (LinearLayout) findViewById(R.id.boxQty);
		btnAddToCart = (Button) findViewById(R.id.btnAddToCart);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		textview3 = (TextView) findViewById(R.id.textview3);
		btnMinus = (Button) findViewById(R.id.btnMinus);
		tbQty = (EditText) findViewById(R.id.tbQty);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		d = new AlertDialog.Builder(this);
		fbauth = FirebaseAuth.getInstance();
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				 int offset = rv.computeHorizontalScrollOffset();
				if ((offset % rv.getWidth()) == 0) {
					currentPos = offset / rv.getWidth();
					_SetnavigateActiveColor(currentPos);
				}
			}
		});
		
		btnImage1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rv.smoothScrollToPosition((int)0);
			}
		});
		
		btnImage2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rv.smoothScrollToPosition((int)1);
			}
		});
		
		btnImage3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rv.smoothScrollToPosition((int)2);
			}
		});
		
		btnImage4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rv.smoothScrollToPosition((int)3);
			}
		});
		
		btnImage5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				rv.smoothScrollToPosition((int)4);
			}
		});
		
		btnLike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				reactsdb.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
								Map<String,Object> reactsInfoMap = (Map<String,Object>) _dataSnapshot.child(unique_id).getValue();
						if (reactsInfoMap == null) {
							reactItemMap = new HashMap<>();
							reactItemMap.put("product_id", unique_id);
							reactItemMap.put("react", "like");
							reactsdb.child(unique_id).updateChildren(reactItemMap);
							_reactItem(unique_id, "like", true);
						}
						else {
							if (reactsInfoMap.get("react").toString().equals("like")) {
								reactItemMap = new HashMap<>();
								reactItemMap.put("product_id", unique_id);
								reactItemMap.put("react", "unlike");
								reactsdb.child(unique_id).updateChildren(reactItemMap);
								_reactItem(unique_id, "like", false);
							}
							if (reactsInfoMap.get("react").toString().equals("unlike")) {
								reactItemMap = new HashMap<>();
								reactItemMap.put("product_id", unique_id);
								reactItemMap.put("react", "like");
								reactsdb.child(unique_id).updateChildren(reactItemMap);
								_reactItem(unique_id, "like", true);
							}
							if (reactsInfoMap.get("react").toString().equals("dislike") || reactsInfoMap.get("react").toString().equals("undislike")) {
								reactItemMap = new HashMap<>();
								reactItemMap.put("product_id", unique_id);
								reactItemMap.put("react", "like");
								reactsdb.child(unique_id).updateChildren(reactItemMap);
								_reactItem(unique_id, "like", true);
								_reactItem(unique_id, "dislike", false);
							}
						}
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
				});
			}
		});
		
		btnDislike.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				reactsdb.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
								Map<String,Object> reactsInfoMap = (Map<String,Object>) _dataSnapshot.child(unique_id).getValue();
						if (reactsInfoMap == null) {
							reactItemMap = new HashMap<>();
							reactItemMap.put("product_id", unique_id);
							reactItemMap.put("react", "dislike");
							reactsdb.child(unique_id).updateChildren(reactItemMap);
							_reactItem(unique_id, "dislike", true);
						}
						else {
							if (reactsInfoMap.get("react").toString().equals("dislike")) {
								reactItemMap = new HashMap<>();
								reactItemMap.put("product_id", unique_id);
								reactItemMap.put("react", "undislike");
								reactsdb.child(unique_id).updateChildren(reactItemMap);
								_reactItem(unique_id, "dislike", false);
							}
							if (reactsInfoMap.get("react").toString().equals("undislike")) {
								reactItemMap = new HashMap<>();
								reactItemMap.put("product_id", unique_id);
								reactItemMap.put("react", "dislike");
								reactsdb.child(unique_id).updateChildren(reactItemMap);
								_reactItem(unique_id, "dislike", true);
							}
							if (reactsInfoMap.get("react").toString().equals("like") || reactsInfoMap.get("react").toString().equals("unlike")) {
								reactItemMap = new HashMap<>();
								reactItemMap.put("product_id", unique_id);
								reactItemMap.put("react", "dislike");
								reactsdb.child(unique_id).updateChildren(reactItemMap);
								_reactItem(unique_id, "like", false);
								_reactItem(unique_id, "dislike", true);
							}
						}
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
				});
			}
		});
		
		btnViewCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ClientViewCartActivity.class);
				startActivity(i);
			}
		});
		
		btnAddToCart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
						@Override
						public void onDataChange(DataSnapshot _dataSnapshot) {
								if (_dataSnapshot.child(unique_id).child("stock").getValue().toString().equals("0")) {
							SketchwareUtil.showMessage(getApplicationContext(), "This product is out of stock.");
						}
						else {
							if (Double.parseDouble(tbQty.getText().toString()) > Double.parseDouble(_dataSnapshot.child(unique_id).child("stock").getValue().toString())) {
								tbQty.setText(_dataSnapshot.child(unique_id).child("stock").getValue().toString());
								SketchwareUtil.showMessage(getApplicationContext(), "You've reach the maximum quantity.");
								return;
							}
							fbdb.child(unique_id).child("stock").setValue(String.valueOf((long)(Double.parseDouble(_dataSnapshot.child(unique_id).child("stock").getValue().toString()) - Double.parseDouble(tbQty.getText().toString()))));
							addModuleBoolean = true;
							cart_id = cartdb.push().getKey();
							addItemsToCartMap = new HashMap<>();
							addItemsToCartMap.put("cart_id", cart_id);
							addItemsToCartMap.put("product_id", unique_id);
							addItemsToCartMap.put("unit", unitStr);
							addItemsToCartMap.put("brand", brandStr);
							addItemsToCartMap.put("price", priceStr);
							addItemsToCartMap.put("thumbnail", thumbnailStr);
							addItemsToCartMap.put("quantity", tbQty.getText().toString());
							cartdb.child(unique_id).updateChildren(addItemsToCartMap);
							_Logs(u_fullname + " added this item " + unitStr + " x " + tbQty.getText().toString() + " to his/her cart.");
						}
						}
						@Override
						public void onCancelled(DatabaseError _databaseError) {
						}
				});
			}
		});
		
		btnMinus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_ItemQuantity(false);
			}
		});
		
		tbQty.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (!tbQty.getText().toString().equals("")) {
					if (tbQty.getText().toString().contains("-")) {
						android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
						btnAdd_design.setColor(0xFFDD1D5E);
						btnAdd_design.setCornerRadius((float)10);
						btnAdd_design.setStroke((int)0,Color.TRANSPARENT);
						android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAdd_design, null);
						btnAdd.setBackground(btnAdd_re);
						android.graphics.drawable.GradientDrawable btnMinus_design = new android.graphics.drawable.GradientDrawable();
						btnMinus_design.setColor(0xFF898F99);
						btnMinus_design.setCornerRadius((float)10);
						btnMinus_design.setStroke((int)0,Color.TRANSPARENT);
						android.graphics.drawable.RippleDrawable btnMinus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnMinus_design, null);
						btnMinus.setBackground(btnMinus_re);
						tbQty.setText("0");
						btnAdd.setEnabled(true);
						btnMinus.setEnabled(false);
						return;
					}
					if (Double.parseDouble(tbQty.getText().toString()) > Double.parseDouble(stock.getText().toString())) {
						android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
						btnAdd_design.setColor(0xFF898F99);
						btnAdd_design.setCornerRadius((float)10);
						btnAdd_design.setStroke((int)0,Color.TRANSPARENT);
						android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnAdd_design, null);
						btnAdd.setBackground(btnAdd_re);
						android.graphics.drawable.GradientDrawable btnMinus_design = new android.graphics.drawable.GradientDrawable();
						btnMinus_design.setColor(0xFFDD1D5E);
						btnMinus_design.setCornerRadius((float)10);
						btnMinus_design.setStroke((int)0,Color.TRANSPARENT);
						android.graphics.drawable.RippleDrawable btnMinus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnMinus_design, null);
						btnMinus.setBackground(btnMinus_re);
						tbQty.setText(stock.getText().toString());
						btnAdd.setEnabled(false);
						btnMinus.setEnabled(true);
						return;
					}
					if (Double.parseDouble(_charSeq) == 0) {
						android.graphics.drawable.GradientDrawable btnMinus_design = new android.graphics.drawable.GradientDrawable();
						btnMinus_design.setColor(0xFF898F99);
						btnMinus_design.setCornerRadius((float)10);
						btnMinus_design.setStroke((int)0,Color.TRANSPARENT);
						android.graphics.drawable.RippleDrawable btnMinus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnMinus_design, null);
						btnMinus.setBackground(btnMinus_re);
					}
					if (Double.parseDouble(_charSeq) == Double.parseDouble(stock.getText().toString())) {
						android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
						btnAdd_design.setColor(0xFF898F99);
						btnAdd_design.setCornerRadius((float)10);
						btnAdd_design.setStroke((int)0,Color.TRANSPARENT);
						android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnAdd_design, null);
						btnAdd.setBackground(btnAdd_re);
					}
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_ItemQuantity(true);
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
				if (_childKey.equals(unique_id)) {
					productsInfoMap = new Gson().fromJson(new Gson().toJson(_childValue), new TypeToken<HashMap<String, Object>>(){}.getType());
					like.setText(productsInfoMap.get("likes").toString());
					dislike.setText(productsInfoMap.get("dislikes").toString());
					stock.setText(productsInfoMap.get("stock").toString());
					if (Double.parseDouble(tbQty.getText().toString()) > Double.parseDouble(productsInfoMap.get("stock").toString())) {
						tbQty.setText(productsInfoMap.get("stock").toString());
					}
					_AddToCart(productsInfoMap.get("stock").toString());
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
		fbdb.addChildEventListener(_fbdb_child_listener);
		
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
		try {
			unique_id = getIntent().getStringExtra("unique_id");
			_LoadLoggedAccountInfo(FirebaseAuth.getInstance().getCurrentUser().getUid());
			userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
			addModuleBoolean = false;
			reactsdb = _firebase.getReference("reacts/" + userid);
			
			cartdb = _firebase.getReference("carts/" + userid);
			_extra();
			fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
							Map<String,Object> productsInfoMap = (Map<String,Object>) _dataSnapshot.child(unique_id).getValue();
					// Text Info
					price.setText("₱ ".concat(new DecimalFormat("###,###,###.##").format(Double.parseDouble(productsInfoMap.get("price").toString()))));
					unit_name.setText(productsInfoMap.get("unit").toString().toUpperCase());
					brand.setText(productsInfoMap.get("brand").toString().toUpperCase());
					spec.setText(productsInfoMap.get("specs").toString().toUpperCase());
					stock.setText(productsInfoMap.get("stock").toString().toUpperCase());
					like.setText(productsInfoMap.get("likes").toString().toUpperCase());
					dislike.setText(productsInfoMap.get("dislikes").toString().toUpperCase());
					_AddToCart(productsInfoMap.get("stock").toString());
					brandStr = productsInfoMap.get("brand").toString();
					thumbnailStr = productsInfoMap.get("thumbnail").toString();
					unitStr = productsInfoMap.get("unit").toString();
					priceStr = productsInfoMap.get("price").toString();
					// Gallery
					gallerListMap = new Gson().fromJson(productsInfoMap.get("gallery").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					if (gallerListMap.size() == 1) {
						btnImage1.setVisibility(View.GONE);
						btnImage2.setVisibility(View.GONE);
						btnImage3.setVisibility(View.GONE);
						btnImage4.setVisibility(View.GONE);
						btnImage5.setVisibility(View.GONE);
						navMenu.setVisibility(View.GONE);
					}
					if (gallerListMap.size() == 2) {
						btnImage1.setVisibility(View.VISIBLE);
						btnImage2.setVisibility(View.VISIBLE);
						btnImage3.setVisibility(View.GONE);
						btnImage4.setVisibility(View.GONE);
						btnImage5.setVisibility(View.GONE);
					}
					if (gallerListMap.size() == 3) {
						btnImage1.setVisibility(View.VISIBLE);
						btnImage2.setVisibility(View.VISIBLE);
						btnImage3.setVisibility(View.VISIBLE);
						btnImage4.setVisibility(View.GONE);
						btnImage5.setVisibility(View.GONE);
					}
					if (gallerListMap.size() == 4) {
						btnImage1.setVisibility(View.VISIBLE);
						btnImage2.setVisibility(View.VISIBLE);
						btnImage3.setVisibility(View.VISIBLE);
						btnImage4.setVisibility(View.VISIBLE);
						btnImage5.setVisibility(View.GONE);
					}
					if (gallerListMap.size() == 5) {
						btnImage1.setVisibility(View.VISIBLE);
						btnImage2.setVisibility(View.VISIBLE);
						btnImage3.setVisibility(View.VISIBLE);
						btnImage4.setVisibility(View.VISIBLE);
						btnImage5.setVisibility(View.VISIBLE);
					}
					_loadRv();
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
			});
		} catch(Exception e) {
			finish();
		}
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
		android.graphics.drawable.GradientDrawable btnBack_design = new android.graphics.drawable.GradientDrawable();
		btnBack_design.setColor(0xFFDD1D5E);
		btnBack_design.setCornerRadius((float)100);
		btnBack_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnBack_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnBack_design, null);
		btnBack.setBackground(btnBack_re);
		android.graphics.drawable.GradientDrawable btnImage1_design = new android.graphics.drawable.GradientDrawable();
		btnImage1_design.setColor(0xFFDD1D5E);
		btnImage1_design.setCornerRadius((float)100);
		btnImage1_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnImage1_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage1_design, null);
		btnImage1.setBackground(btnImage1_re);
		android.graphics.drawable.GradientDrawable btnImage2_design = new android.graphics.drawable.GradientDrawable();
		btnImage2_design.setColor(0xFFDD1D5E);
		btnImage2_design.setCornerRadius((float)100);
		btnImage2_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnImage2_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage2_design, null);
		btnImage2.setBackground(btnImage2_re);
		android.graphics.drawable.GradientDrawable btnImage3_design = new android.graphics.drawable.GradientDrawable();
		btnImage3_design.setColor(0xFFDD1D5E);
		btnImage3_design.setCornerRadius((float)100);
		btnImage3_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnImage3_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage3_design, null);
		btnImage3.setBackground(btnImage3_re);
		android.graphics.drawable.GradientDrawable btnImage4_design = new android.graphics.drawable.GradientDrawable();
		btnImage4_design.setColor(0xFFDD1D5E);
		btnImage4_design.setCornerRadius((float)100);
		btnImage4_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnImage4_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage4_design, null);
		btnImage4.setBackground(btnImage4_re);
		android.graphics.drawable.GradientDrawable btnImage5_design = new android.graphics.drawable.GradientDrawable();
		btnImage5_design.setColor(0xFFDD1D5E);
		btnImage5_design.setCornerRadius((float)100);
		btnImage5_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnImage5_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage5_design, null);
		btnImage5.setBackground(btnImage5_re);
		android.graphics.drawable.GradientDrawable boxStock_design = new android.graphics.drawable.GradientDrawable();
		boxStock_design.setColor(0xFFDD1D5E);
		boxStock_design.setCornerRadius((float)10);
		boxStock_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable boxStock_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxStock_design, null);
		boxStock.setBackground(boxStock_re);
		RelativeLayout rl = new RelativeLayout(this);
		RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		rl.setLayoutParams(lparams); background.removeAllViews();
		rl.addView(rv);
		rl.addView(boxNavMenu);
		background.addView(rl);
		android.graphics.drawable.GradientDrawable btnLike_design = new android.graphics.drawable.GradientDrawable();
		btnLike_design.setColor(0xFFDD1D5E);
		btnLike_design.setCornerRadius((float)10);
		btnLike_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnLike_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnLike_design, null);
		btnLike.setBackground(btnLike_re);
		android.graphics.drawable.GradientDrawable btnDislike_design = new android.graphics.drawable.GradientDrawable();
		btnDislike_design.setColor(0xFFDD1D5E);
		btnDislike_design.setCornerRadius((float)10);
		btnDislike_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnDislike_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDislike_design, null);
		btnDislike.setBackground(btnDislike_re);
		android.graphics.drawable.GradientDrawable btnAddToCart_design = new android.graphics.drawable.GradientDrawable();
		btnAddToCart_design.setColor(0xFF898F99);
		btnAddToCart_design.setCornerRadius((float)10);
		btnAddToCart_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnAddToCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddToCart_design, null);
		btnAddToCart.setBackground(btnAddToCart_re);
		btnAddToCart.setEnabled(false);
		android.graphics.drawable.GradientDrawable btnViewCart_design = new android.graphics.drawable.GradientDrawable();
		btnViewCart_design.setColor(0xFFDD1D5E);
		btnViewCart_design.setCornerRadius((float)10);
		btnViewCart_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnViewCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnViewCart_design, null);
		btnViewCart.setBackground(btnViewCart_re);
		android.graphics.drawable.GradientDrawable btnMinus_design = new android.graphics.drawable.GradientDrawable();
		btnMinus_design.setColor(0xFF898F99);
		btnMinus_design.setCornerRadius((float)10);
		btnMinus_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnMinus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnMinus_design, null);
		btnMinus.setBackground(btnMinus_re);
		btnMinus.setEnabled(false);
		android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
		btnAdd_design.setColor(0xFFDD1D5E);
		btnAdd_design.setCornerRadius((float)10);
		btnAdd_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAdd_design, null);
		btnAdd.setBackground(btnAdd_re);
		android.graphics.drawable.GradientDrawable tbQty_design = new android.graphics.drawable.GradientDrawable();
		tbQty_design.setColor(0xFFFFFFFF);
		tbQty_design.setCornerRadius((float)10);
		tbQty_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbQty_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbQty_design, null);
		tbQty.setBackground(tbQty_re);
	}
	
	
	public void _loadRv () {
		rv.setAdapter(new RvAdapter(gallerListMap));
		rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
	}
	
	
	public void _SetnavigateActiveColor (final double _index) {
		navigationIndex = _index + 1;
		if (navigationIndex == 1) {
			android.graphics.drawable.GradientDrawable btnImage1_design = new android.graphics.drawable.GradientDrawable();
			btnImage1_design.setColor(0xFFDD1D5E);
			btnImage1_design.setCornerRadius((float)100);
			btnImage1_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage1_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage1_design, null);
			btnImage1.setBackground(btnImage1_re);
			android.graphics.drawable.GradientDrawable btnImage2_design = new android.graphics.drawable.GradientDrawable();
			btnImage2_design.setColor(0xFF898F99);
			btnImage2_design.setCornerRadius((float)100);
			btnImage2_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage2_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage2_design, null);
			btnImage2.setBackground(btnImage2_re);
			android.graphics.drawable.GradientDrawable btnImage3_design = new android.graphics.drawable.GradientDrawable();
			btnImage3_design.setColor(0xFF898F99);
			btnImage3_design.setCornerRadius((float)100);
			btnImage3_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage3_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage3_design, null);
			btnImage3.setBackground(btnImage3_re);
			android.graphics.drawable.GradientDrawable btnImage4_design = new android.graphics.drawable.GradientDrawable();
			btnImage4_design.setColor(0xFF898F99);
			btnImage4_design.setCornerRadius((float)100);
			btnImage4_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage4_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage4_design, null);
			btnImage4.setBackground(btnImage4_re);
			android.graphics.drawable.GradientDrawable btnImage5_design = new android.graphics.drawable.GradientDrawable();
			btnImage5_design.setColor(0xFF898F99);
			btnImage5_design.setCornerRadius((float)100);
			btnImage5_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage5_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage5_design, null);
			btnImage5.setBackground(btnImage5_re);
		}
		if (navigationIndex == 2) {
			android.graphics.drawable.GradientDrawable btnImage1_design = new android.graphics.drawable.GradientDrawable();
			btnImage1_design.setColor(0xFF898F99);
			btnImage1_design.setCornerRadius((float)100);
			btnImage1_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage1_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage1_design, null);
			btnImage1.setBackground(btnImage1_re);
			android.graphics.drawable.GradientDrawable btnImage2_design = new android.graphics.drawable.GradientDrawable();
			btnImage2_design.setColor(0xFFDD1D5E);
			btnImage2_design.setCornerRadius((float)100);
			btnImage2_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage2_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage2_design, null);
			btnImage2.setBackground(btnImage2_re);
			android.graphics.drawable.GradientDrawable btnImage3_design = new android.graphics.drawable.GradientDrawable();
			btnImage3_design.setColor(0xFF898F99);
			btnImage3_design.setCornerRadius((float)100);
			btnImage3_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage3_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage3_design, null);
			btnImage3.setBackground(btnImage3_re);
			android.graphics.drawable.GradientDrawable btnImage4_design = new android.graphics.drawable.GradientDrawable();
			btnImage4_design.setColor(0xFF898F99);
			btnImage4_design.setCornerRadius((float)100);
			btnImage4_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage4_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage4_design, null);
			btnImage4.setBackground(btnImage4_re);
			android.graphics.drawable.GradientDrawable btnImage5_design = new android.graphics.drawable.GradientDrawable();
			btnImage5_design.setColor(0xFF898F99);
			btnImage5_design.setCornerRadius((float)100);
			btnImage5_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage5_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage5_design, null);
			btnImage5.setBackground(btnImage5_re);
		}
		if (navigationIndex == 3) {
			android.graphics.drawable.GradientDrawable btnImage1_design = new android.graphics.drawable.GradientDrawable();
			btnImage1_design.setColor(0xFF898F99);
			btnImage1_design.setCornerRadius((float)100);
			btnImage1_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage1_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage1_design, null);
			btnImage1.setBackground(btnImage1_re);
			android.graphics.drawable.GradientDrawable btnImage2_design = new android.graphics.drawable.GradientDrawable();
			btnImage2_design.setColor(0xFF898F99);
			btnImage2_design.setCornerRadius((float)100);
			btnImage2_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage2_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage2_design, null);
			btnImage2.setBackground(btnImage2_re);
			android.graphics.drawable.GradientDrawable btnImage3_design = new android.graphics.drawable.GradientDrawable();
			btnImage3_design.setColor(0xFFDD1D5E);
			btnImage3_design.setCornerRadius((float)100);
			btnImage3_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage3_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage3_design, null);
			btnImage3.setBackground(btnImage3_re);
			android.graphics.drawable.GradientDrawable btnImage4_design = new android.graphics.drawable.GradientDrawable();
			btnImage4_design.setColor(0xFF898F99);
			btnImage4_design.setCornerRadius((float)100);
			btnImage4_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage4_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage4_design, null);
			btnImage4.setBackground(btnImage4_re);
			android.graphics.drawable.GradientDrawable btnImage5_design = new android.graphics.drawable.GradientDrawable();
			btnImage5_design.setColor(0xFF898F99);
			btnImage5_design.setCornerRadius((float)100);
			btnImage5_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage5_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage5_design, null);
			btnImage5.setBackground(btnImage5_re);
		}
		if (navigationIndex == 4) {
			android.graphics.drawable.GradientDrawable btnImage1_design = new android.graphics.drawable.GradientDrawable();
			btnImage1_design.setColor(0xFF898F99);
			btnImage1_design.setCornerRadius((float)100);
			btnImage1_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage1_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage1_design, null);
			btnImage1.setBackground(btnImage1_re);
			android.graphics.drawable.GradientDrawable btnImage2_design = new android.graphics.drawable.GradientDrawable();
			btnImage2_design.setColor(0xFF898F99);
			btnImage2_design.setCornerRadius((float)100);
			btnImage2_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage2_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage2_design, null);
			btnImage2.setBackground(btnImage2_re);
			android.graphics.drawable.GradientDrawable btnImage3_design = new android.graphics.drawable.GradientDrawable();
			btnImage3_design.setColor(0xFF898F99);
			btnImage3_design.setCornerRadius((float)100);
			btnImage3_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage3_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage3_design, null);
			btnImage3.setBackground(btnImage3_re);
			android.graphics.drawable.GradientDrawable btnImage4_design = new android.graphics.drawable.GradientDrawable();
			btnImage4_design.setColor(0xFFDD1D5E);
			btnImage4_design.setCornerRadius((float)100);
			btnImage4_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage4_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage4_design, null);
			btnImage4.setBackground(btnImage4_re);
			android.graphics.drawable.GradientDrawable btnImage5_design = new android.graphics.drawable.GradientDrawable();
			btnImage5_design.setColor(0xFF898F99);
			btnImage5_design.setCornerRadius((float)100);
			btnImage5_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage5_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage5_design, null);
			btnImage5.setBackground(btnImage5_re);
		}
		if (navigationIndex == 5) {
			android.graphics.drawable.GradientDrawable btnImage1_design = new android.graphics.drawable.GradientDrawable();
			btnImage1_design.setColor(0xFF898F99);
			btnImage1_design.setCornerRadius((float)100);
			btnImage1_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage1_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage1_design, null);
			btnImage1.setBackground(btnImage1_re);
			android.graphics.drawable.GradientDrawable btnImage2_design = new android.graphics.drawable.GradientDrawable();
			btnImage2_design.setColor(0xFF898F99);
			btnImage2_design.setCornerRadius((float)100);
			btnImage2_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage2_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage2_design, null);
			btnImage2.setBackground(btnImage2_re);
			android.graphics.drawable.GradientDrawable btnImage3_design = new android.graphics.drawable.GradientDrawable();
			btnImage3_design.setColor(0xFF898F99);
			btnImage3_design.setCornerRadius((float)100);
			btnImage3_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage3_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage3_design, null);
			btnImage3.setBackground(btnImage3_re);
			android.graphics.drawable.GradientDrawable btnImage4_design = new android.graphics.drawable.GradientDrawable();
			btnImage4_design.setColor(0xFF898F99);
			btnImage4_design.setCornerRadius((float)100);
			btnImage4_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage4_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage4_design, null);
			btnImage4.setBackground(btnImage4_re);
			android.graphics.drawable.GradientDrawable btnImage5_design = new android.graphics.drawable.GradientDrawable();
			btnImage5_design.setColor(0xFFDD1D5E);
			btnImage5_design.setCornerRadius((float)100);
			btnImage5_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnImage5_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnImage5_design, null);
			btnImage5.setBackground(btnImage5_re);
		}
	}
	
	
	public void _reactItem (final String _product_id, final String _react, final boolean _add) {
		fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
						productLikes = Double.parseDouble(_dataSnapshot.child(_product_id).child("likes").getValue().toString());
				productDislikes = Double.parseDouble(_dataSnapshot.child(_product_id).child("dislikes").getValue().toString());
				product_name = _dataSnapshot.child(_product_id).child("unit").getValue().toString();
				if (_react.equals("like")) {
					if (_add) {
						newLikeCount = String.valueOf((long)(productLikes + 1));
						_Logs(u_fullname + " likes " + product_name);
					}
					else {
						if (productLikes == 0) {
							newLikeCount = "0";
						}
						else {
							newLikeCount = String.valueOf((long)(productLikes - 1));
						}
						_Logs(u_fullname + " remove his/her reaction to this item " + product_name);
					}
					fbdb.child(_product_id).child("likes").setValue(newLikeCount);
				}
				if (_react.equals("dislike")) {
					if (_add) {
						newDisLikeCount = String.valueOf((long)(productDislikes + 1));
						_Logs(u_fullname + " dislikes " + product_name);
					}
					else {
						if (productDislikes == 0) {
							newDisLikeCount = "0";
						}
						else {
							newDisLikeCount = String.valueOf((long)(productDislikes - 1));
						}
						_Logs(u_fullname + " remove his/her reaction to this item " + product_name);
					}
					fbdb.child(_product_id).child("dislikes").setValue(newDisLikeCount);
				}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
	}
	
	
	public void _AddToCart (final String _stock) {
		cartdb.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot _dataSnapshot) {
						if (_dataSnapshot.hasChild(unique_id)) {
					btnAddToCart.setText("ALREADY ON YOUR CART");
					android.graphics.drawable.GradientDrawable btnAddToCart_design = new android.graphics.drawable.GradientDrawable();
					btnAddToCart_design.setColor(0xFF898F99);
					btnAddToCart_design.setCornerRadius((float)10);
					btnAddToCart_design.setStroke((int)0,Color.TRANSPARENT);
					android.graphics.drawable.RippleDrawable btnAddToCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddToCart_design, null);
					btnAddToCart.setBackground(btnAddToCart_re);
					btnAddToCart.setEnabled(false);
					boxQty.setVisibility(View.GONE);
					return;
				}
				}
				@Override
				public void onCancelled(DatabaseError _databaseError) {
				}
		});
		if (_stock.equals("0")) {
			btnAddToCart.setText("NO AVAILABLE STOCK");
			android.graphics.drawable.GradientDrawable btnAddToCart_design = new android.graphics.drawable.GradientDrawable();
			btnAddToCart_design.setColor(0xFF898F99);
			btnAddToCart_design.setCornerRadius((float)10);
			btnAddToCart_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnAddToCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddToCart_design, null);
			btnAddToCart.setBackground(btnAddToCart_re);
			btnAddToCart.setEnabled(false);
			boxQty.setVisibility(View.GONE);
			return;
		}
		if (tbQty.getText().toString().equals("0")) {
			android.graphics.drawable.GradientDrawable btnAddToCart_design = new android.graphics.drawable.GradientDrawable();
			btnAddToCart_design.setColor(0xFF898F99);
			btnAddToCart_design.setCornerRadius((float)10);
			btnAddToCart_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnAddToCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddToCart_design, null);
			btnAddToCart.setBackground(btnAddToCart_re);
			btnAddToCart.setEnabled(false);
			return;
		}
		android.graphics.drawable.GradientDrawable btnAddToCart_design = new android.graphics.drawable.GradientDrawable();
		btnAddToCart_design.setColor(0xFFDD1D5E);
		btnAddToCart_design.setCornerRadius((float)10);
		btnAddToCart_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnAddToCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddToCart_design, null);
		btnAddToCart.setBackground(btnAddToCart_re);
		btnAddToCart.setEnabled(true);
		boxQty.setVisibility(View.VISIBLE);
	}
	
	
	public void _ItemQuantity (final boolean _add) {
		currentQtyItem = Double.parseDouble(tbQty.getText().toString());
		if (_add) {
			if (currentQtyItem == Double.parseDouble(stock.getText().toString())) {
				newCurrentQty = Double.parseDouble(stock.getText().toString());
				android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
				btnAdd_design.setColor(0xFF898F99);
				btnAdd_design.setCornerRadius((float)10);
				btnAdd_design.setStroke((int)0,Color.TRANSPARENT);
				android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnAdd_design, null);
				btnAdd.setBackground(btnAdd_re);
				btnAdd.setEnabled(false);
			}
			else {
				newCurrentQty++;
				android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
				btnAdd_design.setColor(0xFFDD1D5E);
				btnAdd_design.setCornerRadius((float)10);
				btnAdd_design.setStroke((int)0,Color.TRANSPARENT);
				android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAdd_design, null);
				btnAdd.setBackground(btnAdd_re);
				btnAdd.setEnabled(true);
				android.graphics.drawable.GradientDrawable btnMinus_design = new android.graphics.drawable.GradientDrawable();
				btnMinus_design.setColor(0xFFDD1D5E);
				btnMinus_design.setCornerRadius((float)10);
				btnMinus_design.setStroke((int)0,Color.TRANSPARENT);
				android.graphics.drawable.RippleDrawable btnMinus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnMinus_design, null);
				btnMinus.setBackground(btnMinus_re);
				btnMinus.setEnabled(true);
			}
		}
		else {
			if (currentQtyItem == 0) {
				newCurrentQty = 0;
				android.graphics.drawable.GradientDrawable btnMinus_design = new android.graphics.drawable.GradientDrawable();
				btnMinus_design.setColor(0xFF898F99);
				btnMinus_design.setCornerRadius((float)10);
				btnMinus_design.setStroke((int)0,Color.TRANSPARENT);
				android.graphics.drawable.RippleDrawable btnMinus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnMinus_design, null);
				btnMinus.setBackground(btnMinus_re);
				btnMinus.setEnabled(false);
			}
			else {
				newCurrentQty--;
				android.graphics.drawable.GradientDrawable btnMinus_design = new android.graphics.drawable.GradientDrawable();
				btnMinus_design.setColor(0xFFDD1D5E);
				btnMinus_design.setCornerRadius((float)10);
				btnMinus_design.setStroke((int)0,Color.TRANSPARENT);
				android.graphics.drawable.RippleDrawable btnMinus_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnMinus_design, null);
				btnMinus.setBackground(btnMinus_re);
				btnMinus.setEnabled(true);
				android.graphics.drawable.GradientDrawable btnAdd_design = new android.graphics.drawable.GradientDrawable();
				btnAdd_design.setColor(0xFFDD1D5E);
				btnAdd_design.setCornerRadius((float)10);
				btnAdd_design.setStroke((int)0,Color.TRANSPARENT);
				android.graphics.drawable.RippleDrawable btnAdd_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAdd_design, null);
				btnAdd.setBackground(btnAdd_re);
				btnAdd.setEnabled(true);
			}
		}
		if (newCurrentQty > 0) {
			android.graphics.drawable.GradientDrawable btnAddToCart_design = new android.graphics.drawable.GradientDrawable();
			btnAddToCart_design.setColor(0xFFDD1D5E);
			btnAddToCart_design.setCornerRadius((float)10);
			btnAddToCart_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnAddToCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddToCart_design, null);
			btnAddToCart.setBackground(btnAddToCart_re);
			btnAddToCart.setEnabled(true);
		}
		else {
			android.graphics.drawable.GradientDrawable btnAddToCart_design = new android.graphics.drawable.GradientDrawable();
			btnAddToCart_design.setColor(0xFF898F99);
			btnAddToCart_design.setCornerRadius((float)10);
			btnAddToCart_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnAddToCart_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), btnAddToCart_design, null);
			btnAddToCart.setBackground(btnAddToCart_re);
			btnAddToCart.setEnabled(false);
		}
		tbQty.setText(String.valueOf((long)(newCurrentQty)));
	}
	
	
	public void _extra () {
		_cartdb_child_listener = new ChildEventListener() {
			        @Override
			    public void onChildAdded(DataSnapshot _param1, String _param2) {
				        GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				        final String _childKey = _param1.getKey();
				        final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				        if (addModuleBoolean && _childValue.get("product_id").toString().equals(unique_id)) {
					d.setTitle("SUCCESS");
					d.setMessage("This item was successfully added to your cart.");
					d.setPositiveButton("SHOP MORE", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							i.setClass(getApplicationContext(), ProductListActivity.class);
							startActivity(i);
							finish();
						}
					});
					d.setNegativeButton("MY CART", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							i.setClass(getApplicationContext(), ClientViewCartActivity.class);
							startActivity(i);
							finish();
						}
					});
					d.setCancelable(false);
					d.create().show();
				}
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
		cartdb.addChildEventListener(_cartdb_child_listener);
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
	
	
	public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public RvAdapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.rv_cars_slideshow, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			
			//Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("image_path").toString())).into(imageview1);
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
