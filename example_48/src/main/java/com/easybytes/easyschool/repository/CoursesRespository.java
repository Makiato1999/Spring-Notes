package com.easybytes.easyschool.repository;

import com.easybytes.easyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RepositoryRestResource(path = "courses")
// @RepositoryRestResource(exported = false)
public interface CoursesRespository extends JpaRepository<Courses, Integer> {
    List<Courses> findByOrderByNameDesc();

    List<Courses> findByOrderByName();

}
