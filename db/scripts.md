# Student

```sql
CREATE TABLE IF NOT EXISTS public.student
(
    student_id bigint NOT NULL,
    name character varying(30) COLLATE pg_catalog."default",
    surname character varying(30) COLLATE pg_catalog."default",
    phone_number character varying(20) COLLATE pg_catalog."default" NOT NULL,
    category character varying(3) COLLATE pg_catalog."default",
    teacher_id integer,
    CONSTRAINT student_pkey PRIMARY KEY (student_id),
    CONSTRAINT teacherfk FOREIGN KEY (student_id)
        REFERENCES public.employee (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```

# Employee

```sql
CREATE TABLE IF NOT EXISTS public.employee
(
    employee_id bigint NOT NULL,
    name character varying(30) COLLATE pg_catalog."default",
    surname character varying(30) COLLATE pg_catalog."default",
    phone_number character varying(15) COLLATE pg_catalog."default" NOT NULL,
    experience real,
    CONSTRAINT employee_pkey PRIMARY KEY (employee_id)
)

```

# Post

```sql
CREATE TABLE IF NOT EXISTS public.post
(
    post_id bigint NOT NULL,
    specialization character varying(30) COLLATE pg_catalog."default",
    name character varying(30) COLLATE pg_catalog."default",
    responsibilities character varying(100)[] COLLATE pg_catalog."default",
    CONSTRAINT post_pkey PRIMARY KEY (post_id)
)

```

# Car

```sql
CREATE TABLE IF NOT EXISTS public.car
(
    "number" character varying(10) COLLATE pg_catalog."default" NOT NULL,
    model character varying(40) COLLATE pg_catalog."default",
    year smallint NOT NULL,
    CONSTRAINT car_pkey PRIMARY KEY ("number")
)

```

# Theory

```sql
CREATE TABLE IF NOT EXISTS public.theory
(
    theme character varying(50) COLLATE pg_catalog."default" NOT NULL,
    price real NOT NULL,
    "time" timestamp without time zone,
    CONSTRAINT theory_pkey PRIMARY KEY (theme)
)

```

# Practice

```sql
CREATE TABLE IF NOT EXISTS public.practice
(
    practice_id bigint NOT NULL,
    date timestamp without time zone,
    price real,
    place character varying(50) COLLATE pg_catalog."default",
    car_id character varying(10) COLLATE pg_catalog."default",
    CONSTRAINT practice_pkey PRIMARY KEY (practice_id),
    CONSTRAINT carfk FOREIGN KEY (car_id)
        REFERENCES public.car ("number") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```

# Exam_results

```sql
CREATE TABLE IF NOT EXISTS public.exam_results
(
    result_id smallint NOT NULL,
    exam character varying(40) COLLATE pg_catalog."default",
    date timestamp without time zone,
    grade smallint,
    student_id bigint,
    teacher_id bigint,
    CONSTRAINT exam_results_pkey PRIMARY KEY (result_id),
    CONSTRAINT exam_results_student_id_fkey FOREIGN KEY (student_id)
        REFERENCES public.student (student_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT exam_results_teacher_id_fkey FOREIGN KEY (teacher_id)
        REFERENCES public.employee (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```

# Employee_post
```sql
CREATE TABLE IF NOT EXISTS public.employee_post
(
    employee bigint NOT NULL,
    post_id bigint NOT NULL,
    CONSTRAINT employee_post_pkey PRIMARY KEY (employee, post_id),
    CONSTRAINT employee_post_employee_fkey FOREIGN KEY (employee)
        REFERENCES public.employee (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT employee_post_post_id_fkey FOREIGN KEY (post_id)
        REFERENCES public.post (post_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```

# Student_practice_relation
```sql
CREATE TABLE IF NOT EXISTS public.student_practice_relation
(
    student_id bigint,
    teacher_id bigint,
    practice_id bigint,
    CONSTRAINT student_practice_relation_practice_id_fkey FOREIGN KEY (practice_id)
        REFERENCES public.practice (practice_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT student_practice_relation_student_id_fkey FOREIGN KEY (student_id)
        REFERENCES public.student (student_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT student_practice_relation_teacher_id_fkey FOREIGN KEY (teacher_id)
        REFERENCES public.employee (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```
# Student_theory_relation
```sql
CREATE TABLE IF NOT EXISTS public.student_theory_relation
(
    student_id bigint NOT NULL,
    theory_theme character varying(50) COLLATE pg_catalog."default" NOT NULL,
    date timestamp without time zone,
    grade smallint,
    teacher_id bigint,
    CONSTRAINT student_theory_pkey PRIMARY KEY (student_id, theory_theme),
    CONSTRAINT student_theory_relation_teacher_id_fkey FOREIGN KEY (teacher_id)
        REFERENCES public.employee (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT student_theory_student_id_fkey FOREIGN KEY (student_id)
        REFERENCES public.student (student_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT student_theory_theory_theme_fkey FOREIGN KEY (theory_theme)
        REFERENCES public.theory (theme) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

```
