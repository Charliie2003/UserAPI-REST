package com.swagger.openapi.service.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BodyUserPutTest {

    BodyUserPut bodyUserPut;
    @BeforeEach
    void setUp() {
        bodyUserPut = new BodyUserPut();
    }



    @Test
    void testSetSexualOrientation() {
        // Llamar al setter
        bodyUserPut.setSexual_orientation("orientación");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("orientación", bodyUserPut.getSexual_orientation());
    }
    @Test
    void testSetSex() {
        // Llamar al setter
        bodyUserPut.setSex("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPut.getSex());
    }
    @Test
    void testSetFirstSureName() {
        // Llamar al setter
        bodyUserPut.setFirst_surname("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPut.getFirst_surname());
    }
    @Test
    void testSetSecondName() {
        // Llamar al setter
        bodyUserPut.setSecond_name("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPut.getSecond_name());
    }

    @Test
    void testSetEmail() {
        // Llamar al setter
        bodyUserPut.setEmail("Hetero");

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals("Hetero", bodyUserPut.getEmail());
    }
    @Test
    void testSetMoney() {
        double money = 300;
        // Llamar al setter
        bodyUserPut.setMoney(money);

        // Verificar que el setter fue llamado con el argumento correcto
        assertEquals(money, bodyUserPut.getMoney());
    }
    @Test
    void testSetPhysicalFeatures() {

        // Crear una lista de características físicas
        List<String> features = Arrays.asList("Alto", "Pelo castaño", "Ojos verdes");

        // Establecer la lista usando el setter
        bodyUserPut.setPhysical_features(features);

        // Verificar que la lista se ha establecido correctamente
        // Aquí asumimos que tienes un getter para physical_features
        assertEquals(features, bodyUserPut.getPhysical_features());
    }
    @Test
    void testBirthDateSetterAndGetter() {

        // Crear una fecha de nacimiento
        Date birthDate = new Date();  // Utiliza una fecha específica según sea necesario

        // Establecer la fecha de nacimiento usando el setter
        bodyUserPut.setBirth_date(birthDate);

        // Verificar que el getter retorna la fecha correcta
        assertEquals(birthDate, bodyUserPut.getBirth_date());
    }

}