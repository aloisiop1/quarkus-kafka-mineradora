package org.br.mineradora.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.br.mineradora.service.ProposalService;

@Path("/api/trade")
public class ProposalController {

    @Inject
    ProposalService proposalService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user", "manager"})
    public Response getProposalDetailsById(@PathParam("id") long id){
        try {
            ProposalDetailsDTO proposalDetailsByUd = proposalService.getProposalDetailsByUd(id);
            return  Response.ok(proposalService.getProposalDetailsByUd(id),
            MediaType.APPLICATION_JSON).build();
        }catch (ServerErrorException errorException){
            return Response.serverError().build();
        }
    }

    @POST
    @RolesAllowed("proposal-customer")
    public Response createNewProposal(ProposalDetailsDTO proposalDetails){
        int status = proposalService.createProposal(proposalDetails).getStatus();
        if(status > 199 || status < 205){
            return Response.ok().build();
        }else{
            return Response.status(status).build();
        }
    }
    @DELETE
    @Path("/remove/{id}")
    @RolesAllowed("manager")
    public  Response removeProposal(@PathParam("id") long id){
        int status = proposalService.removeProposal(id).getStatus();
        if(status > 199 || status < 205){
            return Response.ok().build();
        }else{
            return Response.status(status).build();
        }
    }
}
