package com.psk.autoproject.rest;

import com.psk.autoproject.entity.Feature;
import com.psk.autoproject.service.FeatureService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.psk.autoproject.rest.DTO.FeatureDTO;

import java.util.List;
import java.util.stream.Collectors;

@Path("/features")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeatureRestService {

    @Inject
    private FeatureService featureService;

    @GET
    public List<FeatureDTO> getAllFeatures() {
        return featureService.findAll().stream()
                .map(FeatureDTO::new)
                .collect(Collectors.toList());
    }

    @POST
    public Response createFeature(Feature feature) {
        featureService.save(feature);
        return Response.status(Response.Status.CREATED).entity(feature).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateFeature(@PathParam("id") Long id, Feature updatedFeature) {
        Feature existingFeature = featureService.findById(id);
        if (existingFeature == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existingFeature.setName(updatedFeature.getName());
        featureService.save(existingFeature);

        // Prevent LazyInitializationException by returning DTO
        FeatureDTO dto = new FeatureDTO(existingFeature);
        return Response.ok(dto).build();
    }
}