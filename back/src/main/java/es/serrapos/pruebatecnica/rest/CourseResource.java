package es.serrapos.pruebatecnica.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
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

import es.serrapos.pruebatecnica.exceptions.CoursesDuplicatedExceptions;
import es.serrapos.pruebatecnica.exceptions.EntityNotFoundException;
import es.serrapos.pruebatecnica.model.entities.Course;
import es.serrapos.pruebatecnica.services.CourseService;

/**
* Rest service to management of Entity 'Course'
* @author Sergio Raposo Vargas
* @version 1.0
*/
@Component
@Path("/v1/course")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CourseResource {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    private CourseService courseService;
    
    public CourseResource(CourseService courseService) {
        this.courseService = courseService;
    }
    
    @OPTIONS
    @Produces(MediaType.APPLICATION_JSON)
    public Response optionsForCourseResource() {        
        return Response.status(200)
          .header("Allow","POST, PUT, GET, DELETE")
          .header("Content-Type", MediaType.APPLICATION_JSON)
          .header("Content-Length", "0")
          .build();
    }
    
    @GET
    public Response getAll() {
        return Response.ok().entity(courseService.findAllCourses()).build();
    }
    
    @GET
    @Path("/active")
    public Response getAllActive() {
        return Response.ok().entity(courseService.findAllActiveCourses()).build();
    }
 
    @GET
	@Path("/{id}")
	public Response get(@PathParam("id") Long id) {
		Course course = null;
		try {
			course = courseService.findOneCourse(id);
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			return Response.status(404).build();
		}
		return Response.ok().entity(course).build();
    }
    
    @POST
	public Response create(Course course) {
		if(course.getTitle() == null || course.getLevel() == null || course.getTeacher() == null || course.getState() == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Validation error: Failure to fill in required fields").build();
		}
		Course courseCreated = null;
		try {
			courseCreated = courseService.createCourse(course);
		} catch (CoursesDuplicatedExceptions ex) {
			return Response.status(Response.Status.CONFLICT).entity(ex.getMessage()).build();
		}
		return Response.ok().entity(courseCreated).build();
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Course course) {
    	Course courseUpdated = null;
    	try {
    		if(course.getTitle() == null || course.getLevel() == null || course.getTeacher() == null || course.getState() == null) {
    			return Response.status(Response.Status.BAD_REQUEST).entity("Validation error: Failure to fill in required fields").build();
    		}
    		courseUpdated = courseService.updateCourse(id, course);
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).build();
		} catch (CoursesDuplicatedExceptions e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
		}
		return Response.ok().entity(courseUpdated).build();
    }
    
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
    	try {
			courseService.deleteCourse(id);
		} catch (EntityNotFoundException e) {
			log.error(e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).build();
		}
        return Response.ok().entity("Course deleted").build();
    }
    
}   
