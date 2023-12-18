package com.swagger.openapi.service.validation;

import com.swagger.openapi.service.UserService;
import com.swagger.openapi.service.dto.BodyUserPost;
import com.swagger.openapi.service.dto.BodyUserPut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserValidatorTest {

    @Spy
    @InjectMocks
    private UserValidator userValidator;
    @Mock
    private BodyUserPost bodyUserPost;
    @Mock
    private BodyUserPut bodyUserPut;


    @BeforeEach
    void setUp() {

    }
    //  InvalidString
    @Test
    void testContainsInvalidString_ShouldReturnTrueForInvalidString() {
        // Arrange
        String invalidString = "string";

        // Act
        boolean result = userValidator.containsInvalidString(invalidString);

        // Assert
        assertTrue(result);
    }

    @Test
    void testContainsInvalidString_ShouldReturnFalseForValidString() {
        // Arrange
        String validString = "valid";

        // Act
        boolean result = userValidator.containsInvalidString(validString);

        // Assert
        assertFalse(result);
    }

    @Test
    void testContainsInvalidString_ShouldReturnFalseForNull() {
        // Act
        boolean result = userValidator.containsInvalidString(null);

        // Assert
        assertFalse(result);
    }

    @Test
    void testContainsInvalidString_ShouldReturnFalseForEmptyString() {
        // Arrange
        String emptyString = "";

        // Act
        boolean result = userValidator.containsInvalidString(emptyString);

        // Assert
        assertFalse(result);
    }

    @Test
    void testContainsInvalidString_ShouldReturnFalseForWhitespaceString() {
        // Arrange
        String whitespaceString = "   ";

        // Act
        boolean result = userValidator.containsInvalidString(whitespaceString);

        // Assert
        assertFalse(result);
    }
    //VALID EMAIL
    @Test
    void whenEmailIsValid_thenCorrect() {
        assertTrue(userValidator.isValidEmail("example@example.com"));
    }

    @Test
    void whenEmailIsInvalid_thenIncorrect() {
        assertFalse(userValidator.isValidEmail("example.com"));
    }

    @Test
    void whenEmailIsMissingAtSymbol_thenIncorrect() {
        assertFalse(userValidator.isValidEmail("example.com"));
    }

    @Test
    void whenEmailHasMultipleAtSymbols_thenIncorrect() {
        assertFalse(userValidator.isValidEmail("example@@example.com"));
    }

    @Test
    void whenEmailHasInvalidDomain_thenIncorrect() {
        assertFalse(userValidator.isValidEmail("example@example.c"));
    }

    @Test
    void whenEmailHasSpecialCharacters_thenCorrectOrIncorrect() {
        assertTrue(userValidator.isValidEmail("user+name@example.com"));
        assertFalse(userValidator.isValidEmail("user+name@example.c"));
        assertFalse(userValidator.isValidEmail("user+name@.com"));
    }

    @Test
    void whenEmailHasWhitespace_thenIncorrect() {
        assertFalse(userValidator.isValidEmail(" example@example.com "));
        assertFalse(userValidator.isValidEmail("example @example.com"));
        assertFalse(userValidator.isValidEmail("example@ example.com"));
    }
    //PUT VALIDATOR
    @Test
    public void testPutIsValidWithValidInput() {
        // Arrange
        when(bodyUserPut.getFirst_name()).thenReturn("ValidFirstName");
        when(bodyUserPut.getSecond_name()).thenReturn("ValidSecondName");
        // Configura otros campos según sea necesario...

        // Act
        boolean result = userValidator.putIsValid(bodyUserPut);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testPutIsValidWithInvalidFirstName() {
        // Arrange
        when(bodyUserPut.getFirst_name()).thenReturn("String");
        // Configura otros campos según sea necesario...

        // Act
        boolean result = userValidator.putIsValid(bodyUserPut);

        // Assert
        assertFalse(result);
    }

    @Test
    public void testPutIsValidWithNullInput() {
        // Arrange
        bodyUserPut = null;

        // Act
        boolean result = userValidator.putIsValid(bodyUserPut);

        // Assert
        assertFalse(result);
    }
    //POST
    @Test
    void whenBodyUserPostIsNull_thenIncorrect() {
        assertFalse(userValidator.postIsValid(null));
    }

    @Test
    void whenBodyUserPostContainsInvalidString_thenIncorrect() {
       when(bodyUserPost.getFirst_name()).thenReturn("string");

        assertFalse(userValidator.postIsValid(bodyUserPost));
    }


    @Test
    void whenEmailEndsWithHotmail_thenMoneyIsDoubled() {
        when(bodyUserPost.getEmail()).thenReturn("user@hotmail.com");
        when(bodyUserPost.getMoney()).thenReturn(100.0);

        assertTrue(userValidator.postIsValid(bodyUserPost));
        verify(bodyUserPost).setMoney(200.0);
    }

    @Test
    void whenEmailEndsWithOutlook_thenMoneyIsHalved() {
        when(bodyUserPost.getEmail()).thenReturn("user@outlook.com");
        when(bodyUserPost.getMoney()).thenReturn(100.0);

        assertTrue(userValidator.postIsValid(bodyUserPost));
        verify(bodyUserPost).setMoney(50.0);
    }
    @Test
    void whenEmailIsNotValid_thenEmailIsCorrectedByAddingDotCom() {

        String initialEmail = "user@example";
        when(bodyUserPost.getEmail()).thenReturn(initialEmail);

        userValidator.isValidEmail(initialEmail);

        boolean result = userValidator.postIsValid(bodyUserPost);

        assertTrue(result);

        verify(bodyUserPost).setEmail(initialEmail + ".com");
    }




}