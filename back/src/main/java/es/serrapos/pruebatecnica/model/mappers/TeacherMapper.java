package es.serrapos.pruebatecnica.model.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import es.serrapos.pruebatecnica.model.entities.Teacher;

/**
* Mapper of MyBatis to management the Entity Teacher
* @author Sergio Raposo Vargas
* @version 1.0
*/
@Mapper
public interface TeacherMapper {
	
	@Insert("INSERT INTO TEACHERS(FIRSTNAME, LASTNAME) VALUES(#{firstName}, #{lastName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="ID")
    public int insert(Teacher teacher);
 
    @Update("UPDATE TEACHERS SET FIRSTNAME = #{firstName}, LASTNAME = #{lastName} WHERE ID=#{id}")
    public int update(Teacher teacher);
 
    @Delete("DELETE FROM TEACHERS WHERE ID=#{id}")
    public int deleteById(Long id);
 
    @Select("SELECT t.ID, t.FIRSTNAME, t.LASTNAME FROM TEACHERS t")
    @Results(value = {
                @Result(property = "id", column = "ID"),
                @Result(property = "firstName", column = "FIRSTNAME"),
                @Result(property = "lastName", column = "LASTNAME")
            })
    public List<Teacher> getAll();
 
    @Select("SELECT t.ID, t.FIRSTNAME, t.LASTNAME FROM TEACHERS t WHERE t.ID = #{id}")
    @Results(value = {
    		@Result(property = "id", column = "ID"),
            @Result(property = "firstName", column = "FIRSTNAME"),
            @Result(property = "lastName", column = "LASTNAME")
            })
    public Teacher getById(@Param("id") Long id);

}
