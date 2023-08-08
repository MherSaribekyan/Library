package com.library.app.services.mapper;

@FunctionalInterface
public interface EntityMapper<Request, Entity> {

    Entity toEntity(Request request);
}
