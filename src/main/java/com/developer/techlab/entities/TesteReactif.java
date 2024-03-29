package com.developer.techlab.entities;

import lombok.*;
import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "analyse_reactif_table")
public class TesteReactif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "quantite")
    private int quantite;

    @ManyToOne
    @JoinColumn(name = "teste_id")
    private Teste teste;

    @ManyToOne
    @JoinColumn(name = "reactif_id")
    private Reactif reactif;

}
