package com.swagger.openapi.service.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BodyUserPostTest {
    BodyUserPost bodyUserPost;
    @BeforeEach
    void setUp() {
        bodyUserPost = new BodyUserPost();
    }

    @Test
    void testSetFirstSureName() {
        // Llamar al setter
        bodyUserPost.setFirst_surname("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPost.getFirst_surname());
    }
    @Test
    void testSetFirstName() {
        // Llamar al setter
        bodyUserPost.setFirst_name("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPost.getFirst_name());
    }
    @Test
    void testSecondName() {
        // Llamar al setter
        bodyUserPost.setSecond_name("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPost.getSecond_name());
    }
    @Test
    void testEmail() {
        // Llamar al setter
        bodyUserPost.setEmail("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPost.getEmail());
    }
    @Test
    void testSex() {
        // Llamar al setter
        bodyUserPost.setSex("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPost.getSex());
    }
    @Test
    void testSexualOrientation() {
        // Llamar al setter
        bodyUserPost.setSexual_orientation("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPost.getSexual_orientation());
    }
    @Test
    void testSetPhysicalFeatures() {

        // Crear una lista de características físicas
        List<String> features = Arrays.asList("Alto", "Pelo castaño", "Ojos verdes");

        // Establecer la lista usando el setter
        bodyUserPost.setPhysical_features(features);

        // Verificar que la lista se ha establecido correctamente
        // Aquí asumimos que tienes un getter para physical_features
        assertEquals(features, bodyUserPost.getPhysical_features());
    }
    @Test
    void testBirthDateSetterAndGetter() {

        // Crear una fecha de nacimiento
        Date birthDate = new Date();  // Utiliza una fecha específica según sea necesario

        // Establecer la fecha de nacimiento usando el setter
        bodyUserPost.setBirth_date(birthDate);

        // Verificar que el getter retorna la fecha correcta
        assertEquals(birthDate, bodyUserPost.getBirth_date());
    }
    @Test
    void testSetMoney() {
        double money = 300;
        // Llamar al setter
        bodyUserPost.setMoney(money);

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals(money, bodyUserPost.getMoney());
    }
}