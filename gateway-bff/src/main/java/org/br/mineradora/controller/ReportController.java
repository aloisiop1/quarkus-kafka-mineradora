package org.br.mineradora.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.br.mineradora.dto.OpportunityDTO;
import org.br.mineradora.service.ReportService;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@Path("/api/opportunity")
public class ReportController {
    @Inject
    ReportService reportService;

    @GET
    @Path("/report")
    @RolesAllowed({"user","manager"})
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generateReport(){
        try{
            return Response.ok(reportService.generateCSVOpportunityReport(),
                    MediaType.APPLICATION_OCTET_STREAM)
                    .header("content-type", "attachement; filename =" + new Date() + "--opportunities-venda.csv")
                    .build();

        }catch (ServerErrorException e){
            return Response.serverError().build();
        }
    }
    @GET
    @Path("/data")
    @RolesAllowed({"user","manager"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateOpportunitiesData(){

        List<OpportunityDTO> opportunitiesData = reportService.getOpportunitiesData();

        System.out.println("...");

        try{
            return Response.ok(reportService.getOpportunitiesData(),
                            MediaType.APPLICATION_JSON)
                            .build();

        }catch (ServerErrorException e){
            return Response.serverError().build();
        }
    }

}
