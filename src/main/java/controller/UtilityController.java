package controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import service.UtilityService;

@Path("/utility")
@Produces(MediaType.APPLICATION_JSON)
public class UtilityController {

    UtilityService utilityService;

    @Inject
    public UtilityController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @GET
    @Path("/sleep/{time}")
    public Response sleep(@PathParam("time") Long time) {
        utilityService.sleep(time);
        return Response.ok().build();
    }
}
