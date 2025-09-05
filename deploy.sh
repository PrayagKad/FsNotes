#!/bin/bash

# FsNotes Production Deployment Script

echo "🚀 Starting FsNotes Production Deployment..."

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    exit 1
fi

# Check if Docker Compose is installed
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose is not installed. Please install Docker Compose first."
    exit 1
fi

# Build and start services
echo "📦 Building and starting services..."
docker-compose up --build -d

# Wait for services to be ready
echo "⏳ Waiting for services to be ready..."
sleep 30

# Check if services are running
echo "🔍 Checking service status..."
docker-compose ps

# Test backend health
echo "🏥 Testing backend health..."
curl -f http://localhost:8080/auth/login || echo "❌ Backend health check failed"

# Test frontend
echo "🌐 Testing frontend..."
curl -f http://localhost:3000 || echo "❌ Frontend health check failed"

echo "✅ Deployment completed!"
echo "🌐 Frontend: http://localhost:3000"
echo "🔧 Backend API: http://localhost:8080"
echo "📊 Database: localhost:5432"

echo ""
echo "📝 To stop services: docker-compose down"
echo "📝 To view logs: docker-compose logs -f"
