package me.issue;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.kingdom.demo.grpc.GoRequest;
import org.kingdom.demo.grpc.GoResponse;
import org.kingdom.demo.grpc.GoServiceGrpc;

import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.cert.X509Certificate;

import static io.grpc.netty.NegotiationType.TLS;

public class GoServiceTest {

    @Test
    public void test1() throws SSLException {
        int port = 9000;
        String where = "hongkong";
        ManagedChannel channel = createSecuredChannel(port );
        GoResponse goResponse = GoServiceGrpc.newBlockingStub(channel)
                .goWhere(GoRequest.newBuilder().setWhere(where).build());
        System.out.println(goResponse);
        Assert.assertEquals(goResponse.getWhere(), "go to hongkong");
    }

    private ManagedChannel createSecuredChannel(int port) throws SSLException {
        TrustManager[] trustAllCerts = createTrustAllTrustManager();

        return NettyChannelBuilder.forAddress("localhost", port).useTransportSecurity()
                .sslContext(GrpcSslContexts.forClient().trustManager(trustAllCerts[0]).build()).negotiationType(TLS)
                .build();
    }
    private TrustManager[] createTrustAllTrustManager() {
        return new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        } };
    }

}
