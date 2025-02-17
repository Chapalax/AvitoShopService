# 🏪 AvitoShopService

## 🚀 Запуск проекта

### 1️⃣ **Клонирование репозитория**

```sh
$ git clone https://github.com/Chapalax/AvitoShopService.git
$ cd AvitoShopService
```

### 2️⃣ **Создание `.env` файла**

Перед запуском Вы можете создать файл `.env` в корне проекта и указать в нем настройки БД и приложения (для примера в репозитории уже есть такой файл):

```env
SERVER_PORT=8080
DB_NAME=shop
DB_USER=user
DB_PASSWORD=secret
DB_PORT=5432
```

### 3️⃣ **Запуск через Docker Compose**

```sh
AvitoShopService$ docker compose up -d --build
```

⏳ После успешного запуска сервис будет доступен по адресу:

```
http://localhost:8080
```

### 4️⃣ **Проверка запущенных контейнеров**

```sh
$ docker ps
```

Должны быть запущены:

- `avito-shop-service`
- `postgresql`

И должны быть остановлены:
- `liquibase`

## 🔧 **Локальный запуск и тестирование**

### Установите зависимости:

- **Java 17**
- **Maven 3+**

### 1️⃣ **Тестирование**

```sh
AvitoShopService$ mvn test
```

### 2️⃣ **Запуск приложения**

```sh
AvitoShopService$ mvn spring-boot:run
```
Или через jar файл:
```sh
AvitoShopService$ mvn clean package spring-boot:repackage
AvitoShopService$ java -jar target/AvitoShopService-1.0-SNAPSHOT.jar
```


---

### ✨ Авторы

[🐘 Chapalax](https://github.com/Chapalax)

