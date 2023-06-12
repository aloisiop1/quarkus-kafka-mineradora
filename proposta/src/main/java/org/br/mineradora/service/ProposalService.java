package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.ProposalDetaisDTO;


public interface ProposalService {
    ProposalDetaisDTO findFullProposals(long id);
    void createNewProposal(ProposalDetaisDTO proposalDetaisDTO);
    void removeProposal(long id);
}
