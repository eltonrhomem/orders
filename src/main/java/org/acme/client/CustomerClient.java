package org.acme.client;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.dto.CustomerDTO;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/customers")
@RegisterRestClient
public interface CustomerClient {

    @GET
    @Path("/{id}")
    CustomerDTO getCustomerById(@PathParam("id") Long id);

}
