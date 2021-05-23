package xyz.raichu.diplom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 16.05.2021
 * Code
 * 17:04
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String code;
}
