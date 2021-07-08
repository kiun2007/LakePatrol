package com.kssoft.lake.view.value;

import android.webkit.WebView;

import kiun.com.bvroutine.base.binding.value.BindConvert;
import kiun.com.bvroutine.net.ServiceGenerator;

public class WebViewBindConvert extends BindConvert<WebView, String, String> {

    public WebViewBindConvert(WebView view) {
        super(view);
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public void setValue(String value) {
        String url = ServiceGenerator.getBasePrefix() + "/profile" + value;
        view.loadUrl(url.replace("docx","pdf"));
    }
}
