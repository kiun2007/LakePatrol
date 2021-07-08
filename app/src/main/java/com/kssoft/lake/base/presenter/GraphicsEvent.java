package com.kssoft.lake.base.presenter;

import com.esri.arcgisruntime.geometry.Envelope;
import com.esri.arcgisruntime.mapping.view.Graphic;

public interface GraphicsEvent {

    void onGraphicsClick(Graphic graphic);

    void onMapAction(Envelope extent, int level);
}
