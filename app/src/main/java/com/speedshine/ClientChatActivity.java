package com.speedshine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.text.method.LinkMovementMethod;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.content.Intent;
import android.content.ClipData;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.storage.OnProgressListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Continuation;
import android.net.Uri;
import java.io.File;
import android.view.View;
import com.bumptech.glide.Glide;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class ClientChatActivity extends  AppCompatActivity  {

	public final int REQ_CD_FP = 101;
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	private FirebaseStorage _firebase_storage = FirebaseStorage.getInstance();

	private  DatabaseReference chat;
	private String userid = "";
	private HashMap<String, Object> sendMessage = new HashMap<>();
	private String messageID = "";
	private  ChildEventListener chatListener;
	private String filepathstr = "";
	private String fileName = "";
	private String getAccountId = "";
	private HashMap<String, Object> AccountInfoMap = new HashMap<>();
	private boolean hasImage = false;
	private String imgSource = "";
	private String senderId = "";
	private HashMap<String, Object> JoinRoomChatMap = new HashMap<>();
	private String ConversationId = "";
	private String ufname = "";
	private String ulname = "";
	private String uid = "";
	private String strMessage = "";
	private String mname = "";
	private double AllMessagesCount = 0;
	private double LastSegmentIndex = 0;
	private String LastSegmentID = "";
	private boolean hasLastSegment = false;

	private String attachedFileURL = "";

	private ArrayList<HashMap<String, Object>> chatlm = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> AllMessagesLM = new ArrayList<>();

	private LinearLayout main;
	private LinearLayout header;
	private LinearLayout box;
	private LinearLayout btnExit;
	private TextView lblName;
	private ImageView imageview4;
	private LinearLayout chatRoom;
	private LinearLayout startConversation;
	private LinearLayout boxChat;
	private LinearLayout boxChatControl;
	private ListView lv;
	private LinearLayout btnSendImage;
	private EditText tbMessage;
	private LinearLayout btnSend;
	private ImageView imageview5;
	private ImageView imageview6;
	private ImageView imageview7;
	private Button btnStartConversation;

	private DatabaseReference fbproducts = _firebase.getReference("products");
	private ChildEventListener _fbproducts_child_listener;
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
	private Calendar c = Calendar.getInstance();
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private StorageReference fbstorage = _firebase_storage.getReference("uploads/chats/attachments");
	private OnCompleteListener<Uri> _fbstorage_upload_success_listener;
	private OnSuccessListener<FileDownloadTask.TaskSnapshot> _fbstorage_download_success_listener;
	private OnSuccessListener _fbstorage_delete_success_listener;
	private OnProgressListener _fbstorage_upload_progress_listener;
	private OnProgressListener _fbstorage_download_progress_listener;
	private OnFailureListener _fbstorage_failure_listener;
	private Intent i = new Intent();
	private DatabaseReference fbaccount = _firebase.getReference("account");
	private ChildEventListener _fbaccount_child_listener;
	private DatabaseReference dbchatroom = _firebase.getReference("chatroom");
	private ChildEventListener _dbchatroom_child_listener;
	private ProgressDialog pd;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.client_chat);
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
		box = (LinearLayout) findViewById(R.id.box);
		btnExit = (LinearLayout) findViewById(R.id.btnExit);
		lblName = (TextView) findViewById(R.id.lblName);
		imageview4 = (ImageView) findViewById(R.id.imageview4);
		chatRoom = (LinearLayout) findViewById(R.id.chatRoom);
		startConversation = (LinearLayout) findViewById(R.id.startConversation);
		boxChat = (LinearLayout) findViewById(R.id.boxChat);
		boxChatControl = (LinearLayout) findViewById(R.id.boxChatControl);
		lv = (ListView) findViewById(R.id.lv);
		btnSendImage = (LinearLayout) findViewById(R.id.btnSendImage);
		tbMessage = (EditText) findViewById(R.id.tbMessage);
		btnSend = (LinearLayout) findViewById(R.id.btnSend);
		imageview5 = (ImageView) findViewById(R.id.imageview5);
		imageview6 = (ImageView) findViewById(R.id.imageview6);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		btnStartConversation = (Button) findViewById(R.id.btnStartConversation);
		fbauth = FirebaseAuth.getInstance();
		fp.setType("image/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

		btnExit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				finish();
			}
		});

		btnSendImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//startActivityForResult(fp, REQ_CD_FP);

				String[] mimeTypes =
						{"application/pdf"};

				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
					if (mimeTypes.length > 0) {
						intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
					}
				} else {
					String mimeTypesStr = "";
					for (String mimeType : mimeTypes) {
						mimeTypesStr += mimeType + "|";
					}
					intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
				}
				startActivityForResult(Intent.createChooser(intent,"Attach File"), REQ_CD_FP);

			}

		});

		btnSend.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View _view) {

				return true;
			}
		});

		btnSend.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (tbMessage.getText().toString().equals("")) {

				}
				else {
					strMessage = tbMessage.getText().toString();
					messageID = fbproducts.push().getKey();
					sendMessage = new HashMap<>();
					sendMessage.put("type", "text");
					sendMessage.put("content", strMessage);
					sendMessage.put("message_id", messageID);
					sendMessage.put("userid", senderId);
					sendMessage.put("noreply", "true");
					if (hasImage) {
						sendMessage.put("avatar", imgSource);
					}
					c = Calendar.getInstance();
					sendMessage.put("sent_at", new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a").format(c.getTime()));
					chat.child(messageID).updateChildren(sendMessage);
					tbMessage.setText("");
				}
			}
		});

		btnStartConversation.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				pd.setMessage("Starting new conversation...");
				pd.setCancelable(false);
				pd.setCanceledOnTouchOutside(false);
				pd.show();
				ConversationId = dbchatroom.push().getKey();
				JoinRoomChatMap = new HashMap<>();
				JoinRoomChatMap.put("fname", ufname);
				JoinRoomChatMap.put("lname", ulname);
				JoinRoomChatMap.put("mname", mname);
				JoinRoomChatMap.put("conversation_id", ConversationId);
				JoinRoomChatMap.put("uid", uid);
				dbchatroom.child(getAccountId).updateChildren(JoinRoomChatMap);
			}
		});

		_fbproducts_child_listener = new ChildEventListener() {
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
		fbproducts.addChildEventListener(_fbproducts_child_listener);

		_fbstorage_upload_progress_listener = new OnProgressListener<UploadTask.TaskSnapshot>() {
			@Override
			public void onProgress(UploadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

			}
		};

		_fbstorage_download_progress_listener = new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onProgress(FileDownloadTask.TaskSnapshot _param1) {
				double _progressValue = (100.0 * _param1.getBytesTransferred()) / _param1.getTotalByteCount();

			}
		};

		_fbstorage_upload_success_listener = new OnCompleteListener<Uri>() {
			@Override
			public void onComplete(Task<Uri> _param1) {
				final String _downloadUrl = _param1.getResult().toString();
				messageID = fbproducts.push().getKey();
				sendMessage = new HashMap<>();
				sendMessage.put("type", "image");
				sendMessage.put("content", _downloadUrl);
				sendMessage.put("message_id", messageID);
				sendMessage.put("userid", senderId);
				sendMessage.put("noreply", "true");
				c = Calendar.getInstance();
				sendMessage.put("sent_at", new SimpleDateFormat("MMM dd,yyyy hh:mm:ss a").format(c.getTime()));
				chat.child(messageID).updateChildren(sendMessage);
				SketchwareUtil.showMessage(getApplicationContext(), "Success");
			}
		};

		_fbstorage_download_success_listener = new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
			@Override
			public void onSuccess(FileDownloadTask.TaskSnapshot _param1) {
				final long _totalByteCount = _param1.getTotalByteCount();

			}
		};

		_fbstorage_delete_success_listener = new OnSuccessListener() {
			@Override
			public void onSuccess(Object _param1) {

			}
		};

		_fbstorage_failure_listener = new OnFailureListener() {
			@Override
			public void onFailure(Exception _param1) {
				final String _message = _param1.getMessage();

			}
		};

		_fbaccount_child_listener = new ChildEventListener() {
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
		fbaccount.addChildEventListener(_fbaccount_child_listener);

		_dbchatroom_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				pd.dismiss();
				chatRoom.setVisibility(View.VISIBLE);
				startConversation.setVisibility(View.GONE);
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
				SketchwareUtil.showMessage(getApplicationContext(), "Failed to connect to the server.");
			}
		};
		dbchatroom.addChildEventListener(_dbchatroom_child_listener);

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
		pd = new ProgressDialog(ClientChatActivity.this);
		pd.setMessage("Loading assets, Please wait...");
		pd.setCancelable(false);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		hasImage = false;
		getAccountId = FirebaseAuth.getInstance().getCurrentUser().getUid();
		fbaccount.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				Map<String,Object> AccountInfoMap = (Map<String,Object>) _dataSnapshot.child(getAccountId).getValue();
				if (AccountInfoMap.containsKey("account_type")) {
					if (AccountInfoMap.get("account_type").toString().equals("3")) {
						userid = getAccountId;
						senderId = getAccountId;
						lblName.setText("SADCC SUPPORT");
						ufname = AccountInfoMap.get("fname").toString();
						ulname = AccountInfoMap.get("lname").toString();
						mname = AccountInfoMap.get("mname").toString();
						uid = AccountInfoMap.get("uid").toString();

						pd.setMessage("Starting new conversation...");
						pd.setCancelable(false);
						pd.setCanceledOnTouchOutside(false);
						pd.show();
						ConversationId = dbchatroom.push().getKey();
						JoinRoomChatMap = new HashMap<>();
						JoinRoomChatMap.put("fname", ufname);
						JoinRoomChatMap.put("lname", ulname);
						JoinRoomChatMap.put("mname", mname);
						JoinRoomChatMap.put("conversation_id", ConversationId);
						JoinRoomChatMap.put("uid", uid);
						dbchatroom.child(getAccountId).updateChildren(JoinRoomChatMap);
					}
					else {
						userid = getIntent().getStringExtra("userid");
						senderId = "admin";
						lblName.setText(getIntent().getStringExtra("name"));
					}
					if (AccountInfoMap.containsKey("avatar")) {
						hasImage = true;
						imgSource = AccountInfoMap.get("avatar").toString();
					}
					chat = _firebase.getReference("chat/" + userid);
					_loadChats();
					_chatEventListener();
				}
				else {
					fbaccount.child(getAccountId).child("account_type").setValue("3");
				}
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
		dbchatroom.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				pd.dismiss();
				if (_dataSnapshot.hasChild(userid)) {
					chatRoom.setVisibility(View.VISIBLE);
					startConversation.setVisibility(View.GONE);
				}
				else {
					chatRoom.setVisibility(View.GONE);
					startConversation.setVisibility(View.VISIBLE);
				}
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
			case REQ_CD_FP:
				if (_resultCode == Activity.RESULT_OK) {

					Log.d("avrepos","onActivityResult _data.getData()" + _data.getData());
					ArrayList<String> _filePath = new ArrayList<>();


					if (_data != null) {
						if (_data.getClipData() != null) {
							for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
								ClipData.Item _item = _data.getClipData().getItemAt(_index);
								_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
							}
						}						else {
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
						}
					}

					Uri imageuri = _data.getData();
					final String timestamp = "" + System.currentTimeMillis();
					StorageReference storageReference = FirebaseStorage.getInstance().getReference();
					final String messagePushID = timestamp;
					//Toast.makeText(ClientChatActivity.this, imageuri.toString(), Toast.LENGTH_SHORT).show();

					// Here we are uploading the pdf in firebase storage with the name of current time
					final StorageReference filepath = storageReference.child(messagePushID + "." + "pdf");
					//Toast.makeText(ClientChatActivity.this, filepath.getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
					filepath.putFile(imageuri).continueWithTask(new Continuation() {
						@Override
						public Object then(@NonNull Task task) throws Exception {
							if (!task.isSuccessful()) {
								throw task.getException();
							}
							return filepath.getDownloadUrl();
						}
					}).addOnCompleteListener(new OnCompleteListener<Uri>() {
						@Override
						public void onComplete(@NonNull Task<Uri> task) {
							if (task.isSuccessful()) {
								// After uploading is done it progress
								// dialog box will be dismissed

								Uri uri = task.getResult();
								String mimeType = getContentResolver().getType(uri);
								String myurl;
								myurl = uri.toString();
								//Toast.makeText(ClientChatActivity.this, "Uploaded Successfully" + uri.toString(), Toast.LENGTH_SHORT).show();
								Log.d("avrepos","onComplete file upload mimetype" + mimeType);
								Log.d("avrepos","onComplete file URL" + uri.toString());
								attachedFileURL = uri.toString();
								tbMessage.setMovementMethod(LinkMovementMethod.getInstance());
								tbMessage.setText(attachedFileURL);

							} else {

								Toast.makeText(ClientChatActivity.this, "Uploaded failed.", Toast.LENGTH_SHORT).show();
							}
						}
					});

//					fileName = "file-".concat(String.valueOf((long)(SketchwareUtil.getRandom((int)(1000000), (int)(9999999)))));
//					filepathstr = _filePath.get((int)(0));
//					fbstorage.child(fileName).putFile(Uri.fromFile(new File(filepathstr))).addOnFailureListener(_fbstorage_failure_listener).addOnProgressListener(_fbstorage_upload_progress_listener).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//						@Override
//						public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
//							return fbstorage.child(fileName).getDownloadUrl();
//						}}).addOnCompleteListener(_fbstorage_upload_success_listener);
//					SketchwareUtil.showMessage(getApplicationContext(), "Uploading");
				}
				else {

				}
				break;
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
		android.graphics.drawable.GradientDrawable btnSend_design = new android.graphics.drawable.GradientDrawable();
		btnSend_design.setColor(0xFFDD1D5E);
		btnSend_design.setCornerRadius((float)10);
		btnSend_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnSend_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnSend_design, null);
		btnSend.setBackground(btnSend_re);
		android.graphics.drawable.GradientDrawable btnSendImage_design = new android.graphics.drawable.GradientDrawable();
		btnSendImage_design.setColor(0xFFDD1D5E);
		btnSendImage_design.setCornerRadius((float)10);
		btnSendImage_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnSendImage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnSendImage_design, null);
		btnSendImage.setBackground(btnSendImage_re);
		android.graphics.drawable.GradientDrawable tbMessage_design = new android.graphics.drawable.GradientDrawable();
		tbMessage_design.setColor(0xFFFFFFFF);
		tbMessage_design.setCornerRadius((float)10);
		tbMessage_design.setStroke((int)2,0xFFDD1D5E);
		android.graphics.drawable.RippleDrawable tbMessage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), tbMessage_design, null);
		tbMessage.setBackground(tbMessage_re);
		android.graphics.drawable.GradientDrawable btnStartConversation_design = new android.graphics.drawable.GradientDrawable();
		btnStartConversation_design.setColor(0xFFDD1D5E);
		btnStartConversation_design.setCornerRadius((float)10);
		btnStartConversation_design.setStroke((int)0,Color.TRANSPARENT);
		android.graphics.drawable.RippleDrawable btnStartConversation_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ 0xFFFCE4EC }), btnStartConversation_design, null);
		btnStartConversation.setBackground(btnStartConversation_re);
	}


	public void _chatEventListener () {
		chatListener = new ChildEventListener() {
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				_loadChats();
				_getTheLastSegmentId();
			}
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				_loadChats();
				_getTheLastSegmentId();
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
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {

			}
		};
		chat.addChildEventListener(chatListener);
	}


	public void _loadChats () {
		chat.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot _dataSnapshot) {
				chatlm = new ArrayList<>();
				try {
					GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
					for (DataSnapshot _data : _dataSnapshot.getChildren()) {
						HashMap<String, Object> _map = _data.getValue(_ind);
						chatlm.add(_map);
					}
				}
				catch (Exception _e) {
					_e.printStackTrace();
				}
				lv.setStackFromBottom(true);
				lv.setAdapter(new LvAdapter(chatlm));
				((BaseAdapter)lv.getAdapter()).notifyDataSetChanged();
			}
			@Override
			public void onCancelled(DatabaseError _databaseError) {
			}
		});
	}


	public void _getTheLastSegmentId () {
		chat.addListenerForSingleValueEvent(new ValueEventListener() {
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
				hasLastSegment = false;
				AllMessagesCount = AllMessagesLM.size();
				LastSegmentIndex = AllMessagesCount - 2;
				if (AllMessagesCount > 1) {
					LastSegmentID = AllMessagesLM.get((int)LastSegmentIndex).get("message_id").toString();
					hasLastSegment = true;
					chat.child(AllMessagesLM.get((int)LastSegmentIndex).get("message_id").toString()).child("noreply").setValue("false");
				}
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
				_view = _inflater.inflate(R.layout.rv_chat, null);
			}

			final LinearLayout linear6 = (LinearLayout) _view.findViewById(R.id.linear6);
			final LinearLayout boxRight = (LinearLayout) _view.findViewById(R.id.boxRight);
			final LinearLayout boxLeft = (LinearLayout) _view.findViewById(R.id.boxLeft);
			final LinearLayout sas = (LinearLayout) _view.findViewById(R.id.sas);
			final TextView rightDate = (TextView) _view.findViewById(R.id.rightDate);
			final LinearLayout rightImageMessage = (LinearLayout) _view.findViewById(R.id.rightImageMessage);
			final LinearLayout rightText = (LinearLayout) _view.findViewById(R.id.rightText);
			final de.hdodenhof.circleimageview.CircleImageView rightSenderIcon = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.rightSenderIcon);
			final ImageView rightContentImg = (ImageView) _view.findViewById(R.id.rightContentImg);
			final TextView lblRight = (TextView) _view.findViewById(R.id.lblRight);
			final LinearLayout linear5 = (LinearLayout) _view.findViewById(R.id.linear5);
			final TextView leftdate = (TextView) _view.findViewById(R.id.leftdate);
			final de.hdodenhof.circleimageview.CircleImageView leftSenderIcon = (de.hdodenhof.circleimageview.CircleImageView) _view.findViewById(R.id.leftSenderIcon);
			final LinearLayout leftText = (LinearLayout) _view.findViewById(R.id.leftText);
			final LinearLayout leftImageMessage = (LinearLayout) _view.findViewById(R.id.leftImageMessage);
			final TextView lblLeft = (TextView) _view.findViewById(R.id.lblLeft);
			final ImageView leftContentImg = (ImageView) _view.findViewById(R.id.leftContentImg);

			// Design
			android.graphics.drawable.GradientDrawable rightImageMessage_design = new android.graphics.drawable.GradientDrawable();
			rightImageMessage_design.setColor(0xFFDD1D5E);
			rightImageMessage_design.setCornerRadius((float)8);
			rightImageMessage_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable rightImageMessage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), rightImageMessage_design, null);
			rightImageMessage.setBackground(rightImageMessage_re);
			android.graphics.drawable.GradientDrawable leftImageMessage_design = new android.graphics.drawable.GradientDrawable();
			leftImageMessage_design.setColor(0xFFDD1D5E);
			leftImageMessage_design.setCornerRadius((float)8);
			leftImageMessage_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable leftImageMessage_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), leftImageMessage_design, null);
			leftImageMessage.setBackground(leftImageMessage_re);
			android.graphics.drawable.GradientDrawable leftText_design = new android.graphics.drawable.GradientDrawable();
			leftText_design.setColor(0xFFDD1D5E);
			leftText_design.setCornerRadius((float)8);
			leftText_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable leftText_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), leftText_design, null);
			leftText.setBackground(leftText_re);
			android.graphics.drawable.GradientDrawable rightText_design = new android.graphics.drawable.GradientDrawable();
			rightText_design.setColor(0xFFDD1D5E);
			rightText_design.setCornerRadius((float)8);
			rightText_design.setStroke((int)0,Color.TRANSPARENT);
			android.graphics.drawable.RippleDrawable rightText_re = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.TRANSPARENT }), rightText_design, null);
			rightText.setBackground(rightText_re);
			rightText.setVisibility(View.GONE);
			leftText.setVisibility(View.GONE);
			leftImageMessage.setVisibility(View.GONE);
			rightImageMessage.setVisibility(View.GONE);
			boxRight.setVisibility(View.GONE);
			boxLeft.setVisibility(View.GONE);
			// bind
			if (_data.get((int)_position).get("userid").toString().equals(senderId)) {
				boxRight.setVisibility(View.VISIBLE);
				rightDate.setText(_data.get((int)_position).get("sent_at").toString());
				if (_data.get((int)_position).get("type").toString().equals("text")) {
					lblRight.setText(_data.get((int)_position).get("content").toString());
					rightText.setVisibility(View.VISIBLE);
				}
				else {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("content").toString())).into(rightContentImg);
					rightImageMessage.setVisibility(View.VISIBLE);
				}
				if (_data.get((int)_position).containsKey("avatar")) {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("avatar").toString())).into(rightSenderIcon);
				}
			}
			else {
				boxLeft.setVisibility(View.VISIBLE);
				leftdate.setText(_data.get((int)_position).get("sent_at").toString());
				if (_data.get((int)_position).get("type").toString().equals("text")) {
					lblLeft.setText(_data.get((int)_position).get("content").toString());
					leftText.setVisibility(View.VISIBLE);
				}
				else {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("content").toString())).into(leftContentImg);
					leftImageMessage.setVisibility(View.VISIBLE);
				}
				if (_data.get((int)_position).containsKey("avatar")) {
					Glide.with(getApplicationContext()).load(Uri.parse(_data.get((int)_position).get("avatar").toString())).into(leftSenderIcon);
				}
			}
			// event
			rightImageMessage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Log.d("onClick right",attachedFileURL);

					i.putExtra("picture_url", _data.get((int)_position).get("content").toString());
					i.setClass(getApplicationContext(), ViewAssetsActivity.class);
					startActivity(i);
				}
			});
			leftImageMessage.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					Log.d("onClick left",attachedFileURL);

					i.putExtra("picture_url", _data.get((int)_position).get("content").toString());
					i.setClass(getApplicationContext(), ViewAssetsActivity.class);
					startActivity(i);
				}
			});
			leftText.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							Log.d("onActionDown left","");
							break;
						case MotionEvent.ACTION_UP:
							Log.d("onActionUp left","");
							break;
						case MotionEvent.ACTION_CANCEL:
							break;
					}
					String _fileURL ="";
					if (_data.get((int)_position).get("type").toString().equals("text")) {
						_fileURL = _data.get((int)_position).get("content").toString();
					}
					if(Patterns.WEB_URL.matcher(_fileURL).matches()) {
						Log.d("onActionDown left", _fileURL);
						Intent urlIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(_fileURL));
						startActivity(urlIntent);
					}
					return true;
				}
			});
			rightText.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							Log.d("onActionDown right","");
							break;
						case MotionEvent.ACTION_UP:
							Log.d("onActionUp right","");
							break;
						case MotionEvent.ACTION_CANCEL:
							break;
					}
					String _fileURL ="";
					if (_data.get((int)_position).get("type").toString().equals("text")) {
						_fileURL = _data.get((int)_position).get("content").toString();
					}
					if(Patterns.WEB_URL.matcher(_fileURL).matches()) {
						Log.d("onActionDown right", _fileURL);
						Intent urlIntent = new Intent(Intent.ACTION_VIEW,Uri.parse(_fileURL));
						startActivity(urlIntent);
					}
					return true;
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
