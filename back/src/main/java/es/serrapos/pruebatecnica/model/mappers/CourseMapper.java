package es.serrapos.pruebatecnica.model.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import es.serrapos.pruebatecnica.model.entities.Course;
 
 
/**
* Mapper of MyBatis to management the Entity Course
* @author Sergio Raposo Vargas
* @version 1.0
*/
@Mapper
public interface CourseMapper {
 
    @Insert("INSERT INTO COURSES(TITLE, LEVEL, HOURS, TEACHER_ID, STATE) VALUES("
    		+ "#{title}, "
    		+ "#{level}, "
    		+ "#{numberOfHours}, "
    		+ "SELECT ID FROM TEACHERS WHERE ID = #{teacher.id},"
    		+ "#{state}) ")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="ID")
    public int insert(Course course);
 
    @Update("UPDATE COURSES SET TITLE = #{title}, LEVEL = #{level}, HOURS = #{numberOfHours}, TEACHER_ID = #{teacher.id}, STATE = #{state} WHERE ID=#{id}")
    public int update(Course course);
 
    @Delete("DELETE FROM COURSES WHERE ID=#{id}")
    public int deleteById(Long id);
 
    @Select("SELECT c.ID, c.TITLE, c.LEVEL, c.HOURS, c.STATE, c.TEACHER_ID FROM COURSES c")
    @Results(value = {
                @Result(property = "id", column = "ID"),
                @Result(property = "title", column = "TITLE"),
                @Result(property = "level", column = "LEVEL"),
                @Result(property = "numberOfHours", column = "HOURS"),
                @Result(property = "state", column = "STATE"),
                @Result(property="teacher", column="TEACHER_ID", one = @One(select = "es.serrapos.pruebatecnica.model.mappers.TeacherMapper.getById")),
            })
    public List<Course> getAll();
    
    @Select("SELECT c.ID, c.TITLE, c.LEVEL, c.HOURS, c.STATE, c.TEACHER_ID FROM COURSES c WHERE c.STATE = true ORDER BY c.TITLE asc")
    @Results(value = {
                @Result(property = "id", column = "ID"),
                @Result(property = "title", column = "TITLE"),
                @Result(property = "level", column = "LEVEL"),
                @Result(property = "numberOfHours", column = "HOURS"),
                @Result(property = "state", column = "STATE"),
                @Result(property="teacher", column="TEACHER_ID", one = @One(select = "es.serrapos.pruebatecnica.model.mappers.TeacherMapper.getById")),
            })
    public List<Course> getAllActive();
 
    @Select("SELECT c.ID, c.TITLE, c.LEVEL, c.HOURS, c.STATE, c.TEACHER_ID FROM COURSES c WHERE c.ID = #{id}")
    @Results(value = {
                @Result(property = "id", column = "ID"),
                @Result(property = "title", column = "TITLE"),
                @Result(property = "level", column = "LEVEL"),
                @Result(property = "numberOfHours", column = "HOURS"),
                @Result(property = "state", column = "STATE"),
                @Result(property="teacher", column="ID", one = @One(select = "es.serrapos.pruebatecnica.model.mappers.TeacherMapper.getById")),
            })
    public Course getById(@Param("id") Long id);
 
}             