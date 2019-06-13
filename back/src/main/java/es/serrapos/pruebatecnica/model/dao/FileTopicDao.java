package es.serrapos.pruebatecnica.model.dao;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.entities.FileTopic;

public interface FileTopicDao {
	
	public FileTopic create(FileTopic file);
    public void delete(Long id) throws EntityNotFoundException;
    public FileTopic findOne(Long id) throws EntityNotFoundException;
}
