# Authentication

A simple authentication mechanism based on username and password is provided by MQTT.
Such credentials are provided through the CONNECT packet.

Create a simple text file named password.txt with credentials.

```
paolo:mypassword
devday:otherpassword
```

Use the mosquitto_passwd tool for encrypting such information.

```
mosquitto_passwd -U password.txt
```

The result file will be something like this.

```
paolo:$6$8MRc19EenmUBB3lD$iY3NI1Sp047D0+DojgZcEf2Ehd2nr8ezpumBOAbNnAD3pZsjTaM3DO/su6kY10oeKb/QStnAFzFJze3ISR4Shg==
devday:$6$pluQlDchCfjbh1zt$f6uVnkmq8pMmPFdpQs5BiiiF5MMOtiscLRLIBUE0rJso5yGfJlPUr+LuKi0jTkBn+gSQncQf+Fxar3yHwrn+Nw==
```

Use a mosquitto.conf file setting :

* allow_anonymous : false
* password_file : path to the just created password file

Run the mosquitto broker passing such configuration file

```
mosquitto -v -c ./auth/mosquitto.conf
```

If a client tries to connect without providing username and password (anonymous), its connection will be refused.

```
mosquitto_sub -d -t mytopic -q 0
```

the same with wrong credentials

```
mosquitto_sub -d -t mytopic -q 0 -u wrong -P wrong
```

Take a look at the error code in the CONNACK packet (not authorized).

Instead using the right credentials, the subscriber is able to connect.

```
mosquitto_sub -d -t mytopic -q 0 -u paolo -P mypassword
```
