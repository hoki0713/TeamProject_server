package com.mobeom.local_currency.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> ,CustomPostRepository{
}
