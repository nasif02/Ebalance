package aaa.xplo.ebalance;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	MediaPlayer mp;
	TextView tvAppTitle, tvVersionName;
	String appTitle, versionName;
	String TAG = "MT_MASS";
	String manufacturer;
	Typeface font;

	ImageView iv1;

	// SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);
		// actionBar.setBackgroundDrawable(new ColorDrawable(Color
		// .parseColor("#6699FF")));
		// actionBar.setBackgroundDrawable(new ColorDrawable(Color.GREEN));

		setContentView(R.layout.activity_main);

		initialize();

		playAnimation();

		try {
			// mp = MediaPlayer.create(getApplicationContext(),
			// R.raw.startup);
			// mp = MediaPlayer.create(SplashScreen.this, R.raw.startup);

			// mp.reset();
			mp = MediaPlayer.create(getBaseContext(), R.raw.startup); // must be
																		// mp3
			// mp.prepareAsync();
			mp.start();
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			Log.e(TAG, "Startup Sound Didn't start properly");
		}

		final boolean firstOpen = isFirstOpen();

		SharedPreferences sp = getApplicationContext().getSharedPreferences(
				"MyPref", MODE_PRIVATE);
		// Editor editor = sp.edit();

		final boolean singleSim = sp.getBoolean("singleSim", false);
		final boolean dualSim = sp.getBoolean("dualSim", false);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (Exception e) {
					Log.e("TAG", " Sleep Didn't Work properly");
					e.printStackTrace();

				} finally {

					if (firstOpen) {
						Intent intent = new Intent(MainActivity.this,
								SimConfig.class);
						startActivity(intent);

					} else if (singleSim) {

						Intent intent = new Intent(MainActivity.this,
								SingleSim.class);
						startActivity(intent);

					} else if (dualSim) {

						Intent intent = new Intent(MainActivity.this,
								DualSim.class);
						startActivity(intent);
					}

				}
			};
		};

		timer.start();

	}

	private void playAnimation() {
		// TODO Auto-generated method stub

		Animation aShake1 = AnimationUtils.loadAnimation(this, R.anim.shake1);
		iv1.startAnimation(aShake1);

	}

	private void initialize() {
		// TODO Auto-generated method stub

		// tvAppTitle = (TextView) findViewById(R.id.tvAppTitle);
		tvVersionName = (TextView) findViewById(R.id.tvVersionName);
		iv1 = (ImageView) findViewById(R.id.imageView1);
		// font = Typeface.createFromAsset(getAssets(),
		// "solaimanlipinormal.ttf");

		// appTitle = getString(R.string.app_name);

		try {
			PackageInfo pInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);

			versionName = pInfo.versionName;
			// APP_VERSION_CODE = pInfo.versionCode;

		} catch (Exception e) {
			// TODO: handle exception
		}

		// tvVersionName.setTypeface(font);
		// tvAppTitle.setTypeface(font);

		// tvAppTitle.setText(appTitle);
		tvVersionName.setText("Version " + versionName);

	}

	private boolean isFirstOpen() {
		// TODO Auto-generated method stub

		SharedPreferences sp = getApplicationContext().getSharedPreferences(
				"MyPref", MODE_PRIVATE);
		Editor editor = sp.edit();

		// boolean firstOpen = !(sp.contains("firstOpen"));

		boolean firstOpen = sp.getBoolean("firstOpen", true);
		boolean configDone = sp.getBoolean("configDone", false);

		if (firstOpen) {

			editor.putBoolean("firstOpen", false);
			editor.commit();

			Toast.makeText(getApplicationContext(),
					"Please Configure Your sim type",
					Toast.LENGTH_SHORT).show();

			return true;

		} else if (!configDone) {

			Toast.makeText(getApplicationContext(),
					"Sim tyep not configured. Please Configure Your sim type",
					Toast.LENGTH_SHORT).show();

			return true;
		}

		return false;

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub

		if (mp != null) {
			mp.release();
		}

		finish();
		super.onPause();
	}

}
