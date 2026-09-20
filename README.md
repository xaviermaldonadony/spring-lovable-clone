# Spring Lovable Clone

## Prerequisites

- Java 21
- Maven
- Docker and Docker Compose

## Setup

### 1. Start Docker Services

Copy the example docker-compose file and configure it with your credentials:

```bash
cp services.docker-compose-example.yml services.docker-compose.yml
```

Edit `services.docker-compose.yml` to set your passwords, then start the services:

```bash
docker compose -f services.docker-compose.yml up -d
```

This will start:
- PostgreSQL with pgvector extension (port 9010)
- MinIO object storage (ports 9000, 9001)

### 2. Configure Application

Copy the example application configuration:

```bash
cp src/main/resources/application.yaml.example.yaml src/main/resources/application.yaml
```

Edit `application.yaml` with your database credentials, API keys, and other secrets.

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

## Configuration

The application requires the following configuration in `application.yaml`:

- **Database**: PostgreSQL connection details
- **MinIO**: Object storage configuration
- **OpenAI API**: For AI chat functionality
- **JWT Secret**: For authentication
- **Stripe API**: For payment processing
