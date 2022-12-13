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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.widget.Button;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import java.util.Timer;
import java.util.TimerTask;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.View;
import com.bumptech.glide.Glide;
import java.text.DecimalFormat;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ViewProductActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> productsInfoMap = new HashMap<>();
	private String unique_id = "";
	private double navigationIndex = 0;
	private double currentPos = 0;
	
	private ArrayList<HashMap<String, Object>> gallerListMap = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private ScrollView vs;
	private LinearLayout btnBack;
	private TextView textview1;
	private LinearLayout linear7;
	private ImageView imageview1;
	private LinearLayout btnEdit;
	private LinearLayout btnDelete;
	private ImageView imageview5;
	private ImageView imageview6;
	private LinearLayout body;
	private LinearLayout background;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear6;
	private RecyclerView rv;
	private LinearLayout boxNavMenu;
	private LinearLayout navMenu;
	private Button btnImage1;
	private Button btnImage2;
	private Button btnImage3;
	private Button btnImage4;
	private Button btnImage5;
	private TextView unit_name;
	private TextView brand;
	private TextView price;
	private LinearLayout boxStock;
	private LinearLayout boxRate;
	private ImageView imageview2;
	private TextView stock;
	private ImageView imageview3;
	private TextView like;
	private LinearLayout linear5;
	private ImageView imageview4;
	private TextView dislike;
	private TextView spec;
	
	private DatabaseReference fbdb = _firebase.getReference("products");
	private ChildEventListener _fbdb_child_listener;
	private TimerTask tmr;
	private AlertDialog.Builder d;
	private Intent i = new Intent();
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view_product);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		vs = (ScrollView) findViewById(R.id.vs);
		btnBack = (LinearLayout) findViewById(R.id.btnBack);
		textview1 = (TextView) findViewById(R.id.textview1);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		btnEdit = (LinearLayout) findViewById(R.id.btnEdit);
		btnDelete = (LinearLayout) findViewById(R.id.btnDelete);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		body = (LinearLayout) findViewById(R.id.body);
		background = (LinearLayout) findViewById(R.id.background);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		rv = (RecyclerView) findViewById(R.id.rv);
		boxNavMenu = (LinearLayout) findViewById(R.id.boxNavMenu);
		navMenu = (LinearLayout) findViewById(R.id.navMenu);
		btnImage1 = (Button) findViewById(R.id.btnImage1);
		btnImage2 = (Button) findViewById(R.id.btnImage2);
		btnImage3 = (Button) findViewById(R.id.btnImage3);
		btnImage4 = (Button) findViewById(R.id.btnImage4);
		btnImage5 = (Button) findViewById(R.id.btnImage5);
		unit_name = (TextView) findViewById(R.id.unit_name);
		brand = (TextView) findViewById(R.id.brand);
		price = (TextView) findViewById(R.id.price);
		boxStock = (LinearLayout) findViewById(R.id.boxStock);
		boxRate = (LinearLayout) findViewById(R.id.boxRate);
		imageview2 = (ImageView) findViewById(R.id.imageview2);
		stock = (TextView) findViewById(R.id.stock);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		like = (TextView) findViewById(R.id.like);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		dislike = (TextView) findViewById(R.id.dislike);
		spec = (TextView) findViewById(R.id.spec);
		d = new AlertDialog.Builder(this);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btnEdit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				sp.edit().putBoolean("productUpdate", true).commit();
				i.putExtra("unqiue_id", unique_id);
				i.setClass(getApplicationContext(), AddProductActivity.class);
				startActivity(i);
			}
		});
		
		btnDelete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("DELETE");
				d.setMessage("Do you want to delete this item?");
				d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						fbdb.child(unique_id).removeValue();
						tmr = new TimerTask() {
							@Override
							public void run() {
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										SketchwareUtil.showMessage(getApplicationContext(), "Successfully deleted!");
										finish();
									}
								});
							}
						};
						_timer.schedule(tmr, (int)(1000));
					}
				});
				d.setNegativeButton("No", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				d.create().show();
			}
		});
		
		background.setOnLongClickListener(new View.OnLongClickListener() {
			 @Override
				public boolean onLongClick(View _view) {
				
				return true;
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
				
			}
		};
		fbdb.addChildEventListener(_fbdb_child_listener);
	}
	
	private void initializeLogic() {
		_design();
		try {
			unique_id = getIntent().getStringExtra("unique_id");
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
		android.graphics.drawable.GradientDrawable boxRate_design = new android.graphics.drawable.GradientDrawable();
		boxRate_design.setColor(0xFFDD1D5E);
		boxRate_design.setCornerRadius((float)10);
		boxRate_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable boxRate_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), boxRate_design, null);
		boxRate.setBackground(boxRate_re);
		RelativeLayout rl = new RelativeLayout(this);
		RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
		rl.setLayoutParams(lparams); background.removeAllViews();
		rl.addView(rv);
		rl.addView(boxNavMenu);
		background.addView(rl);
		android.graphics.drawable.GradientDrawable btnEdit_design = new android.graphics.drawable.GradientDrawable();
		btnEdit_design.setColor(0xFFDD1D5E);
		btnEdit_design.setCornerRadius((float)100);
		btnEdit_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnEdit_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnEdit_design, null);
		btnEdit.setBackground(btnEdit_re);
		android.graphics.drawable.GradientDrawable btnDelete_design = new android.graphics.drawable.GradientDrawable();
		btnDelete_design.setColor(0xFFDD1D5E);
		btnDelete_design.setCornerRadius((float)100);
		btnDelete_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnDelete_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnDelete_design, null);
		btnDelete.setBackground(btnDelete_re);
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
