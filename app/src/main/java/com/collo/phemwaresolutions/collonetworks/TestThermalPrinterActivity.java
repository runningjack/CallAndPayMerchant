package com.collo.phemwaresolutions.collonetworks;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import cn.weipass.pos.sdk.IPrint;
import cn.weipass.pos.sdk.Printer;
import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;

public class TestThermalPrinterActivity extends AppCompatActivity {

    private WeiposImpl weiposImpl;
    private ProgressDialog pd;
    private Printer printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Test Thermal Printer");
        getSupportActionBar().setHomeButtonEnabled(true);

        setContentView(R.layout.activity_test_thermal_printer);

        weiposImpl.as().init(TestThermalPrinterActivity.this, new Weipos.OnInitListener() {
            @Override
            public void onInitOk() {

                try {
                    printer = WeiposImpl.as().openPrinter();
                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                // TODO Auto-generated method stub
                final String msg = message;
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(TestThermalPrinterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeiposImpl.as().destroy();
    }

    public void doTestPrinter(View view) {
        if (printer == null) {
            Toast.makeText(TestThermalPrinterActivity.this, "Still not initialize print sdk, please try again later",
                    Toast.LENGTH_SHORT).show();
        }
        printer.setOnEventListener(new IPrint.OnEventListener() {

            @Override
            public void onEvent(final int what, final String in) {
                // TODO Auto-generated method stub
                runOnUiThread(new Runnable() {
                    public void run() {
                        switch (what) {
                            case Printer.EVENT_OK:
                                Toast.makeText(TestThermalPrinterActivity.this, in, Toast.LENGTH_LONG).show();
                                break;
                            case Printer.EVENT_CONNECTED:
                                break;
                            case Printer.EVENT_CONNECT_FAILD:
                                Toast.makeText(TestThermalPrinterActivity.this, "Connect to printer failed ", Toast.LENGTH_LONG).show();
                                break;
                            case Printer.EVENT_NO_PAPER:
                                Toast.makeText(TestThermalPrinterActivity.this, "Printer lack paper", Toast.LENGTH_LONG).show();
                                break;
                            case Printer.EVENT_PAPER_JAM:
                                Toast.makeText(TestThermalPrinterActivity.this, "Printer paper jam", Toast.LENGTH_LONG).show();
                                break;
                            case Printer.EVENT_UNKNOW:
                                Toast.makeText(TestThermalPrinterActivity.this, "Print unknown error", Toast.LENGTH_LONG).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
        String sb = new String();
        String companyName, customerName, customerProduct, customerContributionAmount, customerDateTime, underLineString, separator, thankYou;
        companyName = "CALL AND PAY";
        separator = " : ";
        underLineString = "-------------------------------";
        customerName = "Name" + separator + "Femi Adesanya";
        customerProduct = "Product" + separator + "Daily Esusu";
        customerContributionAmount = "Amount" + separator + "NGN1000";
        customerDateTime = "Date Time" + separator + "18/03/18 14:30";
        thankYou = "Thank You!\n";
        sb = companyName + "_" + customerName + "_" + customerProduct + "_" + customerContributionAmount + "_" + customerDateTime;

        //Bitmap bmLogo = BitmapFactory.decodeFile("collologo.png");


        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        printer.printImage(bitmap2Bytes(bMap), Printer.Gravity.CENTER);
        printer.printText(companyName, Printer.FontFamily.SONG, Printer.FontSize.EXTRALARGE, Printer.FontStyle.NORMAL, Printer.Gravity.CENTER);
        printer.printText(underLineString, Printer.FontFamily.SONG, Printer.FontSize.SMALL, Printer.FontStyle.NORMAL, Printer.Gravity.CENTER);
        printer.printText(customerName, Printer.FontFamily.SONG, Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL, Printer.Gravity.LEFT);
        printer.printText(customerProduct, Printer.FontFamily.SONG, Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL, Printer.Gravity.LEFT);
        printer.printText(customerContributionAmount, Printer.FontFamily.SONG, Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL, Printer.Gravity.LEFT);
        printer.printText(customerDateTime, Printer.FontFamily.SONG, Printer.FontSize.MEDIUM, Printer.FontStyle.NORMAL, Printer.Gravity.LEFT);
        printer.printText(underLineString, Printer.FontFamily.SONG, Printer.FontSize.SMALL, Printer.FontStyle.NORMAL, Printer.Gravity.CENTER);
        printer.printText(thankYou, Printer.FontFamily.SONG, Printer.FontSize.EXTRALARGE, Printer.FontStyle.NORMAL, Printer.Gravity.CENTER);

        printer.printQrCode(sb, 5, Printer.Gravity.CENTER);

        printer.printText(underLineString, Printer.FontFamily.SONG, Printer.FontSize.SMALL, Printer.FontStyle.NORMAL, Printer.Gravity.CENTER);
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    // Bitmap → byte[]
    public static byte[] bitmap2Bytes(Bitmap bm) {
        Bitmap nBm = toGrayscale(bm);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        nBm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }
}
