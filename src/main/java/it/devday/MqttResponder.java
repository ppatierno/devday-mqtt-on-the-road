package it.devday;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.mqtt.MqttClient;

import java.io.IOException;

public class MqttResponder {

    private static int port = 1883;
    private static String hostname = "localhost";

    public static void main(String[] args) {

        Vertx vertx = Vertx.vertx();

        MqttClient client = MqttClient.create(vertx);
        client.connect(port, hostname, done -> {

            if (done.succeeded()) {

                String requestTopic = "/request";

                client.subscribe(requestTopic, MqttQoS.AT_MOST_ONCE.value(), done1 -> {

                    if (done1.succeeded()) {
                        System.out.println("Waiting for request on " + requestTopic);
                    }
                });

                client.publishHandler(message ->{

                    System.out.println("Got message on " + message.topicName() + " payload " + String.valueOf(message.payload()));

                    JsonObject jsonObject = new JsonObject(message.payload());
                    int requestId = jsonObject.getInteger("requestId");
                    String body = jsonObject.getString("body");

                    String replyTopic = String.format("/response/%d", requestId);
                    String reply = body.toUpperCase();

                    client.publish(replyTopic, Buffer.buffer(reply.getBytes()), MqttQoS.AT_MOST_ONCE, false, false, done2 -> {

                        if (done2.succeeded()) {
                            System.out.println("Reply sent " + reply + " on " + replyTopic);
                        }
                    });

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
