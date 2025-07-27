# 🚀 ITSS Portal Frontend Setup Guide

## 📋 **BACKEND INTEGRATION READY**

Your Spring Boot backend is now **frontend-ready** with:
- ✅ CORS configuration for all major development ports
- ✅ Standardized API responses (`ApiResponse<T>`)
- ✅ Paginated responses for lists
- ✅ Enhanced error handling with error codes
- ✅ Environment variables for different deployments

---

## 🎯 **FRAMEWORK RECOMMENDATIONS**

### **🥇 RECOMMENDED: Next.js + TypeScript + Tailwind CSS**
**Best for:** Enterprise dashboards, SEO, performance
```bash
npx create-next-app@latest itss-portal-frontend --typescript --tailwind --eslint --app
cd itss-portal-frontend
```

### **🥈 ALTERNATIVE: React + Vite + TypeScript**
**Best for:** SPA, fast development
```bash
npm create vite@latest itss-portal-frontend -- --template react-ts
cd itss-portal-frontend
```

### **🥉 ALTERNATIVE: Vue.js + Nuxt**
**Best for:** Easy learning curve, rapid prototyping
```bash
npx nuxi@latest init itss-portal-frontend
cd itss-portal-frontend
```

---

## 📦 **REQUIRED DEPENDENCIES**

### **Authentication & HTTP Client:**
```bash
# For React/Next.js
npm install axios react-query @tanstack/react-query
npm install @hookform/resolvers yup react-hook-form
npm install js-cookie @types/js-cookie

# For state management (choose one)
npm install zustand                    # Lightweight
npm install @reduxjs/toolkit react-redux  # Complex apps
```

### **UI Libraries (choose one):**
```bash
# Tailwind CSS + Headless UI (recommended)
npm install @headlessui/react @heroicons/react

# Material-UI
npm install @mui/material @emotion/react @emotion/styled

# Ant Design
npm install antd

# Chakra UI
npm install @chakra-ui/react @emotion/react @emotion/styled
```

---

## 🔧 **API INTEGRATION SETUP**

### **1. API Client Configuration:**
```typescript
// lib/api.ts
import axios from 'axios';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8081/api';

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
});

// Request interceptor for auth tokens
apiClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor for token refresh
apiClient.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401) {
      // Handle token refresh logic
      await refreshToken();
    }
    return Promise.reject(error);
  }
);
```

### **2. TypeScript Interfaces:**
```typescript
// types/api.ts
export interface ApiResponse<T> {
  success: boolean;
  message?: string;
  data?: T;
  error?: string;
  errorCode?: string;
  timestamp: string;
  path?: string;
}

export interface User {
  id: number;
  username: string;
  email: string;
  status: 'PENDING' | 'APPROVED' | 'DECLINED' | 'BLOCKED';
  roles: Role[];
  createdAt: string;
  updatedAt: string;
}

export interface Role {
  id: number;
  name: string;
  description: string;
  permissions: Permission[];
}

export interface Permission {
  id: number;
  name: string;
  description: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  message: string;
}
```

### **3. Authentication Service:**
```typescript
// services/auth.ts
import { apiClient } from '@/lib/api';
import { ApiResponse, LoginResponse, User } from '@/types/api';

export class AuthService {
  static async login(usernameOrEmail: string, password: string): Promise<LoginResponse> {
    const response = await apiClient.post<ApiResponse<LoginResponse>>('/auth/login', {
      usernameOrEmail,
      password,
    });
    
    if (response.data.success && response.data.data) {
      localStorage.setItem('accessToken', response.data.data.accessToken);
      localStorage.setItem('refreshToken', response.data.data.refreshToken);
      return response.data.data;
    }
    
    throw new Error(response.data.error || 'Login failed');
  }

  static async logout(): Promise<void> {
    const refreshToken = localStorage.getItem('refreshToken');
    await apiClient.post('/auth/logout', { refreshToken });
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  static async refreshToken(): Promise<void> {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) throw new Error('No refresh token');

    const response = await apiClient.post<ApiResponse<{ accessToken: string }>>('/auth/refresh', {
      refreshToken,
    });

    if (response.data.success && response.data.data) {
      localStorage.setItem('accessToken', response.data.data.accessToken);
    }
  }
}
```

---

## 🎨 **UI COMPONENT STRUCTURE**

### **Recommended Project Structure:**
```
src/
├── app/                    # Next.js 13+ app directory
│   ├── (auth)/
│   │   ├── login/
│   │   └── register/
│   ├── dashboard/
│   │   ├── users/
│   │   ├── roles/
│   │   ├── permissions/
│   │   └── audit-logs/
│   ├── layout.tsx
│   └── page.tsx
├── components/
│   ├── ui/                 # Reusable UI components
│   ├── forms/              # Form components
│   ├── layout/             # Layout components
│   └── auth/               # Auth-specific components
├── hooks/                  # Custom React hooks
├── lib/                    # Utilities and configurations
├── services/               # API services
├── stores/                 # State management
├── types/                  # TypeScript types
└── utils/                  # Helper functions
```

---

## 🔐 **AUTHENTICATION FLOW**

### **Required Pages:**
1. **Login Page** (`/login`)
2. **Dashboard** (`/dashboard`)
3. **User Management** (`/dashboard/users`)
4. **Role Management** (`/dashboard/roles`)
5. **Permission Management** (`/dashboard/permissions`)
6. **Audit Logs** (`/dashboard/audit-logs`)

### **Protected Route Example:**
```typescript
// components/auth/ProtectedRoute.tsx
import { useAuth } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { useEffect } from 'react';

interface ProtectedRouteProps {
  children: React.ReactNode;
  requiredPermission?: string;
}

export function ProtectedRoute({ children, requiredPermission }: ProtectedRouteProps) {
  const { user, loading, hasPermission } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!loading && !user) {
      router.push('/login');
    }
  }, [user, loading, router]);

  if (loading) return <div>Loading...</div>;
  if (!user) return null;
  
  if (requiredPermission && !hasPermission(requiredPermission)) {
    return <div>Access Denied</div>;
  }

  return <>{children}</>;
}
```

---

## 🌐 **ENVIRONMENT VARIABLES**

Create `.env.local`:
```bash
NEXT_PUBLIC_API_URL=http://localhost:8081/api
NEXT_PUBLIC_APP_NAME=ITSS Portal
NEXT_PUBLIC_APP_VERSION=1.0.0
```

---

## 🚀 **NEXT STEPS**

1. **Choose your framework** (Next.js recommended)
2. **Set up the project** with TypeScript and Tailwind
3. **Install dependencies** for API integration
4. **Implement authentication flow**
5. **Create dashboard layout**
6. **Build user management pages**

---

## 📞 **READY FOR IMPLEMENTATION**

Your backend is **100% ready** for frontend integration! 

**Tell me:**
1. Which framework did you choose?
2. Do you want me to create the initial project setup?
3. Should I start with the authentication flow?

Let's build an amazing frontend! 🎉 