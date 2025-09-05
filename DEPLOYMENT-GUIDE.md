# FsNotes Deployment Guide

## 🚀 Deploy to Vercel (Frontend) + Render (Backend)

### Prerequisites
- GitHub account
- Vercel account (free)
- Render account (free)

---

## 📱 Frontend Deployment (Vercel)

### Step 1: Prepare Frontend
1. **Push to GitHub** (if not already done):
   ```bash
   git add .
   git commit -m "Prepare for Vercel deployment"
   git push origin main
   ```

### Step 2: Deploy to Vercel
1. **Go to [Vercel](https://vercel.com)**
2. **Click "New Project"**
3. **Import your GitHub repository**
4. **Configure settings**:
   - **Framework Preset**: Create React App
   - **Root Directory**: `Frontend/fsnotes-fronten` ⚠️ **CRITICAL!**
   - **Build Command**: `npm run build`
   - **Output Directory**: `build`
   - **Install Command**: `npm install`

### Step 3: Set Environment Variables
In Vercel dashboard, go to **Settings > Environment Variables**:
```
REACT_APP_API_URL = https://your-backend-name.onrender.com
```

### Step 4: Deploy
- Click **"Deploy"**
- Wait for deployment to complete
- Note your Vercel URL (e.g., `https://your-app.vercel.app`)

---

## 🔧 Backend Deployment (Render)

### Step 1: Prepare Backend
1. **Ensure all files are committed**:
   ```bash
   git add .
   git commit -m "Prepare for Render deployment"
   git push origin main
   ```

> **Note**: We're using Docker to deploy the Spring Boot app (Java 21) since Render doesn't have a direct Java option.

### Step 2: Deploy to Render
1. **Go to [Render](https://render.com)**
2. **Click "New +" > "Web Service"**
3. **Connect your GitHub repository**
4. **Configure settings**:
   - **Name**: `fsnotes-backend`
   - **Environment**: `Docker`
   - **Root Directory**: `.` (leave empty or put a dot)
   - **Dockerfile Path**: `./Dockerfile`
   - **Build Command**: (leave empty - Docker handles this)
   - **Start Command**: (leave empty - Docker handles this)

### Step 3: Add PostgreSQL Database
1. **In Render dashboard, click "New +" > "PostgreSQL"**
2. **Name**: `fsnotes-database`
3. **Note the database credentials**

### Step 4: Set Environment Variables
In Render dashboard, go to **Environment**:
```
SPRING_PROFILES_ACTIVE = render
DATABASE_URL = [from PostgreSQL service]
JWT_SECRET = [generate a strong secret]
CORS_ORIGINS = https://your-vercel-app.vercel.app
```

### Step 5: Deploy
- Click **"Create Web Service"**
- Wait for deployment to complete
- Note your Render URL (e.g., `https://your-app-name.onrender.com`)

---

## 🔄 Update Frontend with Backend URL

### Step 1: Update Vercel Environment Variables
1. **Go to Vercel dashboard**
2. **Settings > Environment Variables**
3. **Update**:
   ```
   REACT_APP_API_URL = https://your-backend-name.onrender.com
   ```
4. **Redeploy** the frontend

---

## ✅ Testing Your Deployment

### Frontend (Vercel)
- Visit: `https://your-app.vercel.app`
- Test: Registration, Login, Create Notes

### Backend (Render)
- Test API: `https://your-backend-name.onrender.com/auth/login`
- Check logs in Render dashboard

### Shared Notes
- Create a note in the app
- Copy the sharable link
- Test in incognito mode

---

## 🔧 Troubleshooting

### Common Issues

#### Frontend Issues
- **CORS errors**: Check `CORS_ORIGINS` in Render
- **API not found**: Verify `REACT_APP_API_URL` in Vercel
- **Build fails**: Check Vercel build logs

#### Backend Issues
- **Database connection**: Check `DATABASE_URL` in Render
- **JWT errors**: Verify `JWT_SECRET` is set
- **Build fails**: Check Render build logs
- **Docker build fails**: Ensure Dockerfile is in root directory
- **Port issues**: Make sure app uses `PORT` environment variable
- **"Dockerfile is missing" error**: Set Root Directory to `.` (dot) in Render settings

### Debug Commands
```bash
# Check backend health
curl https://your-backend-name.onrender.com/auth/login

# Check frontend
curl https://your-app.vercel.app
```

---

## 📊 Monitoring

### Vercel
- **Analytics**: Built-in analytics
- **Logs**: Function logs in dashboard
- **Performance**: Core Web Vitals

### Render
- **Logs**: Real-time logs in dashboard
- **Metrics**: CPU, Memory usage
- **Health**: Automatic health checks

---

## 💰 Cost

### Free Tier Limits
- **Vercel**: 100GB bandwidth/month
- **Render**: 750 hours/month (free tier)
- **PostgreSQL**: 1GB storage

### Upgrading
- **Vercel Pro**: $20/month
- **Render**: $7/month per service

---

## 🔒 Security Notes

1. **JWT Secret**: Use a strong, random secret
2. **CORS**: Only allow your Vercel domain
3. **Database**: Use strong passwords
4. **HTTPS**: Both platforms provide SSL automatically

---

## 🎉 Success!

Once deployed, your FsNotes app will be live at:
- **Frontend**: `https://your-app.vercel.app`
- **Backend**: `https://your-backend-name.onrender.com`

Users can now access your app from anywhere in the world! 🌍
