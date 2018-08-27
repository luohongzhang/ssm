package com.jiangcheng;

import com.jiangcheng.entity.Person;
import org.ho.yaml.Yaml;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称：App<br>
 * 类描述：<br>
 * 创建时间：2018年08月27日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class App {
    //写入yaml配置文件
    @Test
    public void write() {
        /* Initialize data. */
        Person father = new Person();
        father.setName("simon.jiang");
        father.setAge(23);
        father.setSex("man");
        List<Person> children = new ArrayList<Person>();
        for (int i = 8; i < 10; i++) {
            Person child = new Person();
            if (i % 2 == 0){
                child.setSex("man");
                child.setName("mary" + (i - 7));
            } else {
                child.setSex("fatel");
                child.setName("simon" + (i - 7));
            }
            child.setAge(i);
            children.add(child);
        }
        father.setChildren(children);
        /* Export data to a YAML file. */
        File dumpFile = new File(System.getProperty("user.dir") + "/src/main/resources/testYaml.yaml");
        try {
            Yaml.dump(father,dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //读取yaml配置文件
    @Test
    public void read() throws FileNotFoundException {
        File dumpFile = new File(System.getProperty("user.dir") + "/src/main/resources/testYaml.yaml");
        Person father = (Person) Yaml.loadType(dumpFile,Person.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(father.getName()).append("\t").append(father.getSex()).append("\t").append(father.getAge()).append("\t").append(father.getChildren().size());
        System.out.println(stringBuilder.toString());
    }

    //读取yaml配置文件Map结构
    @Test
    public void read2() throws FileNotFoundException {
        File dumpFile = new File(System.getProperty("user.dir") + "/src/main/resources/testYaml.yaml");
        Map father = Yaml.loadType(dumpFile,HashMap.class);
        for(Object key:father.keySet()){
            System.out.println(key+":\t"+father.get(key).toString());
        }
    }
}
