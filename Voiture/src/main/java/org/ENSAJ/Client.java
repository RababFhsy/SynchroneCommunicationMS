package org.ENSAJ;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;
    private String nom;
    private Float age;

}

