package com.ecusol.core.model;

import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class Feriado {

    @Field("date")
    private LocalDate date;

    @Field("name")
    private String name;

    public Feriado() {}

    public Feriado(LocalDate date, String name) {
        this.date = date;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Feriado{date=" + date + ", name='" + name + "'}";
    }
}