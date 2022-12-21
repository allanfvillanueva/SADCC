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
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class ClientFeedbacksActivity extends  AppCompatActivity  {

	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();

	private ArrayList<HashMap<String, Object>> feedbackslm = new ArrayList<>();

	private LinearLayout main;
	private LinearLayout header;
	private ListView lv;
	private LinearLayout btnExit;
	private TextView textview3;
	private ImageView imageview4;

	private DatabaseReference dbfeedback = _firebase.getReference("feedbacks");
	private ChildEventListener _dbfeedback_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_feedbacks);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}

	private void initialize(Bundle _savedInstanceState) {

		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		lv = (ListView) findViewById(R.id.lv);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		textview3 = (TextView) findViewById(R.id.textview3);
		imageview4 = (ImageView) findViewById(R.id.imageview4);

		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});

		_dbfeedback_child_listener = new ChildEventListener() {
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
		dbfeedback.addChildEventListener(_dbfeedback_child_listener);
	}

	private void initializeLogic() {
		_design();
		dbfeedback.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				feedbackslm = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						feedbackslm.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				lv.setAdapter(new LvAdapter(feedbackslm));
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

	public void _design () {
		android.graphics.drawable.GradientDrawable btnExit_design = new android.graphics.drawable.GradientDrawable();
		btnExit_design.setColor(0xFFDD1D5E);
		btnExit_design.setCornerRadius((float)50);
		btnExit_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnExit_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnExit_design, null);
		btnExit.setBackground(btnExit_re);
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
				_view = _inflater.inflate(R.layout.lv_feedback, null);
			}

			final LinearLayout main = (LinearLayout) _view.findViewById(R.id.main);
			final LinearLayout box = (LinearLayout) _view.findViewById(R.id.box);
			final TextView name = (TextView) _view.findViewById(R.id.name);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView message = (TextView) _view.findViewById(R.id.message);
			final ImageView img1 = (ImageView) _view.findViewById(R.id.img1);
			final ImageView img2 = (ImageView) _view.findViewById(R.id.img2);
			final ImageView img3 = (ImageView) _view.findViewById(R.id.img3);
			final ImageView img4 = (ImageView) _view.findViewById(R.id.img4);
			final ImageView img5 = (ImageView) _view.findViewById(R.id.img5);

			// Design
			box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable box_design = new android.graphics.drawable.GradientDrawable();
			box_design.setColor(0xFFFFFFFF);
			box_design.setCornerRadius((float)10);
			box_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), box_design, null);
			box.setBackground(box_re);
			// Bind
			name.setText(_data.get((int)_position).get("name").toString());
			message.setText(_data.get((int)_position).get("message").toString());
			if (_data.get((int)_position).get("star").toString().equals("1")) {
				img1.setImageResource(R.drawable.star_fill);
				img2.setImageResource(R.drawable.star_outlined);
				img3.setImageResource(R.drawable.star_outlined);
				img4.setImageResource(R.drawable.star_outlined);
				img5.setImageResource(R.drawable.star_outlined);
			}
			if (_data.get((int)_position).get("star").toString().equals("2")) {
				img1.setImageResource(R.drawable.star_fill);
				img2.setImageResource(R.drawable.star_fill);
				img3.setImageResource(R.drawable.star_outlined);
				img4.setImageResource(R.drawable.star_outlined);
				img5.setImageResource(R.drawable.star_outlined);
			}
			if (_data.get((int)_position).get("star").toString().equals("3")) {
				img1.setImageResource(R.drawable.star_fill);
				img2.setImageResource(R.drawable.star_fill);
				img3.setImageResource(R.drawable.star_fill);
				img4.setImageResource(R.drawable.star_outlined);
				img5.setImageResource(R.drawable.star_outlined);
			}
			if (_data.get((int)_position).get("star").toString().equals("4")) {
				img1.setImageResource(R.drawable.star_fill);
				img2.setImageResource(R.drawable.star_fill);
				img3.setImageResource(R.drawable.star_fill);
				img4.setImageResource(R.drawable.star_fill);
				img5.setImageResource(R.drawable.star_outlined);
			}
			if (_data.get((int)_position).get("star").toString().equals("5")) {
				img1.setImageResource(R.drawable.star_fill);
				img2.setImageResource(R.drawable.star_fill);
				img3.setImageResource(R.drawable.star_fill);
				img4.setImageResource(R.drawable.star_fill);
				img5.setImageResource(R.drawable.star_fill);
			}

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
