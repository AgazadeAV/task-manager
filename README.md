# Task Manager

## Описание проекта

**Task Manager** — это веб-приложение для управления проектами и задачами. Приложение позволяет:
- Создавать, обновлять и удалять проекты и задачи.
- Управлять пользователями, связанными с проектами и задачами.
- Экспортировать и импортировать данные в формате JSON.
- Отслеживать метрики задач по их статусу.

Приложение реализует архитектуру MVC с использованием современных технологий.

---

## Стек технологий

Проект построен на следующем стеке технологий:
- **Язык программирования**: Java 22
- **Фреймворк**: Spring Boot 3.4.1
- **База данных**: H2 (в памяти для разработки и тестирования)
- **ORM**: Hibernate/JPA
- **MapStruct**: для преобразования DTO и сущностей
- **Lombok**: для сокращения шаблонного кода
- **Liquibase**: для миграций базы данных
- **Swagger/OpenAPI**: для документирования API
- **Maven**: для управления зависимостями и сборкой
- **JUnit 5** и **Mockito**: для тестирования

---

## Установка и запуск

### Предварительные требования
- Установленный **JDK 22**
- **Maven** версии 3.8 или выше
- **Git** для клонирования репозитория

### Шаги установки

1. **Клонирование репозитория**:
   ```bash
   git clone https://github.com/your-repository/task-manager.git
   cd task-manager
   ```

2. **Запуск приложения с помощью Maven**:
   ```bash
   mvn spring-boot:run
   ```

3. **Доступ к приложению**:
   Приложение будет доступно по адресу: [http://localhost:5555](http://localhost:5555).

---

## Конфигурация

### application.yml

Настройки проекта находятся в файле `application.yml` (директория `src/main/resources`):
```yaml
server:
  port: 5555

spring:
  datasource:
    url: jdbc:h2:mem:taskmanagerdb
    driver-class-name: org.h2.Driver
    username: ${H2_DB_USERNAME}
    password: ${H2_DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
  h2:
    console:
      enabled: true
```

### Liquibase

Миграции базы данных управляются с помощью Liquibase. Скрипты находятся в папке `db/changelog` и подключены через файл `db.changelog-master.yaml`.

Основные изменения:
- Создание таблиц `users`, `projects`, `tasks`.
- Настройка внешних ключей для обеспечения связности данных.

---

## CI/CD

Для автоматической сборки и тестирования используется **GitHub Actions**. Конфигурация хранится в `.github/workflows/ci-pipeline.yml`:

```yaml
name: CI Pipeline

on:
  pull_request:
    branches:
      - main
  push:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v4

      - name: Set up JDK 22
        uses: actions/setup-java@v4
        with:
          java-version: '22'
          distribution: 'temurin'

      - name: Build with Maven
        run: mvn clean install

      - name: Run tests
        run: mvn test

      - name: Build JAR
        run: mvn package

      - name: Upload JAR artifact
        uses: actions/upload-artifact@v4
        with:
          name: task-manager-app
          path: target/*.jar
```

---

## Основные компоненты

### Контроллеры
- **ProjectController**: управление проектами (CRUD, экспорт/импорт, метрики).
- **TaskController**: управление задачами (CRUD, фильтрация по статусу/дате, поиск).
- **UserController**: управление пользователями (CRUD, экспорт/импорт).

### DTO
- **ProjectDTO**: для работы с данными проектов.
- **TaskDTO**: для работы с данными задач.
- **UserDTO**: для работы с данными пользователей.

### Исключения
- **GlobalExceptionHandler**: обработка глобальных исключений.
- **TaskNotFoundException**, **ProjectNotFoundException**, **UserNotFoundException**: пользовательские исключения.

### Репозитории
- **ProjectRepository**: работа с проектами.
- **TaskRepository**: работа с задачами.
- **UserRepository**: работа с пользователями.

### Сервисы
- **ProjectService**, **TaskService**, **UserService**: бизнес-логика для работы с сущностями.
- **DataExportImportService**: экспорт и импорт данных в JSON.

---

## Тестирование

Проект содержит:
- **Unit-тесты**: покрывают сервисы, мапперы и репозитории.
- **Интеграционные тесты**: проверяют контроллеры и взаимодействие с базой данных.

Запуск тестов:
```bash
mvn test
```

---

## API Документация

Документация API доступна через Swagger UI по адресу:  
[http://localhost:5555/swagger-ui.html](http://localhost:5555/swagger-ui.html).
