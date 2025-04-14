package com.psk.autoproject.config;

import com.psk.autoproject.entity.Car;
import com.psk.autoproject.entity.Customer;
import com.psk.autoproject.entity.Feature;
import com.psk.autoproject.entity.Manufacturer;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.mybatis.cdi.SessionFactoryProvider;

import javax.sql.DataSource;

@ApplicationScoped
public class MyBatisProducer {

    @Resource(lookup = "java:/jboss/datasources/autoDatasource")
    private DataSource dataSource;

    @Produces
    @ApplicationScoped
    @SessionFactoryProvider
    public SqlSessionFactory produceFactory() {
        TransactionFactory transactionFactory = new ManagedTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.getTypeAliasRegistry().registerAlias("Car", Car.class);
        configuration.getTypeAliasRegistry().registerAlias("Customer", Customer.class);
        configuration.getTypeAliasRegistry().registerAlias("Feature", Feature.class);
        configuration.getTypeAliasRegistry().registerAlias("Manufacturer", Manufacturer.class);

        configuration.addMappers("com.psk.autoproject.mybatis");

        return new SqlSessionFactoryBuilder().build(configuration);
    }
}
