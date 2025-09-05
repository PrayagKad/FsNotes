# FsNotes - Production Deployment Guide

## 🚀 Quick Start

### Using Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <your-repo-url>
   cd FsNotes
   ```

2. **Deploy with Docker Compose**
   ```bash
   chmod +x deploy.sh
   ./deploy.sh
   ```

3. **Access the application**
   - Frontend: http://localhost:3000
   - Backend API: http://localhost:8080

### Manual Deployment

#### Backend (Spring Boot)

1. **Set environment variables**
   ```bash
   export SPRING_PROFILES_ACTIVE=prod
   export DB_USERNAME=fsnotes_user
   export DB_PASSWORD=fsnotes_password
   export JWT_SECRET=your-super-secure-jwt-secret-key
   export CORS_ORIGINS=https://your-domain.com
   ```

2. **Build and run**
   ```bash
   ./mvnw clean package
   java -jar target/FsNotes-0.0.1-SNAPSHOT.jar
   ```

#### Frontend (React)

1. **Set environment variables**
   ```bash
   export REACT_APP_API_URL=https://your-api-domain.com
   ```

2. **Build and serve**
   ```bash
   cd Frontend/fsnotes-fronten
   npm install
   npm run build
   npx serve -s build -l 3000
   ```

## 🔧 Configuration

### Environment Variables

#### Backend
- `SPRING_PROFILES_ACTIVE`: Set to `prod` for production
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password
- `JWT_SECRET`: JWT signing secret (use a strong secret)
- `CORS_ORIGINS`: Comma-separated list of allowed origins
- `PORT`: Server port (default: 8080)

#### Frontend
- `REACT_APP_API_URL`: Backend API URL

### Database Setup

#### PostgreSQL (Production)
```sql
CREATE DATABASE fsnotes_prod;
CREATE USER fsnotes_user WITH PASSWORD 'fsnotes_password';
GRANT ALL PRIVILEGES ON DATABASE fsnotes_prod TO fsnotes_user;
```

#### H2 (Development)
- No setup required, uses in-memory database

## 🐳 Docker Commands

```bash
# Build and start all services
docker-compose up --build -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Rebuild specific service
docker-compose up --build -d backend

# View service status
docker-compose ps
```

## 🔒 Security Considerations

1. **Change default passwords** in production
2. **Use strong JWT secrets** (at least 256 bits)
3. **Configure HTTPS** for production domains
4. **Set up proper CORS origins** for your domains
5. **Use environment variables** for sensitive data
6. **Enable database SSL** in production

## 📊 Monitoring

- **Backend logs**: `docker-compose logs -f backend`
- **Frontend logs**: `docker-compose logs -f frontend`
- **Database logs**: `docker-compose logs -f postgres`

## 🌐 Production URLs

- **Frontend**: https://your-domain.com
- **Backend API**: https://api.your-domain.com
- **Database**: your-db-host:5432

## 🚨 Troubleshooting

### Common Issues

1. **CORS errors**: Check `CORS_ORIGINS` environment variable
2. **Database connection**: Verify database credentials and connectivity
3. **JWT errors**: Ensure JWT secret is consistent across restarts
4. **Port conflicts**: Change ports in docker-compose.yml

### Health Checks

```bash
# Backend health
curl http://localhost:8080/auth/login

# Frontend health
curl http://localhost:3000

# Database health
docker-compose exec postgres pg_isready
```

## 📝 Features

- ✅ User authentication with JWT
- ✅ Note CRUD operations
- ✅ Note sharing with public links
- ✅ Responsive design
- ✅ Production-ready configuration
- ✅ Docker containerization
- ✅ PostgreSQL support
- ✅ CORS configuration
- ✅ Environment-based configuration
