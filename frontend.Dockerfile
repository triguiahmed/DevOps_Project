# Use the official Node.js image as the base image
FROM node:alpine as builder

# Set the working directory in the container
WORKDIR /front

# Copy package.json and package-lock.json to the container
COPY DevOps_Project_Front/package*.json ./

# Install project dependencies
RUN npm install

# Copy the entire project to the container
COPY ./DevOps_Project_Front/ .

# Build the Angular app for production
RUN npm run build

# Use a smaller, production-ready image as the final image
FROM nginx:alpine

# Copy the production-ready Angular app to the Nginx webserver's root directory
COPY --from=builder /front/dist/summer-workshop-angular /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]
