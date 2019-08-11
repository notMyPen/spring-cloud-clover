package rrx.cnuo.cncommon.accessory.config;

/**
 * 指定eurekaClient访问eurekaServer的sslContext配置
 * @author xuhongyu
 * @date 2019年8月10日
 */
//@Configuration
//public class EurekaHttpsClientConfig {
//
//    @Value("${eureka.client.ssl.key-store}")
//    String keyStoreFileName;
//
//    @Value("${eureka.client.ssl.key-store-password}")
//    String keyStorePassword;
//
//    @Bean
//    public DiscoveryClient.DiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
//        EurekaJerseyClientImpl.EurekaJerseyClientBuilder builder = new EurekaJerseyClientImpl.EurekaJerseyClientBuilder();
//        builder.withClientName("eureka-https-client");
//        SSLContext sslContext = new SSLContextBuilder()
//                .loadTrustMaterial(
//                        this.getClass().getClassLoader().getResource(keyStoreFileName),keyStorePassword.toCharArray()
//                )
//                .build();
//        builder.withCustomSSL(sslContext);
//
//        builder.withMaxTotalConnections(10);
//        builder.withMaxConnectionsPerHost(10);
//
//        DiscoveryClient.DiscoveryClientOptionalArgs args = new DiscoveryClient.DiscoveryClientOptionalArgs();
//        args.setEurekaJerseyClient(builder.build());
//        return args;
//    }
//}
