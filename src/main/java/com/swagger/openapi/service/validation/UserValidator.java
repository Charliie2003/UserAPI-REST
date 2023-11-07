package com.swagger.openapi.service.validation;

import com.swagger.openapi.service.dto.BodyUserPost;
import com.swagger.openapi.service.dto.BodyUserPut;
import com.swagger.openapi.service.entity.User;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    public boolean postIsValid(BodyUserPost bodyUserPost) {
        String email;
        double money;
        if (bodyUserPost == null) {
            return false;
        }
        if (containsInvalidString(bodyUserPost.getFirst_name()) ||
                containsInvalidString(bodyUserPost.getSecond_name()) ||
                containsInvalidString(bodyUserPost.getFirst_surname()) ||
                containsInvalidString(bodyUserPost.getEmail()) ||
                containsInvalidString(bodyUserPost.getSex()) ||
                containsInvalidString(bodyUserPost.getSexual_orientation()) ||
                containsInvalidString(bodyUserPost.getEmail())) {
            return false;
        }
        // Validar y corregir el correo electrónico
       email = bodyUserPost.getEmail();
        if (!isValidEmail(email)) {
            // Agregar ".com" al final si no está presente
            email = email + ".com";
            bodyUserPost.setEmail(email);
        }

        //Veirfica si el correo acaba con "hotmail" o "outlook"
        if (email.endsWith("hotmail.com")){
            //Multiplica money x2
            money = bodyUserPost.getMoney() * 2;
            bodyUserPost.setMoney(money);
        }else if(email.endsWith("outlook.com")){
            //Divide money entre 2
            money =bodyUserPost.getMoney() / 2;
            bodyUserPost.setMoney(money);
        }
        return true;
    }
    public boolean putIsValid(BodyUserPut bodyUserPut){
        if(bodyUserPut == null ){
            return false;
        }
        if (containsInvalidString(bodyUserPut.getFirst_name()) ||
                containsInvalidString(bodyUserPut.getSecond_name()) ||
                containsInvalidString(bodyUserPut.getFirst_surname()) ||
                containsInvalidString(bodyUserPut.getEmail()) ||
                containsInvalidString(bodyUserPut.getSex()) ||
                containsInvalidString(bodyUserPut.getSexual_orientation()) ||
                containsInvalidString(bodyUserPut.getEmail())) {
            return false;
        }
        return true;

    }

    private boolean containsInvalidString(String str) {
        return str != null && str.trim().equalsIgnoreCase("string");
    }

    private boolean isValidEmail(String email) {
        // Patrón de expresión regular para verificar una dirección de correo electrónico
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }
}
