package exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomExceptionHandler implements ExceptionMapper<CustomException> {

    @Override
    public Response toResponse(CustomException e) {
        int statusCode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
        if (e instanceof SleepQueryException) {
            statusCode = Status.OK.getStatusCode();
        }
        return Response.status(statusCode).entity(e.getMessage()).build();
    }
}
