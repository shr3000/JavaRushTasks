package com.javarush.task.task29.task2909.human;

import java.util.*;

public class University {

    private String name;
    private int age;

    private List<Student> students = new ArrayList();

    public University(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Student getStudentWithAverageGrade(double averageGrade) {
        Student student = null;
        for (Student s: students) {
            if (s.getAverageGrade() == averageGrade)
                student = s;
        }
        return student;
    }

    public Student getStudentWithMaxAverageGrade() {
        Student student = null;
        double maxAverageGrade = 0;
        for (Student s: students) {
            if (s.getAverageGrade() > maxAverageGrade) {
                student = s;
                maxAverageGrade = s.getAverageGrade();
            }
        }
        return student;
    }

    public Student getStudentWithMinAverageGrade() {
        Student student = null;
        double minAverageGrade = Double.MAX_VALUE;
        for (Student s: students) {
            if (s.getAverageGrade() < minAverageGrade) {
                student = s;
                minAverageGrade = s.getAverageGrade();
            }
        }
        return student;
    }
    public void expel(Student student) {
        students.remove(students.indexOf(student));
    }
}