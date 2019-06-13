package es.serrapos.pruebatecnica.model.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Service;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.dao.TeacherDao;
import es.serrapos.pruebatecnica.model.entities.Teacher;
import es.serrapos.pruebatecnica.model.mappers.TeacherMapper;

@Service
public class MyBatisTeacherDaoImpl implements TeacherDao{
	
	private TeacherMapper teacherMapper;
	 
    public MyBatisTeacherDaoImpl(final TeacherMapper teacherMapper) {
        this.teacherMapper = teacherMapper;
    }
 
    @Override
    public Teacher create(Teacher teacher) {
        this.teacherMapper.insert(teacher);
        return teacher;
    }
 
    @Override
    public Teacher update(Long id, Teacher teacher) throws EntityNotFoundException {
        Teacher teacherBD = this.teacherMapper.getById(id);
        if (teacherBD == null) {
            throw new EntityNotFoundException("Id to update not found");
        }
        teacherBD.setFirstName(teacher.getFirstName());
        teacherBD.setLastName(teacher.getLastName());
 
        this.teacherMapper.update(teacherBD);
        return teacherBD;
    }
 
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (this.teacherMapper.getById(id) == null) {
            throw new EntityNotFoundException("Id to delete not found");
        }
        this.teacherMapper.deleteById(id);
    }
 
    @Override
    public List<Teacher> findAll() {
        return this.teacherMapper.getAll();
    }
 
    @Override
    public Teacher findOne(Long id) throws EntityNotFoundException {
        Teacher teacher = this.teacherMapper.getById(id);
        if (teacher == null) {
            throw new EntityNotFoundException("Id to get not found");
        }
        return teacher;
    }

}
