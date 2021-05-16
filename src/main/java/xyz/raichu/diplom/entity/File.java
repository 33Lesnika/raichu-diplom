package xyz.raichu.diplom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 16.05.2021
 * File
 * 17:01
 */
@Entity
@Data
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Phrase> phrases = new HashSet<>();

    @ManyToOne
    @JoinTable(name = "users_to_files")
    User user;
}
