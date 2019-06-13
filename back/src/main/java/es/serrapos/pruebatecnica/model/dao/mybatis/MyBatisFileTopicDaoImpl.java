package es.serrapos.pruebatecnica.model.dao.mybatis;

import org.springframework.stereotype.Service;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.dao.FileTopicDao;
import es.serrapos.pruebatecnica.model.entities.FileTopic;
import es.serrapos.pruebatecnica.model.mappers.FilesMapper;

@Service
public class MyBatisFileTopicDaoImpl implements FileTopicDao{

	private FilesMapper filesMapper;
	 
    public MyBatisFileTopicDaoImpl(final FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }
 
    @Override
    public FileTopic create(FileTopic file) {
        this.filesMapper.insert(file);
        return file;
    }
 
    @Override
    public void delete(Long id) throws EntityNotFoundException {
        if (this.filesMapper.getById(id) == null) {
            throw new EntityNotFoundException("Id to delete not found");
        }
        this.filesMapper.deleteById(id);
    }
 
    @Override
    public FileTopic findOne(Long id) throws EntityNotFoundException {
    	FileTopic file = this.filesMapper.getById(id);
        if (file == null) {
            throw new EntityNotFoundException("Id to get not found");
        }
        return file;
    }
}
