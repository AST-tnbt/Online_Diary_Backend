package com.rest.webservices.restful_web_services.posts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rest.webservices.restful_web_services.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Posts {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @Lob
    private String body;

    private LocalDate createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    public Posts() {}
    public Posts(Integer id, String title, String body, LocalDate createAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createAt = createAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "createAt=" + createAt +
                ", body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                '}';
    }
}
