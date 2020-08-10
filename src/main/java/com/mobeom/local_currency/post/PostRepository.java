package com.mobeom.local_currency.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {

}
