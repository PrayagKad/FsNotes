#!/bin/bash

echo "🐳 Testing Docker Setup for FsNotes"
echo "===================================="

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker first."
    echo "   Download from: https://www.docker.com/products/docker-desktop"
    exit 1
fi

echo "✅ Docker is installed"

# Build the Docker image
echo "🔨 Building Docker image..."
docker build -t fsnotes-backend .

if [ $? -eq 0 ]; then
    echo "✅ Docker image built successfully!"
    echo ""
    echo "🚀 To test locally, run:"
    echo "   docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=render fsnotes-backend"
    echo ""
    echo "🌐 Then visit: http://localhost:8080/auth/login"
else
    echo "❌ Docker build failed. Check the error messages above."
    exit 1
fi
