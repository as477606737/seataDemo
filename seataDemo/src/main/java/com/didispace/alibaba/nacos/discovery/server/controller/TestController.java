package com.didispace.alibaba.nacos.discovery.server.controller;


import com.didispace.alibaba.nacos.discovery.server.common.api.CommonResult;
import com.didispace.alibaba.nacos.discovery.server.nosql.elasticsearch.document.EsTestModel;
import com.didispace.alibaba.nacos.discovery.server.nosql.elasticsearch.repository.EsTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private EsTestRepository repository;

@GetMapping("/all")
public CommonResult<Integer> all(){
    List<EsTestModel> list = new ArrayList<EsTestModel>();
    EsTestModel model = new EsTestModel();
    model.setId(1L);
    model.setName("张三");
    model.setAddress("福州市台江区TEST");
    model.setPhone("18850122222");
    list.add(model);
    EsTestModel model1 = new EsTestModel();
    model1.setId(2L);
    model1.setName("李四");
    model1.setAddress("福州市仓山区TEST");
    model1.setPhone("1835222545");
    list.add(model1);
    EsTestModel model2 = new EsTestModel();
    model2.setId(3L);
    model2.setName("王五");
    model2.setAddress("福州市晋安区TEST");
    model2.setPhone("139522212536");
    list.add(model2);
    EsTestModel model3 = new EsTestModel();
    model2.setId(4L);
    model2.setName("测试测试");
    model2.setAddress("福州市晋安区TEST1111");
    model2.setPhone("139522212511136");
    list.add(model3);
    Iterable<EsTestModel> iterable = repository.saveAll(list);
    Iterator<EsTestModel> iterator = iterable.iterator();
    int res = 0 ;
    while (iterator.hasNext()){
        res ++ ;
        iterator.next();
    }
    return CommonResult.success(res);
}


@GetMapping("/search")
public CommonResult<Page<EsTestModel>> search(@RequestParam(required = false) String keyword,
                                                    @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                    @RequestParam(required = false, defaultValue = "5") Integer pageSize){
    Pageable pageable = PageRequest.of(pageNum, pageSize);
    Page<EsTestModel> page = repository.findByNameOrPhoneOrAddress(keyword,keyword,keyword,pageable);
    return CommonResult.success(page);
}

}
