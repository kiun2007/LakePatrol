package com.kssoft.lake.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.FrameLayout;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReference;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.PictureMarkerSymbol;
import com.kssoft.lake.base.presenter.GraphicsPoint;

import kiun.com.bvroutine.utils.ViewUtil;

public class ViewMaker {

    public enum OffsetType {
        top,
        left,
        bottom,
        right,
        center
    }

    public static PictureMarkerSymbol viewToMaker(View view){

        String markerOffset = (String) view.getTag();
        OffsetType offsetType = markerOffset == null ? OffsetType.center : OffsetType.valueOf(markerOffset);

        FrameLayout frameLayout = new FrameLayout(view.getContext());
        frameLayout.addView(view);
        frameLayout.destroyDrawingCache();

        frameLayout.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        frameLayout.layout(0, 0, frameLayout.getMeasuredWidth(), frameLayout.getMeasuredHeight());
        frameLayout.buildDrawingCache();

        Bitmap bitmap = frameLayout.getDrawingCache();

        if (bitmap == null){
            return null;
        }

        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(new BitmapDrawable(view.getResources(), bitmap));

        int height1_2 = ViewUtil.px2dp(view.getContext(), bitmap.getHeight()) / 2;
        int width1_2 = ViewUtil.px2dp(view.getContext(), bitmap.getWidth()) / 2;

        switch (offsetType){
            case top:
                pictureMarkerSymbol.setOffsetY(-height1_2);
                break;
            case left:
                pictureMarkerSymbol.setOffsetX(-width1_2);
                break;
            case right:
                pictureMarkerSymbol.setOffsetX(width1_2);
                break;
            case bottom:
                pictureMarkerSymbol.setOffsetY(height1_2);
                break;
        }

        return pictureMarkerSymbol;
    }

    public static Graphic createGraphicByDrawable(Context context, int resId, GraphicsPoint point){

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        PictureMarkerSymbol pictureMarkerSymbol = new PictureMarkerSymbol(new BitmapDrawable(context.getResources(), bitmap));

        if (bitmap != null){
            return createGraphicByPicture(pictureMarkerSymbol, OffsetType.center, point, context);
        }
        return null;
    }

    public static Graphic createGraphicByPicture(PictureMarkerSymbol pictureMarkerSymbol, OffsetType offsetType, GraphicsPoint point, Context context){

        Point pt = new Point(point.getLng(), point.getLat(), SpatialReference.create(4490));
        Graphic graphic = new Graphic(pt, pictureMarkerSymbol);

        graphic.getAttributes().put("key", point.key());
        graphic.getAttributes().put("count", point.count());

        int minOffsetX = 0;
        int maxOffsetX = 0;
        int minOffsetY = 0;
        int maxOffsetY = 0;

        int imageWidth = pictureMarkerSymbol.getImage(context.getResources()).getIntrinsicWidth();
        int imageHeight = pictureMarkerSymbol.getImage(context.getResources()).getIntrinsicHeight();

        switch (offsetType){
            case top:
                maxOffsetX = imageWidth / 2;
                minOffsetX = -1 * maxOffsetX;
                maxOffsetY = (int) imageHeight;
                break;
            case left:
                maxOffsetX = imageWidth;
                maxOffsetY = imageHeight / 2;
                minOffsetY = - maxOffsetY;
                break;
            case right:
                minOffsetX = imageWidth * -1;
                maxOffsetY = imageHeight / 2;
                minOffsetY = -1 * maxOffsetY;
                break;
            case bottom:
                maxOffsetX = imageWidth / 2;
                minOffsetX = -1 * maxOffsetX;
                minOffsetY = imageHeight * -1;
                break;
            case center:
                maxOffsetX = imageWidth / 2;
                minOffsetX = - maxOffsetX;
                maxOffsetY = imageHeight / 2;
                minOffsetY = - maxOffsetY;
        }

        graphic.getAttributes().put("minOffsetX", minOffsetX);
        graphic.getAttributes().put("maxOffsetX", maxOffsetX);
        graphic.getAttributes().put("minOffsetY", minOffsetY);
        graphic.getAttributes().put("maxOffsetY", maxOffsetY);
        return graphic;
    }

    public static Graphic createGraphicByView(Context context, int layoutId, GraphicsPoint point){

        View view = ViewUtil.makeViewOfBindLayout(context, layoutId, point);
        PictureMarkerSymbol pictureMarkerSymbol = viewToMaker(view);

        String markerOffset = (String) view.getTag();
        OffsetType offsetType = markerOffset == null ? OffsetType.center : OffsetType.valueOf(markerOffset);

        if (pictureMarkerSymbol == null){
            return null;
        }
        return createGraphicByPicture(pictureMarkerSymbol, offsetType, point, context);
    }
}
