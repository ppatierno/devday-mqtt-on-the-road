# Clean session

By default a client connects with clean-session to true in the CONNECT packet.
It means that if a subscriber goes offline, all messages published on its subscribed topics won't be received by the client when it will come back online.

Setting clean-session to false guarantee :

* on reconnection, the subscriber doesn't need to re-subscribes; the broker will save its subscriptions
* on reconnection, all the messages sent to its subscribed topics will be delivered to the client

It happens thanks to the client-id (which can't be empty) provided in the CONNECT packet.

As MQTT 3.1.1 specification, it's valid only for QoS > 0.

```
mosquitto_sub -d -t mytopic -q 1 -c -i myclient
```

Now disconnect the client (i.e. CTRL+C).

```
mosquitto_pub -d -t mytopic -q 1 -l
```

Publishe more messages, while the subscriber is offline, and then close the publisher.

Start the subscriber.

```
mosquitto_sub -d -t mytopic -q 1 -c -i myclient
```

All the previous published messages will be delivered.
NOTE : the mosquitto_sub sends the subscription in any case (because it's what the tool does) but in this case it's not necessary because the broker has saved such subscriptions for that client.