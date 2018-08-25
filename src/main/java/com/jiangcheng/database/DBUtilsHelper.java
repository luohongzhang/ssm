package com.jiangcheng.database;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

/**
 * 类名称：DBUtilsHelper<br>
 * 类描述：<br>
 * 创建时间：2018年08月25日<br>
 *
 * @author jiangcheng
 * @version 1.0.0
 */

public class DBUtilsHelper {
    private DataSource ds = null;
    private QueryRunner runner = null;

    public DBUtilsHelper() {
        this.ds = DbPoolConnection.getInstance().getDataSource();
        if (this.ds != null){
            this.runner = new QueryRunner(this.ds);
        }
    }

    public DBUtilsHelper(DataSource ds){
        this.ds = ds;
        this.runner = new QueryRunner(this.ds);
    }

    public QueryRunner getRunner(){
        return this.runner;
    }
}
