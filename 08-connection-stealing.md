# Connection stealing

In MQTT, if a client connects using a client-id of an already connected client, a "connection stealing" happens.
The already connected client got disconnected from the broker and the new client takes its place.

Start first subscriber.

```
mosquitto_sub -d -t mytopic -q 1 -i myclient
```

Start second subscriber.

```
mosquitto_sub -d -t mytopic -q 1 -i myclient
```

As mosquitto_sub works, every time it's disconnected by the broker, it retries the connection automatically.
What we can see from console is that each of the above clients connect and then are disconnected in a loop.
They are stealing connection each other on every reconnection attempt.

Verify what's happening using Wireshark.