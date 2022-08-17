package com.example.CRUD.with.postgresql.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
}
