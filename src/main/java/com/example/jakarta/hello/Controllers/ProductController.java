package com.example.jakarta.hello.Controllers;

import com.example.jakarta.hello.Models.Product;
import com.example.jakarta.hello.Models.Store;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {


    private final Store store = Store.getInstance();

    @POST
    @Path("/add")
    public Response addProduct(Product product) {
        String result = store.addProduct(product);

        if (result.contains("has been added successfully")) {
            return Response.status(Response.Status.CREATED).entity(result).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    @PUT
    @Path("/update")
    public Response updateProduct(Product product) {
        String result = store.updateProduct(product);

        if (result.contains("has been updated successfully")) {
            return Response.ok(result).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    @DELETE
    @Path("/delete/{name}")
    public Response deleteProduct(@PathParam("name") String name) {
        String result = store.deleteProduct(name);

        if (result.contains("has been removed successfully")) {
            return Response.ok(result).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(result).build();
    }

    @GET
    @Path("/search/{name}")
    public Response searchProduct(@PathParam("name") String name) {
        return store.searchToProduct(name)
                .map(product -> Response.ok(product).build())
                .orElse(Response.status(Response.Status.NOT_FOUND)
                        .entity("The product '" + name + "' does not exist in the store.")
                        .build());
    }

    @GET
    @Path("/list")
    public Response getAllProducts() {
        return Response.ok(store.getProducts()).build();
    }
}
