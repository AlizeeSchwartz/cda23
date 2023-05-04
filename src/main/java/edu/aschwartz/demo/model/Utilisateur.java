package edu.aschwartz.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import edu.aschwartz.demo.view.VueEntreprise;
import edu.aschwartz.demo.view.VueUtilisateur;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
//@Table(name = "user")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;
   // @Column(name = "nom")
   @Column(length = 50, nullable = false)
   @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String lastname;
    //column peut faire d'autre chose, définir la taille, si c'est unique boolean, si c'est possible d'etre null
    @Column(length = 80, nullable = false)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String firstname;

    @JsonView(VueUtilisateur.class)
    private String nomImageProfil;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String email;
    private String password;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    @ManyToOne
    private Role role;
    @ManyToOne
    @JsonView(VueUtilisateur.class)
    private Pays pays;

    @ManyToOne
    @JsonView(VueUtilisateur.class)
    Entreprise entreprise;

    @ManyToMany
    @JoinTable(
            name = "recherche_emploi_utilisateur",
            inverseJoinColumns = @JoinColumn(name = "emploi_id")
    )
    @JsonView(VueUtilisateur.class)
    private Set<Emploi> emploisRecherche = new HashSet<>();

    @CreationTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDate createdAt;

    @UpdateTimestamp
    @JsonView(VueUtilisateur.class)
    private LocalDateTime updatedAt;

}



