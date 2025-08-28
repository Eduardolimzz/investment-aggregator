# Investment Aggregator

Um agregador de investimentos desenvolvido com Spring Boot e MySQL, fornecendo uma API REST para gerenciamento de usuários.

##  Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **MySQL 8.0**
- **Docker & Docker Compose**
- **Lombok**
- **Maven**

##  Pré-requisitos

- Docker Desktop instalado e rodando
- Java 17+ instalado
- IDE de sua preferência (IntelliJ IDEA, VS Code, etc.)
- Postman ou similar para testes de API

##  Como Executar o Projeto

### 1. Clonar o Repositório
```bash
git clone https://github.com/Eduardolimzz/investment-aggregator.git
cd investment-aggregator
```

### 2. Iniciar o Docker Desktop
Certifique-se que o Docker Desktop está aberto e rodando.

### 3. Subir o Banco de Dados
```bash
docker-compose up -d
```

Para verificar se os containers estão rodando:
```bash
docker-compose ps
```

### 4. Executar a Aplicação
Execute a classe `InvestmentaggregatorApplication` na sua IDE ou via command line:
```bash
./mvnw spring-boot:run
```

##  API Endpoints

Base URL: `http://localhost:8080/v1/users`

### Criar Usuário
```http
POST /v1/users
Content-Type: application/json

{
    "username": "eduardo_teste",
    "email": "eduardo@email.com",
    "password": "senha123"
}
```

### Listar Todos os Usuários
```http
GET /v1/users
```

### Buscar Usuário por ID
```http
GET /v1/users/{userId}
```

### Atualizar Usuário
```http
PUT /v1/users/{userId}
Content-Type: application/json

{
    "username": "novo_nome",
    "password": "nova_senha"
}
```

### Deletar Usuário
```http
DELETE /v1/users/{userId}
```

##  Acessar o Banco de Dados

Para verificar os dados diretamente no MySQL:

### 1. Conectar ao Container MySQL
```bash
docker exec -it investmentaggregator-mysql-1 mysql -u springuser -p
```

**Senha:** `ThePassword`

### 2. Comandos MySQL Úteis
```sql
-- Mostrar databases
SHOW DATABASES;

-- Selecionar o database
USE db_example;

-- Mostrar tabelas
SHOW TABLES;

-- Ver dados dos usuários
SELECT * FROM tb_users;

-- Ver estrutura da tabela
DESCRIBE tb_users;
```

##  Estrutura do Projeto

```
src/main/java/investment/aggregator/investmentaggregator/
├── controller/
│   └── UserController.java       # Endpoints REST
├── dto/
│   ├── CreateUserDto.java       # DTO para criação
│   └── UpdateUserDto.java       # DTO para atualização
├── entity/
│   └── User.java                # Entidade JPA
├── repository/
│   └── UserRepository.java      # Interface de acesso a dados
├── service/
│   └── UserService.java         # Lógica de negócio
└── InvestmentaggregatorApplication.java
```

##  Exemplo de Teste no Postman

1. **Criar um usuário:**
   - Method: `POST`
   - URL: `http://localhost:8080/v1/users`
   - Body (JSON):
   ```json
   {
       "username": "teste",
       "email": "teste@email.com", 
       "password": "teste123456"
   }
   ```

2. **Listar usuários:**
   - Method: `GET`
   - URL: `http://localhost:8080/v1/users`

## 🛠️ Solução de Problemas

### Erro de Conexão com MySQL
- Verifique se o Docker está rodando
- Execute `docker-compose ps` para ver se os containers estão up
- Aguarde alguns segundos após `docker-compose up -d` para o MySQL inicializar

### Porta 8080 já em uso
```bash
# Verificar o que está usando a porta
netstat -ano | findstr :8080

# Ou alterar a porta no application.properties
server.port=8081
```

### Logs da Aplicação
Para ver logs detalhados, adicione no `application.properties`:
```properties
logging.level.investment.aggregator=DEBUG
```

##  Funcionalidades da Entidade User

- **ID único:** Gerado automaticamente via UUID
- **Timestamps automáticos:** Criação e atualização registradas automaticamente
- **Validação:** Campos obrigatórios validados
- **Mapeamento JPA:** Tabela `tb_users` no banco `db_example`

##  Contribuição

1. Faça um fork do projeto
2. Crie sua feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'feat: Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request
