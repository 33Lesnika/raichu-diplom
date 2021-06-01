package xyz.raichu.diplom.model;

import lombok.Data;

/**
 * 02.06.2021
 * ChangePasswordModel
 * 02:43
 */
@Data
public class ChangePasswordRequest {
    private String newusername;
    private String pass;
    private String passRepeat;
}
