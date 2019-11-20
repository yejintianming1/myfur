package com.wu.fur.es.mapping;

import com.wu.fur.es.annotations.EsFieldType;
import lombok.Data;

@Data
public class User {

    @EsFieldType("text")
    private String name;

    private String age;
}
