package com.cruddb.jwtauththus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String username;
    private String password;
}
