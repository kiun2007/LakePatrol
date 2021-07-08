package com.kssoft.lake.base.presenter.imp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.annotation.NonNull;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.geometry.Geometry;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.layers.ArcGISTiledLayer;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.Basemap;
import com.esri.arcgisruntime.mapping.Viewpoint;
import com.esri.arcgisruntime.mapping.view.DefaultMapViewOnTouchListener;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedEvent;
import com.esri.arcgisruntime.mapping.view.MapScaleChangedListener;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.util.ListenableList;
import com.kssoft.lake.base.presenter.GraphicsEvent;
import com.kssoft.lake.base.presenter.GraphicsPoint;

import kiun.com.bvroutine.utils.SharedUtil;

public class ArcgisViewPresenter extends DefaultMapViewOnTouchListener implements MapScaleChangedListener {

    private static final int[] levelScale = new int[]{
            51200, //51.2公里
            25600, //25.6公里
            12800, //12.8公里
            6400, //6.4公里
            3200, //3.2公里
            1600, //1.6公里
            800, //800米
            400, //400米
            200, //200米
            100, //100米
            50 //50米
    };

    private MapView mapview;

    private GraphicsOverlay mGraphicsOverlay;

    private Context context;

    private ArcGISMap arcGISMap;

    private GraphicsEvent graphicsEvent;

    private int level = 7;

    private boolean init = false;

    public ArcgisViewPresenter(MapView mapview, String strMapUrl) {
        this(mapview, strMapUrl, null);
    }

    public ArcgisViewPresenter(MapView mapview, String strMapUrl, String annoMapUrl) {
        super(mapview.getContext(), mapview);

        this.mapview = mapview;
        this.context = mapview.getContext();

        mGraphicsOverlay = new GraphicsOverlay();
        mapview.getGraphicsOverlays().add(mGraphicsOverlay);

        ArcGISRuntimeEnvironment.setLicense("runtimelite,1000,rud4449636536,none,NKMFA0PL4S0DRJE15166");
        ArcGISTiledLayer layer = new ArcGISTiledLayer(strMapUrl);
        //设置切片图层作为底图
        Basemap basemap = new Basemap(layer);

        if (annoMapUrl != null){
            ArcGISTiledLayer annoLayer = new ArcGISTiledLayer(annoMapUrl);
            basemap.getBaseLayers().add(annoLayer);
        }

        //创建一个map包含basemap地图数据
        arcGISMap = new ArcGISMap(basemap);

        arcGISMap.addLoadStatusChangedListener(event -> {
            if (event.getNewLoadStatus() == LoadStatus.LOADED){

                String latlng = SharedUtil.getValue("lastLatLng", "");
                if (!latlng.isEmpty()){
                    center(latlng);
                }else{
                    mapview.setViewpointCenterAsync(new Point(119.102435, 33.968797), levelScale[level] * 100).addDoneListener(this::startAction);
                }
                init = true;
            }
        });

        //设置mao显示的视图MapView中
        mapview.setMap(arcGISMap);
        mapview.setAttributionTextVisible(false);
        mapview.setOnTouchListener(this);
        mapview.addMapScaleChangedListener(this);
    }

    public void setGraphicsEvent(GraphicsEvent graphicsEvent) {
        this.graphicsEvent = graphicsEvent;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        Graphic graphic = findGraphic(Math.round(e.getX()), Math.round(e.getY()));

//        Point point = mapview.screenToLocation(new android.graphics.Point(Math.round(e.getX()), Math.round(e.getY())));
        if (graphic != null && graphicsEvent != null) {
            graphicsEvent.onGraphicsClick(graphic);
        }
        return super.onSingleTapConfirmed(e);
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        super.onScaleEnd(detector);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        startAction();
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    private Graphic findGraphic(double xScreen, double yScreen) {

        ListenableList<Graphic> graphics = mGraphicsOverlay.getGraphics();

        for (int i = 0; i < graphics.size(); i++) {
            Graphic graphic = graphics.get(i);

            if (graphic.getAttributes().get("minOffsetX") == null){
                continue;
            }

            int minOffsetX = (int)graphic.getAttributes().get("minOffsetX");
            int maxOffsetX = (int)graphic.getAttributes().get("maxOffsetX");
            int minOffsetY = (int)graphic.getAttributes().get("minOffsetY");
            int maxOffsetY = (int)graphic.getAttributes().get("maxOffsetY");

            android.graphics.Point screenPoint = mapview.locationToScreen(graphic.getGeometry().getExtent().getCenter());

            minOffsetX += screenPoint.x;
            maxOffsetX += screenPoint.x;
            minOffsetY += screenPoint.y;
            maxOffsetY += screenPoint.y;

            if (xScreen >= minOffsetX && xScreen <= maxOffsetX
                    && yScreen >= minOffsetY && yScreen <= maxOffsetY) {
                return graphic;
            }
        }

        return null;
    }

    public void startAction(){

//        Viewpoint vp = mapview.getCurrentViewpoint(Viewpoint.Type.BOUNDING_GEOMETRY);
//        Geometry curExtent = vp == null ? null : vp.getTargetGeometry();
//
//        if (curExtent != null) {
//            Envelope extent = curExtent.getExtent();
//            if (graphicsEvent != null){
//                graphicsEvent.onMapAction(extent, level);
//            }
//        }
    }

    private void scaleLevel(){
        mapview.setViewpointScaleAsync(levelScale[level] * 100).addDoneListener(this::startAction);
    }

    public void zoomOut(){
        if (level > 0){
            level --;
            scaleLevel();
        }
    }

    public void zoomIn(){
        if (level < levelScale.length - 1){
            level ++;
            scaleLevel();
        }
    }

    public boolean withKey(@NonNull String key){
        for (Graphic graphic : mGraphicsOverlay.getGraphics()){
            if (key.equals(graphic.getAttributes().get("key"))){
                return true;
            }
        }
        return false;
    }

    public void clear(){
        mGraphicsOverlay.getGraphics().clear();
    }

    public void addGraphics(Graphic graphic){
        mGraphicsOverlay.getGraphics().add(graphic);
    }

    public void center(){

        double x = 0, y = 0;
        for (Graphic graphic : mGraphicsOverlay.getGraphics()){
            x += graphic.getGeometry().getExtent().getCenter().getX();
            y += graphic.getGeometry().getExtent().getCenter().getY();
        }

        center(x/mGraphicsOverlay.getGraphics().size(), y/mGraphicsOverlay.getGraphics().size());
    }

    public void center(String pointXY){
        String[] xyString = pointXY.split(", ");

        double x = Double.parseDouble(xyString[0]);
        double y = Double.parseDouble(xyString[1]);

        center(x, y);
    }

    public void center(GraphicsPoint point){
        center(point.getLng(), point.getLat());
    }

    @SuppressLint("DefaultLocale")
    public void center(double x, double y){
        SharedUtil.saveValue("lastLatLng", String.format("%f, %f", x, y));
        double scale = SharedUtil.getValue("mapScale", (float)mapview.getMapScale());
        mapview.setViewpointCenterAsync(new Point(x, y), scale).addDoneListener(this::startAction);
    }

    @Override
    public void mapScaleChanged(MapScaleChangedEvent mapScaleChangedEvent) {
        if (init){
            SharedUtil.saveValue("mapScale", (float)mapScaleChangedEvent.getSource().getMapScale());
        }
    }


}
