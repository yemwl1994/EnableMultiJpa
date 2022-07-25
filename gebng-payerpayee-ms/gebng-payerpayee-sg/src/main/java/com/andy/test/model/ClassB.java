package com.andy.test.model;

import javax.persistence.*;

@Entity
@Table(name="TableB", schema="x")
public class ClassB {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="col1")
    private String col1;

    @Column(name="col2")
    private String col2;

}
