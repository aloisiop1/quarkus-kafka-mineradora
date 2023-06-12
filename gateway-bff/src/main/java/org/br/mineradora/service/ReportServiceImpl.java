package org.br.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.br.mineradora.client.ReportRestClient;
import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.utils.CSVHelper;
import org.eclipse.microprofile.opentracing.Traced;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.io.ByteArrayInputStream;
import java.util.List;

@ApplicationScoped
@Traced
public class ReportServiceImpl implements  ReportService {

    @Inject
    @RestClient
    ReportRestClient reportRestClient;

    @Override
    public ByteArrayInputStream generateCSVOpportunityReport() {
        return CSVHelper.OpportunitiesToCSV(reportRestClient.requestOpportunitiesData());
    }

    @Override
    public List<OpportunityDTO> getOpportunitiesData() {
        return reportRestClient.requestOpportunitiesData();
    }
}
