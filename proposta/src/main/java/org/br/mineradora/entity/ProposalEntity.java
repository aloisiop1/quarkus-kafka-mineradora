package org.br.mineradora.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "proposal")
@Data
@NoArgsConstructor
public class ProposalEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private  Long id;

    private String customer;

    @Column(name = "price_tonne")
    private BigDecimal priceTonne;

    private  Integer tonnes;

    private String country;

    @Column(name="proposal_validity_days")
    private Integer proposalValidityDays;

    private Date created;

}
