package edu.aschwartz.demo.dao;

import edu.aschwartz.demo.model.LigneDeContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneDeContratDao extends JpaRepository<LigneDeContrat, LigneDeContrat.IdLigneDeContrat> {
}
