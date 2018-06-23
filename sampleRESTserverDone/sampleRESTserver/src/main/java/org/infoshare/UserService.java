package org.infoshare;

import org.infoshare.model.Credentials;
import org.infoshare.model.User;
import org.infoshare.model.UserStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.InvalidObjectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/user")
public class UserService {

    private Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Context
    UriInfo uriinfo;

    private UserStore userStore;
    public UserService() {
        userStore = new UserStore();
    }

    @GET
    @Path("/hi/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response say(@PathParam("name") String name) {
        LOG.info("Saying hello from " + uriinfo.getAbsolutePath());
        return Response.ok().entity("getUserById is called, id : ").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("id") Integer id) {
        User user = userStore.getBase().get(id);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        Collection<User> users = userStore.getBase().values();
        if (users.isEmpty()) {
            return Response.noContent().build();
        } else {
            return Response.ok(users).build();
        }
    }

    @GET
    @Path("/getUserAgent")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAgent(@HeaderParam("user-agent") String userAgent) {
        LOG.info("Client is using [" + userAgent + "]");
        return Response.ok("You are using : [" + userAgent + "]").build();
    }

    @POST
    @Path("/login")
    @Produces({MediaType.TEXT_PLAIN})
    public Response login(@FormParam("username") String username, @FormParam("pass") String password) throws URISyntaxException {
        LOG.info("Login attempt : [" + username + "]");

        Map<String, User> usersByUsername = userStore
                .getBase()
                .values()
                .stream()
                .filter(x -> x.getCredentials() != null)
                .collect(Collectors.toMap(x -> x.getCredentials().getUsername(), x -> x));

        if (usersByUsername.keySet().contains(username)) {
            if (usersByUsername.get(username).getCredentials().getPassword().equals(password)) {
                LOG.info("Login ok");
                return Response.ok("Wszystko poszło ok " + username).build();
            } else {
                LOG.info("Login ok, haslo nie ok. " + usersByUsername.get(username).getCredentials().getPassword() + ":" + password);
                return Response.temporaryRedirect(new URI("/users/hello/")).build();
            }
        } else {
            LOG.info("nie ma takiego ");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }


    @PUT
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(User incomingUser) {
        if (incomingUser.getName() == "" || incomingUser.getSurname() == "") {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User user = new User(incomingUser.getName(), incomingUser.getSurname(), null);
        userStore.add(user);
        return Response.ok(userStore.getBase().values()).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeUser(@PathParam("id") Integer userId) {
        try {
            userStore.remove(userId);
            return Response.ok("User [" + userId + "] deleted").build();
        } catch (InvalidObjectException e) {
            LOG.error(e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
