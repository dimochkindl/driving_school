# Тема проекта: driving_school

## Задача Спроектировать и создать схему бд и таблицы
  - [схема бд](https://github.com/dimochkindl/driving_school/blob/main/db/scheme.png)
  - [скрипты и описание таблиц](https://github.com/dimochkindl/driving_school/blob/main/db/scripts.md)
## Реализовать слой репозиториев
  - использовался Hibernate(Criteria Builder для динамического построения сложных запросов);
  - реализована вспомогательная утилита QPredicate, которая представляет собой реализацию паттерна Predicate Builder. Этот паттерн используется в сочетании с Criteria API от Hibernate для построения сложных, динамических запросов к базе данных.
## Реализовать слой сервисов
  - Spring Framework: Используется для управления зависимостями и регистрации классов в контексте приложения, что упрощает реализацию бизнес-логики.
  - Lombok: используется для автоматического создания логгера.
## Реализовать слой контроллеров
  - реализован также с использованием Spring
