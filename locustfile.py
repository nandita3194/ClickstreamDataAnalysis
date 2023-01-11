import os
import io
import avro
import avro.io
from kafka import KafkaConsumer
from elasticsearch import Elasticsearch
from locust import TaskSet, task, User
class KafkaConsumerTaskSet(TaskSet):
    @task
    def on_start(self):
        self.es_address = "elastic:nandita@localhost:9200"
        self.schema_path = "./conf/MyEventRecord.avsc"
        self.bootstrap_servers = ['localhost:9092']
        self.index_name = "clickstreamdatabase"
        self.topic = "divolte"
        self.client_id = "divolte.collector"
        self.group_id = 'divolte-group'
        self.schema = avro.schema.Parse(open(self.schema_path, 'r').read())

    @task
    def consume_messages(self):
        consumer = KafkaConsumer(self.topic, client_id=self.client_id, group_id=self.group_id, bootstrap_servers=self.bootstrap_servers)
        es = Elasticsearch(self.es_address)
        for message in consumer:
            bytes_reader = io.BytesIO(message.value)
            decoder = avro.io.BinaryDecoder(bytes_reader)
            reader = avro.io.DatumReader(self.schema)
            events = reader.read(decoder)
            es.index(index=self.index_name, body=events)

class KafkaConsumerLocust(User):
    tasks = [KafkaConsumerTaskSet]
    min_wait = 1000
    max_wait = 2000