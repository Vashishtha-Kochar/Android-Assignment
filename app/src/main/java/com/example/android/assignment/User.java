package com.example.android.assignment;

public class User {
    private int age;
    private String name;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User() {
        name = null;
        age = 0;
        id = 0;
    }

    public User(User user) {
        name = user.getName();
        age = user.getAge();
        id = user.getId();
    }

    public User(int age, String name, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
