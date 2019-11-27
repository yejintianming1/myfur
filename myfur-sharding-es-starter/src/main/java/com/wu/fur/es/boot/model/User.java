package com.wu.fur.es.boot.model;

import com.wu.fur.es.boot.client.EsLogicIndex;
import lombok.Data;

@Data
@EsLogicIndex("id_card")
public class User {

//    @EsFieldType("text")
    private String name;

    private Integer age;

    private String remark;

    private String phone;

    private String email;
}
