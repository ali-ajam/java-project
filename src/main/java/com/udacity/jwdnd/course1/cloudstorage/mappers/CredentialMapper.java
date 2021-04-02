package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId = #{userId}")
    List<Credential> listCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredentialById(Integer credentialId);


    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insert(Credential credential);


    @Update("UPDATE CREDENTIALS SET url=#{credential.url}, username=#{credential.username}, key=#{credential.key}," +
            "password=#{credential.password} " +
            "WHERE credentialId = #{credential.credentialId}")
    void update(@Param("credential") Credential credential);


    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    void delete(Integer credentialId);
}
