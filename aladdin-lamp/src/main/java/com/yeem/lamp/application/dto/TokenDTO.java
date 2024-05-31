package com.yeem.lamp.application.dto;

import com.yeem.lamp.domain.objvalue.Token;
import lombok.Data;

@Data
public class TokenDTO {
    public String token;

    public TokenDTO(Token token) {
        this.token = token.getToken();
    }
}
