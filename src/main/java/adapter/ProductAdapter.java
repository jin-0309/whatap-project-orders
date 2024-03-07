package adapter;

import dto.res.ProductResponseDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.Optional;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/product")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface ProductAdapter {

    @GET
    @Path("/get/{id}")
   ProductResponseDto getProduct(@PathParam("id") Long id);

}
