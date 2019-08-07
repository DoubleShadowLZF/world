package org.world.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description
 * @DAuthor Double
 */
@Data
@Accessors(chain = true)
public class Person {
    protected String name;
    protected Integer age;
}
