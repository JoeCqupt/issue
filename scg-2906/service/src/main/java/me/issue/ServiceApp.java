package me.issue;


import io.grpc.Grpc;
import io.grpc.Server;
import io.grpc.ServerCredentials;
import io.grpc.TlsServerCredentials;
import io.grpc.stub.StreamObserver;
import org.kingdom.demo.grpc.GoRequest;
import org.kingdom.demo.grpc.GoResponse;
import org.kingdom.demo.grpc.GoServiceGrpc;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class ServiceApp {
    public static void main(String[] args) throws Exception {
        int grpcPort = 9000;

        ServerCredentials creds = createServerCredentials();
        Server server = Grpc.newServerBuilderForPort(grpcPort, creds).addService(new GoService()).build().start();

        System.out.println("Starting gRPC server in port " + grpcPort);
        Thread.currentThread().join();
    }

    private static ServerCredentials createServerCredentials() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("public.cert");
        File certChain = new File(url.getFile());

        url = Thread.currentThread().getContextClassLoader().getResource("private.key");
        File privateKey = new File(url.getFile());

        return TlsServerCredentials.create(certChain, privateKey);
    }

    static class GoService extends GoServiceGrpc.GoServiceImplBase {
        @Override
        public void goWhere(GoRequest request, StreamObserver<GoResponse> responseObserver) {

            String where = String.format("go to %s", request.getWhere());
            System.out.println("Sending response: " + where);

            GoResponse response = GoResponse.newBuilder().setWhere(where).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

}

