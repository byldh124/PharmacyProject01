package com.moondroid.pharmacyproject01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class IntroActivity extends AppCompatActivity {

    private Animation logoAnim;
    private TextView tvLogo;
    ProgressDialog progressDialog;

    String apiKey = "bSPPNxDRiwAbHHyVwugShfNzehraTySYWyV8Miumx3bO4Jh6LQLu1cYO6dE4arWvo6gB2BL%2BFCtNCyZnhsz%2FzQ%3D%3D";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //로고 애니메이션 작업
        logoAnim = AnimationUtils.loadAnimation(this, R.anim.logo_anim);
        tvLogo = findViewById(R.id.tv_logo);

        tvLogo.startAnimation(logoAnim);
        progressDialog = new ProgressDialog(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.setMessage("약국정보를 불러오고 있습니다.");
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal);
                progressDialog.show();
            }
        }, 1000);

        new Thread() {
            @Override
            public void run() {

                String address = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown"
                        + "?serviceKey=" + apiKey
                        + "&pageNo=1"
                        + "&numOfRows=3000";

                try {
                    URL url = new URL(address);
                    //해임달에게 무지개로드(InputStream) 열기
                    InputStream is = url.openStream();//바이트 스트림

                    //바이트단위로 읽으면 사용하기 불편하므로 문자단위로
                    //읽어들이는 문자스트림으로 변환!!
                    InputStreamReader isr = new InputStreamReader(is);//문자스트림

                    XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
                    XmlPullParser parser = parserFactory.newPullParser();

                    parser.setInput(isr);

                    int eventType = parser.getEventType();
                    ItemVO item = null;

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        switch (eventType) {
                            case XmlPullParser.START_DOCUMENT:
                                break;

                            case XmlPullParser.START_TAG:
                                String tagName = parser.getName();
                                if (tagName.equals("item")) {
                                    item = new ItemVO();
                                } else if (tagName.equals("dutyAddr")) {
                                    parser.next();
                                    item.setAddress(parser.getText());
                                } else if (tagName.equals("dutyName")) {
                                    parser.next();
                                    item.setName(parser.getText());
                                } else if (tagName.equals("dutyTime1c")){
                                    parser.next();
                                    item.setMonClose(parser.getText());
                                } else if (tagName.equals("dutyTime1s")){
                                    parser.next();
                                    item.setMonOpen(parser.getText());
                                } else if (tagName.equals("dutyTime2c")){
                                    parser.next();
                                    item.setTueClose(parser.getText());
                                } else if (tagName.equals("dutyTime2s")){
                                    parser.next();
                                    item.setTueOpen(parser.getText());
                                } else if (tagName.equals("dutyTime3c")){
                                    parser.next();
                                    item.setWedClose(parser.getText());
                                } else if (tagName.equals("dutyTime3s")){
                                    parser.next();
                                    item.setWedOpen(parser.getText());
                                } else if (tagName.equals("dutyTime4c")){
                                    parser.next();
                                    item.setThuClose(parser.getText());
                                } else if (tagName.equals("dutyTime4s")){
                                    parser.next();
                                    item.setThuOpen(parser.getText());
                                } else if (tagName.equals("dutyTime5c")){
                                    parser.next();
                                    item.setFriClose(parser.getText());
                                } else if (tagName.equals("dutyTime5s")){
                                    parser.next();
                                    item.setFriOpen(parser.getText());
                                } else if (tagName.equals("dutyTime6c")){
                                    parser.next();
                                    item.setSatClose(parser.getText());
                                } else if (tagName.equals("dutyTime6s")){
                                    parser.next();
                                    item.setSatOpen(parser.getText());
                                } else if (tagName.equals("dutyTime7c")){
                                    parser.next();
                                    item.setSunClose(parser.getText());
                                } else if (tagName.equals("dutyTime7s")){
                                    parser.next();
                                    item.setSunOpen(parser.getText());
                                } else if (tagName.equals("dutyTel1")) {
                                    parser.next();
                                    item.setTell(parser.getText());
                                } else if (tagName.equals("wgs84Lat")) {
                                    parser.next();
                                    item.setLat(parser.getText());
                                } else if (tagName.equals("wgs84Lon")) {
                                    parser.next();
                                    item.setLng(parser.getText());
                                    G.items.add(item);
                                }
                                break;

                            case XmlPullParser.TEXT:
                                break;

                            case XmlPullParser.END_TAG:
                                break;
                        }
                        eventType = parser.next();
                    }//while 전체


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Toast.makeText(IntroActivity.this, "1", Toast.LENGTH_SHORT).show();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Toast.makeText(IntroActivity.this, "3", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }
        }.start();
    }
}