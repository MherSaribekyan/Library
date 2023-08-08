package com.library.app.services.mapper;

public interface ResponseMapper<Response, Entity> {

    Response toResponse(Entity entity);
}
