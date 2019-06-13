package es.serrapos.pruebatecnica.model.dao;

import java.util.List;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.entities.Teacher;

public interface TeacherDao {
	
	public Teacher create(Teacher teacher);
    public Teacher update(Long id, Teacher teacher) throws EntityNotFoundException;
    public void delete(Long id) throws EntityNotFoundException;
    public List<Teacher> findAll();
    public Teacher findOne(Long id) throws EntityNotFoundException;


}
