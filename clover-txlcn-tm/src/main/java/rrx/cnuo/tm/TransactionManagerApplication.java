package rrx.cnuo.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagerServer
public class TransactionManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(TransactionManagerApplication.class, args);
    }
}
