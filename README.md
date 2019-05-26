# Adserver-TrafficRecorder
Database Insert LoadBalancer for a digital Advertising Server.
Traffic Receiver application will send the Traffic information to this application to be inserted to the database.

##### Supports
REST-API

##### Project Dependencies
- java8
- Springboot 2
- Jackson Json Parser

##### REST-API Path
http://localhost/recorder/traffic

##### Request Method
POST

##### Example Json
```json
{"publisherID":"2","campaignID":"3","ipAddress":"127.0.0.1","trafficReceivingParamValue":"123456","trafficSendingParamValue":"67890","trafficReceivingDate":"2019-05-29","trafficReceivingTime":"00:50:10","campaignType":"3"}
```

##### Success Response
HTTP 200

##### Error Response
HTTP 406
