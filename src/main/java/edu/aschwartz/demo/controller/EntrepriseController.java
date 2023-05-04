package edu.aschwartz.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.aschwartz.demo.dao.EntrepriseDao;
import edu.aschwartz.demo.model.Entreprise;
import edu.aschwartz.demo.view.VueEntreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EntrepriseController {

    @Autowired
    private EntrepriseDao entrepriseDao;

    @GetMapping("/entreprises")
    @JsonView(VueEntreprise.class)
    public List<Entreprise> getListeEntreprise(){
        List<Entreprise> entreprises = entrepriseDao.findAll();
        return entreprises;
    }

    @GetMapping("/admin/entreprise/{id}")
    @JsonView(VueEntreprise.class)
    public Entreprise getEntreprise(@PathVariable int id){
        Entreprise entreprise = entrepriseDao.findById(id).orElse(null);
        return entreprise;
    }

    @DeleteMapping("/admin/entreprise/{id}")
    public boolean supprimerEntreprise (@PathVariable int id){
        entrepriseDao.deleteById(id);
        return true;
    }
    @PostMapping("/admin/entreprise")
    public Entreprise enregistrerEntreprise(@RequestBody Entreprise entrepriseAenregistrer){
        entrepriseDao.save(entrepriseAenregistrer);
        return entrepriseAenregistrer;
    }


}
