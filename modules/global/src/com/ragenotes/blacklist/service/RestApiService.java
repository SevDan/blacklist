package com.ragenotes.blacklist.service;

public interface RestApiService {
    String NAME = "bl_RestApiService";

    Integer getRestLimit();

    Integer getPublicLimit();

    Integer getSearchLimit();
}