# Student

```sql
CREATE TABLE IF NOT EXISTS public.student
(
    id bigint NOT NULL,
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(30) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(20) COLLATE pg_catalog."default" NOT NULL,
    category character varying(3) COLLATE pg_catalog."default",
    CONSTRAINT pk_student PRIMARY KEY (id)
)
```
### Учащийся
#### Поля:
- имя
- фамилия
- номер телефона
- категория(А, B...)
<br><br>

# Employee

```sql
CREATE TABLE IF NOT EXISTS public.employee
(
    id bigint NOT NULL,
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(30) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(15) COLLATE pg_catalog."default" NOT NULL,
    experience real,
    post_id bigint,
    CONSTRAINT pk_employee PRIMARY KEY (id),
    CONSTRAINT fk_post FOREIGN KEY (post_id)
        REFERENCES public.post (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
```
### Сотрудник
#### Поля:
- имя
- фамилия
- номер телефона
- опыт работы
- внешний ключ - занимаемая должность
<br><br>

# Post

```sql
CREATE TABLE IF NOT EXISTS public.post
(
    id bigint NOT NULL,
    specialization character varying(30) COLLATE pg_catalog."default",
    name character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT pk_post PRIMARY KEY (id)
)
```
### Должность сотрудника
#### Поля:
- специализация
- название должности
<br><br>

# Car

```sql
CREATE TABLE IF NOT EXISTS public.car
(
    car_number character varying(10) COLLATE pg_catalog."default" NOT NULL,
    model character varying(40) COLLATE pg_catalog."default",
    year smallint NOT NULL,
    id bigint NOT NULL,
    CONSTRAINT pk_car PRIMARY KEY (id)
)

```
### Авто
#### Поля:
- номер машины
- модель
- год выпуска
<br><br>

# Theory

```sql
CREATE TABLE IF NOT EXISTS public.theory
(
    theme character varying(50) COLLATE pg_catalog."default" NOT NULL,
    price real NOT NULL,
    tense timestamp without time zone,
    id bigint NOT NULL,
    CONSTRAINT pk_theory PRIMARY KEY (id)
)

```
### Теоретическое занятие
#### Поля:
- тема
- цена
- время
<br><br>

# Practice

```sql
CREATE TABLE IF NOT EXISTS public.practice
(
    id bigint NOT NULL,
    date timestamp without time zone,
    price real,
    place character varying(50) COLLATE pg_catalog."default",
    car_id bigint,
    CONSTRAINT pk_practice PRIMARY KEY (id),
    CONSTRAINT fk_car FOREIGN KEY (car_id)
        REFERENCES public.car (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```
### Практическое занятие
#### Поля:
- дата
- место
- цена
<br><br>

# Exam

```sql
CREATE TABLE IF NOT EXISTS public.exam
(
    id bigint NOT NULL,
    exam character varying(40) COLLATE pg_catalog."default",
    date timestamp without time zone,
    grade smallint,
    student_id bigint,
    teacher_id bigint,
    CONSTRAINT pk_exam PRIMARY KEY (id),
    CONSTRAINT fk_student FOREIGN KEY (student_id)
        REFERENCES public.student (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id)
        REFERENCES public.employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```
### Экзамен
#### Поля:
- название экзамена
- дата
- оценка
- внешние ключи: препод и учащийся
<br><br>

# Student_practice_relation
```sql
CREATE TABLE IF NOT EXISTS public.student_practice_relation
(
    student_id bigint NOT NULL,
    teacher_id bigint NOT NULL,
    practice_id bigint NOT NULL,
    CONSTRAINT pk_student_teacher_practice PRIMARY KEY (student_id, teacher_id, practice_id),
    CONSTRAINT fk_practice FOREIGN KEY (practice_id)
        REFERENCES public.practice (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_student FOREIGN KEY (student_id)
        REFERENCES public.student (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id)
        REFERENCES public.employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```
### Таблица отношений между учащимся и преподавателем через практическое занятие
#### Поля - id-шники учащегося препода и занятия соответственно
<br><br>

# Student_theory_relation
```sql
CREATE TABLE IF NOT EXISTS public.student_theory_relation
(
    student_id bigint NOT NULL,
    theory_theme character varying(50) COLLATE pg_catalog."default" NOT NULL,
    date timestamp without time zone,
    grade smallint,
    teacher_id bigint NOT NULL,
    theory_id bigint NOT NULL,
    CONSTRAINT pk_student_teacher_theory PRIMARY KEY (student_id, teacher_id, theory_id),
    CONSTRAINT fk_student FOREIGN KEY (student_id)
        REFERENCES public.student (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_teacher FOREIGN KEY (teacher_id)
        REFERENCES public.employee (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_theory FOREIGN KEY (theory_id)
        REFERENCES public.theory (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```
### Таблица отношений между учащимся и преподавателем через теоритическое занятие
#### Поля - id-шники учащегося препода и занятия соответственно
