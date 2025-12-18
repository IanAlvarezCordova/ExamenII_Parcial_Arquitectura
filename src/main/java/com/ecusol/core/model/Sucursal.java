package com.ecusol.core.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "branch")
@Getter
@Setter
public class Sucursal {

    @Id
    private String id;

    @Field("emailAddress")
    private String emailAddress;

    @Field("name")
    private String name;

    @Field("phoneNumber")
    private String phoneNumber;

    @Field("state")
    private String state;

    @Field("creationDate")
    private LocalDateTime creationDate;

    @Field("lastModifiedDate")
    private LocalDateTime lastModifiedDate;

    @Field("branchHolidays")
    private List<Feriado> branchHolidays;

    public Sucursal() {
        this.branchHolidays = new ArrayList<>();
    }

    public Sucursal(String id) {
        this.id = id;
        this.branchHolidays = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sucursal sucursal = (Sucursal) o;
        return Objects.equals(id, sucursal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "id='" + id + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", state='" + state + '\'' +
                ", creationDate=" + creationDate +
                ", lastModifiedDate=" + lastModifiedDate +
                ", branchHolidays=" + branchHolidays +
                '}';
    }
}