package org.world.demo.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 */
@Data @Accessors(chain = true)
public abstract class Worker extends People{
    String job;
    double income;
}
