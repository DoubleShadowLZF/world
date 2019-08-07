package org.world.demo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.esotericsoftware.yamlbeans.YamlException;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.world.model.Man;
import org.world.model.Woman;
import org.world.util.file.YamlUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class SpringUtilTest {
    @Test
    public void collectionUtilsTest(){
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, null, 6, 7, 8, 9);
        boolean empty = CollectionUtils.isEmpty(integers);
        System.out.println(empty);
        String[] provins = new String[]{"上海","北京","广州"};
        List list = CollectionUtils.arrayToList(provins);
        System.out.println(list);
        List<Integer> integers1 = Arrays.asList(10, 10, 11, 0);
        boolean b = CollectionUtils.containsAny(integers, integers1);
        System.out.println(b);
        Class<?> commonElementType = CollectionUtils.findCommonElementType(integers);
        System.out.println(commonElementType.getClass());
        boolean b1 = CollectionUtils.hasUniqueObject(integers);
        System.out.println(b1);
    }

    @Test
    public void alibabaJsonTest() throws FileNotFoundException, YamlException {
        File file = ResourceUtils.getFile("classpath:application.yml");
        LinkedHashMap yaml = (LinkedHashMap) YamlUtil.getYaml("D:\\Document\\demo\\world\\src\\main\\resources\\application.yml");
        System.out.println(yaml);
        String s = JSON.toJSONString(yaml);

        //阿里拷贝
        Woman w = new Woman("Yuuki Asuna",17,"82/60/83");
        Man m = new Man("Double",24,"18");
        long begin = System.currentTimeMillis();
        String wStr = JSON.toJSONString(w);
        Man man = JSON.parseObject(wStr, Man.class);
        long time = System.currentTimeMillis() - begin;
        System.out.println("阿里json："+time);
        System.out.println(JSONObject.toJSONString(man));

        //SpringBoot拷贝
        Man man1 = new Man();
        begin = System.currentTimeMillis();
        BeanUtils.copyProperties(w,man1);
        time = System.currentTimeMillis() - begin;
        System.out.println("Spring自带的工具："+time);
        System.out.println(JSONObject.toJSONString(man1));

        //ModelMapper
        Man man2 = new Man();
        begin = System.currentTimeMillis();
        ModelMapper mapper = new ModelMapper();
        time = System.currentTimeMillis() - begin;
        System.out.println("ModelMapper："+time);
        mapper.map(w,man2);
//        mapper.
    }
}
