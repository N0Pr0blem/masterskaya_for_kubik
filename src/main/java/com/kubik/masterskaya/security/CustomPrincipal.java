package com.kubik.masterskaya.security;

import lombok.Getter;

import java.security.Principal;

@Getter
public class CustomPrincipal implements Principal {
    private String name;
    private Long id;

}
