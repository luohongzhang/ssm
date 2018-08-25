package com.jiangcheng.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 类名称：DbPoolConnection<br>
 * 类描述：<br>
 * 创建时间：2018年08月25日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */
@Slf4j
public class DbPoolConnection {

    private static DbPoolConnection databasePool = null;
    private static DruidDataSource dds = null;
    static {
        Properties properties = loadPropertyFile("db_server.properties");
        try {
            dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 私有化构造函数
     */
    private DbPoolConnection(){
    }

    /**
     * 获取实例方法
     * @return
     */
    public static synchronized DbPoolConnection getInstance() {
        if (null == databasePool){
            databasePool = new DbPoolConnection();
        }
        return databasePool;
    }

    public DruidDataSource getDataSource(){
        return dds;
    }

    public DruidPooledConnection getConnection() throws SQLException {
        return dds.getConnection();
    }

    /**
     * 加载配置文件
     * @param fullFile
     * @return
     */
    public static Properties loadPropertyFile(String fullFile) {
        String webRootPath = null;
        if (null == fullFile || fullFile.equals("")){
            throw new IllegalArgumentException("Properties file path can not be null : " + fullFile);
        }
        webRootPath = DbPoolConnection.class.getClassLoader().getResource("\\").getPath();
        webRootPath = new File(webRootPath).getParent();
        InputStream inputStream = null;
        Properties p = null;
        try {
            String profilepath = webRootPath + File.separator + fullFile;
            log.info("profilepath :{} ",profilepath);
            inputStream = new FileInputStream(new File(profilepath));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Properties file not found: " + fullFile);
        } finally {
            try {
                if (inputStream != null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }
}
