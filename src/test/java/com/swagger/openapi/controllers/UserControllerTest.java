package com.swagger.openapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.swagger.openapi.repository.UserRepository;
import com.swagger.openapi.service.UserService;
import com.swagger.openapi.service.dto.BodyUserPost;
import com.swagger.openapi.service.dto.BodyUserPut;
import com.swagger.openapi.service.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;


    @InjectMocks
    private UserController userController;
    private User user;

    private BodyUserPost bodyUserPost;

    private BodyUserPut bodyUserPut;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        // Crear un usuario para utilizar en la prueba
        user = new User();
        bodyUserPost = new BodyUserPost();
        bodyUserPut = new BodyUserPut();
        user.setId("123");
        user.setFirst_name("John");
        user.setSecond_name("Doe");
        user.setFirst_surname("Smith");
        user.setEmail("john.doe@example.com");
        user.setSex("Male");
        user.setSexual_orientation("Heterosexual");
        user.setPhysical_features(Arrays.asList("Tall", "Slim", "Athletic"));
        user.setBirth_date(new Date());
        user.setExpireAt(new Date(System.currentTimeMillis() + 3600000));
        user.setMoney(100.0);
    }
    //POST
    @Test
    public void whenCreateUser_thenUserIsCreated() {

        when(userService.createUser(any(BodyUserPost.class))).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(bodyUserPost);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }
    //POST LOADUSERS
    @Test
    public void testLoadPost() {
        int numberOfUsers = 5;
        List<User> mockUsers = new ArrayList<>();
        for (int i = 0; i < numberOfUsers; i++) {
            mockUsers.add(new User()); // Añade usuarios simulados a la lista
        }

        // Configurar el mock de userService para devolver la lista de usuarios simulados
        when(userService.createRandomUsers(numberOfUsers)).thenReturn(mockUsers);

        // Llamar al método loadPost
        ResponseEntity<List<User>> response = userController.loadPost(numberOfUsers);

        // Verificar que la respuesta es correcta
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(numberOfUsers, response.getBody().size());
    }
    //GET
    @Test
    public void whenGetUserWithValidId_thenUserIsReturned() {
        String idUser = user.getId();


        when(userService.getUserById(idUser)).thenReturn(user);

        ResponseEntity<User> response = userController.getUser(idUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }

    @Test
    public void whenGetUserWithInvalidId_thenNotFound() {
        String idUser = "423";

        when(userService.getUserById(idUser)).thenReturn(null);

        ResponseEntity<User> response = userController.getUser(idUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //DELETE
    @Test
    public void whenDeleteUserWithValidId_thenNoContent() {
        String idUser = user.getId();

        when(userService.deleteUserById(idUser)).thenReturn(true);

        ResponseEntity<Void> response = userController.deleteUserByID(idUser);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void whenDeleteUserWithInvalidId_thenNotFound() {
        String idUser = "423";

        when(userService.deleteUserById(idUser)).thenReturn(false);

        ResponseEntity<Void> response = userController.deleteUserByID(idUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    //PUT
    @Test
    public void whenUpdateExistingUser_thenUserIsReturned() {
        String idUser = user.getId();

        User updatedUser = new User(); // Configura con datos de usuario actualizados

        when(userService.updateUser(eq(idUser), any(BodyUserPut.class))).thenReturn(updatedUser);

        ResponseEntity<User> response = userController.updateUser(idUser, bodyUserPut);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, response.getBody());
    }
    @Test
    public void whenUpdateNonExistingUser_thenNotFound() {
        String idUser = "456";

        when(userService.updateUser(eq(idUser), any(BodyUserPut.class))).thenReturn(null);

        ResponseEntity<User> response = userController.updateUser(idUser, bodyUserPut);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void whenPatchExistingUser_thenUserIsPatched() throws JsonPatchException, IOException {
        String idUser = user.getId();
        String patchString = "[{\"op\": \"replace\", \"path\": \"/first_name\", \"value\": \"Jane\"}]";
        JsonPatch patch = JsonPatch.fromJson(new ObjectMapper().readTree(patchString));


        when(userService.applyPatchToUser8080(eq(idUser), any(JsonPatch.class))).thenReturn(user);
        doNothing().when(userService).applyPatchToUser8081(eq(idUser), any(JsonPatch.class));

        ResponseEntity<User> response = userController.partialUpdateUser(idUser, patch);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user, response.getBody());
    }








}