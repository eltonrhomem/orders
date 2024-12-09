package org.acme.client;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.dto.ProductDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/products")

@RegisterRestClient
public interface ProductClient {

    @GET
    @Path("/{id}")
    ProductDTO getProductById(@PathParam("id") Long id);
}
