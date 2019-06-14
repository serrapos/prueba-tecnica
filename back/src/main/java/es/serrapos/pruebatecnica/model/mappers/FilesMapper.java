package es.serrapos.pruebatecnica.model.mappers;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import es.serrapos.pruebatecnica.model.entities.FileTopic;

@Mapper
public interface FilesMapper {
	
	@Insert("INSERT INTO FILES(FILENAME, MEDIATYPE, CONTENT) VALUES("
    		+ "#{filename}, "
    		+ "#{mediatype}, "
    		+ "#{content}) ")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="ID")
    public int insert(FileTopic file);
	
	@Delete("DELETE FROM FILES WHERE ID=#{id}")
    public int deleteById(Long id);
	
	@Select("SELECT f.ID, f.FILENAME, f.MEDIATYPE, f.CONTENT FROM FILES f WHERE f.ID = #{id}")
    @Results(value = {
                @Result(property = "id", column = "ID"),
                @Result(property = "filename", column = "FILENAME"),
                @Result(property = "mediatype", column = "MEDIATYPE"),
                @Result(property = "content", column = "CONTENT", jdbcType = JdbcType.BLOB)
            })
    public FileTopic getById(@Param("id") Long id);

}
