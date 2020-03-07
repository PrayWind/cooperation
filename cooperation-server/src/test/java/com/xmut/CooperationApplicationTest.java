package com.xmut;

import com.xmut.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CooperationApplicationTest {

	// 记录器
	Logger logger = LoggerFactory.getLogger(getClass());

	@Test
	public void contextLoads() {

		// 日志的级别，由低到高: trace < debug < info < warn < error
		// SpringBoot默认设置info级别，这个框架可以在 logback-spring.xml 文件中修改，目前开发环境时DEBUG级别
		logger.trace("这是 trace 日志。。。");
		logger.debug("这是 debug 日志。。。");
		logger.info("这是 info 日志。。。");
		logger.warn("这是 warn 日志。。。");
		logger.error("这是 error 日志。。。");
	}
}