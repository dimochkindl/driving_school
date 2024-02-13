#Student script

CREATE TABLE IF NOT EXISTS public.student
(
    student_id bigint NOT NULL,
    name character varying(30) COLLATE pg_catalog."default",
    surname character varying(30) COLLATE pg_catalog."default",
    "phone number" character varying(20) COLLATE pg_catalog."default" NOT NULL,
    category character varying(3) COLLATE pg_catalog."default",
    teacher_id integer,
    CONSTRAINT student_pkey PRIMARY KEY (student_id),
    CONSTRAINT teacherfk FOREIGN KEY (student_id)
        REFERENCES public.employee (employee_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.student
    OWNER to postgres;
