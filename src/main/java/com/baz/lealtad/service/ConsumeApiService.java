package com.baz.lealtad.service;

import com.baz.lealtad.daos.ApiProxyDao;
import com.baz.lealtad.dtos.ApiRequestDto;
import com.baz.lealtad.dtos.ApiResponseDto;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

public class ConsumeApiService {

    @Inject
    @RestClient
    private ApiProxyDao apiProxy;

    public ApiResponseDto consumo(ApiRequestDto peticionApi){
        ApiResponseDto respuestaApi = new ApiResponseDto();
        respuestaApi = apiProxy.getProperties(peticionApi);
        return respuestaApi;
    }

}
