# MQTT on the road

![meetup](images/meetup.png)

This repo is used for hosting [MQTT](http://mqtt.org/) related examples showed during the "MQTT on the road" meetup in Naples (October 25th) and organized by [DevDay](http://www.devday.it/) community.

* [Connection](01-connect.md)
* [LWT](02-will.md)
* [Subscribe](03-subscribe.md)
* [Publish](04-publish.md)
* [Publish/Subscribe QoS relationship](05-pub-sub-qos.md)
* [Retained message](06-retain.md)
* [Clean session](07-clean-session.md)
* [Connection stealing](08-connection-stealing.md)
* [Authentication](09-authentication.md)
* [TLS/SSL encrypted connection](10-ssl.md)

Finally the Java source folder contains an example of MQTT request/reply with `MqttRequester` and `MqttResponder` applications.
It shows how request/reply is not supported by MQTT natively so without any correlation between request and response. It means that
it needs a layer on top of the topic infrastructure and carrying correlation information in the message payload.