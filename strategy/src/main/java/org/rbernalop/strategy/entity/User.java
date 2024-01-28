package org.rbernalop.strategy.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Setter
@Getter
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int age;
    private String relationshipStatus;
}
