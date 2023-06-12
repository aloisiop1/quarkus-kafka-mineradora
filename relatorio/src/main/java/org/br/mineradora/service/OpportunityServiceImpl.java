package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.dto.ProposalDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.OpportunityEntity;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.repository.OpportunityRepository;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.opentracing.Traced;

import java.io.ByteArrayInputStream;
import java.util.*;

@ApplicationScoped
@Traced
public class OpportunityServiceImpl implements OpportunityService {

    @Inject
    QuotationRepository quotationRepository;
    @Inject
    OpportunityRepository opportunityRepository;

    @Override
    public void buildOpportunity(ProposalDTO proposal) {
        List<QuotationEntity> quotationEntities = quotationRepository.findAll().list();
        Collections.reverse(quotationEntities);
        OpportunityEntity opportunity = new OpportunityEntity();
        opportunity.setDate(new Date());
        opportunity.setProposalId(proposal.getProposalId());
        opportunity.setCustomer(proposal.getCustomer());
        opportunity.setPriceTonne(proposal.getPriceTonne());
        opportunity.setLastDollarQuotation(quotationEntities.get(0).getCurrencyPrice());

        opportunityRepository.persist(opportunity);
    }

    @Override
    @Transactional
    public void saveQuotation(QuotationDTO quotation) {
        QuotationEntity quotationEntity = new QuotationEntity();
        quotationEntity.setDate(new Date());
        quotationEntity.setCurrencyPrice(quotation.getCurrencyPrice());
        quotationRepository.persist(quotationEntity);
    }

    @Override
    public List<OpportunityDTO> generateOpportunityData() {
        List<OpportunityDTO> opportunities = new ArrayList<>();

        opportunityRepository.findAll()
                .stream()
                .forEach(item -> {
                    opportunities.add(OpportunityDTO.builder()
                            .proposalId(item.getProposalId())
                            .priceTonne(item.getPriceTonne())
                            .customer(item.getCustomer())
                            .lastDollarQuotation(item.getLastDollarQuotation())
                            .build());
                });

        return opportunities;
    }

}
