package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.ProposalDetaisDTO;
import org.br.mineradora.entity.ProposalEntity;
import org.br.mineradora.message.KafkaEvent;
import org.br.mineradora.repository.ProposalRepository;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.Date;

@ApplicationScoped
@Traced
public class ProposalServiceImpl implements ProposalService{

    @Inject
    ProposalRepository proposalRepository;
    @Inject
    KafkaEvent kafkaEvent;

    @Override
    public ProposalDetaisDTO findFullProposals(long id) {
        ProposalEntity proposal = proposalRepository.findById(id);
        return  ProposalDetaisDTO.builder()
                .proposalId(proposal.getId())
                .proposalValidityDays(proposal.getProposalValidityDays())
                .country(proposal.getCountry())
                .priceTonne(proposal.getPriceTonne())
                .customer(proposal.getCustomer())
                .tonnes(proposal.getTonnes())
                .build();
    }

    @Override
    @Transactional
    public void createNewProposal(ProposalDetaisDTO proposalDetaisDTO) {
        ProposalDTO proposal = buildAndSaveNewProposal(proposalDetaisDTO);
        kafkaEvent.sendNewKafkaEvent(proposal);
    }

    private ProposalDTO buildAndSaveNewProposal(ProposalDetaisDTO proposalDetaisDTO) {

        try {
            ProposalEntity proposal = new ProposalEntity();
            proposal.setCreated(new Date());
            proposal.setProposalValidityDays(proposalDetaisDTO.getProposalValidityDays());
            proposal.setCountry(proposalDetaisDTO.getCountry());
            proposal.setCustomer(proposalDetaisDTO.getCustomer());
            proposal.setPriceTonne(proposalDetaisDTO.getPriceTonne());
            proposal.setTonnes(proposalDetaisDTO.getTonnes());
            proposalRepository.persist(proposal);

            return ProposalDTO.builder()
                    .proposalId(proposal.getId())
                    .priceTonne(proposal.getPriceTonne())
                    .customer(proposal.getCustomer())
                    .build();

        }catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException();
        }

    }

    @Override
    @Transactional
    public void removeProposal(long id) {
        proposalRepository.deleteById(id);
    }
}
