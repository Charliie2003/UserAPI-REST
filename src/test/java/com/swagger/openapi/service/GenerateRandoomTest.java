package com.swagger.openapi.service;

import com.swagger.openapi.service.entity.User;
import com.swagger.openapi.service.entity.UserPatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateRandoomTest {
    @Mock
    private Random mockedRandom;
    private GenerateRandoom generateRandoom;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        // Crear una instancia de GenerateRandoom con el mock de Random
        generateRandoom = new GenerateRandoom(mockedRandom);

    }
    @Test
    public void testGenerateRandomSex() {

        when(mockedRandom.nextInt(Mockito.anyInt())).thenReturn(0);

        String result = generateRandoom.generateRandomSex();
        assertEquals("Masculino", result);

        when(mockedRandom.nextInt(Mockito.anyInt())).thenReturn(1);

        result = generateRandoom.generateRandomSex();
        assertEquals("Femenino", result);
    }
    @Test
    public void testGenerateRandomSexualOrientation() {

        when(mockedRandom.nextInt(Mockito.anyInt())).thenReturn(0);
        String result = generateRandoom.generateSexualOrientation();
        assertEquals("HeteroSexual", result);

        when(mockedRandom.nextInt(Mockito.anyInt())).thenReturn(1);

        result = generateRandoom.generateSexualOrientation();
        assertEquals("Homosexual", result);
    }
    @Test
    public void testGenerateRandomBirthDate() {
        // Configurar el comportamiento del mock para devolver un valor específico
        // Este valor debe estar dentro del rango de días entre 1950 y 2000
        int minDay = (int) LocalDate.of(1950, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        int sampleDay = minDay + (maxDay - minDay) / 2; // Un día de muestra dentro del rango
        when(mockedRandom.nextInt(maxDay - minDay)).thenReturn(sampleDay - minDay);

        // Llamar al método y verificar que la fecha esté dentro del rango
        Date result = generateRandoom.generateRandomBirthDate();
        LocalDate resultDate = result.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        assertTrue(resultDate.isAfter(LocalDate.of(1950, 1, 1)) && resultDate.isBefore(LocalDate.of(2000, 1, 1)));
    }
    @Test
    public void testGenerateRandomString() {
        String result = generateRandoom.generateRandomString();

        assertEquals(10, result.length());

        assertTrue(result.matches("[a-zA-Z]+"));
    }
    @Test
    public void testGenerateRandomPhysicalFeatures() {
        List<String> features = generateRandoom.generateRandomPhysicalFeatures();

        // Verificar que la lista no es nula
        assertNotNull(features);

        // Verificar que la lista contiene exactamente 3 elementos
        assertEquals(3, features.size());

        // Verificar que cada elemento de la lista es una cadena no nula y tiene la longitud correcta
        for (String feature : features) {
            assertNotNull(feature);
            assertEquals(10, feature.length()); // Asumiendo que generateRandomString genera cadenas de longitud 10
        }
    }
}