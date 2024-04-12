package com.stu.Entity;

import java.io.Serializable;

public class Student implements Serializable {
    private String id;
    private String name;
    private double score;
    private static  final long serialVersionUID =42L;

    public Student(String id, String name, double score)
    {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Student(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString()
    {
        return "Student(id:" + id + ", name:" + name + ", score:" + score + ")";
    }
}
