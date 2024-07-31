package com.rest.webservices.restful_web_services.jpa;

import com.rest.webservices.restful_web_services.posts.Posts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Posts, Integer> {
}
