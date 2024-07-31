package com.rest.webservices.restful_web_services.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.webservices.restful_web_services.posts.Posts;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "user_table")
public class User {
    @Id
    @GeneratedValue
    private Integer id;

    private String username;

    private String password;

    private String name;

    private LocalDate birthdate;

    private String gender;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Posts> posts;

    public User() {}
    public User(Integer id, String username, String password, String name, LocalDate birthdate, String gender) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", birthdate=" + birthdate +
                ", gender='" + gender + '\'' +
                '}';
    }
}
