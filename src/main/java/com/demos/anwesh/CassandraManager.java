package com.demos.anwesh;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

public class CassandraManager {

    private Cluster cluster;
    private Session initialSession;
    private Session finalSession;

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
        }
    }

    public void createKeyspace(String keyspace) {
        initialSession.execute("create keyspace " + keyspace + " with REPLICATION = {'class':'SimpleStrategy', 'replication_factor':1};");
    }
}
