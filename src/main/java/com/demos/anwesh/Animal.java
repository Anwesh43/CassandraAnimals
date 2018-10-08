package com.demos.anwesh;

import com.datastax.driver.core.Session;

import com.datastax.driver.core.PreparedStatement;

public class Animal {
    private int id, age;
    private String name, sound;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Animal(int id, int age, String name, String sound) {
    
        this.id = id;
        this.age = age;
        this.name = name;
        this.sound = sound;
    }
    
    private static String preparedSt() {
        return "insert into animal(id, age, name, sound) values(?,?,?,?);";
    }

    public static PreparedStatement sessionPrepareSt(Session session) {
        return session.prepare(preparedSt());
    }

    public void executeSt(PreparedStatement preparedStatement, Session session) {
        session.execute(preparedStatement.bind(id, age, name, sound));
    }
}
