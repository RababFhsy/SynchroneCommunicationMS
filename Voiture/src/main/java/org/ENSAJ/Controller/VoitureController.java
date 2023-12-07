package org.ENSAJ.Controller;

import org.ENSAJ.Model.Voiture;
import org.ENSAJ.Repository.VoitureRepository;
import org.ENSAJ.Service.VoitureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoitureController {

    @Autowired
    VoitureRepository voitureRepository;

    @Autowired
    VoitureService voitureService;

    @GetMapping(value ="/voitures", produces = {"application/json"})
    public ResponseEntity<List<Voiture>> chercherVoiture(){
        return ResponseEntity.ok(voitureRepository.findAll());
    }

    @GetMapping("/voitures/{Id}")
    public Voiture chercherUneVoiture(@PathVariable Long Id) throws Exception{
        return voitureRepository.findById(Id).orElseThrow(() -> new Exception("Voiture Introuvable"));
    }

    @GetMapping("/voitures/client/{Id}")
    public List<Voiture> chercherVoitureParClient(@PathVariable Long Id){
        return voitureRepository.findByClientId(Id);
    }

    @PostMapping("/voitures")
    public Voiture enregistrerUneVoiture(@RequestBody Voiture voiture){
        return voitureService.enregistrerVoiture(voiture);
    }



}
