package com.andy.test.model;


import org.hibernate.annotations.OnDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TableA", schema="x")
public class ClassA {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name ="col1")
    private String col1;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name="col1", referencedColumnName="col1")
    private List<ClassB> objC = new ArrayList<>();
}
