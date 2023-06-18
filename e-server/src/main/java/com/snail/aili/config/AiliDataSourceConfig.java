package com.snail.aili.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.snail.aili.mapper"}, sqlSessionFactoryRef = "sqlSessionFactoryAili")
public class AiliDataSourceConfig {
    @Bean(name = "dataSourceAili")
    @ConfigurationProperties(prefix = "spring.datasource.aili")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlSessionFactoryAili")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceAili") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "transactionManagerAili")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSourceAili") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplateAili")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryAili") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}