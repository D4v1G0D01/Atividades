--Implementação de CRUD
--As operações CRUD para a tabela de usuários

-- Criar um novo usuário
INSERT INTO users (username, password_hash, email) 
VALUES ('new_user', MD5('password123'), 'new_user@example.com');

-- Ler um usuário
SELECT * FROM users WHERE user_id = 1;

-- Atualizar dados de um usuário
UPDATE users 
SET email = 'updated_email@example.com'
WHERE user_id = 1;

-- Deletar um usuário
DELETE FROM users WHERE user_id = 1;
