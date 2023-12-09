package org.ENSAJ.Model;


import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Table(name = "voiture")
public class Voiture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "marque")
    private String marque;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "model")
    private String model;
    
    public Voiture(long l, String toyota, String s, String corolla, Long c2) {
    }
}
