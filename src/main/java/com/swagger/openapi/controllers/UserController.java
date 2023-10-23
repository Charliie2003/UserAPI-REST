package com.swagger.openapi.controllers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.swagger.openapi.repository.UserRepository;
import com.swagger.openapi.service.entity.User;
import com.swagger.openapi.service.UserService;
import com.swagger.openapi.service.dto.BodyUserPost;
import com.swagger.openapi.service.dto.BodyUserPut;
import com.swagger.openapi.service.validation.UserNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@Validated
@RequestMapping("/users")
@Tag(name = "users", description = "Maneja la información de los usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    @Operation(summary = "Crea un usuario asignandole una UUID y un TTL")
    public ResponseEntity<User> createUser(@Valid @RequestBody BodyUserPost bodyUserPost) {
        User newUser = userService.createUser(bodyUserPost);
        return ResponseEntity.ok(newUser);
    }
    @GetMapping("/{idUser}")
    @Operation(summary = "Obtiene la información de usuario por ID")
    public ResponseEntity<User> getUser(@Valid @PathVariable String idUser) {
        User user = userService.getUserById(idUser);
        return (user != null) ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{idUser}")
    @Operation(summary = "Actualiza la información de un usuario por ID")
    public ResponseEntity<User> updateUser(@Valid @PathVariable String idUser,@Valid @RequestBody BodyUserPut bodyUserPut) {
        User user = userService.updateUser(idUser, bodyUserPut);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }


    @PatchMapping(path= "/{idUser}", consumes = "application/json-patch+json")
    @Operation(summary = "Modifica parcialmente la información de un usuario por ID")
    public ResponseEntity<User> partialUpdateUser(@Valid @PathVariable String idUser, @Valid @RequestBody JsonPatch patch) {
        try {
            User user = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
            User userPatched = userService.applyPatchToUser(patch, user);
            userService.updateUserPatch(userPatched);
            return ResponseEntity.ok(userPatched);
        } catch (JsonPatchException | JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }

    @DeleteMapping("/{idUser}")
    @Operation(summary = "Elimina un usurio por su ID")
    public ResponseEntity<Void> deleteUserByID(@Valid @PathVariable String idUser){
        return userService.deleteUserById(idUser) ? ResponseEntity.noContent().build()  : ResponseEntity.notFound().build();

    }



}
