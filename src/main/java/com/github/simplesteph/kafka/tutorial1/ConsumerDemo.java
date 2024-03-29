package com.github.simplesteph.kafka.tutorial1;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemo {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class.getName());
        String bootStrapServers = "127.0.0.1:9092";
        String groupId = "my-consumer-application";
        String topic = "first_topic";

        //Create Consumer Configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");

        //Create Consumer
        KafkaConsumer<String,String> consumer = new KafkaConsumer<String, String>(properties);

        //Subscribe Consumer to a topic
        consumer.subscribe(Arrays.asList(topic));

        //poll for new data

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); //new in kafka 2.X

            for (ConsumerRecord<String,String> record : records){
                logger.info("key::"+ record.key() + "," + "Value::" + record.value());
                logger.info("partition::"+ record.partition() + "," + "offSet::" + record.offset());

            }
        }
    }
}
