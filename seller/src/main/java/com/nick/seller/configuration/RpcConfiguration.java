package com.nick.seller.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@Configuration
//@ComponentScan(basePackageClasses = {ProductRpc.class})
public class RpcConfiguration {

    private static Logger LOG = LoggerFactory.getLogger(RpcConfiguration.class);

//    @Bean
//    public AutoJsonRpcClientProxyCreator rpcClientProxyCreator(@Value("${rpc.manager.url}") String url){
//        AutoJsonRpcClientProxyCreator clientProxyCreator = new AutoJsonRpcClientProxyCreator();
//        try {
//            clientProxyCreator.setBaseUrl(new URL(url));
//        } catch (MalformedURLException e) {
//            LOG.error("wrong rpc server address");
//        }
//        clientProxyCreator.setScanPackage(ProductRpc.class.getPackage().getName());
//        return clientProxyCreator;
//    }
}
