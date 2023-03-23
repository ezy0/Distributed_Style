package es.ssdd.Practica.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Shop {
    private Long id;
    private String name;

    public Shop(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
