package com.didispace.alibaba.nacos.discovery.server.nosql.elasticsearch.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "test",type="product",shards = 1,replicas = 0)
@Data
public class EsTestModel implements Serializable {
    private static final long serialVersionUID = -1L;
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name ;
    @Field(type = FieldType.Keyword)
    private String phone;
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String address;
}
