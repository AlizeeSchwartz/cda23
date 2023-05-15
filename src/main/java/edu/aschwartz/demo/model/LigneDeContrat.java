package edu.aschwartz.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@IdClass(LigneDeContrat.IdLigneDeContrat.class)
public class LigneDeContrat {

    @Id
    int contratId;
    @Id
    int materielId;

    LocalDate dateDeRetourAnticipe;

    @ManyToOne
    @MapsId("materiel_id")
    @JoinColumn(name = "materiel_id")
    Materiel materiel;

    @ManyToOne
    @MapsId("contrat_id")
    @JoinColumn(name = "contrat_id")
    Contrat contrat;



    @Embeddable
    @Getter
    @Setter
    public class IdLigneDeContrat implements Serializable {
        @Column(name="contrat_id")
        int contratId;
        @Column(name = "materiel_id")
        int materielId;
    }
}
