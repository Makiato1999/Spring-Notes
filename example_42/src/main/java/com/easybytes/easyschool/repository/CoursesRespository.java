package com.easybytes.easyschool.repository;

import com.easybytes.easyschool.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRespository extends JpaRepository<Courses, Integer> {

}
