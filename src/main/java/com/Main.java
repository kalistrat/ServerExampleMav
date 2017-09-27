package com;


import io.moquette.interception.AbstractInterceptHandler;
import io.moquette.interception.InterceptHandler;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.server.Server;
import io.moquette.server.config.ClasspathResourceLoader;
import io.moquette.server.config.IConfig;
import io.moquette.server.config.IResourceLoader;
import io.moquette.server.config.ResourceLoaderConfig;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttMessageBuilders;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import io.netty.handler.codec.mqtt.MqttQoS;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


class Main {

    static class PublisherListener extends AbstractInterceptHandler {

        @Override
        public String getID() {
            return "EmbeddedLauncherPublishListener";
        }

        @Override
        public void onPublish(InterceptPublishMessage msg) {
//            System.out.println(
//                    "Received on topic: " + msg.getTopicName() + " content: " + new String(msg.getPayload().array()));
        System.out.println("topicName : " + msg.getTopicName().toString());


            System.out.println(StandardCharsets.UTF_8.decode(msg.getPayload().nioBuffer()).toString());
        }
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        //IResourceLoader classpathLoader = new ClasspathResourceLoader("D:/dev/ServerExampleMav/moquette");

        //final IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);

        Properties configProps = new Properties();

        configProps.setProperty("port", Integer.toString(1883));
        configProps.setProperty("host", "0.0.0.0");
        configProps.setProperty("password_file", "D:/dev/ServerExampleMav/password_file.conf");
        configProps.setProperty("allow_anonymous", Boolean.FALSE.toString());
        configProps.setProperty("authenticator_class", "");
        configProps.setProperty("authorizator_class", "");
        configProps.setProperty("ssl_port","8883");
        configProps.setProperty("jks_path","D:/dev/ServerExampleMav/serverkeystore.jks");
        configProps.setProperty("key_store_password","3PointShotMqtt");
        configProps.setProperty("key_manager_password","3PointShotMqtt");

        final Server mqttBroker = new Server();


        List<? extends InterceptHandler> userHandlers = Collections.singletonList(new PublisherListener());
        mqttBroker.startServer(configProps);

        for (int i=0; i<userHandlers.size(); i++) {
            mqttBroker.addInterceptHandler(userHandlers.get(0));
        }

//        System.out.println("Broker started press [CTRL+C] to stop");
//        //Bind  a shutdown hook
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            System.out.println("Stopping broker");
//            mqttBroker.stopServer();
//            System.out.println("Broker stopped");
//        }));
//
//        Thread.sleep(20000);
//        System.out.println("Before self publish");
//        MqttPublishMessage message = MqttMessageBuilders.publish()
//                .topicName("/exit")
//                .retained(true)
////        qos(MqttQoS.AT_MOST_ONCE);
////        qQos(MqttQoS.AT_LEAST_ONCE);
//                .qos(MqttQoS.EXACTLY_ONCE)
//                .payload(Unpooled.copiedBuffer("Hello World!!".getBytes()))
//                .build();
//
//        mqttBroker.internalPublish(message, "INTRLPUB");
//        System.out.println("After self publish");
    }

    private Main() {
    }
}