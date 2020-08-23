package com.mobeom.local_currency.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    private UserController userController;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);

        userController = new UserController(
                userService
        );
    }

    @Test
    void isIdCheckOk() {
        User user = new User();
        user.setUserId("realUserId");
        when(userService.findUserbyUserId(user.getUserId())).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.idCheck(user.getUserId());

        verify(userService).findUserbyUserId("realUserId");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void isIdCheckNotFound() {
        when(userService.findUserbyUserId(Mockito.anyString())).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.idCheck("abc");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void isCheckCurrentPasswordOk() {
        User user = new User();
        user.setPassword("password");
        when(userService.findUser(1L)).thenReturn(Optional.of(user));
        User requestUser = new User();
        requestUser.setPassword("password");

        ResponseEntity<User> result = userController.checkCurrentPassword("1", requestUser);

        verify(userService).findUser(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void isCheckCurrentPasswordBadRequest() {
        User user = new User();
        user.setPassword("correctPW");
        when(userService.findUser(1L)).thenReturn(Optional.of(user));
        User requestUser = new User();
        requestUser.setPassword("wrongPW");

        ResponseEntity<User> result = userController.checkCurrentPassword("1", requestUser);

        verify(userService).findUser(1L);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void isCheckCurrentPasswordNotFound() {
        User requestUser = new User();
        requestUser.setPassword("wrongPW");
        when(userService.findUser(Mockito.anyLong())).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.checkCurrentPassword("1", requestUser);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void isGetOneInfoOk() {
        User user = new User();
        when(userService.findUser(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.getOneInfo("1");

        verify(userService).findUser(1L);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void isGetOneInfoNotFound() {
        when(userService.findUser(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.getOneInfo("1");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void isUpdateUserOk() {
        User user = new User();
        User requestUser = new User();
        requestUser.setDefaultAddr("dAddr");
        requestUser.setOptionalAddr("oAddr");
        requestUser.setPassword("newPW");
        requestUser.setEmail("newEmail");
        when(userService.findUser(1L)).thenReturn(Optional.of(user));
        when(userService.update(user)).thenReturn(user);

        ResponseEntity<User> result = userController.updateUser("1", requestUser);

        verify(userService).findUser(1L);
        verify(userService).update(user);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void isUpdateUserNotFound() {
        User requestUser = new User();
        when(userService.findUser(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.updateUser("1", requestUser);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void isDeleteUserOk() {
        User user = new User();
        when(userService.findUser(1L)).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.deleteUser("1");

        verify(userService).delete(user);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void isDeleteUserNotFound() {
        when(userService.findUser(1L)).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.deleteUser("1");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void isLoginOk() {
        User user = new User();
        user.setUserId("correctId");
        user.setPassword("correctPW");
        User requestUser = new User();
        requestUser.setUserId("correctId");
        requestUser.setPassword("correctPW");
        when(userService.findUserbyUserId(requestUser.getUserId())).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.login(requestUser);

        verify(userService).findUserbyUserId(requestUser.getUserId());
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void isLoginBadRequest() {
        User user = new User();
        user.setUserId("correctId");
        user.setPassword("correctPW");
        User requestUser = new User();
        requestUser.setUserId("correctId");
        requestUser.setPassword("wrongPW");
        when(userService.findUserbyUserId(requestUser.getUserId())).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.login(requestUser);

        verify(userService).findUserbyUserId(requestUser.getUserId());
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void isLoginNotFound() {
        User requestUser = new User();
        requestUser.setUserId("wrongId");
        when(userService.findUserbyUserId(requestUser.getUserId())).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.login(requestUser);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void isCreateOk() {
        User user = new User();
        User requestUser = new User();
        when(userService.createUser(requestUser)).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.create(requestUser);

        verify(userService).createUser(requestUser);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void isCreateBadRequest() {
        User requestUser = new User();
        when(userService.createUser(requestUser)).thenReturn(Optional.empty());

        ResponseEntity<User> result =userController.create(requestUser);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void isFindIdOk() {
        User user = new User();
        when(userService.findUserbyNameAndEmail("userName", "userEmail")).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.findId("userName", "userEmail");

        verify(userService).findUserbyNameAndEmail("userName", "userEmail");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void isFindIdNotFound() {
        when(userService.findUserbyNameAndEmail("wrongName", "wrongEmail")).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.findId("wrongName", "wrongEmail");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void isCheckUserForResetPasswordOk() {
        User user = new User();
        when(userService.findUserForResetPassword(
                "userId",
                "userName",
                "userEmail")).thenReturn(Optional.of(user));

        ResponseEntity<User> result = userController.checkUserForResetPassword(
                "userId",
                "userName",
                "userEmail"
        );

        verify(userService).findUserForResetPassword(
                "userId",
                "userName",
                "userEmail");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(user, result.getBody());
    }

    @Test
    void isCheckUserForResetPasswordNotFound() {
        when(userService.findUserForResetPassword(
                "userId",
                "userName",
                "userEmail")
        ).thenReturn(Optional.empty());

        ResponseEntity<User> result = userController.checkUserForResetPassword(
                "userId",
                "userName",
                "userEmail"
        );

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}