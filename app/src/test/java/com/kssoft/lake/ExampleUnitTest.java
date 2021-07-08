package com.kssoft.lake;

import org.junit.Test;

import java.util.Map;

import kiun.com.bvroutine.data.PagerBean;
import kiun.com.bvroutine.utils.HTTPUtil;
import kiun.com.bvroutine.utils.MCString;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){

        HTTPUtil.get("http://47.93.233.210:8080/profile/JianBao/hufan/%E6%97%A0%E9%94%A1%E6%B9%96%E6%B3%9B%E5%B7%A1%E6%9F%A5%E7%AE%80%E6%8A%A52020.08.14.docx");
        int a = 0;
    }
}