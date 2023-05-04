package edu.aschwartz.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import edu.aschwartz.demo.dao.UtilisateurDao;
import edu.aschwartz.demo.model.Role;
import edu.aschwartz.demo.model.Utilisateur;
import edu.aschwartz.demo.security.JwtUtils;
import edu.aschwartz.demo.services.FichierService;
import edu.aschwartz.demo.view.VueUtilisateur;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class UtilisateurController {
    // injection de dépendance : @Autowired
    @Autowired
    private UtilisateurDao utilisateurDao;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    FichierService fichierService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/utilisateurs")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateurs(){
        return utilisateurDao.findAll();
    }

    @GetMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getUtilisateur(@PathVariable int id){
        //pour réaliser une erreur volontairement
        //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Optional<Utilisateur> optional = utilisateurDao.findById(id);
        if(optional.isPresent()){
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/utilisateur-par-pays/{nomPays}")
    @JsonView(VueUtilisateur.class)
    public List<Utilisateur> getUtilisateur(@PathVariable String nomPays){
        return  utilisateurDao.trouverUtilisateurSelonPays(nomPays);
    }

    //récupérer information de l'utilisateur par son email
    @GetMapping("/profil")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> getProfil(@RequestHeader("Authorization") String bearer){
        String jwt = bearer.substring(7);
        Claims donnees = jwtUtils.getData(jwt);
        Optional<Utilisateur> utilisateur = utilisateurDao.findByEmail(donnees.getSubject());
        if(utilisateur.isPresent()){
            return new ResponseEntity<>(utilisateur.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/image-profil/{idUtilisateur}")
    public ResponseEntity<byte[]> getImageProfil(@PathVariable int idUtilisateur){
        Optional<Utilisateur> optional = utilisateurDao.findById(idUtilisateur);
        if(optional.isPresent()){
            String nomImage = optional.get().getNomImageProfil();
            try {
                byte[] image = fichierService.getImageByName(nomImage);

                HttpHeaders enTete = new HttpHeaders();
                String mimeType = Files.probeContentType(new File(nomImage).toPath());
                enTete.setContentType(MediaType.valueOf(mimeType));

                return new ResponseEntity<>(image, enTete, HttpStatus.OK);

            } catch (FileNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } catch (IOException e) {
                System.out.println("Le test du mimeType a échoué");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/admin/utilisateur")
    public ResponseEntity<Utilisateur> ajoutUtilisateur(
            @RequestPart("utilisateur") Utilisateur newUtilisateur,
            @Nullable @RequestParam("fichier") MultipartFile fichier
            ){
        //pour récupérer l'information du corps (sur postman) de la requête on ajoute @Requestbody pour transformer en objet java qui sera insérer dans la BDD

        //si l'utilisateur fournit possède déjà un id
        if(newUtilisateur.getId() != null){
            Optional<Utilisateur> optional = utilisateurDao.findById(newUtilisateur.getId());

            //si c'est un update
            if(optional.isPresent()){
                Utilisateur userToUpdate = optional.get();
                userToUpdate.setLastname(newUtilisateur.getLastname());
                userToUpdate.setFirstname(newUtilisateur.getFirstname());
                userToUpdate.setEmail(newUtilisateur.getEmail());
                userToUpdate.setPays(newUtilisateur.getPays());

                utilisateurDao.save(userToUpdate);

                // permet ici d'être sûr de ne pas enregistrer une image si l'utilisateur change l'id pour un id qui n'existe pas et donc
                // se retrouver avec une possibilité de saturation à un moment
                // ici c'est pour si l'utilisateur fait une modification alors que la même boucle if après c'est si on ajoute un utilisateur
                if(fichier != null){
                    try {
                        fichierService.transfertVersDossierUpload(fichier, "image_profil");
                    } catch (IOException e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }

                return new ResponseEntity<>(newUtilisateur, HttpStatus.OK);
            }
            // s'il y a eu une tentative d'insertion d'un utilisateur avec un id qui n'existait pas
            return new ResponseEntity<>(newUtilisateur, HttpStatus.BAD_REQUEST);
        }
        Role role = new Role();
        role.setId(1);
        newUtilisateur.setRole(role);

        String passwordHache = passwordEncoder.encode("root");
        newUtilisateur.setPassword(passwordHache);

        //ici c'est si on rentre dans le cas de l'id = null et donc on fait un ajout d'utilisateur et non une modification
        if(fichier != null){
            try {
                String nomImage = UUID.randomUUID() + "_" + fichier.getOriginalFilename();
                newUtilisateur.setNomImageProfil(nomImage);
                fichierService.transfertVersDossierUpload(fichier, nomImage);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        utilisateurDao.save(newUtilisateur);
        return new ResponseEntity<>(newUtilisateur, HttpStatus.CREATED);
    }

    @DeleteMapping("/utilisateur/{id}")
    @JsonView(VueUtilisateur.class)
    public ResponseEntity<Utilisateur> supprimeUtilisateur(@PathVariable int id){
        Optional<Utilisateur> utilisateurAsupprimer = utilisateurDao.findById(id);
        if(utilisateurAsupprimer.isPresent()){
            utilisateurDao.deleteById(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
