package aaa.xplo.ebalance;

import xplo.library.ekush.MyBanglaSupport;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;

public class SimConfig extends Activity {

	Typeface tf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sim_config);

		tf = Typeface.createFromAsset(getAssets(), "solaimanlipinormal.ttf");
		setTitle(MyBanglaSupport.getBanglaSpnString(
				getString(R.string.bn_settings), tf));

		simConfig();

	}

	private void simConfig() {
		// TODO Auto-generated method stub

		try {

			AlertDialog.Builder ab = new AlertDialog.Builder(SimConfig.this);

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

							Intent intent = new Intent(SimConfig.this,
									SingleSim.class);
							startActivity(intent);
							finish();
						}
					});

			ab.setNegativeButton(MyBanglaSupport.getBanglaSpnString(getString(R.string.bn_duel_sim), tf),
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

							Intent intent = new Intent(SimConfig.this,
									DualSim.class);
							startActivity(intent);
							finish();

						}
					});

			ab.show();

		} catch (Exception e) {
		}

	}

	@Override
	protected void onDestroy() {

		finish();

		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
