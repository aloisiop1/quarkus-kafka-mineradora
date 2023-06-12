package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mineradora.client.CurrencyPriceClient;
import org.br.mineradora.dto.CurrencyPriceDTO;
import org.br.mineradora.dto.QuotationDTO;
import org.br.mineradora.entity.QuotationEntity;
import org.br.mineradora.message.KafkaEvents;
import org.br.mineradora.repository.QuotationRepository;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@ApplicationScoped
@Traced
public class QuotationService {
    @Inject
    @RestClient
    CurrencyPriceClient currencyPriceClient;
    @Inject
    QuotationRepository quotationRepository;
    @Inject
    KafkaEvents kafkaEvents;

    public void getCurrencyPrice(){
        CurrencyPriceDTO currencyPriceInfo = currencyPriceClient.getPriceByPair("USD-BRL");

        System.out.println("verificando cotação:" + currencyPriceInfo.getUSDBRL().getBid());

        if(updateCurrencyInfoPrice(currencyPriceInfo)){
            kafkaEvents.sendNewKafkaEvent(QuotationDTO
                    .builder().currencyPrice( new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()))
                    .date( new Date())
                    .build());                    
        }
    }

    private boolean updateCurrencyInfoPrice(CurrencyPriceDTO currencyPriceInfo) {
        BigDecimal currentPrice  = new BigDecimal(currencyPriceInfo.getUSDBRL().getBid());
        AtomicBoolean updatePrice  = new AtomicBoolean(false);
                
        List<QuotationEntity> quotationList = quotationRepository.findAll().list();

        if(quotationList.isEmpty()){
            saveQuotation(currencyPriceInfo);
        }else{
            QuotationEntity lasDollarPrice = quotationList.get(quotationList.size() -1);

            if( currentPrice.floatValue() != lasDollarPrice.getCurrencyPrice().floatValue()){
                updatePrice.set(true);
                saveQuotation(currencyPriceInfo);
            }
        }
        return updatePrice.get();
    }

    private void saveQuotation(CurrencyPriceDTO currencyPriceInfo) {
        QuotationEntity quotation = new QuotationEntity();
        quotation.setDate( new Date());
        quotation.setCurrencyPrice(new BigDecimal(currencyPriceInfo.getUSDBRL().getBid()));
        quotation.setPctChange(currencyPriceInfo.getUSDBRL().getPctChange());
        quotation.setPair("USD-BRL");

        quotationRepository.persist(quotation);
    }
}

