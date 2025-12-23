package com.paras.tradeflow.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterRequest {

    private String name;

    private String email;

    private  String password;
}
