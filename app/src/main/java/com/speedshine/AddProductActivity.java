package com.speedshine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;

import android.annotation.SuppressLint;
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
import android.widget.EditText;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.content.ClipData;
import android.app.AlertDialog;
import android.content.DialogInterface;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Continuation;
import android.net.Uri;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class AddProductActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_FP = 101;
	public final int REQ_CD_FP2 = 102;
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();
	
	private HashMap<String, Object> galleryMap = new HashMap<>();
	private boolean selectedThumbnailPicture = false;
	private String fileName = "";
	private double randomId = 0;
	private String uploadThumbnailUrl = "";
	private HashMap<String, Object> saveProductMap = new HashMap<>();
	private boolean newProduct = false;
	private String unique_id = "";
	private HashMap<String, Object> productsInfoMap = new HashMap<>();
	private boolean productUpdate = false;
	
	private ArrayList<HashMap<String, Object>> galleryMapList = new ArrayList<>();
	
	private LinearLayout main;
	private LinearLayout header;
	private ScrollView vscroll1;
	private LinearLayout btnBack;
	private TextView textview1;
	private ImageView imageview1;
	private LinearLayout body;
	private LinearLayout linear1;
	private LinearLayout linear21;
	private LinearLayout linear22;
	private LinearLayout linear23;
	private LinearLayout linear32;
	private LinearLayout linear24;
	private LinearLayout linear27;
	private Button btnSave;
	private TextView textview9;
	private EditText tbBrand;
	private TextView textview11;
	private EditText tbUnit;
	private TextView textview12;
	private EditText tbSpecs;
	private TextView textview13;
	private EditText tbPrice;
	private TextView textview20;
	private EditText tbStock;
	private TextView textview14;
	private LinearLayout linear30;
	private LinearLayout thumbnail_preview_box;
	private Button btnSelectImage;
	private ImageView thumbnail_img;
	private TextView textview16;
	private LinearLayout linear28;
	private LinearLayout boxImageSelected;
	private RecyclerView recyclerview2;
	private LinearLayout linear29;
	private TextView textview18;
	private LinearLayout boxGalleryCount;
	private Button btnAddImage;
	private TextView textview17;
	private TextView gallery_count;
	
	private DatabaseReference fbdb = _firebase.getReference("products");
	private ChildEventListener _fbdb_child_listener;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private AlertDialog.Builder d;
	private Intent fp2 = new Intent(Intent.ACTION_GET_CONTENT);
	private ProgressDialog pdUploadGallery;
	private StorageReference fbStorage = _firebase_storage.getReference("uploads/images");
	private OnCompleteListener<Uri> _fbStorage_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fbStorage_download_success_listener;
	private OnSuccessListener _fbStorage_delete_success_listener;
	private OnProgressListener _fbStorage_upload_progress_listener;
	private OnProgressListener _fbStorage_download_progress_listener;
	private OnFailureListener _fbStorage_failure_listener;
	private ProgressDialog pdStatus;
	private StorageReference fbStorageThumbnail = _firebase_storage.getReference("uploads/images");
	private OnCompleteListener<Uri> _fbStorageThumbnail_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fbStorageThumbnail_download_success_listener;
	private OnSuccessListener _fbStorageThumbnail_delete_success_listener;
	private OnProgressListener _fbStorageThumbnail_upload_progress_listener;
	private OnProgressListener _fbStorageThumbnail_download_progress_listener;
	private OnFailureListener _fbStorageThumbnail_failure_listener;
	private TimerTask tmr;
	private Intent i = new Intent();
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add_product);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		main = (LinearLayout) findViewById(R.id.main);
		header = (LinearLayout) findViewById(R.id.header);
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		btnBack = (LinearLayout) findViewById(R.id.btnBack);
		textview1 = (TextView) findViewById(R.id.textview1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		body = (LinearLayout) findViewById(R.id.body);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear21 = (LinearLayout) findViewById(R.id.linear21);
		linear22 = (LinearLayout) findViewById(R.id.linear22);
		linear23 = (LinearLayout) findViewById(R.id.linear23);
		linear32 = (LinearLayout) findViewById(R.id.linear32);
		linear24 = (LinearLayout) findViewById(R.id.linear24);
		linear27 = (LinearLayout) findViewById(R.id.linear27);
		btnSave = (Button) findViewById(R.id.btnSave);
		textview9 = (TextView) findViewById(R.id.textview9);
		tbBrand = (EditText) findViewById(R.id.tbBrand);
		textview11 = (TextView) findViewById(R.id.textview11);
		tbUnit = (EditText) findViewById(R.id.tbUnit);
		textview12 = (TextView) findViewById(R.id.textview12);
		tbSpecs = (EditText) findViewById(R.id.tbSpecs);
		textview13 = (TextView) findViewById(R.id.textview13);
		tbPrice = (EditText) findViewById(R.id.tbPrice);
		textview20 = (TextView) findViewById(R.id.textview20);
		tbStock = (EditText) findViewById(R.id.tbStock);
		textview14 = (TextView) findViewById(R.id.textview14);
		linear30 = (LinearLayout) findViewById(R.id.linear30);
		thumbnail_preview_box = (LinearLayout) findViewById(R.id.thumbnail_preview_box);
		btnSelectImage = (Button) findViewById(R.id.btnSelectImage);
		thumbnail_img = (ImageView) findViewById(R.id.thumbnail_img);
		textview16 = (TextView) findViewById(R.id.textview16);
		linear28 = (LinearLayout) findViewById(R.id.linear28);
		boxImageSelected = (LinearLayout) findViewById(R.id.boxImageSelected);
		recyclerview2 = (RecyclerView) findViewById(R.id.recyclerview2);
		linear29 = (LinearLayout) findViewById(R.id.linear29);
		textview18 = (TextView) findViewById(R.id.textview18);
		boxGalleryCount = (LinearLayout) findViewById(R.id.boxGalleryCount);
		btnAddImage = (Button) findViewById(R.id.btnAddImage);
		textview17 = (TextView) findViewById(R.id.textview17);
		gallery_count = (TextView) findViewById(R.id.gallery_count);
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		d = new AlertDialog.Builder(this);
		fp2.setType("image/*");
		fp2.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		sp = getSharedPreferences("data", Activity.MODE_PRIVATE);
		
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tbBrand.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter the brand name");
					return;
				}
				if (tbUnit.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter the unit name");
					return;
				}
				if (tbSpecs.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter the specifications");
					return;
				}
				if (tbPrice.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter the specifications");
					return;
				}
				if (tbStock.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please enter the available stocks.");
					return;
				}
				if (Double.parseDouble(tbStock.getText().toString()) < 0) {
					SketchwareUtil.showMessage(getApplicationContext(), "The amount of available stock must be larger than one.");
					return;
				}
				if (!selectedThumbnailPicture) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please select a picture for thumbnail");
					return;
				}
				if (galleryMapList.size() == 0) {
					SketchwareUtil.showMessage(getApplicationContext(), "Please select atleast 1 picture for gallery.");
					return;
				}
				pdStatus.setTitle("SAVING");
				pdStatus.setMessage("Saving the product information, Please wait.");
				pdStatus.setCancelable(false);
				pdStatus.setCanceledOnTouchOutside(false);
				pdStatus.show();
				if (productUpdate) {
					saveProductMap = new HashMap<>();
					saveProductMap.put("brand", tbBrand.getText().toString().toLowerCase());
					saveProductMap.put("unit", tbUnit.getText().toString());
					saveProductMap.put("specs", tbSpecs.getText().toString());
					saveProductMap.put("price", tbPrice.getText().toString());
					saveProductMap.put("stock", tbStock.getText().toString());
					saveProductMap.put("thumbnail", uploadThumbnailUrl);
					saveProductMap.put("gallery", new Gson().toJson(galleryMapList));
					fbdb.child(unique_id).updateChildren(saveProductMap);
				}
				else {
					unique_id = fbdb.push().getKey();
					newProduct = true;
					saveProductMap = new HashMap<>();
					saveProductMap.put("unique_id", unique_id);
					saveProductMap.put("brand", tbBrand.getText().toString().toLowerCase());
					saveProductMap.put("unit", tbUnit.getText().toString());
					saveProductMap.put("specs", tbSpecs.getText().toString());
					saveProductMap.put("price", tbPrice.getText().toString());
					saveProductMap.put("stock", tbStock.getText().toString());
					saveProductMap.put("thumbnail", uploadThumbnailUrl);
					saveProductMap.put("gallery", new Gson().toJson(galleryMapList));
					saveProductMap.put("dislikes", "0");
					saveProductMap.put("likes", "0");
					fbdb.child(unique_id).updateChildren(saveProductMap);
				}
			}
		});
		
		tbPrice.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		btnSelectImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp2, REQ_CD_FP2);
			}
		});
		
		recyclerview2.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int _scrollState) {
				super.onScrollStateChanged(recyclerView, _scrollState);
				
			}
			
			@Override
			public void onScrolled(RecyclerView recyclerView, int _offsetX, int _offsetY) {
				super.onScrolled(recyclerView, _offsetX, _offsetY);
				
			}
		});
		
		btnAddImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				startActivityForResult(fp, REQ_CD_FP);
			}
		});
		
		_fbdb_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (newProduct) {
					tmr = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									pdStatus.dismiss();
									SketchwareUtil.showMessage(getApplicationContext(), "Product successfully added.");
									finish();
								}
							});
						}
					};
					_timer.schedule(tmr, (int)(1000));
				}
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (newProduct) {
					tmr = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									SketchwareUtil.showMessage(getApplicationContext(), "Product successfully changed.");
									finish();
								}
							});
						}
					};
					_timer.schedule(tmr, (int)(1000));
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
		
		_fbStorage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				pdUploadGallery.setProgress((int)_progressValue);
			}
		};
		
		_fbStorage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fbStorage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				galleryMap = new HashMap<>();
				galleryMap.put("image_path", _downloadUrl);
				galleryMapList.add(galleryMap);
				_imageGalleryCount();
				_refreshListofGallery();
				pdUploadGallery.dismiss();
			}
		};
		
		_fbStorage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_fbStorage_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fbStorage_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
		
		_fbStorageThumbnail_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				pdUploadGallery.setProgress((int)_progressValue);
			}
		};
		
		_fbStorageThumbnail_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();
				
			}
		};
		
		_fbStorageThumbnail_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				selectedThumbnailPicture = true;
				uploadThumbnailUrl = _downloadUrl;
				pdUploadGallery.dismiss();
			}
		};
		
		_fbStorageThumbnail_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();
				
			}
		};
		
		_fbStorageThumbnail_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {
				
			}
		};
		
		_fbStorageThumbnail_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();
				
			}
		};
	}
	
	private void initializeLogic() {
		_design();
		pdUploadGallery = new ProgressDialog(AddProductActivity.this);
		pdStatus = new ProgressDialog(AddProductActivity.this);
		if (sp.contains("productUpdate")) {
			if (sp.getBoolean("productUpdate",true)) {
				productUpdate = true;
			}
			else {
				productUpdate = false;
			}
		}
		else {
			productUpdate = false;
		}
		if (productUpdate) {
			textview1.setText("EDIT PRODUCT");
			unique_id = getIntent().getStringExtra("unqiue_id");
			fbdb.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
							Map<String,Object> productsInfoMap = (Map<String,Object>) _dataSnapshot.child(unique_id).getValue();
					// Text Info
					tbPrice.setText(productsInfoMap.get("price").toString());
					tbUnit.setText(productsInfoMap.get("unit").toString());
					tbBrand.setText(productsInfoMap.get("brand").toString());
					tbSpecs.setText(productsInfoMap.get("specs").toString());
					tbStock.setText(productsInfoMap.get("stock").toString());
					uploadThumbnailUrl = productsInfoMap.get("thumbnail").toString();
					Glide.with(getApplicationContext()).load(Uri.parse(productsInfoMap.get("thumbnail").toString())).into(thumbnail_img);
					galleryMapList = new Gson().fromJson(productsInfoMap.get("gallery").toString(), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
					_imageGalleryCount();
					_refreshListofGallery();
					selectedThumbnailPicture = true;
					newProduct = true;
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
			});
		}
		else {
			selectedThumbnailPicture = false;
			newProduct = false;
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				fileName = "image-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(1000000), (int)(9999999)))));
				fbStorage.child(fileName).putFile(Uri.fromFile(new File(_filePath.get((int)(0))))).addOnFailureListener(_fbStorage_failure_listener).addOnProgressListener(_fbStorage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return fbStorage.child(fileName).getDownloadUrl();
					}}).addOnCompleteListener(_fbStorage_upload_success_listener);
				pdUploadGallery.setTitle("UPLOADING THE SELECTED IMAGES");
				pdUploadGallery.setMax((int)100);
				pdUploadGallery.setCancelable(false);
				pdUploadGallery.setCanceledOnTouchOutside(false);
				pdUploadGallery.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pdUploadGallery.show();
			}
			else {
				
			}
			break;
			
			case REQ_CD_FP2:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
				fileName = "image-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(1000000), (int)(9999999)))));
				thumbnail_img.setImageBitmap(FileUtil.decodeSampleBitmapFromPath(_filePath.get((int)(0)), 1024, 1024));
				fbStorageThumbnail.child(fileName).putFile(Uri.fromFile(new File(_filePath.get((int)(0))))).addOnFailureListener(_fbStorageThumbnail_failure_listener).addOnProgressListener(_fbStorageThumbnail_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
					@Override
					public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
						return fbStorageThumbnail.child(fileName).getDownloadUrl();
					}}).addOnCompleteListener(_fbStorageThumbnail_upload_success_listener);
				pdUploadGallery.setTitle("UPLOADING THE SELECTED IMAGES");
				pdUploadGallery.setMax((int)100);
				pdUploadGallery.setCancelable(false);
				pdUploadGallery.setCanceledOnTouchOutside(false);
				pdUploadGallery.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				pdUploadGallery.show();
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (sp.contains("productUpdate")) {
			sp.edit().remove("productUpdate").commit();
		}
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (sp.contains("productUpdate")) {
			sp.edit().remove("productUpdate").commit();
		}
	}
	public void _design () {
		android.graphics.drawable.GradientDrawable tbBrand_design = new android.graphics.drawable.GradientDrawable();
		tbBrand_design.setColor(0xFFFFFFFF);
		tbBrand_design.setCornerRadius((float)3);
		tbBrand_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbBrand_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbBrand_design, null);
		tbBrand.setBackground(tbBrand_re);
		android.graphics.drawable.GradientDrawable tbUnit_design = new android.graphics.drawable.GradientDrawable();
		tbUnit_design.setColor(0xFFFFFFFF);
		tbUnit_design.setCornerRadius((float)3);
		tbUnit_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbUnit_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbUnit_design, null);
		tbUnit.setBackground(tbUnit_re);
		android.graphics.drawable.GradientDrawable tbSpecs_design = new android.graphics.drawable.GradientDrawable();
		tbSpecs_design.setColor(0xFFFFFFFF);
		tbSpecs_design.setCornerRadius((float)3);
		tbSpecs_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbSpecs_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbSpecs_design, null);
		tbSpecs.setBackground(tbSpecs_re);
		android.graphics.drawable.GradientDrawable tbPrice_design = new android.graphics.drawable.GradientDrawable();
		tbPrice_design.setColor(0xFFFFFFFF);
		tbPrice_design.setCornerRadius((float)3);
		tbPrice_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbPrice_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbPrice_design, null);
		tbPrice.setBackground(tbPrice_re);
		android.graphics.drawable.GradientDrawable tbStock_design = new android.graphics.drawable.GradientDrawable();
		tbStock_design.setColor(0xFFFFFFFF);
		tbStock_design.setCornerRadius((float)3);
		tbStock_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbStock_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbStock_design, null);
		tbStock.setBackground(tbStock_re);
		android.graphics.drawable.GradientDrawable btnBack_design = new android.graphics.drawable.GradientDrawable();
		btnBack_design.setColor(0xFFDD1D5E);
		btnBack_design.setCornerRadius((float)100);
		btnBack_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable btnBack_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnBack_design, null);
		btnBack.setBackground(btnBack_re);
		android.graphics.drawable.GradientDrawable btnSelectImage_design = new android.graphics.drawable.GradientDrawable();
		btnSelectImage_design.setColor(0xFFDD1D5E);
		btnSelectImage_design.setCornerRadius((float)10);
		btnSelectImage_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable btnSelectImage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnSelectImage_design, null);
		btnSelectImage.setBackground(btnSelectImage_re);
		android.graphics.drawable.GradientDrawable btnAddImage_design = new android.graphics.drawable.GradientDrawable();
		btnAddImage_design.setColor(0xFFDD1D5E);
		btnAddImage_design.setCornerRadius((float)10);
		btnAddImage_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable btnAddImage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddImage_design, null);
		btnAddImage.setBackground(btnAddImage_re);
		android.graphics.drawable.GradientDrawable btnSave_design = new android.graphics.drawable.GradientDrawable();
		btnSave_design.setColor(0xFFDD1D5E);
		btnSave_design.setCornerRadius((float)10);
		btnSave_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable btnSave_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnSave_design, null);
		btnSave.setBackground(btnSave_re);
		thumbnail_preview_box.setElevation((float)5);
		android.graphics.drawable.GradientDrawable thumbnail_preview_box_design = new android.graphics.drawable.GradientDrawable();
		thumbnail_preview_box_design.setColor(0xFFFFFFFF);
		thumbnail_preview_box_design.setCornerRadius((float)8);
		thumbnail_preview_box_design.setStroke((int)0,0xFFFFFFFF);
		android.graphics.drawable.RippleDrawable thumbnail_preview_box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFFFFFF }), thumbnail_preview_box_design, null);
		thumbnail_preview_box.setBackground(thumbnail_preview_box_re);
		_imageGalleryCount();
	}
	
	
	public void _imageGalleryCount () {
		gallery_count.setText(String.valueOf((long)(galleryMapList.size())).concat("/5"));
		if (galleryMapList.size() == 5) {
			android.graphics.drawable.GradientDrawable btnAddImage_design = new android.graphics.drawable.GradientDrawable();
			btnAddImage_design.setColor(0xFF9E9E9E);
			btnAddImage_design.setCornerRadius((float)8);
			btnAddImage_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnAddImage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddImage_design, null);
			btnAddImage.setBackground(btnAddImage_re);
			btnAddImage.setEnabled(false);
		}
		else {
			android.graphics.drawable.GradientDrawable btnAddImage_design = new android.graphics.drawable.GradientDrawable();
			btnAddImage_design.setColor(0xFFDD1D5E);
			btnAddImage_design.setCornerRadius((float)8);
			btnAddImage_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable btnAddImage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnAddImage_design, null);
			btnAddImage.setBackground(btnAddImage_re);
			btnAddImage.setEnabled(true);
			if (galleryMapList.size() == 0) {
				boxImageSelected.setVisibility(View.VISIBLE);
				recyclerview2.setVisibility(View.GONE);
				boxGalleryCount.setVisibility(View.INVISIBLE);
			}
			else {
				boxImageSelected.setVisibility(View.GONE);
				recyclerview2.setVisibility(View.VISIBLE);
				boxGalleryCount.setVisibility(View.VISIBLE);
			}
		}
	}
	
	
	public void _refreshListofGallery () {
		recyclerview2.setAdapter(new Recyclerview2Adapter(galleryMapList));
		recyclerview2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
	}
	
	
	public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.rv_cars_gallery, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, @SuppressLint("RecyclerView") final int _position) {
			View _view = _holder.itemView;
			
			final LinearLayout body = (LinearLayout) _view.findViewById(R.id.body);
			final LinearLayout gallery_preview_box = (LinearLayout) _view.findViewById(R.id.gallery_preview_box);
			final ImageView imageview1 = (ImageView) _view.findViewById(R.id.imageview1);
			
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_view.setLayoutParams(_lp);
			Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("image_path").toString())).into(imageview1);
			gallery_preview_box.setElevation((float)5);
			android.graphics.drawable.GradientDrawable gallery_preview_box_design = new android.graphics.drawable.GradientDrawable();
			gallery_preview_box_design.setColor(0xFFFFFFFF);
			gallery_preview_box_design.setCornerRadius((float)8);
			gallery_preview_box_design.setStroke((int)0,0xFFFFFFFF);
			android.graphics.drawable.RippleDrawable gallery_preview_box_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), gallery_preview_box_design, null);
			gallery_preview_box.setBackground(gallery_preview_box_re);
			gallery_preview_box.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					d.setTitle("Delete");
					d.setMessage("Do you want to delete this image?");
					d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							_firebase_storage.getReferenceFromUrl(_data.get((int)_position).get("image_path").toString()).delete().addOnSuccessListener(_fbStorage_delete_success_listener).addOnFailureListener(_fbStorage_failure_listener);
							_data.remove((int)(_position));
							_imageGalleryCount();
							_refreshListofGallery();
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
