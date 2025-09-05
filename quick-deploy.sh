#!/bin/bash

echo "🚀 FsNotes Quick Deploy Script"
echo "================================"

# Check if git is initialized
if [ ! -d ".git" ]; then
    echo "❌ Git not initialized. Please run: git init && git add . && git commit -m 'Initial commit'"
    exit 1
fi

# Check if changes are committed
if [ -n "$(git status --porcelain)" ]; then
    echo "📝 Uncommitted changes detected. Committing them..."
    git add .
    git commit -m "Prepare for deployment $(date)"
fi

# Push to GitHub
echo "📤 Pushing to GitHub..."
git push origin main

echo ""
echo "✅ Code pushed to GitHub!"
echo ""
echo "📋 Next Steps:"
echo "1. Go to https://vercel.com and deploy frontend"
echo "2. Go to https://render.com and deploy backend"
echo "3. Follow the DEPLOYMENT-GUIDE.md for detailed instructions"
echo ""
echo "🔗 Quick Links:"
echo "- Vercel: https://vercel.com/new"
echo "- Render: https://render.com/dashboard"
echo ""
echo "📖 Full Guide: DEPLOYMENT-GUIDE.md"
