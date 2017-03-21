package com.collo.phemwaresolutions.collonetworks;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Dashboard");

        setContentView(R.layout.activity_menu);
    }


    public void gotoQcashPage(View view) {
//        Toast.makeText(this, "Quick Cash page!", LENGTH_LONG).show();
        Intent i = new Intent(this, TestThermalPrinterActivity.class);
        startActivity(i);
    }

    public void gotoUtilityPage(View view) {
        Toast.makeText(this, "Utility page!", LENGTH_LONG).show();
    }

    public void gotoTopUpPage(View view) {
        Toast.makeText(this, "Top Up page!", LENGTH_LONG).show();
    }

    public void gotoPinVendingPage(View view) {
        Toast.makeText(this, "Pin Vending page!", LENGTH_LONG).show();

    }

    public void gotoTicketsPage(View view) {
        Toast.makeText(this, "Ticket page!", LENGTH_LONG).show();
    }

    public void gotoTransferPage(View view) {
        Toast.makeText(this, "Transfer page!", LENGTH_LONG).show();
    }

    public void gotoNewAccountPage(View view) {
        Intent i = new Intent(this, NewCustomerAccountActivity.class);
        startActivity(i);
        //Toast.makeText(this, "New Account page!", LENGTH_LONG).show();
    }

    public void gotoReportsPage(View view) {
        Toast.makeText(this, "Reports page!", LENGTH_LONG).show();
    }

    public void gotoSettingsPage(View view) {
        Toast.makeText(this, "Settings page!", LENGTH_LONG).show();

    }

    public void gotoKolloPage(View view) {
//        Toast.makeText(this, "Kollo page!", LENGTH_LONG).show();
        Intent i = new Intent(this, GetCadInfoActivity.class);
        startActivity(i);
    }
}
