# Stage 1 – build the React app with Vite
FROM node:22-alpine AS build

WORKDIR /app

COPY package.json package-lock.json* ./
RUN npm install

COPY . .
RUN npm run build

# Stage 2 – serve with nginx
FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

# ← this line is the fix
COPY default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]