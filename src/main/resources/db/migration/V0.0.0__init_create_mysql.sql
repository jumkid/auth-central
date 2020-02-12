DROP TABLE IF EXISTS users;
CREATE TABLE users (
    username varchar(50) not null primary key,
    password varchar(120) not null,
    email varchar(255),
    active boolean not null
);
CREATE UNIQUE INDEX unique_key_email ON users(email(255));

DROP TABLE IF EXISTS authorities;
CREATE TABLE authorities(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    username varchar(50) not null,
    role varchar(50) not null,
    foreign key (username) references users (username)
);
CREATE INDEX unique_key_username ON authorities(username(50));

DROP TABLE IF EXISTS oauth_client_details;
CREATE TABLE oauth_client_details (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    client_id VARCHAR(255),
    resource_ids VARCHAR(255),
    client_secret VARCHAR(255),
    scope VARCHAR(255),
    authorized_grant_types VARCHAR(255),
    web_server_redirect_uri VARCHAR(255),
    authorities VARCHAR(255),
    access_token_validity INTEGER,
    refresh_token_validity INTEGER,
    additional_information VARCHAR(5000),
    autoapprove VARCHAR(255)
);
CREATE UNIQUE INDEX unique_key_client_id ON oauth_client_details(client_id(255));