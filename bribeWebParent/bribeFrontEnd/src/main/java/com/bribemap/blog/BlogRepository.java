package com.bribemap.blog;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bribemap.common.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Long> , JpaSpecificationExecutor<Blog> {
	@Query("select b from Blog b where b.recommend = true ")
    List<Blog> findAllRecommand();
	
}
