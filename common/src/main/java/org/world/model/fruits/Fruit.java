package org.world.model.fruits;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract  class Fruit {
    private String name;
    private Integer size;
    private Integer sugar;
}
