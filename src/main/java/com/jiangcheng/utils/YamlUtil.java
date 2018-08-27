package com.jiangcheng.utils;

import org.ho.yaml.Yaml;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 类名称：YamlUtil<br>
 * 类描述：<br>
 * 创建时间：2018年08月25日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class YamlUtil<T> {

    private static YamlUtil instance;
    /**
     * 私有化构造类
     */
    private YamlUtil(){}

    /**
     * 如果要使用本工具类需要实例化对象
     * @return
     */
    public static synchronized YamlUtil getInstance(){
        if (instance == null){
            instance = new YamlUtil();
        }
        return instance;
    }

    /**
     * 写入yaml配置文件
     * @param t
     * @param path
     */
    public void write(T t,String path) {
        /* Export data to a YAML file. */
        File dumpFile = new File(System.getProperty("user.dir") + path);
        try {
            Yaml.dump(t,dumpFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取yaml配置文件
     * @param path
     * @param clazz
     * @return
     * @throws FileNotFoundException
     */
    public T read(String path,Class<T> clazz) throws FileNotFoundException {
        File dumpFile = new File(System.getProperty("user.dir") + "/src/main/resources/testYaml.yaml");
        T t = Yaml.loadType(dumpFile,clazz);
        return t;
    }

    /**
     * 读取yaml配置文件Map结构
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    public Map read2Map(String path) throws FileNotFoundException {
        File dumpFile = new File(System.getProperty("user.dir") + path);
        Map map = Yaml.loadType(dumpFile,HashMap.class);
        return map;
    }
}
