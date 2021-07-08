package com.kssoft.lake.view.value;


import android.app.Activity;

import androidx.core.content.ContextCompat;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.kssoft.lake.R;
import com.kssoft.lake.base.presenter.GraphicsEvent;
import com.kssoft.lake.base.presenter.GraphicsPoint;
import com.kssoft.lake.base.presenter.imp.ArcgisViewPresenter;
import com.kssoft.lake.data.model.vo.MapStateVo;
import com.kssoft.lake.net.requests.dto.XcTrailR;
import com.kssoft.lake.utils.ViewMaker;

import java.util.List;

import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.SharedUtil;

public class ArcgisMapViewBindConvert extends BindConvert<MapView, Object, Object> {

    protected ArcgisViewPresenter viewPresenter;

    private static final String localMapServer = "http://36.153.143.166/arcgis15/rest/services/jssl_vector_with_water_2019/MapServer";
    private static final String localMapAnnoServer = "http://36.153.143.166/arcgis15/rest/services/jssl_vector_with_water_anno_2019/MapServer";

    public static final String mapServer = "http://218.94.6.92:6080/arcgis/rest/services/jssl_vector_map/MapServer";

    public ArcgisMapViewBindConvert(MapView view) {
        super(view);

        if (view.getContext() instanceof Activity){
            boolean isNoMap =((Activity) view.getContext()).getIntent().getBooleanExtra("NoMap", false);
            if (isNoMap){
                return;
            }
        }

        boolean isInnerMap = SharedUtil.getValue("InnerMap", false);

        if (isInnerMap){
            viewPresenter = new ArcgisViewPresenter(view, localMapServer, localMapAnnoServer);
        }else {
            viewPresenter = new ArcgisViewPresenter(view, mapServer);
        }

        if (view.getContext() instanceof GraphicsEvent){
            viewPresenter.setGraphicsEvent((GraphicsEvent) view.getContext());
        }
    }

    @Override
    public Object getValue() {
        return null;
    }

    /**
     * 画arcgis图线
     * @param mPoints
     */
    private void drawArcgisLine(PointCollection mPoints, int color) {
        Polyline mPolyline = new Polyline(mPoints);//点画线，mPoints为坐标集合
        SimpleLineSymbol lineSym = new SimpleLineSymbol(SimpleLineSymbol.Style.DASH_DOT, ContextCompat.getColor(view.getContext(), color), 5);
        Graphic graphicTrail = new Graphic(mPolyline, lineSym);

        viewPresenter.addGraphics(graphicTrail);
    }

    @Override
    public void setValue(Object value) {

        boolean isShow = false;

        if (viewPresenter == null){
            return;
        }

        if (view.getContext() instanceof Activity){
            isShow = ((Activity) view.getContext()).getIntent().getBooleanExtra("arcGisShow", false);
        }

        if (value instanceof List){

            List list = (List) value;
            viewPresenter.clear();
            if (list.isEmpty()){
                return;
            }

            if (list.get(0) instanceof XcTrailR){

                List<XcTrailR> xcTrailRList = list;

                PointCollection mPointCollection = new PointCollection(SpatialReferences.getWgs84());
                for (XcTrailR trailR : xcTrailRList){
                    mPointCollection.add(new Point(trailR.getLgtd(), trailR.getLttd()));
                }
                drawArcgisLine(mPointCollection, R.color.blue);

                XcTrailR first = xcTrailRList.get(0);
                XcTrailR last = xcTrailRList.get(xcTrailRList.size() - 1);

                viewPresenter.center(last);
                if (xcTrailRList.size() >= 3){
                    viewPresenter.addGraphics(ViewMaker.createGraphicByDrawable(view.getContext(), R.drawable.icon_run_24, first));
                }

                if (isShow){
                    viewPresenter.addGraphics(ViewMaker.createGraphicByView(view.getContext(), R.layout.layout_location_flag, last));
                }else{
                    viewPresenter.addGraphics(ViewMaker.createGraphicByDrawable(view.getContext(), R.drawable.arcgisruntime_location_display_default_symbol, last));
                }
            } else if (list.get(0) instanceof MapStateVo){

                List<MapStateVo> mapStateVoList = list;
                for (MapStateVo mapStateVo : mapStateVoList){
                    viewPresenter.addGraphics(ViewMaker.createGraphicByDrawable(view.getContext(), mapStateVo.getMipMap(), mapStateVo));
                }

            }
        }
    }
}
