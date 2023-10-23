package com.swagger.openapi.service.validation;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuario no encontrado"); // Puedes personalizar el mensaje de la excepción según tus necesidades
    }
}
