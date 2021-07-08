package com.kssoft.lake.ui.activity.leader;

import android.os.Environment;

import com.kssoft.lake.R;
import com.kssoft.lake.databinding.ActivityWordBrowseBinding;
import kiun.com.bvroutine.base.RequestBVActivity;
import kiun.com.bvroutine_apt.IntentValue;

/**
 * 文 件 名: WordBrowse
 * 作 者: 刘春杰
 * 创建日期: 2020/9/3 23:38
 * 说明: word浏览器
 */
public class WordBrowseActivity extends RequestBVActivity<ActivityWordBrowseBinding> {

    public static final Class clz = WordBrowseActivity.class;
    public final static String DOCX_ROOT_PATH = Environment.getExternalStorageDirectory() + "/docx";

    @IntentValue
    String url;

    @Override
    public int getViewId() {
        return R.layout.activity_word_browse;
    }

    @Override
    public void initView() {
        binding.setUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.tbsView.destroy();
    }
}