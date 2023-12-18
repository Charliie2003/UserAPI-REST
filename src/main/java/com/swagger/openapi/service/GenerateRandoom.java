package com.swagger.openapi.service;

import com.swagger.openapi.service.entity.User;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class GenerateRandoom {
    private Random random = new Random();
    public GenerateRandoom(Random random) {
        this.random = random;
    }

    public List<String> generateRandomPhysicalFeatures() {
        int numberOfFeatures = 3; // Por ejemplo, generar 3 características físicas
        List<String> physicalFeatures = new ArrayList<>();

        for (int i = 0; i < numberOfFeatures; i++) {
            physicalFeatures.add(generateRandomString());
        }

        return physicalFeatures;
    }

    public String generateRandomString() {
        int length = 10; // Define la longitud de la cadena aleatoria
        boolean useLetters = true; // Incluir letras
        boolean useNumbers = false; // Incluir números

        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
    public String generateRandomSex() {
        List<String> sexes = Arrays.asList("Masculino", "Femenino");
        return sexes.stream()
                .skip(random.nextInt(sexes.size()))
                .findFirst()
                .orElse(null); // or any default value
    }

    public String generateSexualOrientation() {
        List<String> orientations = Arrays.asList("HeteroSexual", "Homosexual");
        return orientations.stream()
                .skip(random.nextInt(orientations.size()))
                .findFirst()
                .orElse(null); // or any default value
    }
    public Date generateRandomBirthDate() {
        // Generar una fecha de nacimiento aleatoria dentro de un rango permitido
        int minDay = (int) LocalDate.of(1950, 1, 1).toEpochDay();
        int maxDay = (int) LocalDate.of(2000, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextInt(maxDay - minDay);

        // Convertir LocalDate a Date
        LocalDate randomBirthDate = LocalDate.ofEpochDay(randomDay);
        return Date.from(randomBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
