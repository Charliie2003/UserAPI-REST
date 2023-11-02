package com.swagger.openapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.swagger.openapi.repository.UserRepository;
import com.swagger.openapi.service.entity.User;
import com.swagger.openapi.service.dto.BodyUserPost;
import com.swagger.openapi.service.dto.BodyUserPut;
import com.swagger.openapi.service.entity.UserPatch;
import com.swagger.openapi.service.validation.UserNotFoundException;
import com.swagger.openapi.service.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    private final String baseURL8081 = "http://localhost:8081/users/";



    //POST
    public User createUser(BodyUserPost bodyUserPost) {
        User newUser = new User();
        if (userValidator.postIsValid(bodyUserPost)) {
            newUser.setFirst_name(bodyUserPost.getFirst_name());
            newUser.setSecond_name(bodyUserPost.getSecond_name());
            newUser.setFirst_surname(bodyUserPost.getFirst_surname());
            newUser.setEmail(bodyUserPost.getEmail());
            newUser.setSex(bodyUserPost.getSex());
            newUser.setSexual_orientation(bodyUserPost.getSexual_orientation());
            newUser.setPhysical_features(bodyUserPost.getPhysical_features());
            newUser.setBirth_date(bodyUserPost.getBirth_date());
            newUser.setMoney(bodyUserPost.getMoney());
            newUser = userRepository.save(newUser);
            return newUser;
        }
        return null;
    }

    //PATCH USER 8080

    public User applyPatchToUser(JsonPatch patch, User user) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }

    public void updateUserPatch(User updatedUser) {
        userRepository.save(updatedUser);
    }
    public User applyPatchToUser8080(String idUser, JsonPatch patch) throws JsonPatchException, JsonProcessingException {
        User user = userRepository.findById(idUser).orElseThrow(UserNotFoundException::new);
        User userPatched = applyPatchToUser(patch, user);
        updateUserPatch(userPatched);
        return userPatched;
    }
    //PATCH USER 8081
    public UserPatch getUserByIdFrom8081(String idUser) {
        String url = baseURL8081 + idUser;
        try {
            ResponseEntity<UserPatch> response = restTemplate.exchange(url, HttpMethod.GET, null, UserPatch.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (HttpClientErrorException.NotFound e) {
            // El usuario no se encontró en la API en el puerto 8080
        }
        return null;
    }
    public void applyPatchToUser8081(String idUser, JsonPatch patch) throws JsonPatchException {
        String url = baseURL8081 + idUser;

        // Obtén el usuario existente
        UserPatch user = getUserByIdFrom8081(idUser);

        if (user != null) {
            // Convierte el JsonPatch a una representación JSON
            JsonNode patchNode = patch.apply(objectMapper.convertValue(user, JsonNode.class));
            HttpEntity<JsonNode> requestEntity = new HttpEntity<>(patchNode);

            try {
            ResponseEntity<UserPatch> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity,UserPatch.class);

            } catch (HttpClientErrorException e) {
                //error
            }
        }
    }


    //GET
    public User getUserById(String idUser) {
        return userRepository.findById(idUser).orElse(null);
    }

    //PUT
    public User updateUser(String idUser, BodyUserPut bodyUserPut) {
        // Buscar al usuario actual por su ID en MongoDB
        User user = userRepository.findById(idUser).orElse(null);

        if (userValidator.putIsValid(bodyUserPut)) {
            user.setFirst_name(bodyUserPut.getFirst_name());
            user.setSecond_name(bodyUserPut.getSecond_name());
            user.setFirst_surname(bodyUserPut.getFirst_surname());
            user.setEmail(bodyUserPut.getEmail());
            user.setSex(bodyUserPut.getSex());
            user.setSexual_orientation(bodyUserPut.getSexual_orientation());
            user.setSexual_orientation(bodyUserPut.getSexual_orientation());
            user.setPhysical_features(bodyUserPut.getPhysical_features());
            user.setBirth_date(bodyUserPut.getBirth_date());
            user.setMoney(bodyUserPut.getMoney());
            return userRepository.save(user);
        }

        return null;
    }

    //Delete
    public boolean deleteUserById(String idUser) {
        User user = userRepository.findById(idUser).orElse(null);

        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}
