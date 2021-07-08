package com.province.libcacheline;

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.province.libcacheline.data.ExtractorBase;
import com.province.libcacheline.data.JSONExtractor;
import com.province.libcacheline.data.SqliteDBHelper;
import com.province.libcacheline.utils.HTTPUtil;
import com.province.libcacheline.utils.JexlUtil;
import com.province.libcacheline.utils.MCString;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    public class TestCacheSetting implements CacheSettingInterface{

        @Override
        public String theDbPath() {
            return "test.db";
        }

        @CacheSet(Url = "dc/insp/pblm/delete/{pblmId:32}/{presId:32}/{pad:d[12]}", Name = "pblm_table", Key = "pblmId", Params = "{'pblmId':PARAMS.pblmId}",IsDelete = true)
        public CacheType pblm_delete = CacheType.CacheUpLoad;

        @CacheSet(Url = "dc/insp/pblm/delete", Name = "pblm_table", Key = "pblmId", Params = "{'pblmId':PARAMS.pblmId}",IsDelete = true)
        public CacheType pblm_delete_no_param = CacheType.CacheUpLoad;
    }

    @Test
    public void test_del() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        CacheSettings.initSettings(appContext, "abc", new TestCacheSetting());

        byte[] ret = CacheSettings.cacheIn("http://sv.goldenwater.com.cn/dc/insp/pblm/delete/683faa70f6e941b6af4e09d720e39820/82C66BD251437F29E0530DC3010ACF31/123456789012", null, null, null);

    }

    @Test
    public void test_jexl(){

        Map<String, String> map = new HashMap<String, String>(){{put("a","b");}};

        String Funtion_IsCenterCity = "function isCenterCity(code){\n" +
                "   var centerCode = ['11','12','50','31'];\n" +
                "   for(var i = 0; i < centerCode.length; i ++){\n" +
                "       if(centerCode[i] == code.substring(0,2)) return true;\n" +
                "   }\n" +
                "   return false;" +
                "}\n";

        Object value = JexlUtil.exeCodeByParams(Funtion_IsCenterCity+"isCenterCity('1100')?{'ab':'cc'}:{'de':'ef'}", new JSONObject());

        int a = 0;
    }

    @Test
    public void testTableCluster() throws Exception {

//        TableCluster tableCluster = new TableCluster("pblm_table={gwComFiles=pblm_file_table}", "pblmId(about_id)={gwComFiles=id}");

        long start = SystemClock.currentThreadTimeMillis();
        for (int i = 0; i < 100000; i++) {
//            if("pblmId".indexOf("(") > 0){
                String string[] = MCString.patternValues("(.+?)\\((.+?)\\)", "pblmId");
//            }

        }
        long end = SystemClock.currentThreadTimeMillis();

        System.out.printf("use times = %d\n", end - start);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        CacheSettings.initSettings(appContext, "abc", new TestCacheSetting());

//        CacheSettings.cacheKernel.getURLParams("http://sv.goldenwater.com.cn/dc/insp/base/getInspPlan?userid=121312&de=12312");

        int a = 0;
    }

    @Test
    public void cacheTownData(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        CacheSettings.initSettings(appContext, "abc", new TestCacheSetting());

        try {
            byte[] date = CacheSettings.cacheIn("http://sv.goldenwater.com.cn/dc/att/adXBase/queryTCListLast", new JSONObject(paramAdCode), null, null);
            int a = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJexl(){
        Boolean bool = (Boolean) JexlUtil.exeCode("{{p='1'}p=='1'}",null);
        int a = 0;
    }

    @Test
    public void testDBHelper() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();

        SqliteDBHelper sqliteDBHelper = new SqliteDBHelper(new File(appContext.getFilesDir(), "test.db").getAbsolutePath());

//        sqliteDBHelper.createTableAndFill("pblm_table", "pblmId", new JSONArray(jsonValue));
//        sqliteDBHelper.createTableAndFill("testTable", "id", new JSONObject("{\"id\":\"49302\",\"name\":\"good\", \"pad\":null}"));


//        Object object = sqliteDBHelper.readDataByParams("pblm_table", new JSONObject("{\"inspPblmName\":\"\",\"villType\":\"\",\"presId\":\"89E69B31FE1344A98316A0153BA7386C\",\"persId\":\"89E69B31FE1344A98316A0153BA7386C\",\"pageNum\":1,\"pageSize\":10}"), 10, 1);

//        Object value = sqliteDBHelper.readDataByParams("testTable_a", new JSONObject("{\"name\":\"d\"}"));
        int a = 0;
    }

    @Test
    public void jsonParamGet(){
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject("{'tab':'ae','files':[{'id':'0001'},{'id':'0002'}],'ab':{'ef':'de','put':{'dd':'11'}}}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ExtractorBase<Object> objectExtractor = new JSONExtractor(jsonObject, true);

        String key = "adb.add[i].att";
        int lastIndex = key.lastIndexOf(".");
        String arrayPath = key.substring(0, lastIndex);
        String pathKey = key.substring(lastIndex + 1, key.length());

        JSONObject object = objectExtractor.extract("ab.put");

//        for (int i = 0; i < object.length(); i ++){
//            try {
//                object.optJSONObject(i).put("id", String.format("%d_0000", i));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
        int a = 0;
    }

    @Test
    public void jsonExtractor(){

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(townDataValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ExtractorBase<Object> objectExtractor = new JSONExtractor(jsonObject, true);
        Object value = objectExtractor.extract("data[i].list[i]", "list.townCode");

        int a = 0;
    }

    @Test
    public void testHttp() throws JSONException {

        JSONObject jsonObject = new JSONObject("{'a.b':'ccc'}");

//        String value = HTTPUtil.postURL("http://hzz-monitor.goldenwater.com.cn/dc/insp/VlgdrinkFacOper/update", null);
//        HTTPUtil.postURL("http://www.baidu.com", "{}", null);
        HTTPUtil.postURLJSON("http://sv.goldenwater.com.cn/dc/insp/VlgdrinkFacOper/update",
                new JSONObject("{\"adCode\":\"110109001002\",\"adminAge\":0,\"adminNm\":\"\",\"adminPro\":\"\",\"adnm\":\"月季园东里社区居委会\",\"createTime\":\"2019-03-21 10:57:07\",\"engId\":\"58196857b8894a33980ab94d7fb4c099\",\"isAdmin\":\"0\",\"isNdAdmin\":\"0\",\"operFee\":12000,\"otherFeeOrg\":\"A\",\"runstseId\":\"3544fb4a4d59422b883e503368fcb768\",\"salary\":0,\"updateTime\":\"2019-03-21 13:47:26\",\"visitDate\":\"2019-03-22\",\"recPersId\":\"89E69B31FE1344A98316A0153BA7386C\"}"),
                new JSONObject("{\"client\":\"001\",\"persId\":\"89E69B31FE1344A98316A0153BA7386C\",\"source\":\"google,Android SDK built for x86\",\"token\":\"11119143aea1dddd4691bab8a922e9752a79\",\"uid\":\"358240051111110\",\"url_name\":\"emergency\",\"version\":\"v2\"}"));
    }

    private String townDataValue = "{\"success\":true,\"code\":null,\"message\":null,\"path\":null,\"throwable\":null,\"data\":[{\"townCode\":\"110109107000\",\"townName\":\"清水镇\",\"townFullName\":null,\"status\":0,\"guid\":\"8330E74159EE1759E0530DC3010A9154\",\"list\":[{\"villageName\":\"燕家台村委会\",\"villageCode\":\"110109107201\",\"villageFullName\":null,\"status\":0,\"lgtd\":null,\"lttd\":null,\"lgtdpc\":null,\"lttdpc\":null,\"isPoverty\":\"2\",\"guid\":\"8330E74159EF1759E0530DC3010A9154\"},{\"villageName\":\"李家庄村委会\",\"villageCode\":\"110109107202\",\"villageFullName\":null,\"status\":0,\"lgtd\":null,\"lttd\":null,\"lgtdpc\":null,\"lttdpc\":null,\"isPoverty\":\"2\",\"guid\":\"8330E74159F01759E0530DC3010A9154\"}]},{\"townCode\":\"110109108000\",\"townName\":\"妙峰山镇\",\"townFullName\":null,\"status\":0,\"guid\":\"8330E7415A0F1759E0530DC3010A9154\",\"list\":[{\"villageName\":\"陇驾庄村委会\",\"villageCode\":\"110109108201\",\"villageFullName\":null,\"status\":0,\"lgtd\":null,\"lttd\":null,\"lgtdpc\":null,\"lttdpc\":null,\"isPoverty\":\"2\",\"guid\":\"8330E7415A101759E0530DC3010A9154\"},{\"villageName\":\"丁家滩村委会\",\"villageCode\":\"110109108202\",\"villageFullName\":null,\"status\":0,\"lgtd\":null,\"lttd\":null,\"lgtdpc\":null,\"lttdpc\":null,\"isPoverty\":\"2\",\"guid\":\"8330E7415A111759E0530DC3010A9154\"}]}]}";


    String paramAdCode = "{\"pageNum\":1,\"pageSize\":10,\"name\":\"\",\"adCode\":\"110109000000\",\"engId\":\"58196857b8894a33980ab94d7fb4c099\",\"isPoveryt\":\"\"}";


    String jsonValue = "[{\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": null,\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000116\",\n" +
            "\t\t\t\"regid\": null,\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": null,\n" +
            "\t\t\t\"pblmId\": \"c5d6413a9f144f398eee8f2cc7fbeb49\",\n" +
            "\t\t\t\"objId\": \"e8518c85f3584ebd8dfceea2a77b0965\",\n" +
            "\t\t\t\"objType\": \"3\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"16\",\n" +
            "\t\t\t\"inspPblmName\": \"进度管理情况\",\n" +
            "\t\t\t\"inspPblmDesc\": \"未开工\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552977561061,\n" +
            "\t\t\t\"recPers\": \"6f0a3ff7e6384baa81c4f3ffba5427f0\",\n" +
            "\t\t\t\"note\": \" 抓紧开工，采取有效措施，确保汛前完工。如不能完工，应制定针对性的度防汛方案，确保工程和财产安全。\",\n" +
            "\t\t\t\"villType\": \"4\",\n" +
            "\t\t\t\"persName\": \"李庆勋\",\n" +
            "\t\t\t\"inspPblmsName\": \"项目未开工建设\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"杜集区龙河大堤修复加固\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"杜集区龙河大堤修复加固\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"B9F837515A5C4AD3ACCBF53266DA1977\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": \"530129001659\",\n" +
            "\t\t\t\"villageCode\": null,\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000141\",\n" +
            "\t\t\t\"regid\": \"99e363d25bf0433781394a53d96643b2\",\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": \"10000000000000000000000000000133\",\n" +
            "\t\t\t\"pblmId\": \"9d62c2c8dc204266a68ea850c14ebfb2\",\n" +
            "\t\t\t\"objId\": \"f6ca58f2a4374fc6b1c3e89ab9778cb0\",\n" +
            "\t\t\t\"objType\": \"2\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"41\",\n" +
            "\t\t\t\"inspPblmName\": \"饮水工程\",\n" +
            "\t\t\t\"inspPblmDesc\": \"县水务局抽检\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552977420417,\n" +
            "\t\t\t\"recPers\": \"155\",\n" +
            "\t\t\t\"note\": null,\n" +
            "\t\t\t\"villType\": \"2\",\n" +
            "\t\t\t\"persName\": \"廖芳珍\",\n" +
            "\t\t\t\"inspPblmsName\": \"水质报告存在问题\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"寻甸回族彝族自治县\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": null,\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"530129000000\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": null,\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000116\",\n" +
            "\t\t\t\"regid\": null,\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": null,\n" +
            "\t\t\t\"pblmId\": \"7b539fe4d6ad4e0c9c54047ba0449576\",\n" +
            "\t\t\t\"objId\": \"b5896e83779b477598e3cb86db7846af\",\n" +
            "\t\t\t\"objType\": \"3\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"16\",\n" +
            "\t\t\t\"inspPblmName\": \"进度管理情况\",\n" +
            "\t\t\t\"inspPblmDesc\": \"未开工\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552977401430,\n" +
            "\t\t\t\"recPers\": \"6f0a3ff7e6384baa81c4f3ffba5427f0\",\n" +
            "\t\t\t\"note\": \" 抓紧开工，采取有效措施，确保汛前完工。如不能完工，应制定针对性的度防汛方案，确保工程和财产安全。\",\n" +
            "\t\t\t\"villType\": \"4\",\n" +
            "\t\t\t\"persName\": \"李庆勋\",\n" +
            "\t\t\t\"inspPblmsName\": \"项目未开工建设\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"杜集区矿山集老毛沟闸拆除重建\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"杜集区矿山集老毛沟闸拆除重建\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"407057A8479F457C9893755BF3A36D9F\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": null,\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000116\",\n" +
            "\t\t\t\"regid\": null,\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": null,\n" +
            "\t\t\t\"pblmId\": \"5b7a5745bbc6418ebd27edd8209703db\",\n" +
            "\t\t\t\"objId\": \"1891e246695240d0b2181cb246a00791\",\n" +
            "\t\t\t\"objType\": \"3\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"16\",\n" +
            "\t\t\t\"inspPblmName\": \"进度管理情况\",\n" +
            "\t\t\t\"inspPblmDesc\": \"未开工\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552977350290,\n" +
            "\t\t\t\"recPers\": \"6f0a3ff7e6384baa81c4f3ffba5427f0\",\n" +
            "\t\t\t\"note\": \" 抓紧开工，采取有效措施，确保汛前完工。如不能完工，应制定针对性的度防汛方案，确保工程和财产安全。\",\n" +
            "\t\t\t\"villType\": \"4\",\n" +
            "\t\t\t\"persName\": \"李庆勋\",\n" +
            "\t\t\t\"inspPblmsName\": \"项目未开工建设\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"杜集区朔里段朱楼闸拆除重建\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"杜集区朔里段朱楼闸拆除重建\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"2E9E5C7171854261A4FD348AF66F6567\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": null,\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000116\",\n" +
            "\t\t\t\"regid\": null,\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": null,\n" +
            "\t\t\t\"pblmId\": \"099e9618bb7749fda23eb567695db333\",\n" +
            "\t\t\t\"objId\": \"6485247d10584c8a84179978a260465a\",\n" +
            "\t\t\t\"objType\": \"3\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"16\",\n" +
            "\t\t\t\"inspPblmName\": \"进度管理情况\",\n" +
            "\t\t\t\"inspPblmDesc\": \"未开工\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552977288847,\n" +
            "\t\t\t\"recPers\": \"6f0a3ff7e6384baa81c4f3ffba5427f0\",\n" +
            "\t\t\t\"note\": \" 抓紧开工，采取有效措施，确保汛前完工。如不能完工，应制定针对性的度防汛方案，确保工程和财产安全。\",\n" +
            "\t\t\t\"villType\": \"4\",\n" +
            "\t\t\t\"persName\": \"李庆勋\",\n" +
            "\t\t\t\"inspPblmsName\": \"项目未开工建设\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"杜集区石台段窦庄六嘴子闸（和谐新家园）拆除重建\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"杜集区石台段窦庄六嘴子闸（和谐新家园）拆除重建\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"FD2E664936974F0FBF4761AB5467EB85\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": \"520527108211\",\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000136\",\n" +
            "\t\t\t\"regid\": \"832C4EC9B620829CE0530EC3010A9157\",\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": \"10000000000000000000000000000132\",\n" +
            "\t\t\t\"pblmId\": \"b034e8ffc4bb4ffa99c141134204904a\",\n" +
            "\t\t\t\"objId\": \"b97fc6555d42476da0f717da1e1b3212\",\n" +
            "\t\t\t\"objType\": \"2\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"36\",\n" +
            "\t\t\t\"inspPblmName\": \"用水户情况\",\n" +
            "\t\t\t\"inspPblmDesc\": \"赫章县哲庄乡香坪村香坪组居民反映，全组30户人家在3-7月5个月水量不足，需要到3公里外运水。\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552977287574,\n" +
            "\t\t\t\"recPers\": \"71dc0b17dcbf4c4fbfa72fcfabe31d5a\",\n" +
            "\t\t\t\"note\": null,\n" +
            "\t\t\t\"villType\": null,\n" +
            "\t\t\t\"persName\": \"李自明\",\n" +
            "\t\t\t\"inspPblmsName\": \"水量问题\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"赫章县\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"香坪村委会\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"520527000000\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": null,\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000240\",\n" +
            "\t\t\t\"regid\": \"44f8b0d19d234ef89e8e75bcd3fc5fa7\",\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": \"00000000000000000000000000000000\",\n" +
            "\t\t\t\"pblmId\": \"6bb3493200274626b20b37f5da08b090\",\n" +
            "\t\t\t\"objId\": \"01afc01dd3bc42089a27c627a089ab73\",\n" +
            "\t\t\t\"objType\": \"1\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": \"4\",\n" +
            "\t\t\t\"inspPblmCode\": \"89\",\n" +
            "\t\t\t\"inspPblmName\": \"工程实体\",\n" +
            "\t\t\t\"inspPblmDesc\": \"高水涵洞出口左侧反滤沟底部存在渗水迹象，水清。\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552976871829,\n" +
            "\t\t\t\"recPers\": \"9d62068011bb40b7b32c31edc9aa96c8\",\n" +
            "\t\t\t\"note\": null,\n" +
            "\t\t\t\"villType\": null,\n" +
            "\t\t\t\"persName\": \"杜庆顺\",\n" +
            "\t\t\t\"inspPblmsName\": \"工程实体\",\n" +
            "\t\t\t\"checkPoint\": \"放水建筑物\",\n" +
            "\t\t\t\"nm\": \"下张水库\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": \"鸡隆坝水厂\",\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"下张水库\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"320481000004\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": null,\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000149\",\n" +
            "\t\t\t\"regid\": \"64a1b8dd7e574ac48f35e0addbad9dfe\",\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": \"10000000000000000000000000000134\",\n" +
            "\t\t\t\"pblmId\": \"160895e186e14753be69b4c380e8fffa\",\n" +
            "\t\t\t\"objId\": \"699ad1c7c5d04f9eb58df92f611cf4d3\",\n" +
            "\t\t\t\"objType\": \"2\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"491\",\n" +
            "\t\t\t\"inspPblmName\": \"水源地\",\n" +
            "\t\t\t\"inspPblmDesc\": \"河铺镇廖家坳村2个河水井供全村85%，用泵提水，靠河沙净化。\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552975883065,\n" +
            "\t\t\t\"recPers\": \"fe519792e2b84609a46e7edee7bc4afd\",\n" +
            "\t\t\t\"note\": null,\n" +
            "\t\t\t\"villType\": \"2\",\n" +
            "\t\t\t\"persName\": \"魏国远\",\n" +
            "\t\t\t\"inspPblmsName\": \"不存在水源保护标志\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"罗田县\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"罗田县\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"421123000000\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": \"530129106214\",\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000137\",\n" +
            "\t\t\t\"regid\": \"832C4EC9C941829CE0530EC3010A9157\",\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": \"10000000000000000000000000000132\",\n" +
            "\t\t\t\"pblmId\": \"c1a2ba4617c947b59b7e80cb30b5e838\",\n" +
            "\t\t\t\"objId\": \"f6ca58f2a4374fc6b1c3e89ab9778cb0\",\n" +
            "\t\t\t\"objType\": \"2\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"37\",\n" +
            "\t\t\t\"inspPblmName\": \"用水户情况\",\n" +
            "\t\t\t\"inspPblmDesc\": \"有安装水表，但是未收水费\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552975306445,\n" +
            "\t\t\t\"recPers\": \"155\",\n" +
            "\t\t\t\"note\": null,\n" +
            "\t\t\t\"villType\": null,\n" +
            "\t\t\t\"persName\": \"廖芳珍\",\n" +
            "\t\t\t\"inspPblmsName\": \"水价收费问题\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"寻甸回族彝族自治县\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"云龙村委会\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"530129000000\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}, {\n" +
            "\t\t\t\"cwsCode\": null,\n" +
            "\t\t\t\"villageCode\": \"530129106214\",\n" +
            "\t\t\t\"pblmsTypeId\": \"10000000000000000000000000000137\",\n" +
            "\t\t\t\"regid\": \"832C4EC9C941829CE0530EC3010A9157\",\n" +
            "\t\t\t\"commonFileIds\": \"-1\",\n" +
            "\t\t\t\"fileNo\": null,\n" +
            "\t\t\t\"fileNoNumber\": null,\n" +
            "\t\t\t\"pblmsId\": \"10000000000000000000000000000132\",\n" +
            "\t\t\t\"pblmId\": \"0fdb87af5c7d464fac3cf882de7edf4a\",\n" +
            "\t\t\t\"objId\": \"f6ca58f2a4374fc6b1c3e89ab9778cb0\",\n" +
            "\t\t\t\"objType\": \"2\",\n" +
            "\t\t\t\"inspGroupId\": null,\n" +
            "\t\t\t\"pguid\": null,\n" +
            "\t\t\t\"inspPblmType\": null,\n" +
            "\t\t\t\"inspPblmCode\": \"37\",\n" +
            "\t\t\t\"inspPblmName\": \"用水户情况\",\n" +
            "\t\t\t\"inspPblmDesc\": \"有安装水表，但是未收费\",\n" +
            "\t\t\t\"inspAddDesc\": null,\n" +
            "\t\t\t\"pblmLong\": null,\n" +
            "\t\t\t\"pblmLat\": null,\n" +
            "\t\t\t\"ifCasePblm\": \"0\",\n" +
            "\t\t\t\"inspPblmOrgName\": null,\n" +
            "\t\t\t\"pblmPersName\": null,\n" +
            "\t\t\t\"inspPblmCate\": \"1\",\n" +
            "\t\t\t\"pblmStat\": \"0\",\n" +
            "\t\t\t\"reviOpin\": null,\n" +
            "\t\t\t\"reviConc\": null,\n" +
            "\t\t\t\"reviOrgGuid\": null,\n" +
            "\t\t\t\"dataStat\": \"1\",\n" +
            "\t\t\t\"collTime\": 1552975255716,\n" +
            "\t\t\t\"recPers\": \"155\",\n" +
            "\t\t\t\"note\": null,\n" +
            "\t\t\t\"villType\": null,\n" +
            "\t\t\t\"persName\": \"廖芳珍\",\n" +
            "\t\t\t\"inspPblmsName\": \"水价收费问题\",\n" +
            "\t\t\t\"checkPoint\": null,\n" +
            "\t\t\t\"nm\": \"寻甸回族彝族自治县\",\n" +
            "\t\t\t\"objNm\": null,\n" +
            "\t\t\t\"cwsName\": null,\n" +
            "\t\t\t\"pblmDesc\": null,\n" +
            "\t\t\t\"srcDesc\": \"云龙村委会\",\n" +
            "\t\t\t\"startTime\": null,\n" +
            "\t\t\t\"endTime\": null,\n" +
            "\t\t\t\"state\": null,\n" +
            "\t\t\t\"code\": \"530129000000\",\n" +
            "\t\t\t\"gwComFiles\": null\n" +
            "\t\t}]";
}
