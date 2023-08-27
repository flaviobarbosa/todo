create sequence todo_id_seq
owned by todo.id;

alter table todo
alter column id set default nextval('todo_id_seq');