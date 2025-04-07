pipeline {
    agent any

    environment {
        // Set environment variables for flexibility.
        DOCKER_IMAGE   = "xperience-server"
        CONTAINER_NAME = "xserver"
        PORT           = "9020"
        // Use the password file name/path as needed (if you mount it, adjust the container path accordingly)
        PASSWORD_FILE  = "passwords.txt"
        // Update JAR_NAME to reflect the correct jar (in-memory version)
        JAR_NAME       = "xperience-project-1.0-SNAPSHOT-jar-with-dependencies.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull code from GitHub
                git 'https://github.com/kaylinb2/XPerienceServer.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Use Maven to build the jar with dependencies.
                sh 'mvn clean compile assembly:single'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build the Docker image with no cache so that all changes are picked up.
                sh 'docker build --no-cache -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Run Docker Container') {
            steps {
                // Stop and remove any previous container with the same name.
                sh 'docker rm -f ${CONTAINER_NAME} || true'
                // Run the container in detached mode, mapping the specified port,
                // and passing the port and password file as arguments.
                sh 'docker run -d --name ${CONTAINER_NAME} -p ${PORT}:${PORT} ${DOCKER_IMAGE} ${PORT} ${PASSWORD_FILE}'
            }
        }

        stage('Copy JAR to Local Directory') {
            steps {
                // Copy the built jar to a local directory (adjust path as necessary).
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
