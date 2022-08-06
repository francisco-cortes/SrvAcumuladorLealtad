package com.baz.lealtad.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponse {
    String refresh_token_expires_in;
    String api_product_list;
    String[] api_product_list_json;
    String organization_name;
    String developer_email;
    String token_type;
    String issued_at;
    String client_id;
    String access_token;
    String scope;
    String expires_in;
    String refresh_count;
    String status;
}
