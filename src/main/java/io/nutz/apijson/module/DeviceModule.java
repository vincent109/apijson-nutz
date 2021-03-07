/**
 * 
 */
package io.nutz.apijson.module;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.adaptor.JsonAdaptor;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.POST;
import org.nutz.mvc.annotation.Param;

import io.nutz.apijson.bean.Device;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author Administrator
 *
 */
@Api("device")
@At("/device")
@IocBean
@Ok("json:full")
public class DeviceModule {
	 @Inject
	 private Dao dao;
	 
	 @ApiOperation(value = "查询设备列表", notes = "可分页", httpMethod="GET")
     @ApiImplicitParams({
        @ApiImplicitParam(name = "pageNumber", paramType="query", value = "起始页是1", dataType="int", required = false, defaultValue="1"),
        @ApiImplicitParam(name = "pageSize", paramType="query", value = "每页数量", dataType="int", required = false, defaultValue="20"),
        })
    @At("/query")
    public NutMap query(@Param("..")Pager pager) {
        List<Device> users = dao.query(Device.class, Cnd.orderBy().desc("id"), pager);
        pager.setRecordCount(dao.count(Device.class));
        return new NutMap("ok", true).setv("data", new QueryResult(users, pager));
    }
    
    @At("/add")
    @POST
    public NutMap add(@Param("..")Device dev) {
        if (Strings.isBlank(dev.getDno())){
        	return new NutMap("ok", false);
        }
        if (Strings.isBlank(dev.getAddress())){
        	return new NutMap("ok", false);
        }
        dao.insert(dev);
        return new NutMap("ok", true).setv("data", dev);
    }
    
    @At("/delete")
    @POST
    public NutMap delete(long id) {
        dao.clear(Device.class, Cnd.where("id", "=", id));
        return new NutMap("ok", true);
    }
    
    @At("/update")
    @POST
    @AdaptBy(type=JsonAdaptor.class)
    public NutMap update(@Param("..")Device dev) {
        dao.updateIgnoreNull(dev);
        return new NutMap("ok", true);
    }
}
