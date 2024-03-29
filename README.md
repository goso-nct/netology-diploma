AQA-19    
# Дипломный проект профессии «Тестировщик»

**Prerequisites**:  

- java 11
- git
- docker
- docker-compose
- google chrome

**Установка и запуск**  

1. Клонировать репозиторий
	
   открыть терминал, выполнить
	`git clone https://github.com/goso-nct/netology-diploma`
	
2. Запустить СУБД и гейт банка

   перейти в рабочую директорию

   `cd netology-diploma`

   выполнить

   `docker-compose up`

   дождаться окончания вывода сообщений

   ```
   gate_1      | [
   gate_1      |   { number: '4444 4444 4444 4441', status: 'APPROVED' },
   gate_1      |   { number: '4444 4444 4444 4442', status: 'DECLINED' }
   gate_1      | ]
   ...
   pg_1        | LOG: database system is ready to accept connections
   ...
   my_1        | [System] [MY-010931] [Server] /usr/sbin/mysqld: ready for connections.
   ```

3. Запустить тестируемое приложение

   открыть терминал в рабочей директории, перейти в папку artifacts

   `cd artifacts`

   для тестирования с использованием БД MySql или БД PostgreSQL в файле application.properties свойство *spring.datasource.url* должно указывать на соответствующий драйвер:

   `spring.datasource.url=jdbc:mysql://localhost:3306/app`

   или

   `spring.datasource.url=jdbc:postgresql://localhost:5432/app`

   запустить

   `java -jar aqa-shop.jar`

   дождаться окончания вывода сообщений

   ```
   INFO 33280 --- [           main] ru.netology.shop.ShopApplication         : Started ShopApplication
   ```

4. Запустить тесты

   открыть терминал в рабочей директории, запустить 

   `./gradlew test -Dselenide.headless=true`

   Примерное время работы тестов 3-4 мин

5. Посмотреть отчёты в браузере
   
   `./gradlew allureServe`

6. Завершение тестирования

   В терминале с тестами/allure (п.4,5) `<Ctrl>+C, ./gradlew --stop`, закрыть

   В терминале с приложением (п.3) `<Ctrl>+C`, закрыть

   В терминале с бакендом (п.1,2) 

   `<Ctrl>+C`, дождаться остановки контейнеров

   `docker-compose down`, закрыть
   
   
   
   

