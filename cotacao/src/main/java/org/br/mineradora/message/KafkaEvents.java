package org.br.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import org.br.mineradora.dto.QuotationDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    @Channel("quotation-channel")

    Emitter<QuotationDTO> quotationRequestEmitter;
    private final Logger LOG = LoggerFactory.getLogger(KafkaEvents.class);

    public void sendNewKafkaEvent(QuotationDTO quotation){
        LOG.info("-- Enviando Cotação para Tópico Kafka --");
        quotationRequestEmitter.send(quotation).toCompletableFuture().join();
    }
}
