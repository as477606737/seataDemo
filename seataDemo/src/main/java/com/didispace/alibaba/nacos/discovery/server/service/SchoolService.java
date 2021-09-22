package com.didispace.alibaba.nacos.discovery.server.service;

import com.didispace.alibaba.nacos.discovery.server.entity.School;
import com.didispace.alibaba.nacos.discovery.server.service.impl.SchoolServiceFactroy;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value ="seataDemoSchool" ,fallback = SchoolServiceFactroy.class)
public interface SchoolService {
    @PostMapping("/school/add")
    public Integer add(@RequestBody School school);
}
