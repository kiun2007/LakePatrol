package com.kssoft.lake.net.cacheline.settings;

import com.province.libcacheline.CacheSet;
import com.province.libcacheline.CacheSettingInterface;
import com.province.libcacheline.CacheType;

public class MainCacheLine implements CacheSettingInterface {


    /**
     * PARAMS 入参JSON对象.
     * EXTRA 附加值,如果是下行Return 指的是请求参数.
     * <DEL> 删除操作标志.
     * <WHE> 标记字段用作查询.
     *
     * util.newParams(params, adds, igs) 重组传入参数.
     * 方法参数描述.
     * @param params 参数JSON.
     * @param adds 需要增加的键值对{key:value}.
     * @param igs 需要忽略的Key.
     *
     * util.cloneJSONs(params, values, path, key)  根据路径返回的数组对对象进行复制.
     * @param params 参数JSON.
     * @param values 所有对象都保留的属性集合.
     * @param path 根据路径特征收集属性集合.
     * @param key 收集属性重组对象后的键值.
     *
     * util.queryValue(table, values, key) 查询表格字段值.
     * @param table 表格名称.
     * @param values 查询条件集合.
     * @param key 提取的字段名.
     *
     * util.toCamelCase(params) 将传入的参数所有键名以驼峰命名规则重命名.
     * @param params 参数JSON.
     *
     * util.toUnderline(params) 将传入的参数所有键名以数据库下划线命名规则重命名.
     * @param params 参数JSON.
     */

    @CacheSet(Url = "task/taskStbprp?xctp={xctp}&tkcd={tkcd}&lttd={lttd}&lgtd={lgtd}",
              SQL = "sqlUtil.distanceSql(Setting, PARAMS, 'lttd', 'lgtd', 'gapLimit')",
              Return = "util.newParams(PARAMS, {'gap': sqlUtil.distance(PARAMS.lttd,PARAMS.lgtd,util.parseDouble(EXTRA.lttd),util.parseDouble(EXTRA.lgtd))})",
              Name = "task_site", Key = "stcd", Data = "rows")
    public CacheType getNearbySite = CacheType.CacheDownLoad;

    @CacheSet(Url = "task/list?stm={stm}&etm={etm}&xctp={xctp}",
            Params = "{'xctp':PARAMS.xctp,'tm':PARAMS.stm}",
            Name = "task_list={taskP=task_person}{taskSt=task_station}", Key = "tkcd={taskP=pcd}{taskSt=stcd}", Data = "rows")
    public CacheType taskList = CacheType.CacheDownLoad;

    @CacheSet(Url = "app/mList",
            Name = "mode_list", Key = "mcd", Data = "rows")
    public CacheType getModeList = CacheType.CacheDownLoad;

    @CacheSet(Url = "base/lkwqList?stcd={stcd}&slcd={slcd}", Name = "lkwqList", Key = "stcd",
                Params = "{'stcd':PARAMS.stcd}", Return = "util.newParams(PARAMS,{'stcd':EXTRA.stcd})", Data = "rows")
    public CacheType lkwqList = CacheType.CacheDownLoad;

    @CacheSet(Url = "task/xcTaskProList?stcd={stcd}&tm={tm}", Name = "site_pro", Key = "proId",
              Return = "util.newParams(PARAMS, {'proId':PARAMS.id+PARAMS.stcd+PARAMS.enname})",
              Params = "{'stcd':PARAMS.stcd,'tm':PARAMS.tm}", Data = "rows")
    public CacheType xcTaskProList = CacheType.CacheDownLoad;

    @CacheSet(Url = "app/picList", Name = "pic_table", Key = "icd", Data = "rows")
    public CacheType picList = CacheType.CacheDownLoad;


    @CacheSet(Url = "data/editProData", Name = "update_pro", Key = "proId", NoSave = true)
    public CacheType editProData = CacheType.CacheUpLoad;

    @CacheSet(Url = "mdt/upload", Name = "files", Key = "fcd",
            Return = "{'fcd':PARAMS.fcd,'lttd':PARAMS.lttd,'xctp': PARAMS.xctp}", Data = "data", PutFile = true)
    public CacheType uploadFile = CacheType.CacheUpLoad;

    @Override
    public String theDbPath() {
        return "main_cache.db";
    }
}
