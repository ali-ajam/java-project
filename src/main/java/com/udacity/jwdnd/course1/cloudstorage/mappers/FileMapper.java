package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    List<File> listFilesWithSameFilenameAndUserId(String fileName, Integer userId);

    @Select("SELECT fileId, filename FROM FILES WHERE userId=#{userId}")
    List<File> getFiles(Integer userid);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileid);

    @Insert("INSERT INTO FILES (fileName , fileSize , fileData , contentType , userId ) VALUES ( #{fileName}, #{fileSize}, #{fileData}, #{contentType}, #{userId} ) ")
    @Options(useGeneratedKeys = true, keyColumn = "fileId", keyProperty = "fileId")
    Integer uploadFile(File file);


    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userId = #{userId}")
    void delete(Integer fileId, Integer userId);
}
