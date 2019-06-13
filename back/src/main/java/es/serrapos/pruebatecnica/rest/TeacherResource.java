package es.serrapos.pruebatecnica.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.dao.TeacherDao;
import es.serrapos.pruebatecnica.model.entities.Teacher;
import es.serrapos.pruebatecnica.services.CourseService;

/**
* Rest service to management of Entity 'Course'
* @author Sergio Raposo Vargas
* @version 1.0
*/
@Component
@Path("/v1/teacher")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeacherResource {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private CourseService courseService;
    
    public TeacherResource(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @GET
    public Response getAll() {
        return Response.ok().entity(courseService.findAllTeacher()).build();
    }
 
    @GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		Teacher teacher = null;
		try {
			teacher = courseService.findOneTeacher(id);
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok().entity(teacher).build();
    }
    
    @POST
	public Response create(Teacher teacher) {
		if(teacher.getFirstName() == null || teacher.getLastName() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Validation error: Failure to fill in required fields").build();
		}
		return Response.ok().entity(courseService.createTeacher(teacher)).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Teacher teacher) {
    	Teacher teacherUpdated = null;
    	try {
    		if(teacher.getFirstName() == null || teacher.getLastName() == null) {
    			return Response.status(Response.Status.BAD_REQUEST).entity("Validation error: Failure to fill in required fields").build();
    		}
    		teacherUpdated = courseService.updateTeacher(id, teacher);
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.ok().entity(teacherUpdated).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
    	try {
    		courseService.deleteTeacher(id);
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).build();
		}
        return Response.ok().entity("Teacher deleted").build();
    }
    
}   
