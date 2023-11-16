package com.swagger.openapi.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;


import com.swagger.openapi.repository.UserRepository;
import com.swagger.openapi.service.dto.BodyUserPost;
import com.swagger.openapi.service.dto.BodyUserPut;
import com.swagger.openapi.service.entity.User;
import com.swagger.openapi.service.entity.UserPatch;
import com.swagger.openapi.service.validation.UserNotFoundException;
import com.swagger.openapi.service.validation.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidator userValidator;

    @Mock
    private ObjectMapper objectMapper;
    @Spy
    @InjectMocks
    private UserService userService;
    @Mock
    private RestTemplate restTemplate;

    private User user;


    private UserPatch userPatch8081;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        // Crear un usuario para utilizar en la prueba
        user = new User();
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
        userPatch8081 = new UserPatch();
        userPatch8081.setId("123");
        userPatch8081.setFirst_name("John");
        userPatch8081.setSecond_name("Doe");
        userPatch8081.setFirst_surname("Smith");
        userPatch8081.setEmail("john.doe@example.com");

    }

    //POST
    @Test
    void testCreateUser() {
        BodyUserPost validBodyUserPost = new BodyUserPost();
        // Configuración simulada de userValidator
        when(userValidator.postIsValid(validBodyUserPost)).thenReturn(true);

        // Configuración simulada de userRepository
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Llamar al método que se está probando
        User createdUser = userService.createUser(validBodyUserPost);

        // Verificar que el usuario se haya guardado en el repositorio
        verify(userRepository).save(any(User.class));


        assertNotNull(createdUser); // El usuario creado no debe ser nulo
    }

    @Test
    void testCreateUserNull() {
        BodyUserPost invalidUserPost = new BodyUserPost();

        //Configuración simulada de userValidator para un user null
        when(userValidator.postIsValid(invalidUserPost)).thenReturn(false);

        //Lama al método con datos inválidos
        User createdUser = userService.createUser(invalidUserPost);

        //Revisa que si es null el usuario no lo guarde
        verify(userRepository, never()).save(any(User.class));
        assertNull(createdUser); // el usuario debe ser nulo
    }

    //GET
    @Test
    void testGetUserById() {
        String userId = user.getId();

        //Simula la busqueda de usuario por Id
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //Lama al método findByID
        User retrievedUser = userService.getUserById(userId);

        assertEquals(user, retrievedUser);//Comprueba que el usuario de prueba sea el mismo que el que devuelve

        // Verifica que el tiempo de expiración se haya aumentado en 1 día
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(user.getExpireAt());
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        verify(userRepository).save(user);
        assertNotEquals(calendar.getTime(), user.getExpireAt());
    }

    @Test
    void testGetUserByIdInvalidUser() {
        String userId = "456";

        // Simula la búsqueda de usuario por ID que no existe
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Llama al método que se está probando y verifica que lance la excepción UserNotFoundException
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));

        // Verifica que no se haya llamado a userRepository.save
        verify(userRepository, never()).save(any(User.class));
    }

    //PUT
    @Test
    void testUpdateUserValid() {
        String userId = user.getId();
        String newName = "Charlie";
        BodyUserPut validBodyUserPut = new BodyUserPut();
        validBodyUserPut.setFirst_name(newName);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(userValidator.putIsValid(validBodyUserPut)).thenReturn(true);

        User updateUser = userService.updateUser(userId, validBodyUserPut);

        assertEquals(newName, user.getFirst_name());

    }

    @Test
    void testUpdateUserExpiredUser() {
        String userId = user.getId();
        BodyUserPut validBodyUserPut = new BodyUserPut();
        validBodyUserPut.setFirst_name("Charlie");

        // Simula la búsqueda de usuario por ID
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Simula una fecha de expiración que ya ha pasado
        user.setExpireAt(new Date(System.currentTimeMillis() - 3600000)); // 1 hora en el pasado

        // Llama al método que se está probando y verifica que lance la excepción
        assertThrows(RuntimeException.class, () -> userService.updateUser(userId, validBodyUserPut));

        // Verifica que no se haya llamado a userRepository.save
        verify(userRepository, never()).save(user);
    }

    @Test
    void testUpdateUserInvalidData() {
        String userId = user.getId();
        BodyUserPut invalidBodyUserPut = new BodyUserPut();

        // Simula la búsqueda de usuario por ID
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Simula la validación fallida
        when(userValidator.putIsValid(invalidBodyUserPut)).thenReturn(false);

        // Llama al método que se está probando
        User updatedUser = userService.updateUser(userId, invalidBodyUserPut);

        // Verifica que no se haya llamado a userRepository.save
        verify(userRepository, never()).save(user);

        // Verifica que el resultado sea nulo
        assertNull(updatedUser);
    }

    //DELETE
    @Test
    public void testDeleteUserByIdTrue() {
        String userId = user.getId();

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        boolean result = userService.deleteUserById(userId);

        assertTrue(result);

        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUserByIdFalse() {
        String userId = "456";

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());

        boolean result = userService.deleteUserById(userId);

        assertFalse(result);

        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    public void testUpdateUserPatch() {
        user.setFirst_name("Charlie");
        user.setSecond_name("Hinojosa");

        when(userRepository.save(user)).thenReturn(user);

        userService.updateUserPatch(user);

        verify(userRepository).save(user);

    }

//PATCH USER 8080
    @Test
    void applyPatchToUser_ShouldUpdateUser() throws JsonPatchException, IOException {
        //Crear un JsonPatch para la prueba
        String patchString = "[{\"op\": \"replace\", \"path\": \"/first_name\", \"value\": \"Jane\"}]";
        JsonPatch patch = JsonPatch.fromJson(new ObjectMapper().readTree(patchString));

        //Mockear el comportamiento del objectMapper para convertir User a JsonNode
        JsonNode userNode = new ObjectMapper().convertValue(user, JsonNode.class);
        when(objectMapper.convertValue(any(User.class), eq(JsonNode.class))).thenReturn(userNode);

        //Mockear el objectMapper para convertir un JsonNode de vuelta a un User
        when(objectMapper.treeToValue(any(JsonNode.class), eq(User.class))).thenAnswer(invocation -> {
            JsonNode node = (JsonNode) invocation.getArguments()[0];
            return new ObjectMapper().treeToValue(node, User.class); // Convertir el nodo modificado de vuelta a User
        });

        //Llamar al método bajo prueba
        User result = userService.applyPatchToUser(patch, user);

        // Verificar el resultado
        assertEquals("Jane", result.getFirst_name());
    }
    @Test
    void whenApplyPatchToUser8080Called_ThenPatchAndSaveUser() throws JsonPatchException, IOException {
        // Given
        String idUser = "123";
        String patchString = "[{\"op\": \"replace\", \"path\": \"/first_name\", \"value\": \"Jane\"}]";
        JsonPatch patch = JsonPatch.fromJson(new ObjectMapper().readTree(patchString));


        when(userRepository.findById(idUser)).thenReturn(Optional.of(user));
        when(objectMapper.convertValue(any(User.class), eq(JsonNode.class))).thenReturn(new ObjectMapper().convertValue(user, JsonNode.class));
        when(objectMapper.treeToValue(any(JsonNode.class), eq(User.class))).thenAnswer(invocation -> {
            JsonNode node = (JsonNode) invocation.getArguments()[0];
            return new ObjectMapper().treeToValue(node, User.class); // Convertir el nodo modificado de vuelta a User
        });

        // When
        User patchedUser = userService.applyPatchToUser8080(idUser, patch);

        // Then
        assertEquals("Jane", patchedUser.getFirst_name()); // Asegurar que el nombre se ha actualizado
        verify(userRepository).save(patchedUser); // Verificar que el usuario modificado se guarda en el repositorio
    }
    //PATCH 8081

    @Test
    public void getUserByIdFrom8081_ShouldReturnNull_WhenUserNotFound() throws HttpClientErrorException {
        String url = "http://localhost:8081/users/" + "456";

        ResponseEntity<UserPatch> myEntity = new ResponseEntity<>(userPatch8081, HttpStatus.NOT_FOUND);
        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(),
                eq(UserPatch.class))
        ).thenReturn(myEntity);

        UserPatch result = userService.getUserByIdFrom8081("456");
        assertNull(result);
    }
    @Test
    public void getUserByIdFrom8081_ShouldReturnUserPatch_WhenUserExists() {

        String url = "http://localhost:8081/users/" + "123";

        ResponseEntity<UserPatch> myEntity = new ResponseEntity<>(userPatch8081, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                any(),
                eq(UserPatch.class))
        ).thenReturn(myEntity);


        UserPatch result = userService.getUserByIdFrom8081("123");

        assertNotNull(result);
        assertEquals(userPatch8081, result);
    }

   @Test
   public void patchApply8081() throws IOException, JsonPatchException {
       String idUser = "123";
       String patchString = "[{\"op\": \"replace\", \"path\": \"/first_name\", \"value\": \"Jane\"}]";
       JsonPatch patch = JsonPatch.fromJson(new ObjectMapper().readTree(patchString));

       ResponseEntity<UserPatch> myEntity = new ResponseEntity<>(userPatch8081, HttpStatus.OK);

       when(restTemplate.exchange(
               anyString(),
               eq(HttpMethod.GET),
               any(),
               eq(UserPatch.class))
       ).thenReturn(myEntity);

       when(userService.getUserByIdFrom8081(idUser)).thenReturn(userPatch8081);


       JsonNode userNode = objectMapper.convertValue(userPatch8081, JsonNode.class);
       HttpEntity<JsonNode> requestEntity = new HttpEntity<>(userNode);

       ResponseEntity<UserPatch> mockResponse = new ResponseEntity<>(userPatch8081, HttpStatus.OK);
       when(restTemplate.exchange(
               eq("http://localhost:8081/users/" + idUser),
               eq(HttpMethod.PUT),
               eq(requestEntity),
               eq(UserPatch.class)
       )).thenReturn(mockResponse);

       userService.applyPatchToUser8081(idUser, patch);

   }


}