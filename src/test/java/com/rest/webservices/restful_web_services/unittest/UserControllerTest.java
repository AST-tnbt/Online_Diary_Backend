package com.rest.webservices.restful_web_services.unittest;

import com.rest.webservices.restful_web_services.jpa.PostRepository;
import com.rest.webservices.restful_web_services.jpa.UserRepository;
import com.rest.webservices.restful_web_services.posts.Posts;
import com.rest.webservices.restful_web_services.posts.PostsNotFoundException;
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
import static org.mockito.Mockito.*;

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

    @Test
    public void getAllPostByUserTest_2Posts() {
        Posts post1 = new Posts(1, "Post 1", "Body post", LocalDate.parse("2024-08-13"));
        Posts post2 = new Posts(2, "Post 2", "Body post 2", LocalDate.parse("2024-05-13"));
        User user = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");
        user.setPosts(Arrays.asList(post1, post2));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        List<Posts> result = userController.getAllPostByUser(1);
        assertThat(result).size().isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(post1);
        assertThat(result.get(1)).isEqualTo(post2);
    }

    @Test
    public void getAllPostByUserTest_0Posts() {
        User user = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        List<Posts> result = userController.getAllPostByUser(1);
        assertThat(result).isNotNull().hasSize(0);
    }

    @Test
    public void getAllPostByUserTest_UserNotExisted() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.getAllPostByUser(1));
    }

    @Test
    public void createPostTest_UserExisted() {
        Posts post = new Posts(null, "Post 1", "Body post", LocalDate.parse("2024-08-13"));
        Posts postSaved = new Posts(1, "Post 1", "Body post", LocalDate.parse("2024-08-13"));
        User user = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(postRepository.save(post)).thenReturn(postSaved);

        assertThat(userController.createPost(1, post)).isEqualTo(postSaved);
    }

    @Test
    public void createPostTest_UserNotExisted() {
        Posts post = new Posts(null, "Post 1", "Body post", LocalDate.parse("2024-08-13"));

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.createPost(1, post));
    }

    @Test
    public void updatePostTest_HappyCase() {
        Posts post = new Posts(1, "Post 1", "Body post", LocalDate.parse("2024-08-13"));
        User user = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(postRepository.findById(1)).thenReturn(Optional.of(post));
        when(postRepository.save(post)).thenReturn(post);

        assertThat(userController.updatePost(1,1, post)).isEqualTo(post);
    }

    @Test
    public void updatePostTest_UserNotExisted() {
        Posts post = new Posts(1, "Post 1", "Body post", LocalDate.parse("2024-08-13"));

        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userController.updatePost(1, 1, post));
    }

    @Test
    public void updatePostTest_PostNotExisted() {
        User user = new User(1, "user1", "123", "Andy", LocalDate.parse("2004-02-27"), "Male");
        Posts post = new Posts(1, "Post 1", "Body post", LocalDate.parse("2024-08-13"));

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(postRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(PostsNotFoundException.class, () -> userController.updatePost(1, 1, post));
    }

    @Test
    public void deletePostTest() {
        int postId = 1;
        postRepository.deleteById(postId);

        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    public void deleteUserTest() {
        int userId = 1;
        userRepository.deleteById(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }

}
