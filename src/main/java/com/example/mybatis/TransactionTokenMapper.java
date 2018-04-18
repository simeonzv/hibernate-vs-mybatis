package com.example.mybatis;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

public interface TransactionTokenMapper {

    @Update("create table trans_token (id bigint auto_increment, trans_id varchar, token_id varchar);" +
            "create table referenceIds (trans_token_id bigint not null, referenceId varchar);")
    void schema();

    @Select({
            "select * from trans_token where id = #{id}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "trans_id", property = "transaction", jdbcType = JdbcType.VARCHAR),
            @Result(column = "token_id", property = "token", jdbcType = JdbcType.VARCHAR),
            @Result(column = "id", property = "referenceIds", javaType = List.class, many=@Many(select="selectReferenceIds"))
    })
    TransactionToken getById(long id);

    @Select("select referenceId from referenceIds where trans_token_id = #{transTokenId}")
    @ResultType(String.class)
    List<String> selectReferenceIds(long transTokenId);

    @Insert({
            "insert into trans_token (trans_id, token_id)",
            "values (#{transaction}, #{token})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TransactionToken record);

    @Insert({
            "<script>",
            "insert into referenceIds (trans_token_id, referenceId)",
            "values ",
            "<foreach  collection='referenceIds' item='referenceId' separator=','>",
            "( #{id}, #{referenceId})",
            "</foreach>",
            "</script>"
    })
    int insertReferenceIds(TransactionToken token);

    @Delete({
            "delete from referenceIds where trans_token_id = #{id}"
    })
    void deleteAllReferenceIds(TransactionToken token);

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
