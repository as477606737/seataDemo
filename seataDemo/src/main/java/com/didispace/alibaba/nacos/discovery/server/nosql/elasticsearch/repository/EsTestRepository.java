package com.didispace.alibaba.nacos.discovery.server.nosql.elasticsearch.repository;




import com.didispace.alibaba.nacos.discovery.server.nosql.elasticsearch.document.EsTestModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface EsTestRepository extends ElasticsearchRepository<EsTestModel,Long> {

   Page<EsTestModel> findByNameOrPhoneOrAddress(String name , String phone , String address, Pageable page);
}
