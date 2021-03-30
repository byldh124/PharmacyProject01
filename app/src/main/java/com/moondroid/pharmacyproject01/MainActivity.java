package com.moondroid.pharmacyproject01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener {
    private final int REQUEST_CODE_FOR_PERMISSION = 3;
    private final int REQUEST_CODE_FOR_INTENT = 2;
    private MapView mapView;
    private FusedLocationSource fusedLocationSource;
    private final int PERMISSION_REQUEST_CODE = 5;
    private Marker marker;
    private NaverMap mNaverMap;
    private Toolbar toolbar;
    private MaterialCardView detailLayout;
    private Animation layoutAnim;
    private TextView itemName, itemAddress, itemTell, itemOpenTime, itemOpenTimeSat, itemOpenTimeSun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(this, permissions[1]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_FOR_PERMISSION);
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        fusedLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        detailLayout = findViewById(R.id.layout_detail);
        itemName = findViewById(R.id.item_name);
        itemAddress = findViewById(R.id.item_address);
        itemTell = findViewById(R.id.item_tell);
        itemOpenTime = findViewById(R.id.item_open_time);
        itemOpenTimeSat = findViewById(R.id.item_open_time_sat);
        itemOpenTimeSun = findViewById(R.id.item_open_time_sun);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_search) {
            startActivityForResult(new Intent(this, SearchActivity.class), REQUEST_CODE_FOR_INTENT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (requestCode == REQUEST_CODE_FOR_INTENT) {
            ItemVO itemVO = new Gson().fromJson(data.getStringExtra("item"), ItemVO.class);
            loadInfo(itemVO);
            if (mNaverMap != null) {
                CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.parseDouble(itemVO.getLat()), Double.parseDouble(itemVO.getLng())), 16, 0, 0);
                mNaverMap.setCameraPosition(cameraPosition);
            }
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setMapType(NaverMap.MapType.Basic);

        //건물 표시
//        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, false);
//        naverMap.setIndoorEnabled(false);

        //카메라 세팅
        CameraPosition cameraPosition = new CameraPosition(new LatLng(37.5670135, 126.9783740), 16, 0, 0);
        naverMap.setCameraPosition(cameraPosition);
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(fusedLocationSource);
        UiSettings uiSettings = mNaverMap.getUiSettings();
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        for (int i = 0; i < G.items.size(); i++) {
            Marker marker = new Marker(new LatLng(Double.parseDouble(G.items.get(i).getLat()), Double.parseDouble(G.items.get(i).getLng())));
            marker.setIcon(OverlayImage.fromResource(R.drawable.maker_icon));
            marker.setTag(G.items.get(i));
            marker.setCaptionText(G.items.get(i).getName());
            marker.setOnClickListener(this);
            marker.setMap(mNaverMap);
        }

        //네이버맵 UI 설정. 로고 클릭은 반드시 true 값으로 해야함(정책사항)
        uiSettings.setCompassEnabled(true);
        uiSettings.setLocationButtonEnabled(true);
        uiSettings.setLogoClickEnabled(true);
        uiSettings.setScrollGesturesEnabled(true);
        uiSettings.setZoomControlEnabled(true);
        uiSettings.setRotateGesturesEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker) {

            ItemVO itemVO = (ItemVO) overlay.getTag();
            loadInfo(itemVO);
//            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//            intent.putExtra("item", new Gson().toJson(overlay.getTag()));
//            startActivity(intent);
//            return true;
        }
        return false;
    }

    public void clickHide(View view) {
        detailLayout.setVisibility(View.GONE);
    }

    @SuppressLint("SetTextI18n")
    public void loadInfo(ItemVO itemVO) {
        detailLayout.setVisibility(View.VISIBLE);
        layoutAnim = AnimationUtils.loadAnimation(this, R.anim.layout_view);
        detailLayout.setAnimation(layoutAnim);
        itemName.setText(itemVO.getName());
        itemAddress.setText("주소 : " + itemVO.getAddress());
        itemTell.setText("tel. " + itemVO.getTell());

        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
            case 2:
            case 7:
                if (itemVO.getMonOpen() != null && itemVO.getMonClose() != null) {
                    itemOpenTime.setText("평  일 : " + itemVO.getMonOpen() + " - " + itemVO.getMonClose());
                } else {
                    itemOpenTime.setText("평  일 : - ");
                }
                if (itemVO.getSatOpen() != null && itemVO.getSatClose() != null) {
                    itemOpenTimeSat.setText("토요일 : " + itemVO.getSatOpen() + " - " + itemVO.getSatClose());
                } else {
                    itemOpenTimeSat.setText("토요일 : - ");
                }
                if (itemVO.getSunOpen() != null && itemVO.getSunClose() != null) {
                    itemOpenTimeSun.setText("일요일 : " + itemVO.getSunOpen() + " - " + itemVO.getSunClose());
                }
                break;
            case 3:
                if (itemVO.getTueOpen() != null && itemVO.getTueClose() != null) {
                    itemOpenTime.setText("평  일 : " + itemVO.getTueOpen() + " - " + itemVO.getTueClose());
                } else {
                    itemOpenTime.setText("평  일 : - ");
                }
                if (itemVO.getSatOpen() != null && itemVO.getSatClose() != null) {
                    itemOpenTimeSat.setText("토요일 : " + itemVO.getSatOpen() + " - " + itemVO.getSatClose());
                } else {
                    itemOpenTimeSat.setText("토요일 : - ");
                }
                if (itemVO.getSunOpen() != null && itemVO.getSunClose() != null) {
                    itemOpenTimeSun.setText("일요일 : " + itemVO.getSunOpen() + " - " + itemVO.getSunClose());
                }
                break;
            case 4:
                if (itemVO.getWedOpen() != null && itemVO.getWedClose() != null) {
                    itemOpenTime.setText("평  일 : " + itemVO.getWedOpen() + " - " + itemVO.getWedClose());
                } else {
                    itemOpenTime.setText("평  일 : - ");
                }
                if (itemVO.getSatOpen() != null && itemVO.getSatClose() != null) {
                    itemOpenTimeSat.setText("토요일 : " + itemVO.getSatOpen() + " - " + itemVO.getSatClose());
                } else {
                    itemOpenTimeSat.setText("토요일 : - ");
                }
                if (itemVO.getSunOpen() != null && itemVO.getSunClose() != null) {
                    itemOpenTimeSun.setText("일요일 : " + itemVO.getSunOpen() + " - " + itemVO.getSunClose());
                }
                break;
            case 5:
                if (itemVO.getThuOpen() != null && itemVO.getThuClose() != null) {
                    itemOpenTime.setText("평  일 : " + itemVO.getThuOpen() + " - " + itemVO.getThuClose());
                } else {
                    itemOpenTime.setText("평  일 : - ");
                }
                if (itemVO.getSatOpen() != null && itemVO.getSatClose() != null) {
                    itemOpenTimeSat.setText("토요일 : " + itemVO.getSatOpen() + " - " + itemVO.getSatClose());
                } else {
                    itemOpenTimeSat.setText("토요일 : - ");
                }
                if (itemVO.getSunOpen() != null && itemVO.getSunClose() != null) {
                    itemOpenTimeSun.setText("일요일 : " + itemVO.getSunOpen() + " - " + itemVO.getSunClose());
                }
                break;
            case 6:
                if (itemVO.getFriOpen() != null && itemVO.getFriClose() != null) {
                    itemOpenTime.setText("평  일 : " + itemVO.getFriOpen() + " - " + itemVO.getFriClose());
                } else {
                    itemOpenTime.setText("평  일 : - ");
                }
                if (itemVO.getSatOpen() != null && itemVO.getSatClose() != null) {
                    itemOpenTimeSat.setText("토요일 : " + itemVO.getSatOpen() + " - " + itemVO.getSatClose());
                } else {
                    itemOpenTimeSat.setText("토요일 : - ");
                }
                if (itemVO.getSunOpen() != null && itemVO.getSunClose() != null) {
                    itemOpenTimeSun.setText("일요일 : " + itemVO.getSunOpen() + " - " + itemVO.getSunClose());
                }
                break;

        }
    }
}