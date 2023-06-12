package org.br.mineradora.controller;


import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.dto.ProposalDetaisDTO;
import org.br.mineradora.service.ProposalService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/api/proposal/")
@Authenticated
public class ProposalController {
    private final Logger LOG = LoggerFactory.getLogger(ProposalController.class);
    @Inject
    ProposalService proposalService;

    @Inject
    JsonWebToken jsonWebToken;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user","manager"})
    //@RolesAllowed({"user", "manager"}))
    public ProposalDetaisDTO findDetailsProposal(@PathParam("id") long id){
        return  proposalService.findFullProposals(id);
    }
    @POST
    @RolesAllowed("proposal-customer")
    public Response createProposal(ProposalDetaisDTO proposalDetaisDTO){
        LOG.info("--Recebendo Proposta de Compra--");
        try {
            proposalService.createNewProposal(proposalDetaisDTO);
            return Response.ok().build();
        }catch (Exception e){
            return Response.serverError().build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("manager")
    public Response deleteProposal(@PathParam("id") long id){
        try {
            proposalService.removeProposal(id);
            return Response.ok().build();
        }catch (Exception e){
            return Response.serverError().build();
        }
    }
}
