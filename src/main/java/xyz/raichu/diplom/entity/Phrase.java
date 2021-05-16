package xyz.raichu.diplom.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 16.05.2021
 * Phrase
 * 17:02
 */
@Entity
@Data
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Code> codes = new HashSet<>();
}
