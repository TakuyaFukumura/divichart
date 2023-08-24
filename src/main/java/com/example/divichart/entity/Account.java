package com.example.divichart.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;

    protected Account() {}

    public Account(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, name='%s', password='%s']",
                id, name, password);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String password() {
        return password;
    }

}
