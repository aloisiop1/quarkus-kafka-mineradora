package org.br.mineradora.client;

import io.quarkus.oidc.token.propagation.reactive.AccessTokenRequestReactiveFilter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/proposal")
@RegisterRestClient
@RegisterProvider(AccessTokenRequestReactiveFilter.class)
@ApplicationScoped

public interface ProposalRestClient {

    @GET
    @Path("/{id}")
    ProposalDetailsDTO getProposalDetailsById(@PathParam("id") long proposalId);

    @POST
    Response createProposal(ProposalDetailsDTO proposalDetails);

    @DELETE
    @Path("/{id}")
    Response removeProposal(@PathParam("id") long id);

}
