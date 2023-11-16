package com.swagger.openapi.service.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserPatchTest {
    private UserPatch userPatch;

    @BeforeEach
    void setup(){
        userPatch= new UserPatch();
        userPatch.setId("123");
        userPatch.setFirst_name("John");
        userPatch.setSecond_name("Doe");
        userPatch.setFirst_surname("Smith");

    }
    @Test
    public void testGetSecondName() {
        // Suponiendo que la clase se llama MyClass y tiene un constructor que acepta second_name
        String expectedSecondName = userPatch.getSecond_name();
        // Ejecuta el método getSecond_name y verifica que el valor devuelto sea el esperado
        String actualSecondName = userPatch.getSecond_name();

        assertEquals(expectedSecondName, actualSecondName, "El segundo nombre no coincide");
    }
    @Test
    public void testGetId(){
        String userId = userPatch.getId();;

        String actualId = userPatch.getId();

        assertEquals(userId, actualId);
    }
    @Test
    public void testGetFirstName() {
        // Suponiendo que la clase se llama MyClass y tiene un constructor que acepta second_name
        String expectedFirstName = userPatch.getFirst_name();
        // Ejecuta el método getSecond_name y verifica que el valor devuelto sea el esperado
        String actualFirstName = userPatch.getFirst_name();

        assertEquals(expectedFirstName, actualFirstName, "El nombre no coincide");
    }
    @Test
    public void testGetFirstSureName() {
        // Suponiendo que la clase se llama MyClass y tiene un constructor que acepta second_name
        String expectedFirstSureName = userPatch.getFirst_surname();
        // Ejecuta el método getSecond_name y verifica que el valor devuelto sea el esperado
        String actualFirstSureName = userPatch.getFirst_surname();

        assertEquals(expectedFirstSureName, actualFirstSureName, "El apellido no coincide");
    }
    @Test
    public void testGetEmail() {
        // Suponiendo que la clase se llama MyClass y tiene un constructor que acepta second_name
        String expectedEmail = userPatch.getEmail();
        // Ejecuta el método getSecond_name y verifica que el valor devuelto sea el esperado
        String actualEmail = userPatch.getEmail();

        assertEquals(expectedEmail, actualEmail, "El email no coincide");
    }
}