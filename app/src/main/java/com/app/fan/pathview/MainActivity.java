package com.app.fan.pathview;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

public class MainActivity extends AppCompatActivity implements PathView.OnFinishListener {
    private PathView pview;
    private TextureMapView mapView;
    private UiSettings mUiSettings;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pview = (PathView) findViewById(R.id.pview);
        mapView = (TextureMapView) findViewById(R.id.bmapView);
        mBaiduMap = mapView.getMap();
        pview.setOnFinishListener(this);
        mUiSettings = mBaiduMap.getUiSettings();
        mUiSettings.setAllGesturesEnabled(false);
    }

    @Override
    public void onFinish(Path p, RectF r) {
        pview.setVisibility(View.GONE);
        mUiSettings.setAllGesturesEnabled(true);
        RailView view = new RailView(this);
        view.setLayoutParams(new FrameLayout.LayoutParams(pview.getWidth(), pview.getHeight()));
        view.setPadding(pview.getWidth() / 2, pview.getHeight() / 2, pview.getWidth() / 2, pview.getHeight() / 2);
        view.set(p);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
        Point point = new Point(0, 0);
        Point point1 = new Point(pview.getWidth(), pview.getHeight() + getResources().getDimensionPixelSize(R.dimen.map_vertical));

        LatLng southwest = mBaiduMap.getProjection().fromScreenLocation(point);
        LatLng northeast = mBaiduMap.getProjection().fromScreenLocation(point1);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
        OverlayOptions path = new GroundOverlayOptions().positionFromBounds(bounds).image(bitmapDescriptor).zIndex(4);

        mBaiduMap.addOverlay(path);

    }
}
