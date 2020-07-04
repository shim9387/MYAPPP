package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;
    private Frag4 frag4;
    private final int SMS_PERMISSION =1;
    String Number="01076975600";
    String Sms="다시 연락줘";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int permssionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permssionCheck == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(), "SMS 수신권한 있음", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "SMS 수신권한 없음", Toast.LENGTH_SHORT).show();

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
            {

                Toast.makeText(getApplicationContext(), "SMS권한이 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS}, SMS_PERMISSION);
            }
            else{ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.SEND_SMS}, SMS_PERMISSION);
            }
        }
        int permissionCheck2= ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE);
        if(permissionCheck2 == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "CALL 수신권한 있음", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "CALL 수신권한 없음", Toast.LENGTH_SHORT).show();

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)){

                Toast.makeText(getApplicationContext(), "CALL권한 필요합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE}, SMS_PERMISSION);
            }
            else{ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission.CALL_PHONE}, SMS_PERMISSION);
            }
        }

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {



                switch (menuItem.getItemId())
                {
                    case R.id.action_work:
                        setFrag(0);
                        Toast.makeText(getApplicationContext(), "전화를 걸겠습니다.", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+Number));
                        //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
                        startActivity(intent);
                        break;
                    case R.id.action_message:
                        setFrag(1);
                        HiSendMessage(Number,Sms);
                        Toast.makeText(getApplicationContext(), "메세지를 보냅니다.", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.action_settings:
                        setFrag(2);
                        break;

                    case R.id.action_security:
                        setFrag(3 );
                        break;
                }
                return true;
            }
        });
        frag1 = new Frag1();
        frag2 = new Frag2();
        frag3 = new Frag3();
        frag4 = new Frag4();
        setFrag(0);


    }

    private void setFrag(int n){
    fm = getSupportFragmentManager();
    ft = fm.beginTransaction();
    switch (n){
        case 0:
        ft.replace(R.id.frame, frag1);
        ft.commit();
        break;
        case 1:
        ft.replace(R.id.frame, frag2);
        ft.commit();
        break;
        case 2:
            ft.replace(R.id.frame, frag3);
            ft.commit();
            break;
        case 3:
            ft.replace(R.id.frame, frag4);
            ft.commit();
            break;

    }


    }
    private void HiSendMessage(String phoneNo, String sms )
    {

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNo, null, sms, null, null);
    }
}