package com.demos.anwesh;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class CassandraManager {

    private Cluster cluster;
    private Session initialSession;
    private Session finalSession;
    private PreparedStatement animalInsertPrepareStatement;

    public void init(String hostName, int port) {
        if (cluster == null) {
            cluster = new Cluster.Builder()
                    .addContactPoint("127.0.0.1")
                    .withPort(port).build();
            initialSession = cluster.connect();
        }
    }

    public void close() {
        if (cluster != null && initialSession != null) {
            cluster.close();
            initialSession.close();
            if (finalSession != null) {
                finalSession.close();
            }
        }
    }

    public void createKeyspace(String keyspace) {
        initialSession.execute("create keyspace " + keyspace + " with REPLICATION = {'class':'SimpleStrategy', 'replication_factor':1};");
    }

    public void connectToNewKeyspace(String keyspace) {
        if (cluster != null && finalSession == null) {
            finalSession = cluster.connect(keyspace);
            animalInsertPrepareStatement = Animal.sessionPrepareSt(finalSession);
        }
    }

    public void createAnimalTable() {
        finalSession.execute("create table animal (name text, sound text, age int, id int primary key)");
        System.out.println("created table");
    }

    public void insertIntoAnimalTable(Animal animal) {
        animal.executeSt(animalInsertPrepareStatement, finalSession);
        System.out.println("inserted animal");
    }

    public void displayAnimals() {
        if (finalSession != null) {
            ResultSet rs = finalSession.execute("select * from animal");
            rs.forEach((row) -> {
                System.out.println(row.getString("name")+"," + row.getInt("age") + row.getString("sound"));
            });
        }
    }
}
