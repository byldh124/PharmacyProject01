package com.moondroid.pharmacyproject01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

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

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener {
    private final int REQUEST_CODE_FOR_PERMISSION = 3;
    private MapView mapView;
//    FusedLocationSource fusedLocationSource;
    private final int PERMISSION_REQUEST_CODE = 5;
    private Marker marker;
    private NaverMap mNaverMap;
    private Toolbar toolbar;

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
//        fusedLocationSource = new FusedLocationSource(this, 10);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMap.setMapType(NaverMap.MapType.Basic);

        //건물 표시
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true);
        naverMap.setIndoorEnabled(true);

        //카메라 세팅
        CameraPosition cameraPosition = new CameraPosition(new LatLng(37.5670135, 126.9783740), 16, 0, 0);
        naverMap.setCameraPosition(cameraPosition);
        mNaverMap = naverMap;
//        mNaverMap.setLocationSource(fusedLocationSource);
        UiSettings uiSettings = mNaverMap.getUiSettings();
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        for (int i=0; i<G.items.size(); i++){
            Marker marker = new Marker(new LatLng(Double.parseDouble(G.items.get(i).getLat()),Double.parseDouble(G.items.get(i).getLng())));
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

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        if (overlay instanceof Marker){
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("item", new Gson().toJson(overlay.getTag()));
            startActivity(intent);
            return true;
        }
        return false;
    }
}