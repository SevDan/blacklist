package com.ragenotes.blacklist.service;

import com.ragenotes.blacklist.config.RestApiConfig;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(RestApiService.NAME)
public class RestApiServiceBean implements RestApiService {

    @Inject
    private RestApiConfig restApiConfig;

    @Override
    public Integer getRestLimit() {
        return restApiConfig.getRestLimit();
    }

    @Override
    public Integer getPublicLimit() {
        return restApiConfig.getPublicLimit();
    }

    @Override
    public Integer getSearchLimit() {
        return restApiConfig.getSearchLimit();
    }
}