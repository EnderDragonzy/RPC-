package com.stu.mapper;

import com.stu.Entity.Student;

import java.util.List;
import java.util.Map;

public interface Mapper {
    List<Student> findStuList();

    Student findStuById(String value);

    List<Student> findStuByName(String value);

    List<Student> findStuByScore(Map<String,Object> map);

    int insertStu(Student student);

    int updateStu(Student student);

    int deleteStu(String id);

}
