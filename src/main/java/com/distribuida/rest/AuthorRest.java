package com.distribuida.rest;

import com.distribuida.db.Authors;
import com.distribuida.servicios.AuthorRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Consumes("application/json")
@Produces("application/json")
public class AuthorRest {
    @Inject
    AuthorRepository authorRepository;
    @GET
    public List<Authors> list() {
        return authorRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Authors get(@PathParam("id") Long id) {
        return authorRepository.findById(id);
    }

    @POST
    @Transactional
    public Response create(Authors author) {
        if (author.getId() != null) {
            throw new WebApplicationException("Id no válido en la solicitud.", 422);
        }
        authorRepository.persist(author);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Authors author) {
        Authors entity = authorRepository.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.setFirst_name(author.getFirst_name());
        entity.setLast_name(author.getLast_name());
        return Response.status((Response.Status.OK) ).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        Authors entity = authorRepository.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        authorRepository.deleteById(id);
    }

}
