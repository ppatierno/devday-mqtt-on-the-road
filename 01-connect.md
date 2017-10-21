# MQTT connection

Simple connection for subscribing.

```
mosquitto_sub -d -t mytopic -q 0
```

Simple connection for publishing.

```
mosquitto_pub -d -t mytopic -q 0 -m "MQTT on the road"
```

Check CONNECT and CONNACK packets with Wireshark.

Providing client-id and keep alive (default 60 seconds).

```
mosquitto_sub -d -t mytopic -q 0 -i myclient -k 5
```

Take a look and the PINGREQ and PINGRESP packets.