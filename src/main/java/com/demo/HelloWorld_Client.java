package com.demo;

import com.demo.helloworld.GreeterGrpc;
import com.demo.helloworld.HelloReply;
import com.demo.helloworld.HelloRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by wangguojun01 on 2018/12/8.
 */
public class HelloWorld_Client {
    private final ManagedChannel channel;
    private final GreeterGrpc.GreeterBlockingStub blockingStub;
    private static final Logger logger = Logger.getLogger(HelloWorld_Client.class.getName());

    public HelloWorld_Client(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        blockingStub = GreeterGrpc.newBlockingStub(channel);
    }


    public void shutdown() throws InterruptedException {
        channel.shutdown();

        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void greet(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response;
        try {
            response = blockingStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Message from gRPC-Server: " + response.getMessage());
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            grptest("frank" + i);
            Thread.sleep(1000);
        }

    }

    private static void grptest(String arg) throws InterruptedException {
        HelloWorld_Client client = new HelloWorld_Client("111.230.147.33", 50051);
        try {
            String user = arg;
            client.greet(user);
            client.shutdown();
        } finally {
            client.shutdown();
        }
    }
}
