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
import android.widget.Button;
import de.hdodenhof.circleimageview.*;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.widget.AdapterView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bumptech.glide.Glide;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ViewUsersProfileActivity extends  AppCompatActivity  { 
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> fromDbAccountMap = new HashMap<>();
	private String str_uid = "";
	private HashMap<String, Object> map_account = new HashMap<>();
	private double account_type_number = 0;
	
	private ArrayList<HashMap<String, Object>> fromdbAccountMap = new ArrayList<>();
	private ArrayList<String> ListOfGender = new ArrayList<>();
	private ArrayList<String> ListOfAccountLevel = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private ScrollView vsbody;
	private LinearLayout btnBack;
	private TextView textview1;
	private ImageView imageview1;
	private LinearLayout body;
	private LinearLayout linear1;
	private LinearLayout boxOverview;
	private LinearLayout boxVacinationState;
	private Button btnOverview;
	private Button btnVerificationState;
	private CircleImageView profile_picture_img;
	private LinearLayout linear5;
	private LinearLayout linear6;
	private LinearLayout linear15;
	private LinearLayout linear24;
	private Button btnSave;
	private LinearLayout linear7;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private TextView textview4;
	private EditText tblastname;
	private TextView textview5;
	private EditText tbfirstname;
	private TextView textview6;
	private EditText tbmiddlename;
	private LinearLayout linear11;
	private LinearLayout linear17;
	private TextView textview3;
	private LinearLayout tbdatebox;
	private TextView tbdate;
	private LinearLayout linear16;
	private ImageView imageview3;
	private Spinner spinnerGenderList;
	private TextView textview7;
	private LinearLayout tbgenderbox;
	private TextView tbgender;
	private LinearLayout linear14;
	private ImageView imageview4;
	private Spinner spinner_account_level;
	private TextView textview13;
	private LinearLayout account_type_box;
	private TextView tbAccountLevel;
	private LinearLayout linear26;
	private ImageView imageview6;
	private TextView textview19;
	private TextView textview20;
	private LinearLayout valid_id_preview_box;
	private TextView textview21;
	private LinearLayout selfie_preview_box;
	private LinearLayout linear27;
	private Button btnActivate;
	private LinearLayout valid_id_preview_text;
	private ImageView valid_id_preview_img;
	private TextView status_label;
	private LinearLayout selfie_preview_text;
	private ImageView selfie_preview_img;
	private TextView textview15;
	private TextView textview16;
	private TextView verification_status_lb;
	
	private DatabaseReference fbdbaccount = _firebase.getReference("account");
	private ChildEventListener _fbdbaccount_child_listener;
	private AlertDialog.Builder d;
	private TimerTask tmr;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.view_users_profile);
		initialize(_savedInstanceState);

		Log.d("av","ViewUsersProfileActivity onCreate");

		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		vsbody = (ScrollView) findViewById(R.id.vsbody);
		btnBack = (LinearLayout) findViewById(R.id.btnBack);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		body = (LinearLayout) findViewById(R.id.body);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		boxOverview = (LinearLayout) findViewById(R.id.boxOverview);
		boxVacinationState = (LinearLayout) findViewById(R.id.boxVacinationState);
		btnOverview = (Button) findViewById(R.id.btnOverview);
		btnVerificationState = (Button) findViewById(R.id.btnVerificationState);
		profile_picture_img = (CircleImageView) findViewById(R.id.profile_picture_img);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear15 = (LinearLayout) findViewById(R.id.linear15);
		linear24 = (LinearLayout) findViewById(R.id.linear24);
		btnSave = (Button) findViewById(R.id.btnSave);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		textview4 = (TextView) findViewById(R.id.textview4);
		tblastname = (EditText) findViewById(R.id.tblastname);
		textview5 = (TextView) findViewById(R.id.textview5);
		tbfirstname = (EditText) findViewById(R.id.tbfirstname);
		textview6 = (TextView) findViewById(R.id.textview6);
		tbmiddlename = (EditText) findViewById(R.id.tbmiddlename);
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		linear17 = (LinearLayout) findViewById(R.id.linear17);
		textview3 = (TextView) findViewById(R.id.textview3);
		tbdatebox = (LinearLayout) findViewById(R.id.tbdatebox);
		tbdate = (TextView) findViewById(R.id.tbdate);
		linear16 = (LinearLayout) findViewById(R.id.linear16);
		imageview3 = (ImageView) findViewById(R.id.imageview3);
		spinnerGenderList = (Spinner) findViewById(R.id.spinnerGenderList);
		textview7 = (TextView) findViewById(R.id.textview7);
		tbgenderbox = (LinearLayout) findViewById(R.id.tbgenderbox);
		tbgender = (TextView) findViewById(R.id.tbgender);
		linear14 = (LinearLayout) findViewById(R.id.linear14);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		spinner_account_level = (Spinner) findViewById(R.id.spinner_account_level);
		textview13 = (TextView) findViewById(R.id.textview13);
		account_type_box = (LinearLayout) findViewById(R.id.account_type_box);
		tbAccountLevel = (TextView) findViewById(R.id.tbAccountLevel);
		linear26 = (LinearLayout) findViewById(R.id.linear26);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		textview19 = (TextView) findViewById(R.id.textview19);
		textview20 = (TextView) findViewById(R.id.textview20);
		valid_id_preview_box = (LinearLayout) findViewById(R.id.valid_id_preview_box);
		textview21 = (TextView) findViewById(R.id.textview21);
		selfie_preview_box = (LinearLayout) findViewById(R.id.selfie_preview_box);
		linear27 = (LinearLayout) findViewById(R.id.linear27);
		btnActivate = (Button) findViewById(R.id.btnActivate);
		valid_id_preview_text = (LinearLayout) findViewById(R.id.valid_id_preview_text);
		valid_id_preview_img = (ImageView) findViewById(R.id.valid_id_preview_img);
		status_label = (TextView) findViewById(R.id.status_label);
		selfie_preview_text = (LinearLayout) findViewById(R.id.selfie_preview_text);
		selfie_preview_img = (ImageView) findViewById(R.id.selfie_preview_img);
		textview15 = (TextView) findViewById(R.id.textview15);
		textview16 = (TextView) findViewById(R.id.textview16);
		verification_status_lb = (TextView) findViewById(R.id.verification_status_lb);
		d = new AlertDialog.Builder(this);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btnOverview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				boxOverview.setVisibility(View.VISIBLE);
				boxVacinationState.setVisibility(View.GONE);
			}
		});
		
		btnVerificationState.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				boxVacinationState.setVisibility(View.VISIBLE);
				boxOverview.setVisibility(View.GONE);
			}
		});
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tbfirstname.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your first name");
					return;
				}
				if (tblastname.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter your last name");
					return;
				}
				map_account = new HashMap<>();
				map_account.put("fname", tbfirstname.getText().toString());
				map_account.put("mname", tbmiddlename.getText().toString());
				map_account.put("lname", tblastname.getText().toString());
				map_account.put("bday", tbdate.getText().toString());
				map_account.put("gender", tbgender.getText().toString());
				map_account.put("account_type", String.valueOf((long)(account_type_number)));
				fbdbaccount.child(str_uid).updateChildren(map_account);
				SketchwareUtil.showMessage(getApplicationContext(), "Successfully updated.");
				finish();
			}
		});
		
		tbdatebox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		spinnerGenderList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				tbgender.setText(ListOfGender.get((int)(_position)));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		tbgenderbox.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		spinner_account_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				tbAccountLevel.setText(ListOfAccountLevel.get((int)(_position)));
				account_type_number = _position + 1;
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		account_type_box.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				
			}
		});
		
		btnActivate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				d.setTitle("ACTIVATE ACCOUNT");
				d.setMessage("Do you want to activate this account?");
				d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						fbdbaccount.child(str_uid).child("verified").setValue("2");

						SketchwareUtil.showMessage(getApplicationContext(), "This user was successfully activated.");
						sendPushNotification("SADCC",tbfirstname.getText().toString() + " " + tblastname.getText().toString() + " was successfully activated.");
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
		
		_fbdbaccount_child_listener = new ChildEventListener() {
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
		fbdbaccount.addChildEventListener(_fbdbaccount_child_listener);
	}

	private void updateFirebaseUserData() {
		Log.d("av","trying to update user data");

		DatabaseReference dbAccounts = _firebase.getReference("account");

		map_account = new HashMap<>();
		map_account.put("uid", "");
		map_account.put("fname", "");
		map_account.put("mname", "");
		map_account.put("lname", "");
		map_account.put("email", "");
		map_account.put("bday", "");
		map_account.put("gender", "");
		map_account.put("verified", "");
		map_account.put("account_type", "");
		dbAccounts.child("").updateChildren(map_account);

		Log.d("av","update finished for user account");

	}
	
	private void initializeLogic() {
		_design();
		_listData();
		boxVacinationState.setVisibility(View.GONE);
		str_uid = getIntent().getStringExtra("userid");
		fromDbAccountMap = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<HashMap<String, Object>>(){}.getType());

		String accountType = getIntent().getStringExtra("account_type");
		String accountVerified = getIntent().getStringExtra("verified");

		tmr = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						tblastname.setText(getIntent().getStringExtra("lname"));
						tbfirstname.setText(getIntent().getStringExtra("fname"));
						tbmiddlename.setText(getIntent().getStringExtra("mname"));
						tbdate.setText(getIntent().getStringExtra("bday"));
						tbgender.setText(getIntent().getStringExtra("gender"));

						Log.d("avrepos","account_type=" + accountType+ " verified=" + accountVerified);

						if(accountType.equals("1")) {
							account_type_number = 1;
							tbAccountLevel.setText("Administrator");
							verification_status_lb.setVisibility(View.VISIBLE);
							Log.d("avrepos","account_type=1");
						}
						if(accountType.equals("2")) {
							account_type_number = 2;
							btnVerificationState.setText("Employee");
							Log.d("avrepos","account_type=2");
						}
						if (accountType.equals("3")) {
							Log.d("avrepos","account_type=3");
							account_type_number = 3;
							tbAccountLevel.setText("Client");
							btnVerificationState.setVisibility(View.GONE);
							if (accountVerified.equals("2")) {
								Log.d("avrepos","verified=2");
								verification_status_lb.setText("VERIFIED");
								verification_status_lb.setTextColor(0xFF1C6758);
								btnActivate.setEnabled(false);
								android.graphics.drawable.GradientDrawable btnActivate_design = new android.graphics.drawable.GradientDrawable();
								btnActivate_design.setColor(0xFF9E9E9E);
								btnActivate_design.setCornerRadius((float)8);
								btnActivate_design.setStroke((int)0,Color.TRANSPARENT);
								android.graphics.drawable.RippleDrawable btnActivate_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFF9E9E9E }), btnActivate_design, null);
								btnActivate.setBackground(btnActivate_re);
							}
							if (accountVerified.equals("1")) {
								Log.d("avrepos","verified=1");
								verification_status_lb.setText("NOT VERIFIED");
								verification_status_lb.setTextColor(0xFFDD1D5E);
							}
							if (accountVerified.equals("0")) {
								Log.d("avrepos","verified=0");
								status_label.setText("NO UPLOADED VALID ID");
								verification_status_lb.setText("NOT VERIFIED");
								verification_status_lb.setTextColor(0xFFDD1D5E);
								btnActivate.setEnabled(false);
								android.graphics.drawable.GradientDrawable btnActivate_design = new android.graphics.drawable.GradientDrawable();
								btnActivate_design.setColor(0xFF9E9E9E);
								btnActivate_design.setCornerRadius((float)8);
								btnActivate_design.setStroke((int)0,Color.TRANSPARENT);
								android.graphics.drawable.RippleDrawable btnActivate_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFF9E9E9E }), btnActivate_design, null);
								btnActivate.setBackground(btnActivate_re);
							}
						}

						if (accountVerified.equals("2")) {
							btnVerificationState.setVisibility(View.GONE);
						}
						else {
							btnVerificationState.setVisibility(View.VISIBLE);
						}

						spinner_account_level.setSelection((int)(account_type_number - 1));
						if (fromDbAccountMap.containsKey("valid_id")) {
							valid_id_preview_text.setVisibility(View.GONE);
							Glide.with(getApplicationContext()).load(Uri.parse(fromDbAccountMap.get("valid_id").toString())).into(valid_id_preview_img);
						}
						else {
							
						}
						if (fromDbAccountMap.containsKey("selfie_id")) {
							selfie_preview_text.setVisibility(View.GONE);
							Glide.with(getApplicationContext()).load(Uri.parse(fromDbAccountMap.get("selfie_id").toString())).into(selfie_preview_img);
						}
						else {
							selfie_preview_img.setVisibility(View.GONE);
						}
						if (fromDbAccountMap.containsKey("avatar")) {
							Glide.with(getApplicationContext()).load(Uri.parse(fromDbAccountMap.get("avatar").toString())).into(profile_picture_img);
						}
						else {
							
						}
					}
				});
			}
		};
		_timer.schedule(tmr, (int)(500));



	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
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

		int oneTimeID = (int) SystemClock.uptimeMillis();
		notificationManager.notify(oneTimeID, builder.build());
	}
	
	public void _showDatePicker () {
		androidx.appcompat.app.AppCompatDialogFragment newFragment = new DatePickerFragment();
		
		newFragment.show(getSupportFragmentManager(), "Date Picker");
	}
	
	
	public void _listData () {
		ListOfGender.add("Male");
		ListOfGender.add("Female");
		spinnerGenderList.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ListOfGender));
		((ArrayAdapter)spinnerGenderList.getAdapter()).notifyDataSetChanged();
		ListOfAccountLevel.add("Administrator");
		ListOfAccountLevel.add("Employee");
		ListOfAccountLevel.add("Client");
		spinner_account_level.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, ListOfAccountLevel));
		((ArrayAdapter)spinner_account_level.getAdapter()).notifyDataSetChanged();
	}
	
	
	public void _design () {
		android.graphics.drawable.GradientDrawable valid_id_preview_box_design = new android.graphics.drawable.GradientDrawable();
		valid_id_preview_box_design.setColor(0xFFF5F5F5);
		valid_id_preview_box_design.setCornerRadius((float)8);
		valid_id_preview_box_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable valid_id_preview_box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), valid_id_preview_box_design, null);
		valid_id_preview_box.setBackground(valid_id_preview_box_re);
		android.graphics.drawable.GradientDrawable tblastname_design = new android.graphics.drawable.GradientDrawable();
		tblastname_design.setColor(0xFFFFFFFF);
		tblastname_design.setCornerRadius((float)0);
		tblastname_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tblastname_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tblastname_design, null);
		tblastname.setBackground(tblastname_re);
		android.graphics.drawable.GradientDrawable tbfirstname_design = new android.graphics.drawable.GradientDrawable();
		tbfirstname_design.setColor(0xFFFFFFFF);
		tbfirstname_design.setCornerRadius((float)0);
		tbfirstname_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbfirstname_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbfirstname_design, null);
		tbfirstname.setBackground(tbfirstname_re);
		android.graphics.drawable.GradientDrawable tbmiddlename_design = new android.graphics.drawable.GradientDrawable();
		tbmiddlename_design.setColor(0xFFFFFFFF);
		tbmiddlename_design.setCornerRadius((float)0);
		tbmiddlename_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbmiddlename_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbmiddlename_design, null);
		tbmiddlename.setBackground(tbmiddlename_re);
		android.graphics.drawable.GradientDrawable tbdatebox_design = new android.graphics.drawable.GradientDrawable();
		tbdatebox_design.setColor(0xFFFFFFFF);
		tbdatebox_design.setCornerRadius((float)0);
		tbdatebox_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbdatebox_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbdatebox_design, null);
		tbdatebox.setBackground(tbdatebox_re);
		android.graphics.drawable.GradientDrawable tbgenderbox_design = new android.graphics.drawable.GradientDrawable();
		tbgenderbox_design.setColor(0xFFFFFFFF);
		tbgenderbox_design.setCornerRadius((float)0);
		tbgenderbox_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbgenderbox_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbgenderbox_design, null);
		tbgenderbox.setBackground(tbgenderbox_re);
		android.graphics.drawable.GradientDrawable account_type_box_design = new android.graphics.drawable.GradientDrawable();
		account_type_box_design.setColor(0xFFFFFFFF);
		account_type_box_design.setCornerRadius((float)0);
		account_type_box_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable account_type_box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), account_type_box_design, null);
		account_type_box.setBackground(account_type_box_re);
		android.graphics.drawable.GradientDrawable btnVerificationState_design = new android.graphics.drawable.GradientDrawable();
		btnVerificationState_design.setColor(0xFFDD1D5E);
		btnVerificationState_design.setCornerRadius((float)8);
		btnVerificationState_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnVerificationState_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnVerificationState_design, null);
		btnVerificationState.setBackground(btnVerificationState_re);
		android.graphics.drawable.GradientDrawable btnOverview_design = new android.graphics.drawable.GradientDrawable();
		btnOverview_design.setColor(0xFFDD1D5E);
		btnOverview_design.setCornerRadius((float)8);
		btnOverview_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnOverview_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnOverview_design, null);
		btnOverview.setBackground(btnOverview_re);
		android.graphics.drawable.GradientDrawable btnSave_design = new android.graphics.drawable.GradientDrawable();
		btnSave_design.setColor(0xFFDD1D5E);
		btnSave_design.setCornerRadius((float)8);
		btnSave_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnSave_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnSave_design, null);
		btnSave.setBackground(btnSave_re);
		android.graphics.drawable.GradientDrawable btnActivate_design = new android.graphics.drawable.GradientDrawable();
		btnActivate_design.setColor(0xFFDD1D5E);
		btnActivate_design.setCornerRadius((float)8);
		btnActivate_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnActivate_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnActivate_design, null);
		btnActivate.setBackground(btnActivate_re);
		btnSave.setVisibility(View.GONE);
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
