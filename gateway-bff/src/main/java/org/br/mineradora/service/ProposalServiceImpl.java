package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.client.ProposalRestClient;
import org.br.mineradora.dto.ProposalDetailsDTO;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
@Traced
public class ProposalServiceImpl implements ProposalService {
    @Inject
    @RestClient
    ProposalRestClient proposalRestClient;

    @Override
    public ProposalDetailsDTO getProposalDetailsByUd(long proposalId) {
        return proposalRestClient.getProposalDetailsById(proposalId);
    }

    @Override
    public Response createProposal(ProposalDetailsDTO proposalDetails) {
        return proposalRestClient.createProposal(proposalDetails);
    }

    @Override
    public Response removeProposal(long id) {
        return  proposalRestClient.removeProposal(id);
    }
}
