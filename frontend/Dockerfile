# =========================================
# Stage 1: Build the Angular Application
# =========================================
FROM node:18-alpine AS build
WORKDIR /app

# Temporary placeholder: no dependencies, no source
RUN echo "Frontend placeholder stage — Angular build not implemented yet."

# Copy package-related files first to leverage Docker's caching mechanism
#COPY package.json package-lock.json ./

# Install project dependencies using npm ci (ensures a clean, reproducible install)
# npm ci is like npm i but faster, reliable for docker builds and even CI/CD pipelines
# RUN --mount=type=cache,target=/root/.npm npm ci

# Copy source code into container
#COPY . .
# Build Angular app for production
# RUN npm run build --prod

# =========================================
# Stage 2: Prepare Nginx to Serve Static Files
# =========================================

FROM nginx:1.25-alpine
#COPY --from=build /app/dist/frontend /usr/share/nginx/html

# Placeholder: no dist folder copy yet
RUN echo "Frontend placeholder — Nginx serving nothing yet."

# Copy default nginx configuration (optional, for SPA routing)
#COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
