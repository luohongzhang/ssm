package com.jiangcheng.mapper;

import com.jiangcheng.bean.User;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao类
 * @author Chan Kiang
 *
 */
//@CacheNamespace(implementation = (org.mybatis.caches.redis.RedisCache.class))
@Repository
public interface UserMapper {

    /**
     * 根据用户名和密码查询用户数
     * @param user
     * @return
     */
	//@Options(useCache = true) //这种使用方式是无效的 必须跟@Select同时使用
	int login(User user);

    /**
     * 根据用户名查询用户信息
     * @param user
     * @return
     */
    @Select("select t_user.* from t_user where t_user.username = #{username,jdbcType=VARCHAR}")
    @Options(useCache = true)
	List<User> queryList(User user);

    /**
     * 根据用户id查询用户信息，以及用户下面的所有订单信息
     * @param id
     * @return
     */
    User selectUserAndOrdersByUserId(int id);

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    User selectUserById(int id);
}
