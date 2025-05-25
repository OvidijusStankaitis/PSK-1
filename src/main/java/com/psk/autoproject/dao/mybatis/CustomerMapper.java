package com.psk.autoproject.dao.mybatis;

import com.psk.autoproject.entity.Customer;
import org.apache.ibatis.annotations.Insert;
import org.mybatis.cdi.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface CustomerMapper {

    @Select("SELECT id, first_name AS firstName, last_name AS lastName FROM customers")
    List<Customer> findAll();

    @Select("SELECT id, first_name AS firstName, last_name AS lastName FROM customers WHERE id = #{id}")
    Customer findById(@Param("id") Long id);

    @Insert("INSERT INTO customers (first_name, last_name) VALUES (#{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Customer customer);

    @Update("UPDATE customers SET first_name = #{firstName}, last_name = #{lastName} WHERE id = #{id}")
    void update(Customer customer);
}
