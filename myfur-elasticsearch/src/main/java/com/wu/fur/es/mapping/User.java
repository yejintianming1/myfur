package com.wu.fur.es.mapping;

import com.wu.fur.es.annotations.EsFieldType;
import lombok.Data;

@Data
public class User {

//    @EsFieldType("text")
    private String name;

    private Integer age;

    private String remark;

    private String phone;

    private String email;
}
