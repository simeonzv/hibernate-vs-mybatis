package com.example.mybatis;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface TransactionTokenMapper {

    @Update("create table trans_token (id bigint auto_increment, trans_id varchar, token_id varchar)")
    void schema();

    @Select({
            "select * from trans_token where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "trans_id", property = "transaction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "token_id", property = "token", jdbcType = JdbcType.VARCHAR)
    })
    TransactionToken getById(long id);


    @Insert({
            "insert into trans_token (trans_id, token_id)",
            "values (#{transaction}, #{token})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TransactionToken record);


    @Select("select count(*) from trans_token")
    long count();

    @Delete({
            "delete from trans_token where id = #{id}"
    })
    void deleteById(TransactionToken token);

    @Delete({
            "delete from trans_token where trans_id = #{transaction}"
    })
    void deleteByTransaction(TransactionToken token);

    @Update({
            "update trans_token",
            "set trans_id = #{transaction},",
            "token_id = #{token}",
            "where id = #{id}"
    })
    int update(TransactionToken record);

    @Select({
            "select id, trans_id, token_id",
            "from trans_token where trans_id = #{transaction}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "trans_id", property = "transaction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "token_id", property = "token", jdbcType = JdbcType.VARCHAR)
    })
    TransactionToken selectByTransaction(String transaction);
}
