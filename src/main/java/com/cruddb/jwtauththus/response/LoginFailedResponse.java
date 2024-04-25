package com.cruddb.jwtauththus.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginFailedResponse implements LoginResponse {
    private String message = "failed";
    private String error;

    public static LoginFailedResponse builder(String message, String error) {
        return new LoginFailedResponse(message, error);
    }
}
