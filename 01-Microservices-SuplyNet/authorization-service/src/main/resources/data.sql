insert into client (client_id,client_secret,duration_in_minutes,required_proof_key) values ('basic-client','$2a$12$oU698BnrvIb4XV6HYFDLKeQW9wbjo8H.1SOdXYrZagU7XFvf8Iy1i',10,false);
insert into client_authorization_grant_types (client_id,authorization_grant_types) values ((select id from client where client_id = 'client'),'authorization_code');
insert into client_authorization_grant_types (client_id,authorization_grant_types) values ((select id from client where client_id = 'client'),'refresh_token');
insert into client_authorization_grant_types (client_id,authorization_grant_types) values ((select id from client where client_id = 'client'),'client_credentials');
insert into client_client_authentication_methods (client_id,client_authentication_methods) values ((select id from client where client_id = 'client'),'client_secret_basic');
insert into client_redirect_uris (client_id,redirect_uris) values ((select id from client where client_id = 'client'),'http://127.0.0.1:8080/authorized');
insert into client_scopes (client_id,scopes) values ((select id from client where client_id = 'client'),'openid');
insert into client_scopes (client_id,scopes) values ((select id from client where client_id = 'client'),'all');
# insert into client_scopes (client_id,scopes) values ((select id from client where client_id = 'client'),'READ_ONE_PRODUCT');


insert into client (client_id,client_secret,duration_in_minutes,required_proof_key) values ('my-own-client','$2a$12$oU698BnrvIb4XV6HYFDLKeQW9wbjo8H.1SOdXYrZagU7XFvf8Iy1i',10,true);
insert into client_authorization_grant_types (client_id,authorization_grant_types) values ((select id from client where client_id = 'my-own-client'),'authorization_code');
insert into client_authorization_grant_types (client_id,authorization_grant_types) values ((select id from client where client_id = 'my-own-client'),'refresh_token');
insert into client_authorization_grant_types (client_id,authorization_grant_types) values ((select id from client where client_id = 'my-own-client'),'client_credentials');
insert into client_client_authentication_methods (client_id,client_authentication_methods) values ((select id from client where client_id = 'my-own-client'),'client_secret_basic');
insert into client_redirect_uris (client_id,redirect_uris) values ((select id from client where client_id = 'my-own-client'),'https://oauthdebugger.com/debug');
insert into client_scopes (client_id,scopes) values ((select id from client where client_id = 'my-own-client'),'openid');
insert into client_scopes (client_id,scopes) values ((select id from client where client_id = 'my-own-client'),'ALL');


INSERT INTO role (role) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO user (credentials_expired,disabled,expired,locked, password, username) VALUES (false,false, false, false, '$2a$12$osh88cvVZWuFIFtlMqJED.S.GpYJS2st/JJ8gtw3kCSMXDI14g0Im','dacm');
INSERT INTO user_role(user_id, role_id) value (1,1);
INSERT INTO user_role(user_id, role_id) value (1,2);

# SET @role_id := (SELECT id FROM role WHERE role = 'ROLE_USER');
#
# SET @user_id := (SELECT id FROM user WHERE username = 'dacm');
#
# -- Insertar en la tabla user_role
# INSERT INTO user_role (user_id, role_id) VALUES (@user_id, @role_id);