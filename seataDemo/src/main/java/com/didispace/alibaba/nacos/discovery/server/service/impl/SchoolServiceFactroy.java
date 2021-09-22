package com.didispace.alibaba.nacos.discovery.server.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.didispace.alibaba.nacos.discovery.server.entity.School;
import com.didispace.alibaba.nacos.discovery.server.service.SchoolService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SchoolServiceFactroy implements SchoolService {

    @Override
    public Integer add(School school) {
        log.error("触发降级");
        return 3;
    }
}
