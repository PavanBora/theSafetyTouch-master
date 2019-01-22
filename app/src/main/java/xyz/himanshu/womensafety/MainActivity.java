package xyz.himanshu.womensafety;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    db dbhandler;
    ProgressDialog pd;

    EditText Msg;


    public void onclick(View view) {
        Intent i = new Intent(this, Add_Numbers.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    public void onclick2(View view) {
        Intent i = new Intent(this, Tips.class);
        startActivity(i);
        overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure, you want to exit?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("no", null).show();
    }

    //btnsend
    public void message(View view) {
        if (dbhandler.number() == 2) {
            String phoneNo1 = dbhandler.databaseToPhoneFirst();
            String phoneNo2 = dbhandler.databaseToPhoneSecond();
            Double latitude = 0.0, longitude;
            String message = "Need Your Help. I am in danger.Please Contact me ASAP.Please inform to police immediately.";
            LocationManager mlocManager = null;
            LocationListener mlocListener;
            mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mlocListener = new MyLocationListener(this);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mlocListener);
            if (mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                latitude = MyLocationListener.latitude;
                longitude = MyLocationListener.longitude;
                String lat1=String.valueOf(latitude);
                String lat2=String.valueOf(longitude);
                message = message + "https://www.google.com/maps/search/?api=1&query="+lat1+","+lat2;
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                if (latitude == 0.0) {
                    Toast.makeText(getApplicationContext(), "Currently gps has not found your location....", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "GPS is currently off...", Toast.LENGTH_LONG).show();
            }
            //message sending




            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo1, null, message, null, null);
                //Toast.makeText(getApplicationContext(), "SMS1 sent.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
               // Toast.makeText(getApplicationContext(), "SMS1 faild, please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo2, null, message, null, null);
                //Toast.makeText(getApplicationContext(), "SMS2 sent.", Toast.LENGTH_LONG).show();
               // Toast.makeText(getApplicationContext(), "You have sent this message: "+ message, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
               // Toast.makeText(getApplicationContext(), "SMS2 faild, please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Please add two phone numbers of close ones first.....", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("The Push");
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();

//key board
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dbhandler= new db(this,null,null,1);
        Bundle numbers=getIntent().getExtras();
        if(numbers==null)
        {
            return;
        }
        String number1=numbers.getString("Number1");
        String number2=numbers.getString("Number2");
        phone_number n1=new phone_number(number1);
        phone_number n2=new phone_number(number2);
        dbhandler.addnumber1(n1);
        dbhandler.addnumber2(n2);
    }

}
