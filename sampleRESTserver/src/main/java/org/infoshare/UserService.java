package org.infoshare;

import org.infoshare.model.UserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserService {

    private Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Inject
    private UserStore us;

    public UserService() {
    }

    @GET
    @Path("/hello/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response sayHello() {
        LOG.info("Hello world!");
        return Response.ok("Hello world").build();
    }



}
