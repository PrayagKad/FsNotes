# 🚨 Render Database Connection Fix

## The Problem
```
Driver org.postgresql.Driver claims to not accept jdbcUrl, postgresql://fsnotedb_user:2trrembNTYJ7tzPHqEV0T9lNETOqAHQl@dpg-d2snk7re5dus73etlcqg-a.oregon-postgres.render.com/fsnotedb
```

The PostgreSQL driver is rejecting the DATABASE_URL format from Render.

## ✅ Solution 1: Use DatabaseConfig (Recommended)

I've created a `DatabaseConfig.java` that properly parses the DATABASE_URL:

```java
@Configuration
@Profile("render")
public class DatabaseConfig {
    @Value("${DATABASE_URL}")
    private String databaseUrl;

    @Bean
    public DataSource dataSource() {
        // Parses postgresql://user:pass@host:port/dbname
        // into proper JDBC URL format
    }
}
```

## ✅ Solution 2: Manual Environment Variables

If the above doesn't work, set these in Render dashboard:

### In Render Dashboard:
1. **Go to your service**
2. **Click "Environment" tab**
3. **Add these variables**:

```
SPRING_PROFILES_ACTIVE=render
DATABASE_URL=postgresql://fsnotedb_user:2trrembNTYJ7tzPHqEV0T9lNETOqAHQl@dpg-d2snk7re5dus73etlcqg-a.oregon-postgres.render.com/fsnotedb
DB_USERNAME=fsnotedb_user
DB_PASSWORD=2trrembNTYJ7tzPHqEV0T9lNETOqAHQl
DB_HOST=dpg-d2snk7re5dus73etlcqg-a.oregon-postgres.render.com
DB_PORT=5432
DB_NAME=fsnotedb
JWT_SECRET=your-strong-jwt-secret
CORS_ORIGINS=https://your-vercel-app.vercel.app
```

## ✅ Solution 3: Update application-render.properties

If you prefer to use individual properties:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:fsnotedb}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=org.postgresql.Driver
```

## 🔧 Quick Fix Steps

### Step 1: Update Environment Variables
1. **Go to Render dashboard**
2. **Find your service**
3. **Click "Environment" tab**
4. **Add/Update**:
   - `SPRING_PROFILES_ACTIVE` = `render`
   - `DATABASE_URL` = (from your PostgreSQL service)
   - `JWT_SECRET` = (generate a strong secret)
   - `CORS_ORIGINS` = (your Vercel URL)

### Step 2: Redeploy
1. **Click "Manual Deploy"**
2. **Wait for build to complete**
3. **Check logs for success**

## 🎯 Key Points

### DATABASE_URL Format
- **Render provides**: `postgresql://user:pass@host:port/dbname`
- **Spring Boot needs**: `jdbc:postgresql://host:port/dbname`
- **Our DatabaseConfig** handles this conversion automatically

### Environment Variables
- **SPRING_PROFILES_ACTIVE=render** - Uses our render profile
- **DATABASE_URL** - Full connection string from Render
- **JWT_SECRET** - Strong secret for token signing
- **CORS_ORIGINS** - Your frontend URL

## 🆘 Still Having Issues?

### Check These:
1. **Database service** is running in Render
2. **Environment variables** are set correctly
3. **Build logs** show successful compilation
4. **Database URL** format is correct

### Common Errors:
- **"Driver claims to not accept jdbcUrl"** → DATABASE_URL format issue
- **"Connection refused"** → Database not running
- **"Authentication failed"** → Wrong credentials

## ✅ Success Indicators
- Build completes without errors
- Application starts successfully
- Database connection established
- Health check passes

## 📞 Need Help?
- Check Render build logs
- Verify database service is running
- Test database connection manually
- Contact Render support if needed
