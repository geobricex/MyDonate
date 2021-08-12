package com.example.mydonate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imgOrganizationA, imgOrganizationB, imgOrganizationC, imgOrganizationD, imgOrganizationE, imgOrganizationF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        OnClickListener();
    }

    private void initialize() {
        imgOrganizationA = findViewById(R.id.imgOrganizationA);
        imgOrganizationB = findViewById(R.id.imgOrganizationB);
        imgOrganizationC = findViewById(R.id.imgOrganizationC);
        imgOrganizationD = findViewById(R.id.imgOrganizationD);
        imgOrganizationE = findViewById(R.id.imgOrganizationE);
        imgOrganizationF = findViewById(R.id.imgOrganizationF);
    }

    private void OnClickListener() {
        imgOrganizationA.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonationActivity.class);
            Bundle b = new Bundle();
            b.putString("Organization", "Aflatoun International");
            intent.putExtras(b);
            startActivity(intent);
        });
        imgOrganizationB.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonationActivity.class);
            Bundle b = new Bundle();
            b.putString("Organization", "Clean the World");
            intent.putExtras(b);
            startActivity(intent);
        });
        imgOrganizationC.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonationActivity.class);
            Bundle b = new Bundle();
            b.putString("Organization", "Organics Unlimited");
            intent.putExtras(b);
            startActivity(intent);
        });
        imgOrganizationD.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonationActivity.class);
            Bundle b = new Bundle();
            b.putString("Organization", "Heart to Heart International");
            intent.putExtras(b);
            startActivity(intent);
        });
        imgOrganizationE.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonationActivity.class);
            Bundle b = new Bundle();
            b.putString("Organization", "Sally and Dick Roberts Coyote Foundation");
            intent.putExtras(b);
            startActivity(intent);
        });
        imgOrganizationF.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DonationActivity.class);
            Bundle b = new Bundle();
            b.putString("Organization", "TOMS Shoes");
            intent.putExtras(b);
            startActivity(intent);
        });
    }
}