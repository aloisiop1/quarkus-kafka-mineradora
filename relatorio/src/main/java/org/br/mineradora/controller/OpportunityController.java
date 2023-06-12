package org.br.mineradora.controller;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.service.OpportunityService;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Date;
import java.util.List;

@Path("/api/opportunity")
@Authenticated
public class OpportunityController {

    @Inject
    OpportunityService opportunityService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/data")
    @RolesAllowed({"user","manager"})
    //@Produces(MediaType.APPLICATION_OCTET_STREAM)
    public List<OpportunityDTO> generateReport(){
        return opportunityService.generateOpportunityData();
    }
}

