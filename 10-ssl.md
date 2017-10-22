# TLS/SSL encrypted connection

## Create certificates

What we need is :

* CA certificate for signing the broker certificate
* server certificate (signed by the CA)
* server private key

Create CA private key, using "password" as passphrase for example.

```
openssl genrsa -des3 -out ca.key 2048
```

Now create the CA certificate using the corresponding private key.

```
openssl req -new -x509 -days 365 -key ca.key -out ca.crt -subj "/C=IT/ST=Italy/L=Naples/O=DevDay/CN=CA"
```

You can see the CA certificate information.

```
openssl x509 -in ca.crt -text
```

Create a server private key (without passphrase for example).

```
openssl genrsa -out server.key 2048
```

Create a certificate request which will be used by the CA for generating a signed server certificate (the CA will sign the server certificate with its private key).

```
openssl req -new -out server.csr -key server.key -subj "/C=IT/ST=Italy/L=Naples/O=DevDay/CN=localhost"
```

The CN (Common Name) needs to be set at the domain name of the server. It can be the IP address or a FQDN and will be the same used by the client.

Now the CA needs to verify and sign the server certificate.

```
openssl x509 -req -in server.csr -CA ca.crt -CAkey ca.key -CAcreateserial -out server.crt -days 365
```

You can see the server certificate information.

```
openssl x509 -in server.crt -text
```

## Configure and run the broker

The mosquitto.conf needs to be configured in the following way (default listener section) :

* port : set to 8883 which is the default port for encrypted traffic (MQTT on TLS)
* cafile : path to the CA certificate (ca.crt)
* certfile : path to the server certificate (server.crt)
* keyfile : path to the server private key (server.key)
* tls_version : TLS version to use (i.e. tlsv1)

Run the mosquitto broker passing such configuration file

```
mosquitto -v -c ./ssl/mosquitto.conf
```

## Running clients

The clients need to be configured with the CA certificate in order to verify, during the SSL/TLS exchange, the identify of the server.

```
mosquitto_sub -p 8883 -d -t mytopic -q 0 --tls-version tlsv1 --cafile /home/ppatiern/github/mqtt-on-the-road/ssl/ca.crt
```

```
mosquitto_pub -p 8883 -d -t mytopic -q 0 --tls-version tlsv1 --cafile /home/ppatiern/github/mqtt-on-the-road/ssl/ca.crt -m "MQTT on the road"
```

Verify that there is a TLS exchange before encrypted MQTT packets are sent.

