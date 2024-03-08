package controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
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
    @Operation(summary = "지연 쿼리")
    @APIResponse(responseCode = "200",
    content = @Content(schema = @Schema(implementation = String.class)))
    public Response sleep(@PathParam("time") Long time) {
        utilityService.sleep(time);
        return Response.ok().build();
    }
}
