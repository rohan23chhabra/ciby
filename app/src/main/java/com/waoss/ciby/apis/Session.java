package com.waoss.ciby.apis;

public interface Session {
    Consumer.Internal consumerLogin(UserCredentials credentials);
    Producer.Internal producerLogin(UserCredentials credentials);
}
