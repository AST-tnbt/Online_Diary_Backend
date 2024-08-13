package com.rest.webservices.restful_web_services.unittest;

import com.rest.webservices.restful_web_services.jpa.PostRepository;
import com.rest.webservices.restful_web_services.jpa.UserRepository;
import com.rest.webservices.restful_web_services.user.User;
import com.rest.webservices.restful_web_services.user.UserController;
import com.rest.webservices.restful_web_services.user.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Test
    public void getAllUserTest_2Users() {
        User user1 = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");
        User user2 = new User(2, "user2", "123", "Eva", LocalDate.parse("2005-02-27"), "Female");
        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userController.getAllUsers();

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(user1);
        assertThat(result.get(1)).isEqualTo(user2);
    }

    @Test
    public void getAllUserTest_0User() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> result = userController.getAllUsers();

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    public void getUserByIdTest_UserExisted() {
        User user1 = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");

        when(userRepository.findById(1)).thenReturn(Optional.of(user1));

        assertThat(userController.getUserById(1)).isEqualTo(user1);
    }

    @Test
    public void getUserByIdTest_UserNotExisted() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userController.getUserById(1));
    }

    @Test
    public void createUserTest() {
        User user = new User(null, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");
        User userSaved = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");

        when(userRepository.save(user)).thenReturn(userSaved);

        assertThat(userController.createUser(user)).isEqualTo(userSaved);
    }

    @Test
    public void updateUserTest() {
        User user = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");
        User userUpdated = new User(1, "user1", "123", "Adam", LocalDate.parse("2004-02-28"), "Male");

        when(userRepository.save(user)).thenReturn(userUpdated);

        assertThat(userController.updateUserDetail(1, user)).isEqualTo(userUpdated);
    }
}
