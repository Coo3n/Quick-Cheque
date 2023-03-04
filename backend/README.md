# Документация ritm3-the-tyre

1. [Архитектура веб-приложения](#архитектура-веб-приложения)\
2. [Структура контейнеров](#структура-контейнеров)
3. [Основные Docker команды](#основные-docker-команды)
4. [container-back](#container-back)\
4.1. [auth.py](#authpy)\
4.2. [support_functions.py](#support_functionspy)
5. [container-db](#container-db)
6. [Взаимодествие фронтенда с бекенд API](#взаимодествие-фронтенда-с-бекенд-api)
7. [API reference](#api-reference)\
7.1. [/api/login](#apilogin)\
7.2. [/api/register](#apiregister)\
7.3. [/api/auth_test](#apiauth_test)\
8. [Тестирование](#тестирование)

## Архитектура веб-приложения

Основная цель приложения - обработка данных со сторонних API для последующей манипуляции с ними. Приложение поддерживает функционал [выгрузки данных](#выгрузка-данных), [их обработки](#обработка-данных) и [сохранения](#сохранение-данных) либо в базу данных, либо в файл. Каждая функция представлена в виде отдельного блока - [процессора](#процессор). Процессоры можно выстраивать в любом порядке для получения желаемого результата с помощью [конвейера процессоров](#конвйеер-процессоров). Приложение работает с помощью [запросов](#api-reference) между бекендом, базой данных и фронтендом, где каждый элемент представлен в виде [отдельного Docker контейнера](#структура-контейнеров).

## Структура контейнеров

Основная информация о структуре контейнеров храниться в файле `docker-compose.yml`.

Есть 3 контейнера:
- `container-front` - фронтенд составляющая
(веб-приложение, отрисовка GUI, авторизация пользователя через back-end API)
- `container-back` - бекенд составляющая (предоставление бекенд API, авторизация
пользователя, взаимодействие с базой данных)
- `container-bd` - база данных PostgreSQL

Фронтенд и бэкенд контейнерам соответсвуют папки `container-front` и `container-back`, в которых
находится код, используемый данными контейнерами.

В `container-back` есть `Dockerfile`. Этот файл содержит инструкции для более детальной настройки контейнера.

`.env` файл содержит переменные среды, которые доступны в контейнерах `container-back`
и `container-bd`.

*В будующем будет разделение на `docker-compose-dev.yml` и `docker-compose-prod.yml`
для разработки и продакшена соответственно (на данный момент все настройки расчитаны
на разрабоку)*

[Обратно к оглавлению](#документация-ritm3-the-tyre)

## Основные Docker команды

Каждому контейнеру присвоено нескольно профилей, что позволяет запускать те контейнеры,
которые нужны в данный момент.

- frontend: `container-front`
- backend: `container-back`
- backend-api: `container-back`
- backend-db: `container-db`
- all: `container-front` `container-back` `container-db`

Запустить все 3 контейнера:
(для некоторых систем команда может быть `docker-compose`, а не `docker compose`)
```bash
docker compose --profile all up --build
```

Запустить `container-front` и `container-back`:
```bash
docker compose --profile frontend --profile backend-api up --build
```

Опция `-d` позволяет запустить конейнеры в фоновом режиме.
Запустить `container-back` в фоновом режиме:
```bash
docker compose --profile backend up --build -d
```

Отобразить контейнеры, которые запущенны сейчас:
```bash
docker ps
```

Остановить контейнер `<container_name>`:
```bash
docker rm -f <container_name>
```

Зайти в `bash` на уже запущенном контейнере `<container_name>`:
```bash
docker exec -it <container_name> bash
```

[Обратно к оглавлению](#документация-ritm3-the-tyre)

## container-back

- Python 3
- [Flask](https://flask.palletsprojects.com) - Web Framework
- [psycopg2](https://www.psycopg.org/docs/) - модуль для взаимодействия с Postgres базой данных
- [PyJWT](https://pyjwt.readthedocs.io/en/latest/) - модуль для работы с JWT токенами

Переменные среды - из файла `.env`

Структура (папка `src`):
- `ritm3` - папка для бэкенд API, реализованного Flask приложением
- `processors_watch.py` - контроль конвейера процессоров
- `processors` - папка с файлами процессоров

Переменные приложения задаются в файле `__init__.py`:
```python
app.config.from_mapping(
        SECRET_KEY=os.environ['FLASK_SECRET_KEY'],
        DB_HOST=os.environ['DB_HOST'],
		...
    )
# os.environ['VAR_NAME'] - возвращает значение переменной среды VAR_NAME
```

Далее их можно использовать следующим образом:
```python
host=current_app.config["DB_HOST"]
```

Список документации к файлам:
- [auth.py](#authpy)
- [support_funcitions.py](#support_functionspy)

### auth.py
Модуль для хранения функций аутентификации пользователя.

- **validate_token(token)**

*token* - потенциальный JWT токен\
Тип - int

Используется для проверки токена на подлинность. 

Возвращает\
`auth.TOKEN_INVALID`, если токен неверный;\
`auth.TOKEN_EXPIRED`, если токен вышел из срока годности;\
`auth.TOKEN_VALID`, если токен корректный.

### support_functions.py
Модуль для хранения вспомогательных функций.

- **fetchone_by_pattern_attribute_value(table_name, query)**

*table_name* - имя таблицы, откуда нужно взять данные\
*query* - SQL запрос для получения данных из таблицы\
Тип - dict

Используется для получения данных из таблицы по шаблону "атрибут-значение" по заданному запросу (query). Является расширением функции fetchone() из библиотеки psycopg2, поэтому может выдать только один кортеж, значения которого удовлетворяют условиям запроса. Если такого кортежа нет, то функция возвращает None.

Пример:
```python
table_name = "users_list"
query = f"SELECT * FROM users_list WHERE username = 'admin';"
user = fetchone_by_pattern_attribute_value(table_name, query)

Результат: ["id": 1, "username": "admin", "password": "admin"]
```

- **get_page_html_markup(url)**

*url* - ссылка, откуда нужно выгрузить HTML разметку\
Тип - str (формат: utf-8)

Используется для получения HTML разметки веб-страницы по данной ссылки. Работает с HTTP и HTTPS запросами. Для реализации использовались библиотеки pycurl, выгрузка HTML разметки, и certifi, для работы с HTTPS. Принимает данные вне зависимости от языка. 

- **check_json_fields_existance(fields_list, json_dict)**

*fields_list* - список, полей, которые должен содержать JSON словарь\
*json_dict* - JSON словарь\
Тип - bool

Используется для проверки JSON словаря на содержание всех нужных полей. Если
словарь содержит все поля из списка, то возращается `True`, в противном случаее - `False`.

[Обратно к оглавлению](#документация-ritm3-the-tyre)

## container-db

- [Postgres](https://www.postgresql.org/) - СУБД

Переменные среды - из файла `.env`

Подключение к интерфесу СУБД (сначала зайти в `bash`):
```bash
psql -U "$POSTGRES_USER" "$POSTGRES_DB"
```

База данных создается в корне проекта в папке `data`, поэтому ее нужно создать перед запуском.

Чтобы произвести инициализацию базы данных, необходимо после создания проекта зайти в `bash` на контейнере
`container-back` и прописать следующую команду:

```bash
flask init-db
```
(конечно, `container-db` должен быть запушен в это время)

Если нужно очисть базу данных, то самый простой метод в таком случаее это просто удалить
и заново создать папку `data`. Соответственно, придется после этого сделать инициализацию.

[Обратно к оглавлению](#документация-ritm3-the-tyre)  

## Взаимодествие фронтенда с бекенд API

Здесь на примере процесса аутентификации представлено объяснение взаимодействия фронтенда и бекенда при помощи API.

Аутентификация и проверка статуса аутентификации пользователя
происходит с помощью [JWT](https://en.wikipedia.org/wiki/JSON_Web_Token)
(JSON Web Token) токенов.

При регистрации у каждого пользователя есть свой уникальный `username`.
Когда осуществляется процес аутентификации пользователя, фронтенд отправляет
http POST запрос в виде JSON файлаю. Например:
```json
{
	"username": "<username>",
	"password": "<password>"
	...
}
```

Если данный `username` существует в базе данных и пароль совпадает, то
бэкенд возвращает следующий JSON файл:
```json
{
	"status": "sucess",
	"token": "<new JWT token>",
	...
}
```
в противном случаее:
```json
{
	"status": "failure"
	"token": ""
}
```
и тогда пользователя должен ввести пароль снова.

Далее если фронтенд хочет получить с бэкенда некоторую информацию, требующую
аутентификации, то он к каждому запросу прикрепляет текущий токен, например:
```json
{
	"token": "<current JWT token>"
	"field1": "<>"
	"field2": "<>"
	...
}
```
и по аналогии: если токен действующий, то сервер возвращает следующий следующий JSON:
```json
{
	"token": "<current JWT token>",
	"field1": "<>",
	"field2": "<>",
	...
}
```
в противном случаее
```json
{
	"status": "failure"
	...
}
```
и тогда пользователя отправляет на страницу аутентификации.

При этом важно помнить, что обратиться к API бекенда можно по пути: `/api/<request>`.

Пример отправки JSON файла http POST запросом:
```js
// JSON запрос для передачи на бекенд
let data = {
	"username": "<username>",
	"password": "<password>",
};

// Ссылка, куда нужно передать данные
let url = "/api/login";

// Настройки для передачи данных. Строки два и три обязательны
let config = {
	"method": "POST",
	"headers": {"Content-Type": "application/json"},
	"body": JSON.stringify(data),
}

fetch(url, config)
	.then(function(response) {
		return response.json();
	})
	.then(function(data) {
		console.log(data) // Выводит данные в консоль
		auth_token = data["token"] // Сохраняет данные в переменную
		// Важно! Из-за ассинхронности переменная auth_token доступна только внутри блока then.
	});
```

[Обратно к оглавлению](#документация-ritm3-the-tyre)

## API reference

- [/api/login](#apilogin)
- [/api/register](#apiregister)
- [/api/auth_test](#apiauth_test)
- [/api/handle-workflow](#apihandle-workflow)
- [/api/get-processors-list](#apiget-processors-list)
- [/api/get-workflow-status](#apiget-workflow-status)

[Обратно к оглавлению](#документация-ritm3-the-tyre)

### /api/login

Проверить действующий токен пользователя.

Форма запроса
```json
{
	"username": "<username>",
	"password": "<password>"
}
```

Успешный ответ (поле с токеном есть только при успешной авторизации)
```json
{
	"status": "success",
	"message": "logged in, token generated",
	"token": "<new JWT token>"
}
```

Все возможные варианты ответов

status  | message
--------|------
success | logged in, token generated
failure | wrong request format
failure | wrong username or password

[Обратно к API ссылкам](#api-reference)

### /api/register

Зарегестрировать нового пользователя.

- `username`: от 4 до 32 символов, английские буквы и цифры
- `password`: от 8 до 16 символов, английские буквы и цифры
(вот тут нужно еще согласовать но пока на беке так)
- `email`: до 40 символов

Форма запроса
```json
{
	"username": "<username>",
	"password": "<password>",
	"email": "<email>"
}
```

Успешный ответ
```json
{
	"status": "success",
	"message": "user created, try to login now",
}
```
Внимание! Высока вероятность вылета и битого JSON (будет исправлено в ближайшее время).

Все возможные варианты ответов

status  | message
--------|------
success | user created, try to login now
failure | wrong request format
failure | username is too small
failure | username is too big
failure | password is too small
failure | password is too big
failure | email is too big
failure | username is not valid
failure | password is not valid
failure | email is not valid
failure | username already exists

[Обратно к API ссылкам](#api-reference)

### /api/auth_test

Проверка действительности токена.

Форма запроса
```json
{
	"token": "<current JWT token>",
}
```

Успешный ответ
```json
{
	"status": "success",
	"message": "authentication confirmed"
}
```

Все возможные варианты ответов

status  | message
--------|------
success | authentication confirmed
failure | wrong request format
failure | token invalid
failure | token expired

[Обратно к API ссылкам](#api-reference)

## Тестирование

Для реализации юнит-тестов использовался фреймворк `pytest 7.1.2`. 

Расположение папки с юнит-тестами:
```bash
container-back/src/tests/
```

Чтобы протестировать существующий код, необходимо выполнить следующий набор действий:
1. Запустить контейнер бекенда с именем ritm3-the-tyre-container-back-1
```bash
docker compose --profile backend-api up --build
```
2. Войти в bash панель контейнера бекенда
```bash
docker exec -it ritm3-the-tyre-container-back-1 bash
```
3. Запустить работу юнит-тестов командой
```bash
pytest
```

Внимание! В процессе выполнения тестов могут выходить предупреждения (warnings): это нормально. На данные предупреждения не нужно обращать внимания.

[Обратно к оглавлению](#документация-ritm3-the-tyre)
