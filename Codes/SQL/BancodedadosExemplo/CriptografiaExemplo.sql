--Criptografia de Senhas com MD5
--A senha do usuário será armazenada com um hash MD5. No código backend, ao criar um novo usuário, 
--a senha será criptografada antes de ser inserida no banco de dados. 

-- Inserir usuário com hash de senha
INSERT INTO users (username, password_hash, email)
VALUES ('admin', MD5('secure_password'), 'admin@example.com');
