INSERT INTO users(username, password, email, active) VALUES('admin', '$2a$10$pNBYn58dJOgnkILvnoxmgeutWryIGlUKqYLrIg.RceDXnQcY3kfJm', 'chooli.yip@gmail.com', true);
INSERT INTO authorities(username, role) VALUES('admin', 'SUPER_ADMIN');

INSERT INTO oauth_client_details(client_id, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES ('abizgame', '$2a$10$TUc5f4QQlKBW3l9erhFrvuFdATAZVYuXM3lo6KzApyRsXXE9K93Le', 'read,write', 'password,client_credentials,refresh_token', null, null, 36000, 240000, null, true);