# Apache-Kafka-Examples

Example Consumers, Producers, Streams etc.

[![Build Status](https://travis-ci.com/wilmol/Apache-Kafka-Examples.svg?branch=master)](https://travis-ci.com/wilmol/Apache-Kafka-Examples) [![codecov](https://codecov.io/gh/wilmol/Apache-Kafka-Examples/branch/master/graph/badge.svg)](https://codecov.io/gh/wilmol/Apache-Kafka-Examples)

### 1. Install 
* Download Kafka 

https://www.apache.org/dyn/closer.cgi?path=/kafka/2.2.0/kafka_2.12-2.2.0.tgz

* unzip
```
tar -xzf kafka_2.12-2.2.0.tgz
cd kafka_2.12-2.2.0
```
* Start ZooKeeper server
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
* Start Kafka server
```
bin/kafka-server-start.sh config/server.properties
```

*(Installation steps taken from https://kafka.apache.org/quickstart)*

### 2. Create topics
* Must create the topics which clients (producers/consumers etc.) will connect to ahead of time
* Just run:
```
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic <TOPIC_NAME>
```
* Currently support examples for the following topics:
  * stdin
  * primes

### 3. Run producers
* Run one or more in the **producer-examples module**
  * Producers send messages to a topic

### 4. Run consumers
* See **consumer-examples module**
  * Consumers subscribe to topic(s)
  * Consumers read messages from subscribed topics

### 5. Run streams (if needed)
* See **streams-examples module**
  * Streams read messages from input topics and send transformed messages to output topics
  * I.e. streams create *pipelines*
