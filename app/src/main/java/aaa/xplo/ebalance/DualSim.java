package aaa.xplo.ebalance;

import xplo.library.ekush.MyBanglaSupport;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DualSim extends Activity implements AccelerometerListener,
		OnCheckedChangeListener {

	private static String textShareApp, aboutInfo;
	private static String appName, appVersionName, appPackage;
	// Context mContext;

	private static String APP_DEVELOPER = "Xplo";
	private static String APP_DEVELOPER_REAL_NAME = "Nasif Ahmed";
	private static String APP_DEVELOPER_EMAIL = "nasif.002@gmail.com";
	private static String APP_SUB_TITLE = "Awesome App";
	private static String APP_SHORT_LINK = null;
	
	// private static String TAG = "Dual Sim ";

	String opName = null, opDigit = null;
	String ussdCode = null;

	// PhoneCallListener pcl;
	PhoneStateListener pcl;
	TelephonyManager tpManager;

	// ToggleButton tbGP, tbRobi, tbBanglalink;

	Switch sEbGetGP, sEbGetRobi, sEbGetBanglalink;
	Switch sEbCheckGP, sEbCheckRobi, sEbCheckBanglalink;

	Button bHotKey;

	TextView tvEbGet, tvEbCheck, tvAlertMsg;
	Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dual_sim);

		tf = Typeface.createFromAsset(getAssets(), "solaimanlipinormal.ttf");
		setTitle(MyBanglaSupport.getBanglaSpnString(
				getString(R.string.bn_duel_sim), tf));

		// Check onResume Method to start accelerometer listener

		initialize();
		hotKeyAlert();

		// add PhoneStateListener
		pcl = new PhoneCallListener();
		tpManager = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		tpManager.listen(pcl, PhoneStateListener.LISTEN_CALL_STATE);

		// read sim operator
		// opDigit = tpManager.getSimOperator();
		// Toast.makeText(this, opDigit, Toast.LENGTH_SHORT).show();
		// opName = tpManager.getNetworkOperatorName();
		// Toast.makeText(this, opName, Toast.LENGTH_SHORT).show();

		bHotKey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				emergencyBalanceJobDualSim();

			}
		});

	}

	public void onAccelerationChanged(float x, float y, float z) {
		// TODO Auto-generated method stub

	}

	public void onShake(float force) {

		// Called when Motion Detected

		// Toast.makeText(getBaseContext(), "Motion detected",
		// Toast.LENGTH_SHORT)
		// .show();

		// Do your stuff here

		emergencyBalanceJobDualSim();

	}

	private void emergencyBalanceJobDualSim() {
		// TODO Auto-generated method stub

		MobileOperatorBD mobd = new MobileOperatorBD();

		boolean checkedEbGet = sEbGetGP.isChecked() || sEbGetRobi.isChecked()
				|| sEbGetBanglalink.isChecked();

		boolean checkedEbCheck = sEbCheckGP.isChecked()
				|| sEbCheckRobi.isChecked() || sEbCheckBanglalink.isChecked();

		if (opDigit != null && checkedEbGet) {

			ussdCode = mobd.getEbGettingUssd(opDigit);

			dial(ussdCode);

		} else if (opDigit != null && checkedEbCheck) {

			ussdCode = mobd.getEbCheckingUssd(opDigit);

			dial(ussdCode);

		} else {

			Toast.makeText(
					getBaseContext(),
					MyBanglaSupport.getBanglaSpnString(
							getString(R.string.bn_select_alert), tf),
					Toast.LENGTH_SHORT).show();

		}

	}

	private void dial(String no) {
		// TODO Auto-generated method stub

		// Toast.makeText(getApplicationContext(), no,
		// Toast.LENGTH_SHORT).show();

		if (no != null) {

			String number = "tel:" + no;
			Intent callIntent = new Intent(Intent.ACTION_CALL,
					Uri.parse(number));
			// Intent callIntent = new Intent(Intent.ACTION_DIAL,
			// Uri.parse(number));
			startActivity(callIntent);

		} else {

			String userWarning = opName + " Don't Provide Emergency Balance";

			Toast.makeText(getApplicationContext(), userWarning,
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub

		// Toast.makeText(getBaseContext(),
		// TAG + "onResume Accelerometer Started", Toast.LENGTH_SHORT)
		// .show();

		// Check device supported Accelerometer senssor or not
		if (AccelerometerManager.isSupported(this)) {

			// Start Accelerometer Listening
			AccelerometerManager.startListening(this);
		}

		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		// Check device supported Accelerometer senssor or not
		if (AccelerometerManager.isListening()) {

			// Start Accelerometer Listening
			AccelerometerManager.stopListening();

			// Toast.makeText(getBaseContext(),
			// TAG + "onPuse Accelerometer Stoped", Toast.LENGTH_SHORT)
			// .show();
		}

		super.onPause();
	}

	// @Override
	// protected void onStop() {
	//
	// // Check device supported Accelerometer senssor or not
	// if (AccelerometerManager.isListening()) {
	//
	// // Start Accelerometer Listening
	// AccelerometerManager.stopListening();
	//
	// Toast.makeText(getBaseContext(),
	// TAG + "onStop Accelerometer Stoped", Toast.LENGTH_SHORT)
	// .show();
	// }
	//
	// // TODO Auto-generated method stub
	// super.onStop();
	// }

	// @Override
	// protected void onDestroy() {
	//
	// Log.i("Sensor", "Service  distroy");
	//
	// // Check device supported Accelerometer senssor or not
	// if (AccelerometerManager.isListening()) {
	//
	// // Start Accelerometer Listening
	// AccelerometerManager.stopListening();
	//
	// Toast.makeText(getBaseContext(),
	// TAG + "onDestroy Accelerometer Stoped", Toast.LENGTH_SHORT)
	// .show();
	// }
	//
	// finish();
	//
	// // TODO Auto-generated method stub
	// super.onDestroy();
	// }

	private void initialize() {
		// TODO Auto-generated method stub

		tf = Typeface.createFromAsset(getAssets(), "solaimanlipinormal.ttf");

		tvEbGet = (TextView) findViewById(R.id.tvEbGet);
		tvEbCheck = (TextView) findViewById(R.id.tvEbCheck);
		tvAlertMsg = (TextView) findViewById(R.id.tvAlertMsg);

		tvEbGet.setTypeface(tf);
		tvEbCheck.setTypeface(tf);
		tvAlertMsg.setTypeface(tf);

		// tvEbGet.setText(getString(R.string.bn_get_eb));
		// tvEbCheck.setText(getString(R.string.bn_check_eb));
		// tvAlertMsg.setText(getString(R.string.bn_select_op));

		sEbGetGP = (Switch) findViewById(R.id.sEbGP);
		sEbGetRobi = (Switch) findViewById(R.id.sEbRobi);
		sEbGetBanglalink = (Switch) findViewById(R.id.sEbBanglalink);

		sEbCheckGP = (Switch) findViewById(R.id.sEbCheckGP);
		sEbCheckRobi = (Switch) findViewById(R.id.sEbCheckRobi);
		sEbCheckBanglalink = (Switch) findViewById(R.id.sEbCheckBanglalink);

		sEbGetGP.setOnCheckedChangeListener(this);
		sEbGetRobi.setOnCheckedChangeListener(this);
		sEbGetBanglalink.setOnCheckedChangeListener(this);

		sEbCheckGP.setOnCheckedChangeListener(this);
		sEbCheckRobi.setOnCheckedChangeListener(this);
		sEbCheckBanglalink.setOnCheckedChangeListener(this);

		sEbGetGP.setTypeface(tf);
		sEbGetRobi.setTypeface(tf);
		sEbGetBanglalink.setTypeface(tf);

		sEbCheckGP.setTypeface(tf);
		sEbCheckRobi.setTypeface(tf);
		sEbCheckBanglalink.setTypeface(tf);

		int apiVersion = android.os.Build.VERSION.SDK_INT;

		if (apiVersion < 16) {

			tvEbGet.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_get_eb)));
			tvEbCheck.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_check_eb)));
			tvAlertMsg.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_que_want)));

			sEbGetGP.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_gp)));
			sEbGetRobi.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_robi)));
			sEbGetBanglalink.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_banglalink)));

			sEbCheckGP.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_gp)));
			sEbCheckRobi.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_robi)));
			sEbCheckBanglalink.setText(MyBanglaSupport
					.getBanglaString(getString(R.string.bn_banglalink)));
		}

		bHotKey = (Button) findViewById(R.id.bHotKey);

		// linkArray = getResources().getStringArray(R.array.link_list);
		// titleArray = getResources().getStringArray(R.array.link_title);
		// titleArray = getResources().getResourceEntryName(R.array.link_list);

		// need this when default context not exist
		// mContext = getApplicationContext();
		// APP_NAME = mContext.getString(R.string.app_name);
		// APP_PNAME = mContext.getPackageName();

		appName = getString(R.string.app_name);
		appPackage = getPackageName();

		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			// APP_VERSION_CODE = pInfo.versionCode;
			appVersionName = pInfo.versionName;

		} catch (Exception e) {

		}

		// String version = pInfo.versionName;


		String appLink;

		if (APP_SHORT_LINK != null) {
			appLink = APP_SHORT_LINK;
		} else {
			appLink = "https://play.google.com/store/apps/details?id="
					+ appPackage;
		}


		aboutInfo = appName + "\nVersion : " + appVersionName
				+ "\nCopyright © 2014, Xplo" + "\nDeveloper : "
				+ APP_DEVELOPER_REAL_NAME + "\nContact : "
				+ APP_DEVELOPER_EMAIL + "\n";

		textShareApp = appName + "\n" + APP_SUB_TITLE + "\n" + appLink;

	}

	public void hotKeyAlert() {

		SharedPreferences sp = getApplicationContext().getSharedPreferences(
				"MyPref", MODE_PRIVATE);
		final Editor editor = sp.edit();

		Boolean hotKeyAlert = sp.getBoolean("hotKeyAlert", true);

		if (hotKeyAlert) {

			AlertDialog.Builder ab = new AlertDialog.Builder(DualSim.this);

			ab.setTitle(MyBanglaSupport.getBanglaSpnString(getString(R.string.bn_alert), tf));
			ab.setMessage(MyBanglaSupport.getBanglaSpnString(getString(R.string.bn_motion_sensor_alert), tf));
			ab.setCancelable(false);
			ab.setPositiveButton(MyBanglaSupport.getBanglaSpnString(getString(R.string.bn_got_it), tf),
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {

							editor.putBoolean("hotKeyAlert", false);
							editor.commit();

						}
					});

			ab.show();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.app_menu_bangla, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.mShare:

			String text = textShareApp;

			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, text);
			startActivity(Intent
					.createChooser(intent, "Share this application"));

			return true;

		case R.id.mAbout:

			try {

				AlertDialog.Builder ab = new AlertDialog.Builder(DualSim.this);

				ab.setTitle("About");
				ab.setMessage(aboutInfo);
				ab.setCancelable(false);
				ab.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

							}
						});

				ab.show();

			} catch (Exception e) {
			}

			return true;

		case R.id.mFeedback:

			try {
				Intent Email = new Intent(Intent.ACTION_SEND);
				// Email.setType("text/email");
				Email.setType("plain/text");
				// Email.setType("message/rfc822");

				Email.putExtra(Intent.EXTRA_EMAIL,
						new String[] { "nasif.002@gmail.com" });
				Email.putExtra(Intent.EXTRA_SUBJECT, "Feedback " + appName);
				Email.putExtra(Intent.EXTRA_TEXT, "Dear Developer,\n..." + "");
				startActivity(Intent.createChooser(Email, "Send Feedback"));

			} catch (Exception e) {
			}

			return true;

		case R.id.mRateUs:

			try {

				// AppRater.showRateDialog(this, null);
				AppRaterMine.showRateDialog(this, null);

			} catch (Exception e) {
			}

			return true;

		case R.id.mMoreApps:
			try {

				Uri uri = Uri.parse("market://search?q=pub:" + APP_DEVELOPER);
				Intent moreApps = new Intent(Intent.ACTION_VIEW, uri);
				// intent.setAction(Intent.ACTION_VIEW);
				startActivity(moreApps);

			} catch (android.content.ActivityNotFoundException anfe) {

				Uri uri = Uri
						.parse("http://play.google.com/store/apps/developer?id="
								+ APP_DEVELOPER);
				Intent moreApps = new Intent(Intent.ACTION_VIEW, uri);
				// intent.setAction(Intent.ACTION_VIEW);
				startActivity(moreApps);

			}
			return true;

		case R.id.mSetting:

			try {

				AlertDialog.Builder ab = new AlertDialog.Builder(DualSim.this);

				ab.setTitle(MyBanglaSupport.getBanglaSpnString(
						getString(R.string.bn_settings), tf));
				ab.setMessage(MyBanglaSupport.getBanglaSpnString(
						getString(R.string.bn_que_sim_type), tf));

				ab.setCancelable(false);
				ab.setPositiveButton(MyBanglaSupport.getBanglaSpnString(
						getString(R.string.bn_single_sim), tf),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								SharedPreferences sp = getApplicationContext()
										.getSharedPreferences("MyPref",
												MODE_PRIVATE);
								Editor editor = sp.edit();

								editor.putBoolean("singleSim", true);
								editor.putBoolean("dualSim", false);
								editor.putBoolean("configDone", true);
								editor.commit();

								Intent intent = new Intent(DualSim.this,
										SingleSim.class);
								startActivity(intent);
								finish();
							}
						});

				ab.setNegativeButton(MyBanglaSupport.getBanglaSpnString(
						getString(R.string.bn_duel_sim), tf),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface arg0, int arg1) {

								SharedPreferences sp = getApplicationContext()
										.getSharedPreferences("MyPref",
												MODE_PRIVATE);
								Editor editor = sp.edit();
								editor.putBoolean("singleSim", false);
								editor.putBoolean("dualSim", true);
								editor.putBoolean("configDone", true);
								editor.commit();

								// Intent intent = new Intent(DualSim.this,
								// DualSim.class);
								// startActivity(intent);
								// finish();

							}
						});

				ab.show();

			} catch (Exception e) {
			}

			return true;

		default:
			return true;
		}

		// return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		switch (buttonView.getId()) {
		case R.id.sEbGP:

			if (isChecked) {

				opDigit = MobileOperatorBD.getOpdGP();

				sEbGetRobi.setChecked(false);
				sEbGetBanglalink.setChecked(false);

				sEbCheckGP.setChecked(false);
				sEbCheckRobi.setChecked(false);
				sEbCheckBanglalink.setChecked(false);
			}

			break;

		case R.id.sEbRobi:

			if (isChecked) {

				opDigit = MobileOperatorBD.getOpdRobi();

				sEbGetGP.setChecked(false);
				sEbGetBanglalink.setChecked(false);

				sEbCheckGP.setChecked(false);
				sEbCheckRobi.setChecked(false);
				sEbCheckBanglalink.setChecked(false);
			}

			break;

		case R.id.sEbBanglalink:

			if (isChecked) {

				opDigit = MobileOperatorBD.getOpdBanglalink();

				sEbGetRobi.setChecked(false);
				sEbGetGP.setChecked(false);

				sEbCheckGP.setChecked(false);
				sEbCheckRobi.setChecked(false);
				sEbCheckBanglalink.setChecked(false);
			}

			break;
		case R.id.sEbCheckGP:

			if (isChecked) {

				opDigit = MobileOperatorBD.getOpdGP();

				sEbCheckRobi.setChecked(false);
				sEbCheckBanglalink.setChecked(false);

				sEbGetGP.setChecked(false);
				sEbGetRobi.setChecked(false);
				sEbGetBanglalink.setChecked(false);
			}

			break;

		case R.id.sEbCheckRobi:

			if (isChecked) {

				opDigit = MobileOperatorBD.getOpdRobi();

				sEbCheckGP.setChecked(false);
				sEbCheckBanglalink.setChecked(false);

				sEbGetGP.setChecked(false);
				sEbGetRobi.setChecked(false);
				sEbGetBanglalink.setChecked(false);

			}

			break;

		case R.id.sEbCheckBanglalink:

			if (isChecked) {

				opDigit = MobileOperatorBD.getOpdBanglalink();

				sEbCheckRobi.setChecked(false);
				sEbCheckGP.setChecked(false);

				sEbGetGP.setChecked(false);
				sEbGetRobi.setChecked(false);
				sEbGetBanglalink.setChecked(false);
			}

			break;

		default:
			break;
		}

		if (sEbGetRobi.isChecked()) {

			tvAlertMsg.setText(MyBanglaSupport.getBanglaSpnString(
					getString(R.string.bn_shake), tf));
			Toast.makeText(
					getApplicationContext(),
					MyBanglaSupport.getBanglaSpnString(
							getString(R.string.bn_robi_alert), tf),
					Toast.LENGTH_LONG).show();

		} else if (isChecked) {

			tvAlertMsg.setText(MyBanglaSupport.getBanglaSpnString(
					getString(R.string.bn_shake), tf));
		} else {
			tvAlertMsg.setText(MyBanglaSupport.getBanglaSpnString(
					getString(R.string.bn_select_op), tf));
		}

	}

	private class PhoneCallListener extends PhoneStateListener {

		private boolean isPhoneCalling = false;

		String LOG_TAG = "LOGGING 123";

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {

			if (TelephonyManager.CALL_STATE_RINGING == state) {
				// phone ringing
				Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
			}

			if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
				// active
				Log.i(LOG_TAG, "OFFHOOK");

				isPhoneCalling = true;
			}

			if (TelephonyManager.CALL_STATE_IDLE == state) {
				// run when class initial and phone call ended, need detect flag
				// from CALL_STATE_OFFHOOK
				Log.i(LOG_TAG, "IDLE");

				if (isPhoneCalling) {

					Log.i(LOG_TAG, "restart app");

					// restart app
					Intent i = getBaseContext().getPackageManager()
							.getLaunchIntentForPackage(
									getBaseContext().getPackageName());
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);

					isPhoneCalling = false;
				}

			}
		}
	}
}