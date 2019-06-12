package es.serrapos.pruebatecnica.model.services;

import java.util.List;

import es.serrapos.pruebatecnica.model.entities.Teacher;
import es.serrapos.pruebatecnica.model.exceptions.EntityNotFoundException;

public interface TeacherService {
	
	public Teacher create(Teacher teacher);
    public Teacher update(Long id, Teacher teacher) throws EntityNotFoundException;
    public void delete(Long id) throws EntityNotFoundException;
    public List<Teacher> findAll();
    public Teacher findOne(Long id) throws EntityNotFoundException;


}
