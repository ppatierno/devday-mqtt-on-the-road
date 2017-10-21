# Publish

Simple publishing with QoS 0.

```
mosquitto_pub -d -t mytopic -q 0 -m "MQTT on the road"
```

Take a look at PUBLISH packet.

Simple publishing with QoS 1.

```
mosquitto_pub -d -t mytopic -q 1 -m "MQTT on the road"
```

Take a look at PUBLISH and PUBACK packets.

Simple publishing with QoS 2.

```
mosquitto_pub -d -t mytopic -q 2 -m "MQTT on the road"
```

Take a look at PUBLISH, PUBREC, PUBREL and PUBCOMP packets.

