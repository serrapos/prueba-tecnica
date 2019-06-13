package es.serrapos.pruebatecnica.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.entities.FileTopic;
import es.serrapos.pruebatecnica.services.CourseService;

/**
* Rest service to management of Entity 'Course'
* @author Sergio Raposo Vargas
* @version 1.0
*/
@Component
@Path("/v1/filetopics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FileTopicResource {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private CourseService courseService;
    
    public FileTopicResource(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		FileTopic file = null;
		try {
			file = courseService.findOneFile(id);
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok().entity(file.getContent()).build();
    }
    
    @POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
		@FormDataParam("file") InputStream uploadedInputStream,
		@FormDataParam("file") FormDataContentDisposition fileDetail) {
    	
    	FileTopic file = new FileTopic();
    	file.setFilename(fileDetail.getFileName());
    	
    	// Pass inputstream to byte[] 
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	try {
			uploadedInputStream.transferTo(bos);
		} catch (IOException e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).build();
		}    	
    	file.setContent(bos.toByteArray());
    	
    	file = this.courseService.createFile(file);


		return Response.status(200).entity(file).build();

	}
    
}   
