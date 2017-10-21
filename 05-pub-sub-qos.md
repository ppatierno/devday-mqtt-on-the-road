# Publish-Subscribe QoS relationship

From MQTT 3.1.1 specification :

The QoS of payload messages sent in response to a Subscription MUST be the minimum of the QoS of the originally published message and the maximum QoS granted by the server.

Subscription with QoS 2.

```
mosquitto_sub -d -t mytopic -q 2
```

Publisher with QoS 1 (or 0).

```
mosquitto_pub -d -t mytopic -q 1 -m "MQTT on the road"
```

Verify that the publisher QoS 1 is granted (PUBLISH and PUBACK packet).
Verify that the granted QoS 2 for the subscriber isn't confirmed but the QoS 1 from the publisher is used (no PUBLISH, PUBREC, PUBREL and PUBCOMP as defined by QoS 2 b but just PUBACK as QoS 1).

Subscription with QoS 1.

```
mosquitto_sub -d -t mytopic -q 1
```

Publisher with QoS 2.

```
mosquitto_pub -d -t mytopic -q 2 -m "MQTT on the road"
```

Verify that publisher really publishes with QoS 2 (PUBLISH, PUBREC, PUBREL and PUBCOMP packets) and subscriber receives with QoS 1 (PUBLISH and PUBACK).

QoS is not an end-to-end guarantee.