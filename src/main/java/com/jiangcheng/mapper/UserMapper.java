package com.jiangcheng.mapper;

import com.jiangcheng.bean.UserBean;
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
@CacheNamespace(implementation = (org.mybatis.caches.redis.RedisCache.class))
@Repository
public interface UserMapper {

	//@Options(useCache = true) //这种使用方式是无效的
	int login(UserBean user);

    @Select("select t_user.* from t_user where t_user.username = #{username,jdbcType=VARCHAR}")
    @Options(useCache = true)
	List<UserBean> queryList(UserBean user);
}
