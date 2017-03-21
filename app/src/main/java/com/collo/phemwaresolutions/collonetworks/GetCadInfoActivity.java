package com.collo.phemwaresolutions.collonetworks;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.weipass.pos.sdk.MagneticReader;
import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;

public class GetCadInfoActivity extends AppCompatActivity {

    private WeiposImpl weiposImpl;
    private MagneticReader mMagneticReader;
    private TextView txtCard;
    private TextView txtExp;
    private TextView txtPan;
    private TextView txtServCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a support ActionBar corresponding to this toolbar
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Get Card Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        setContentView(R.layout.activity_get_cad_info);

        txtCard = (TextView) findViewById(R.id.txtCardDetails);
        txtExp = (TextView) findViewById(R.id.txtExpire);
        txtPan = (TextView) findViewById(R.id.txtPanNumber);
        txtServCode = (TextView) findViewById(R.id.txtServiceCode);


        weiposImpl.as().init(GetCadInfoActivity.this, new Weipos.OnInitListener() {
            @Override
            public void onInitOk() {

                try {
                    mMagneticReader = WeiposImpl.as().openMagneticReader();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onError(String message) {
                // TODO Auto-generated method stub
                final String msg = message;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(GetCadInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }

    public void getMagneticReaderInfo(View view) {
        if (mMagneticReader == null) {
            Toast.makeText(GetCadInfoActivity.this, "Initialize magnetic card sdk failed", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        // byte[] cardByte = mMagneticReader.readCard();

        String decodeData = mMagneticReader.getCardDecodeData();
        txtCard.setText("Card Details = " + decodeData);
        //split decodeData
        String newDecodeData[] = decodeData.split("=");
        txtPan.setText("PAN = " + newDecodeData[0].toString());
        String newDecodeData2 = newDecodeData[1].toString();
        String expDate = newDecodeData2.substring(0, 4);
        txtExp.setText("Exp. Date(YY/MM) = " + new StringBuffer(expDate).insert(2, "/"));
        String servCode = newDecodeData2.substring(5, newDecodeData2.length());
        txtServCode.setText("Service Code = " + servCode);

        if (decodeData != null && decodeData.length() != 0) {
            Toast.makeText(GetCadInfoActivity.this, "Acquire magnetic card data successfully!",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(GetCadInfoActivity.this, "Acquire magnetic card data unsuccessfully, please make sure you already swiped card",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeiposImpl.as().destroy();
    }
}
