package com.jiangcheng.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

/**
 * 类名称：TimeFilter<br>
 * 类描述：<br>
 * 创建时间：2019年01月15日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */
@Component
public class TimeFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Time filter inited");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println("Time filter started");
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("time filter:" + (System.currentTimeMillis() - startTime));
        System.out.println("time filter finished");

    }

    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }
}
