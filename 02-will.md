# Last Will Testament

Subscriber provides a "last will testament" message which has the following meaning : "If I'll die without sending a DISCONNECT packet, please publish this message on this specific topic with this QoS I provided you".
A client is considered dead if the socket is closed, the keep live timeout expires, ....
All the "will" information are provided through the CONNECT packet on connection.

```
mosquitto_sub -d -t mytopic -q 0 --will-qos 0 --will-topic dead_clients --will-payload imdead
```

The provided "will" information :

* will-qos : QoS with which the "will" message will be published
* will-payload : body of the "will" message published

By default the "will" message is published with "no retention". You can use the `--will-retain` for retaining.

Another subscriber wants to be informed when other clients are effected by a brute disconnection (no DISCONNECT packet sent).
In this case a client can subscribe to the "dead_clients" topic.

```
mosquitto_sub -d -t dead_clients -q 0
```

Closing the client suddenly (i.e. CTRL+C) means no DISCONNECT packet sent, socket closed and the broker will publish the "will" message.

Check CONNECT packet with "will" information.

The "will" message isn't sent if the client sends DISCONNECT packet.

```
mosquitto_sub -d -t mytopic -q 0 --will-qos 0 --will-topic dead_clients --will-payload imdead -C 1
```

The client will wait for `1` incoming message and then disconnect.

```
mosquitto_sub -d -t dead_clients -q 0
```

The let's use a publisher for sending an expected packet to the first client.

```
mosquitto_pub -d -t mytopic -q 0 -m "MQTT on the road"
```

No "will" message is received by the client subscribed to `dead_clients` topic.