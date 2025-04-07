pipeline {
    agent any

    environment {
        // Customize these values as needed.
        DOCKER_IMAGE   = "xperience-server"
        CONTAINER_NAME = "xserver"
        // Change this value to run on any port you want.
        PORT           = "9020"
        // In the container, the password file will be available at /app/passwords.txt
        PASSWORD_FILE  = "passwords.txt"
        // Update this to match your jar file name (for the in-memory server)
        JAR_NAME       = "xperience-project-1.0-SNAPSHOT-jar-with-dependencies.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull code from your GitHub repository.
                git 'https://github.com/kaylinb2/XPerienceServer.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Build the jar with dependencies.
                sh 'mvn clean compile assembly:single'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build the Docker image without cache to pick up all changes.
                sh 'docker build --no-cache -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Run Docker Container') {
            steps {
                // Stop and remove any previous container with the same name.
                sh 'docker rm -f ${CONTAINER_NAME} || true'
                // Run the container in detached mode, mapping the specified port.
                // The container is run with the PORT and PASSWORD_FILE as arguments.
                sh """
                   docker run -d --name ${CONTAINER_NAME} -p ${PORT}:${PORT} ${DOCKER_IMAGE} ${PORT} ${PASSWORD_FILE}
                   """
            }
        }

        stage('Copy Jar to Local Directory') {
            steps {
                // Copy the built jar to your host's ~/Program directory.
                sh 'cp target/${JAR_NAME} ~/Program/'
            }
        }
    }

    post {
        always {
            echo 'Pipeline complete!'
        }
    }
}
