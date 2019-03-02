package org.world.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Man extends Person {
    private String chest;

    public Man(String name,Integer age ,String chest){
        this.name =name ;
        this.age= age;
        this.chest = chest;
    }
    public Man(){

    }
}
