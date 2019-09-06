package com.daye.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Configuration
@Primary
public class DataSourceConfig {
    private Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    //@Value("${spring.datasource.type}")
    //private String datasourceType;
    @Value("${spring.datasource.url}")
    //jdbc:mysql://localhost:3306/billing_test?characterEncoding=utf-8&useSSL=false&serverTimezone=Hongkong
    private String datasourceUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    //@Value("${spring.datasource.schema}")
    //private String schema;

    @Bean     //声明其为Bean实例
    public DataSource dataSource(){
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(datasourceUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        try {
            Class.forName(driverClassName);

            String url01 = datasourceUrl.substring(0,datasourceUrl.indexOf("?"));

            String url02 = url01.substring(0,url01.lastIndexOf("/"));

            String datasourceName = url01.substring(url01.lastIndexOf("/")+1);
            url02 = url02+"?serverTimezone=Hongkong";
            // 连接已经存在的数据库，如：mysql
            Connection connection = DriverManager.getConnection(url02, username, password);
            Statement statement = connection.createStatement();

            // 创建数据库
            statement.executeUpdate("create database if not exists `" + datasourceName + "` default character set utf8mb4 COLLATE utf8mb4_general_ci");

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return datasource;
    }
}

