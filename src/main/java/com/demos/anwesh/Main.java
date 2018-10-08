package com.demos.anwesh;

public class Main {
    public static void main(String args[]) {
        CassandraManager cassandraManager = new CassandraManager();
        cassandraManager.init("127.0.0.1", 9042);
        cassandraManager.connectToNewKeyspace("testks");
        cassandraManager.displayAnimals();
        cassandraManager.close();
    }
}
