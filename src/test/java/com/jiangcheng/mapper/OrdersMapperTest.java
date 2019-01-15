package com.jiangcheng.mapper;

import com.jiangcheng.bean.Orders;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import sun.font.TrueTypeFont;

import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 类名称：OrdersMapperTest<br>
 * 类描述：<br>
 * 创建时间：2018年08月30日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */
public class OrdersMapperTest {

    //定义 SqlSession
    SqlSession session = null;

    @Before
    public void init(){
        //定义mybatis全局配置文件
        String resource = "mybatis-configuration.xml";

        //加载mybatis全局配置文件
        InputStream inputStream = OrdersMapperTest.class.getClassLoader().getResourceAsStream(resource);

        //构件sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        //根据sqlSessionFactory 产生session
        session = sessionFactory.openSession();
    }

    /**
     * 方式一：嵌套结果
     * select * from orders o,user u where o.user_id=u.id and o.id=#{id}
     */
    @Test
    public void testSelectOrderAndUserByOrderId(){
        String statement = "com.jiangcheng.mapper.OrdersMapper.selectOrderAndUserByOrderID";
        //创建OrdersMapper对象，mybatis自动生成mapepr代理对象
        OrdersMapper orderMapper = session.getMapper(OrdersMapper.class);
        //Orders order = orderMapper.selectOrderAndUserByOrderID(1);
        //System.out.println(order);
        session.close();
    }

    /**
     * 方式二：嵌套查询
     * select * from order WHERE id=1;//得到user_id
     * select * from user WHERE id=1   //1 是上一个查询得到的user_id的值
     */
    @Test
    public void testgetOrderByOrderId(){
        String statement = "com.jiangcheng.mapper.OrdersMapper.getOrderByOrderId";
        //创建OrdersMapper对象，mybatis自动生成mapepr代理对象
        OrdersMapper orderMapper = session.getMapper(OrdersMapper.class);
        //Orders order = orderMapper.selectOrderAndUserByOrderID(1);
        //System.out.println(order);
        session.close();
    }


    @Test
    public void testLazy(){
        String statement = "com.jiangcheng.mapper.OdersMapper.getOrderByOrderId";
        //创建OrdersMapper对象，mybatis自动生成mapepr代理对象
        OrdersMapper orderMapper = session.getMapper(OrdersMapper.class);
        //List<Orders> orders = orderMapper.getOrderByOrderId();//第一步
        //for(Orders order : orders){
        //}
        session.close();
    }
}