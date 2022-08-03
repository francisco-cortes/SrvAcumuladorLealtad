package com.baz.lealtad.daos;

import com.baz.lealtad.dtos.ApiRequestDto;
import com.baz.lealtad.dtos.ApiResponseDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "systemClient",
        baseUri = "http://localhost:9080/system")
@Path("/properties")
public interface ApiProxyDao extends AutoCloseable {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    ApiResponseDto getProperties(@RequestBody ApiRequestDto peticionApi) throws ProcessingException;
}
