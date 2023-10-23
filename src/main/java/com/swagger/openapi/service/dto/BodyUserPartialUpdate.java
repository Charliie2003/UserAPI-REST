package com.swagger.openapi.service.dto;

import com.github.fge.jsonpatch.JsonPatchOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.List;

public class BodyUserPartialUpdate {
    /*@Size(max = 30, message = "The name must be have 30 character max")
    @NotNull(message = "The first name is required (is null)")
    @NotEmpty(message = "The first name is required (is empty)")
    @NotBlank(message = "The first name is required (is blank)")
    @Schema(description = "Primer nombre del usuario (opcional)")

    private String first_name;

    @Schema(description = "Segundo nombre del usuario (opcional)")
    @Size(max = 30, message = "The second name must be have 30 character max")
    @NotBlank(message = "The second name is required (is blank)")
    @NotNull(message = "The second name is required (is null)")
    @NotEmpty(message = "The second name is required (is empty)")
    private String second_name;

    @Schema(description = "Primer Apellido del usuario (opcional)")
    @Size(max = 30, message = "The first sure name must be have 30 character max")
    @NotBlank(message = "The first sure name is required (is blank)")
    @NotNull(message = "The first sure name is required (is null)")
    @NotEmpty(message = "The first sure name is required (is empty)")
    private String first_surname;

    @Schema(description = "Correo electrónico del usuario (opcional)")
    @Email(message = "This field must be a valid email")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @NotBlank(message = "The first sure name is required (is blank)")
    @NotNull(message = "The first sure name is required (is null)")
    @NotEmpty(message = "The first sure name is required (is empty)")
    private String email;

    // Getters y setters

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
    }*/
    private List<JsonPatchOperation> patchOperations;

    public List<JsonPatchOperation> getPatchOperations() {
        return patchOperations;
    }

    public void setPatchOperations(List<JsonPatchOperation> patchOperations) {
        this.patchOperations = patchOperations;
    }
}