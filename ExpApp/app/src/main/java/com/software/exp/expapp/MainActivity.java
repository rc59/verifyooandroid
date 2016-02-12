package com.software.exp.expapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.software.exp.expapp.Activities.ErrorPage;
import com.software.exp.expapp.Activities.UserDetailsName;
import com.software.exp.expapp.Logic.ApiUserExists;
import com.software.exp.expapp.Logic.ApiUserExistsByName;
import com.software.exp.expapp.Logic.Consts;
import com.software.exp.expapp.Logic.Tools;


public class MainActivity extends Activity {

    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMessagingService();
        init();
    }

    private void initMessagingService() {
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(Consts.SENT_TOKEN_TO_SERVER, false);
                if (!sentToken) {
                    Intent intentError = new Intent(getApplicationContext(), ErrorPage.class);
                    startActivity(intentError);
                }
            }
        };

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intentService = new Intent(getApplicationContext(), RegistrationIntentService.class);
            startService(intentService);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Consts.REGISTRATION_COMPLETE));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void init() {
        setTitle(getString(R.string.mainPage));

        if (Tools.Username.length() > 0) {
            new ApiUserExistsByName(getApplicationContext()).isUserExistsByName(Tools.Username);
        }
        else {
            boolean isByName = true;

            if (isByName) {
                Intent intent = new Intent(getApplicationContext(), UserDetailsName.class);
                startActivity(intent);
            }
            else {
                checkIfUserExists();
            }
        }
    }

    private void checkIfUserExists() {
        String serviceName = Context.TELEPHONY_SERVICE;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(serviceName);

        String deviceId = telephonyManager.getDeviceId();
        isDeviceIdExists(deviceId);
    }

    private void isDeviceIdExists(String deviceId) {
        new ApiUserExists(getApplicationContext()).isUserExists(deviceId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            return true;
        }

        if (id == R.id.action_exit) {
            int p = android.os.Process.myPid();
            android.os.Process.killProcess(p);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
