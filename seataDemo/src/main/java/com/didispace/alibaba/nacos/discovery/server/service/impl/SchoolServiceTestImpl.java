package com.didispace.alibaba.nacos.discovery.server.service.impl;

import com.didispace.alibaba.nacos.discovery.server.entity.School;
import com.didispace.alibaba.nacos.discovery.server.mapper.SchoolMapper;
import com.didispace.alibaba.nacos.discovery.server.service.SchoolServiceTest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchoolServiceTestImpl implements SchoolServiceTest {

    private SchoolMapper mapper;

    @Override
    @Transactional
    public void add(School school) {
      mapper.insert(school);
      int a = 1/ 0;
    }
}
