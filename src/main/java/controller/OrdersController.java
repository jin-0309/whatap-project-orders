package controller;

import dto.req.OrdersRequestDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import service.OrdersService;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdersController {

    OrdersService ordersService;

    @Inject
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @POST
    @Path("/add")
    public Response orderProduct(OrdersRequestDto dto) {
        return Response.status(Status.CREATED).entity(ordersService.save(dto)).build();
    }

    @GET
    @Path("/get/{orders_id}")
    public Response getOrder(@PathParam("orders_id") Long ordersId) {
        return Response.ok(ordersService.findById(ordersId)).build();
    }
}
