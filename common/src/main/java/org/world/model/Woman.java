package org.world.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Woman extends Person{
    private String BWH;

    public Woman(String name,Integer age,String bwh){
        this.name = name;
        this.age = age;
        this.BWH = bwh;
    }
}
