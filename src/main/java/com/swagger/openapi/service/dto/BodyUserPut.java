package com.swagger.openapi.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Date;
import java.util.List;


public class BodyUserPut {
    @Schema(description = "Primer nombre del usuario")
    @Size(max = 30, message = "The name must be have 30 character max")
    @NotNull(message = "The first name is required (is null)")
    @NotEmpty(message = "The first name is required (is empty)")
    @NotBlank(message = "The first name is required (is blank)")
    private String first_name;

    @Schema(description = "Segundo nombre del usuario")
    @Size(max = 30, message = "The second name must be have 30 character max")
    @NotBlank(message = "The second name is required (is blank)")
    @NotNull(message = "The second name is required (is null)")
    @NotEmpty(message = "The second name is required (is empty)")
    private String second_name;

    @Schema(description = "Primer Apellido del usuario")
    @Size(max = 30, message = "The first sure name must be have 30 character max")
    @NotBlank(message = "The first sure name is required (is blank)")
    @NotNull(message = "The first sure name is required (is null)")
    @NotEmpty(message = "The first sure name is required (is empty)")
    private String first_surname;

    @Schema(description = "Correo electrónico del usuario")
    @Email(message = "Thi must be a valid email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotBlank(message = "The email is required (is blank)")
    @NotNull(message = "The email name is required (is null)")
    @NotEmpty(message = "The email sure name is required (is empty)")
    private String email;

    @Schema(description = "Sexo del usuario")
    @Size(max = 30, message = "The sex must be have 30 character max")
    @NotBlank(message = "The sex is required (is blank)")
    @NotNull(message = "The sex is required (is null)")
    @NotEmpty(message = "The sex name is required (is empty)")
    private String sex;

    @Schema(description = "Orientación sexual del usuario")
    @Size(max = 30, message = "The sexual orientation must be have 30 character max")
    @NotBlank(message = "The sexual orientation is required (is blank)")
    @NotNull(message = "The sexual orientation is required (is null)")
    @NotEmpty(message = "The sexual orientation name is required (is empty)")
    private String sexual_orientation;

    @Schema(description = "Rasgos Físicos del usuario")

    private List<String> physical_features;


    // Getters y setters para cada campo

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }

    public String getFirst_surname() {
        return first_surname;
    }

    public void setFirst_surname(String first_surname) {
        this.first_surname = first_surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSexual_orientation() {
        return sexual_orientation;
    }

    public void setSexual_orientation(String sexual_orientation) {
        this.sexual_orientation = sexual_orientation;
    }

    public List<String> getPhysical_features() {
        return physical_features;
    }

    public void setPhysical_features(List<String> physical_features) {
        this.physical_features = physical_features;
    }
}

