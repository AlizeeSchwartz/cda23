package edu.aschwartz.demo.dao;

import edu.aschwartz.demo.controller.EntrepriseController;
import edu.aschwartz.demo.model.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseDao extends JpaRepository<Entreprise, Integer> {

}
