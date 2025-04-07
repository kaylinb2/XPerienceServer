pipeline {
    agent any

    parameters {
        string(name: 'PORT', defaultValue: '9020', description: 'Port for the XPerience Server')
        string(name: 'PASSWORD_FILE', defaultValue: 'passwords.txt', description: 'Password file to load')
    }

    environment {
        DOCKER_IMAGE   = "xperience-server"
        CONTAINER_NAME = "xserver"
        // Update this if your jar file name is different.
        JAR_NAME       = "xperience-project-1.0-SNAPSHOT-jar-with-dependencies.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                // Pull code from GitHub repository.
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
                // Build a fresh Docker image with no cache.
                sh 'docker build --no-cache -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Run Docker Container') {
            steps {
                // Remove any existing container with the same name.
                sh 'docker rm -f ${CONTAINER_NAME} || true'
                // Run the container using the parameters provided from Jenkins,
                // mapping the parameter PORT to the container.
                sh """
                   docker run -d --name ${CONTAINER_NAME} -p ${params.PORT}:${params.PORT} ${DOCKER_IMAGE} ${params.PORT} ${params.PASSWORD_FILE}
                   """
            }
        }

        stage('Copy Jar to Local Directory') {
            steps {
                // Copy the built jar to the specified local directory.
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
