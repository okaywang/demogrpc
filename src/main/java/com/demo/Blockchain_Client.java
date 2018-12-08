package com.demo;

import com.bj58.arch.blockchain.baas.admin.protocol.service.ChannelServiceGrpc;
import com.bj58.arch.blockchain.baas.admin.protocol.service.QueryBlockByNumberRequest;
import com.bj58.arch.blockchain.baas.admin.protocol.service.QueryBlockchainInfoRequest;
import com.bj58.arch.blockchain.baas.admin.protocol.service.QueryBlockchainInfoResponse;
import com.demo.helloworld.GreeterGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.logging.Logger;

public class Blockchain_Client {
    public static void main(String[] args) throws InterruptedException {

        String host = "10.126.215.100";
        int port = 32767;
        System.out.println("host:" + host);

        for (int i = 0; i < 10; i++) {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
            ChannelServiceGrpc.ChannelServiceBlockingStub channelStub = ChannelServiceGrpc.newBlockingStub(channel);
            QueryBlockchainInfoRequest request = QueryBlockchainInfoRequest.newBuilder()
                    .setAppId(200)
                    .setOrgId(478)
                    .setChannelName("chllaa123")
                    .setPeerAdminUserId(825)
                    .build();
            QueryBlockchainInfoResponse response = channelStub.queryBlockchainInfo(request);
            channel.shutdown();
            System.out.println(response);
            Thread.sleep(1000);
        }

    }
}
