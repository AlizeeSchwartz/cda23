package edu.aschwartz.demo.controller;

import edu.aschwartz.demo.dao.PaysDao;
import edu.aschwartz.demo.model.Pays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class PaysController {
    @Autowired
    private PaysDao paysDao;

    @GetMapping("/list-pays")
    public List<Pays> getListPays(){
        return paysDao.findAll();
    }

    @GetMapping("/pays/{id}")
    public ResponseEntity<Pays> getPays(@PathVariable int id){
        Optional<Pays> optional = paysDao.findById(id);
        if(optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/pays")
    public ResponseEntity<Pays> ajoutPays(@RequestBody Pays newPays){
        if(newPays.getId() != null){
            Optional<Pays> optional = paysDao.findById(newPays.getId());

            if(optional.isPresent()){
                paysDao.save(newPays);
                return new ResponseEntity<>(newPays, HttpStatus.OK);
            }
            return new ResponseEntity<>(newPays, HttpStatus.BAD_REQUEST);
        }
        paysDao.save(newPays);
        return new ResponseEntity<>(newPays, HttpStatus.CREATED);
    }

    @DeleteMapping("/pays/{id}")
    public ResponseEntity<Pays> supprimePays(@PathVariable int id){
        Optional<Pays> paysAsupprimer = paysDao.findById(id);
        if(paysAsupprimer.isPresent()){
            paysDao.deleteById(id);
            return new ResponseEntity<>(paysAsupprimer.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
