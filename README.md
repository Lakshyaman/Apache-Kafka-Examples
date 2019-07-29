# Apache-Kafka-Examples

Example Consumers, Producers, Streams etc.

[![Build Status](https://travis-ci.com/wilmol/Apache-Kafka-Examples.svg?branch=master)](https://travis-ci.com/wilmol/Apache-Kafka-Examples) [![codecov](https://codecov.io/gh/wilmol/Apache-Kafka-Examples/branch/master/graph/badge.svg)](https://codecov.io/gh/wilmol/Apache-Kafka-Examples)

### Requirements
* Java 8 lts
* Kafka

### 1. Install Kafka
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

**Example topics:**
  * stdin
  * primes

### 3. Run producers
* Producers - send messages to a topic

**Example producers:**

* stdin - sends terminal input to the stdin topic
```
./gradlew :producer-examples:stdin
```

* primes - sends the prime numbers to the primes topic
```
./gradlew :producer-examples:primes
```

### 4. Run consumers
* Consumers - read messages from subscribed topics

**Example consumers:**

* stdout - sends messages to system out
```
./gradlew :consumer-examples:stdout
```

### 5. Run streams
* Streams - read messages from input topics and send transformed messages to output topics
* I.e. streams create *pipelines*

**Example streams:**
  
* identity - sends input back into the given topic (creating an infinite cycle)
```
./gradlew :streams-examples:identity -Ptopic=<TOPIC_NAME>
```

### Kill processes
```
killall -9 java
```