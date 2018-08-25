package com.jiangcheng.utils;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 类名称：DataSourceUtil<br>
 * 类描述：<br>
 * 创建时间：2018年08月25日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class DataSourceUtil {
    // public static Logger logger = null;
    public static DruidDataSource dataSource = null;
    // 保证一个线程一个Connection，线程安全
    private static final ThreadLocal<Connection> CONNECTION_HOLDER;
    // 静态代码块
    static {
        // logger = Logger.getLogger(DataSourceUtil.class.getName());
        // 初始化ThreadLocal变量
        CONNECTION_HOLDER = new ThreadLocal<Connection>();
        dataSource = getDruidDataSource();
        System.out.println("获取DruidDataSource");
    }

    // 获取DruidDataSource
    public static DruidDataSource getDruidDataSource() {
        // https://www.cnblogs.com/JavaSubin/p/5294721.html
        // https://www.cnblogs.com/nima/p/5718331.html
        // 获取DruidDataSource的属性配置文件
        // https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_LogFilter
        // https://github.com/alibaba/druid/wiki/DruidDataSource%E9%85%8D%E7%BD%AE%E5%B1%9E%E6%80%A7%E5%88%97%E8%A1%A8
        Properties properties = PropsUtil.loadProps("druidconfig.properties");
        // 创建DruidDataSource
        DruidDataSource dataSource = new DruidDataSource();
        // 对DruidDataSource设置属性值
        dataSource
                .setDriverClassName(properties.getProperty("driverClassName"));
        dataSource.setUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        dataSource.setMaxActive(Integer.parseInt(properties
                .getProperty("maxActive")));
        dataSource.setInitialSize(Integer.parseInt(properties
                .getProperty("initialSize")));
        dataSource
                .setMaxWait(Long.parseLong(properties.getProperty("maxWait")));
        dataSource.setMinIdle(Integer.parseInt(properties
                .getProperty("minIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(properties
                .getProperty("timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(properties
                .getProperty("minEvictableIdleTimeMillis")));
        dataSource.setTestWhileIdle(Boolean.parseBoolean(properties
                .getProperty("testWhileIdle")));
        dataSource.setTestOnBorrow(Boolean.parseBoolean(properties
                .getProperty("testOnBorrow")));
        dataSource.setTestOnReturn(Boolean.parseBoolean(properties
                .getProperty("testOnReturn")));
        dataSource.setPoolPreparedStatements(Boolean.parseBoolean(properties
                .getProperty("poolPreparedStatements")));
        dataSource.setMaxOpenPreparedStatements(Integer.parseInt(properties
                .getProperty("maxOpenPreparedStatements")));
        System.out.println("完成设置DruidDataSource参数");
        return dataSource;
    }

    // 获取数据库连接
    public static Connection getConnection() {
        // 定义连接变量
        // Connection connection = null;
        //
        Connection connection = CONNECTION_HOLDER.get();
        //boolean flag = false;
        /*if (connection != null) {
            try {
                flag = connection.isClosed();
                System.out.println("80---connection is closed!!!");
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }*/
        if (connection == null ) {
            try {
                System.out.println("通过CONNECTION_HOLDER.get()方法获取连接为null，下面通过数据库连接池获取连接");
                // 获取连接
                connection = dataSource.getConnection();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("获取Connection连接失败.!!!");
            } finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    public static void update(int thlsh, String ltnr) {
        Connection connection = getConnection();
        if (connection == null) {
            System.out.println("获取Connection连接失败,不进行update,直接返回!!!");
            return;
        }
        PreparedStatement pstmt = null;
        System.out.println("update开始!");
        int ltlsh = 0;
        String sql = "update message set CONTENT = ? where id=?";
        System.out.println("更新的sql语句为：sql->" + sql);
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, "this is dbcp2 test 3333333");
            pstmt.setInt(2, 6);
            if (pstmt.executeUpdate() > 0) {
                // System.out.println("更新id=1的数据成功!");
                System.out.println("更新thlsh=" + thlsh + "的聊天内容数据成功!\n聊天内容为："
                        + ltnr);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println("更新数据异常connection=" + connection);
            System.out.println("update t_wx_ltnrk failure:" + e);
            throw new RuntimeException(e);
        } finally {
            CONNECTION_HOLDER.remove();
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String arg[]) {
        System.out.println("11111111");
        Connection connection = null;
        try {
            for (int i = 0; i < 120; i++) {
                System.out.println("获取连接开始i=" + i);
                connection = DataSourceUtil.dataSource.getConnection();
                System.out.println("获取连接结束i=" + i);
                connection.close();
                System.out.println("dataSource=" + dataSource);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            // System.out.println("获取连接失败Connection:"+dataSource);
        }
        // System.out.println("dataSource=" + dataSource);
    }
}
