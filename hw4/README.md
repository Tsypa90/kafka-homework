## Домашнее задание №4.
### 1. Запустить брокер kafka через docker-compose.yml
### 2. Создать топик events внутри контейнера kafka
#### bin/kafka-topics --create --topic events --bootstrap-server localhost:9092
### 3. Запустить приложение Consumer
### 4. Внутри контейнера kafka отправить сообщения key:value
#### kafka-console-producer --broker-list localhost:9092 --topic events --property "parse.key=true" --property "key.separator=:"



