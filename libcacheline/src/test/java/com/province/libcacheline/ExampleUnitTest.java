package com.province.libcacheline;

import com.province.libcacheline.data.beans.TableCluster;
import com.province.libcacheline.utils.JexlUtil;
import com.province.libcacheline.utils.MCString;
import com.province.libcacheline.utils.ObjectUtil;

import org.apache.commons.jexl2.JexlContext;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

//    @SuppressWarnings("all")
//    abstract static class Abstract {
//        public abstract Interface add(Interface a);
//
//        interface Interface {
//            Interface add(Interface a);
//        }
//
//        static class Concrete extends Abstract implements Interface {
//            public Interface add(Interface a) {
//                return this;
//            }
//        }
//
//        public static boolean test() {
//            Abstract a1 = new Concrete();
//            Interface a2 = new Concrete();
//            return a1 == (a1 + a2);
//        }
//    }

    public class TestCacheSetting implements CacheSettingInterface{

        @Override
        public String theDbPath() {
            return "test.db";
        }

        @CacheSet(Url = "test/test/{id}", Name = "testTable", Key = "id")
        public CacheType testSetting = CacheType.CacheDownLoad;
    }

    public class DestObject{

        private String name;

        public String getName() {
            return name;
        }

        @FieldMapping(@FieldRead(type = SrcObject.class, field = "age"))
        public void setName(String name) {
            this.name = name;
        }
    }

    public class SrcObject{

        private String name;

        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

    @Test
    public void test_TableCluster() throws Exception {
        new TableCluster("atable={blist=btable}{clist=ctable}", "pblmId={gwComFiles=id}{bc=id}", "");
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_uppcase(){

        String c = MCString.toCamelCase("AB_HUB");
        String a = MCString.toUnderlineName("SSB_BB",true);
        int b = 0;
    }

    @Test
    public void test_cacheSetting() throws Exception {

//        String value = "32";

//        String randid = MCString.randNum(12);

//        JSONObject jsonObject = new JSONObject("{'a':'ccc'}");
//        String rets[] = MCString.patternValues("\\{(.+?)=(.+?)\\}", "town_table={list=village_table}");

//        TableCluster tableCluster = new TableCluster("town_table={list=village_table}", "town_table={list=village_table}");
//
//        String tableName = tableCluster.getTableName("list");
//        int a = 0;

        DestObject destObject = new DestObject();
        SrcObject srcObject = new SrcObject();
        srcObject.setName("Hello Name");
        srcObject.setAge("Hello age");

        long time = System.currentTimeMillis();
        for (int i = 0; i < 10000; i ++){
//            destObject.setName(srcObject.getName());
            ObjectUtil.copyByMapping(destObject, srcObject);
        }
        long a = System.currentTimeMillis() - time;
        int b = 0;
    }

    @Test
    public void test_typeSetter(){

        Boolean bool = (Boolean) JexlUtil.exeCode("{p=2}{p==1}p",null);

        Map<Class,String> classStringMap = new HashMap<>();
        classStringMap.put(String.class, String.class.toString());

        String abc = "12312";
        classStringMap.put(abc.getClass(), abc.getClass().getName());

        int a = 0;
    }
}

