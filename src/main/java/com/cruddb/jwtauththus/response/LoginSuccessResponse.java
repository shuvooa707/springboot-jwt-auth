package com.cruddb.jwtauththus.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginSuccessResponse implements LoginResponse {
    public String message = "success";
    public String token;

    public static LoginSuccessResponse builder(String message, String error) {
        return new LoginSuccessResponse(message, error);
    }
}
