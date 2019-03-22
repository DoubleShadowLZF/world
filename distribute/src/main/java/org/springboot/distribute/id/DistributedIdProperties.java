package org.springboot.distribute.id;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

//@Configuration
@ConfigurationProperties(prefix ="distribute.id")
@Data
public class DistributedIdProperties {
    /**
     * 工作机器id
     */
    private Long workerId = 1L;
    /**
     * /数据中心ID
     */
    private Long dataCenterId = 1L;
}
