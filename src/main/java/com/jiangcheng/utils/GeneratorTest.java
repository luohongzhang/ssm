package com.jiangcheng.utils;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：GeneratorTest<br>
 * 类描述：<br>
 *
 *     更倾向于使用插件 使用插件更方便
 *
 *
 * 创建时间：2018年08月30日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class GeneratorTest {

    public void testGenerator() throws Exception{

        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        //指向逆向工程配置文件
        File configFile = new File(GeneratorTest.class.getResource("/generatorConfig.xml").getFile());
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                callback, warnings);
        myBatisGenerator.generate(null);

    }
    public static void main(String[] args) throws Exception {
        try {
            GeneratorTest generator = new GeneratorTest();
            generator.testGenerator();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
