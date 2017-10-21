# Retained message

If a publisher publishes message on a topic and no one subscriber is subscribed, the message is just lost.

```
mosquitto_pub -d -t mytopic -q 0 -m "MQTT on the road"
```

```
mosquitto_sub -d -t mytopic -q 0
```

The subscriber doesn't receive any message on subscription.

The "retain" flag on a PUBLISH packet says to the broker to save this message as "last well known message".
When a subscriber connects, it will be the first packet received.
NOTE : every retained message overrides the previous one.

```
mosquitto_pub -d -t mytopic -q 0 -m "MQTT on the road" -r
```

```
mosquitto_sub -d -t mytopic -q 0
```

In this case, the subscriber receives the message immediately after the connection.

In order to "delete" such retained message, the publisher needs to send and empty and retained message.

```
mosquitto_pub -d -t mytopic -q 0 -n -r
```

```
mosquitto_sub -d -t mytopic -q 0
```

In this case, the previous retained message is deleted and the subscriber, on connection, doesn't receive any message.
