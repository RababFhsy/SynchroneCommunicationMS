package org.ENSAJ.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ENSAJ.Client;


import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Voiture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricule;
    private String marque;
    private String model;
    private Long id_client;



    public Voiture(long l, String toyota, String s, String corolla, Client c2) {
    }
}
