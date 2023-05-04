package edu.aschwartz.demo.dao;

import edu.aschwartz.demo.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository <Utilisateur, Integer>{
    Utilisateur findByFirstname(String firstname);
    Optional<Utilisateur> findByEmail(String email);

    @Query("FROM Utilisateur U JOIN U.pays P WHERE P.name = ?1")
    List<Utilisateur> trouverUtilisateurSelonPays(String pays);

}
