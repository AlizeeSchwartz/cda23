package edu.aschwartz.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.aschwartz.demo.view.VueEntreprise;
import edu.aschwartz.demo.view.VueUtilisateur;
import jdk.jshell.execution.Util;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Entreprise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private Integer id;

    @JsonView({VueUtilisateur.class, VueEntreprise.class})
    private String nom;
    @OneToMany(mappedBy = "entreprise")
    @JsonView(VueEntreprise.class)
    private Set<Utilisateur> listeEmploye = new HashSet<>();

}
