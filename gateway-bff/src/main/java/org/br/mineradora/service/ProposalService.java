package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.dto.ProposalDetailsDTO;

@ApplicationScoped
public interface ProposalService {
    ProposalDetailsDTO getProposalDetailsByUd(@PathParam("id") long proposalId);
    Response createProposal(ProposalDetailsDTO proposalDetails);
    Response removeProposal(long id);
}
