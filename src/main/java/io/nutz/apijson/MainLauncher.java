package io.nutz.apijson;

import java.util.Map;
import java.util.regex.Pattern;

import org.nutz.boot.NbApp;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import apijson.StringUtil;
import apijson.framework.APIJSONApplication;
import apijson.framework.APIJSONCreator;
import apijson.orm.AbstractVerifier;
import apijson.orm.FunctionParser;
import apijson.orm.Parser;
import apijson.orm.SQLConfig;
import apijson.orm.SQLExecutor;
import apijson.orm.Verifier;
import io.nutz.apijson.bean.Device;
import io.nutz.apijson.demo.DemoFunctionParser;
import io.nutz.apijson.demo.DemoParser;
import io.nutz.apijson.demo.DemoSQLConfig;
import io.nutz.apijson.demo.DemoSQLExecutor;
import io.nutz.apijson.demo.DemoVerifier;
/**
 * 右键这个类 > Run As > Java Application
 * @author Administrator
 *
 */
@IocBean(create="init", depose="depose")
public class MainLauncher {
    
    @Inject
    protected PropertiesProxy conf;
    @Inject
    protected Dao dao;
    
    @At("/")
    @Ok("->:/index.html")
    public void index() {}
    
    public void init() {
        // NB自身初始化完成后会调用这个方法
       Daos.createTablesInPackage(dao, "io.nutz.apijson.bean", false);
        if (dao.count(Device.class) == 0) {
        	Device dev = null;
        	for(int i = 1;i <= 10 ; i++){
        		dev = new Device();
        		dev.setAddress("福建省福州市五四路" + R.random(10, 100) + "号");
        		dev.setProv(350001);
        		dev.setSign(R.random(10, 30));
        		dev.setDno("860340426695" + R.random(300, 500));
        		dao.insert(dev);
        	}
         }
        
        // APIJSON 配置 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

 		Map<String, Pattern> COMPILE_MAP = AbstractVerifier.COMPILE_MAP;
 		COMPILE_MAP.put("PHONE", StringUtil.PATTERN_PHONE);
 		COMPILE_MAP.put("EMAIL", StringUtil.PATTERN_EMAIL);
 		COMPILE_MAP.put("ID_CARD", StringUtil.PATTERN_ID_CARD);

 		// 使用本项目的自定义处理类
 		APIJSONApplication.DEFAULT_APIJSON_CREATOR = new APIJSONCreator() {
 			@Override
 			public Parser<Long> createParser() {
 				return new DemoParser();
 			}
 			@Override
 			public FunctionParser createFunctionParser() {
 				return new DemoFunctionParser();
 			}

 			@Override
 			public Verifier<Long> createVerifier() {
 				return new DemoVerifier();
 			}

 			@Override
 			public SQLConfig createSQLConfig() {
 				return new DemoSQLConfig();
 			}
 			@Override
 			public SQLExecutor createSQLExecutor() {
 				return new DemoSQLExecutor();
 			}

 		};

 		// APIJSON 配置 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    }
    public void depose() {}

    public static void main(String[] args) throws Exception {
        new NbApp().setArgs(args)
        .setPrintProcDoc(true)
        .setMainPackage("io.nutz.apijson")
        .run();
        /**
         * 初始化，加载APIJSON配置并校验
         */
        APIJSONApplication.init();
    }

}
