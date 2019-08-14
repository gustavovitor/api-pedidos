CREATE SCHEMA security;

CREATE SEQUENCE user_id_sequence INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE security.users (
  id BIGINT NOT NULL DEFAULT nextval('user_id_sequence'::regclass),
  "username" VARCHAR(128) NOT NULL,
  email VARCHAR(256) NOT NULL,
  pass VARCHAR(128) NOT NULL,
  CONSTRAINT users_pkey PRIMARY KEY (id),
  CONSTRAINT users_ukey UNIQUE ("username"),
  CONSTRAINT users_email_ukey UNIQUE (email)
);

CREATE SEQUENCE permission_id_sequence INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1;
CREATE TABLE security.permissions_list (
  id BIGINT NOT NULL DEFAULT nextval('permission_id_sequence'::regclass),
  description VARCHAR(124) NOT NULL,
  CONSTRAINT permissions_pkey PRIMARY KEY (id),
  CONSTRAINT permissions_ukey UNIQUE (description)
);

CREATE TABLE security.users_permissions (
  user_id BIGINT NOT NULL,
  permission_id BIGINT NOT NULL,
  CONSTRAINT user_id_fk FOREIGN KEY (user_id)
    REFERENCES security.users(id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT permission_id_fk FOREIGN KEY (permission_id)
    REFERENCES security.permissions_list(id) MATCH SIMPLE
    ON UPDATE NO ACTION ON DELETE CASCADE
);

INSERT INTO security.permissions_list (description) VALUES ('READ_USERS');
INSERT INTO security.permissions_list (description) VALUES ('WRITE_USERS');
INSERT INTO security.permissions_list (description) VALUES ('DELETE_USERS');

INSERT INTO security.users ("username", email, pass) VALUES ('admin', 'admin@admin.com', '$2a$10$1jH42s1Czqzj/erhy0iFA.nMRqqpcs3WBn8mC10Pev.Dkx9ZeHOzK');

INSERT INTO security.users_permissions (user_id, permission_id) VALUES (1, 1);
INSERT INTO security.users_permissions (user_id, permission_id) VALUES (1, 2);
INSERT INTO security.users_permissions (user_id, permission_id) VALUES (1, 3);