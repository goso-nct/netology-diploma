# Отчёт о проведённом тестировании

Проведённым тестированием была проверена работа приложения "Путешествие дня" по покупке тура по дебетовой и кредитной картам. Проверялась работа пользовательского интерфейса и работа приложения с СУБД MySQL и PostgreSQL.

Было проведено 27 тестов, как позитивных так и негативных.

Из 27 тестов 16 пройдены (59%), 11 не пройдены.

![image](https://user-images.githubusercontent.com/48862268/133488922-ee5c6473-bf3b-4c7c-9de6-1f5afb112769.png)
![image](https://user-images.githubusercontent.com/48862268/133488991-b668f790-3fc1-429c-b046-05ebb835f2ae.png)


Описание выявленных дефектов оформлены в разделе Issues

Отчеты в reports.zip

**Общие рекомендации:**

- Согласно ISO IEC 7813 поле "Владелец" может содержать от 2 до 26 символов. Нужно выполнять эту проверку.  
- При работе с финансами необходимо использовать транзакционные механизмы СУБД.
