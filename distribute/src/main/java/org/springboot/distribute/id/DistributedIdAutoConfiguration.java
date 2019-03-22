package org.springboot.distribute.id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(DistributedIdProperties.class)
@Configuration
@ConditionalOnClass(DistributedId.class)
public class DistributedIdAutoConfiguration {
    @Autowired
    private DistributedIdProperties idProperties;

    @Bean
    public DistributedId distributedId(){
        return new DistributedId(idProperties.getWorkerId(),idProperties.getDataCenterId());
    }
}
