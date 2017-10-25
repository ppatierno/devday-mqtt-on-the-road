package it.devday;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.mqtt.MqttClient;

import java.io.IOException;
import java.util.Random;

public class MqttRequester {

    private static int port = 1883;
    private static String hostname = "localhost";

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        MqttClient client = MqttClient.create(vertx);
        client.connect(port, hostname, done -> {

            if (done.succeeded()) {

                Random random = new Random();
                int requestId = random.nextInt(Integer.MAX_VALUE);

                String requestTopic = "/request";
                String responseTopic = String.format("/response/%d", requestId);

                client.subscribe(responseTopic, MqttQoS.AT_MOST_ONCE.value(), done1 -> {

                    if (done1.succeeded()) {
                        System.out.println("Waiting for response on " + responseTopic);
                    }
                });

                client.publishHandler(message ->{

                   System.out.println("Got message on " + message.topicName() + " payload " + String.valueOf(message.payload()));
                });

                String request = "Hello DevDay !";
                JsonObject jsonObject = new JsonObject();
                jsonObject.put("body", request);
                jsonObject.put("requestId", requestId);

                client.publish(requestTopic, jsonObject.toBuffer(), MqttQoS.AT_MOST_ONCE, false, false, done2 -> {

                    if (done2.succeeded()) {
                        System.out.println("Request sent " + request + " on " + requestTopic);
                    }
                });
            }

        });


        try {
            System.in.read();
            client.disconnect();
            vertx.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
