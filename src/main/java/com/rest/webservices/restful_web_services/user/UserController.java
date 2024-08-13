package com.rest.webservices.restful_web_services.user;

import com.rest.webservices.restful_web_services.jpa.PostRepository;
import com.rest.webservices.restful_web_services.jpa.UserRepository;
import com.rest.webservices.restful_web_services.posts.Posts;
import com.rest.webservices.restful_web_services.posts.PostsNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserRepository userRepository;
    private PostRepository postRepository;

    public UserController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("id: " + id);
        return user.get();
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setId(null);
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/users/{id}")
    public User updateUserDetail(@PathVariable int id, @RequestBody User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    @GetMapping("/users/{id}/posts")
    public List<Posts> getAllPostByUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("id: " + id);
        return user.get().getPosts();
    }

    @PostMapping("/users/{id}/posts")
    public Posts createPost(@PathVariable int id, @RequestBody Posts posts) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("id: " + id);
        posts.setUser(user.get());
        posts.setId(null);
        return postRepository.save(posts);
    }

    @PutMapping("/users/{userId}/posts/{postId}")
    public Posts updatePost(@PathVariable int userId, @PathVariable int postId, @RequestBody Posts post) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) throw new UserNotFoundException("id: " + userId);
        post.setUser(user.get());
        Optional<Posts> posts = postRepository.findById(postId);
        if (posts.isEmpty()) throw new PostsNotFoundException("id: " + postId);
        post.setId(postId);
        return postRepository.save(post);
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable int id) {
        postRepository.deleteById(id);
    }
    

}
