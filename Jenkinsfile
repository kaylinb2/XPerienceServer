pipeline {
    agent any

    parameters {
        string(name: 'PORT', defaultValue: '9020', description: 'Port for the XPerience Server')
        string(name: 'PASSWORD_FILE', defaultValue: 'passwords.txt', description: 'Password file name')
    }

    environment {
        DOCKER_IMAGE   = "xperience-server"
        CONTAINER_NAME = "xserver"
        // Adjust this if your in-memory jar name is different.
        JAR_NAME       = "xperience-project-1.0-SNAPSHOT-jar-with-dependencies.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                // Replace with your repo if needed
                git 'https://github.com/kaylinb2/XPerienceServer.git'
            }
        }

        stage('Build with Maven') {
            steps {
                // Build the jar with dependencies
                sh 'mvn clean compile assembly:single'
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build with --no-cache to force a fresh build
                sh 'docker build --no-cache -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Run Docker Container') {
            steps {
                // Remove any old container named xserver
                sh 'docker rm -f ${CONTAINER_NAME} || true'
                // Run container with user-chosen PORT and PASSWORD_FILE
                // Mapping container port to same host port
                sh """
                   docker run -d --name ${CONTAINER_NAME} \
                   -p ${params.PORT}:${params.PORT} \
                   ${DOCKER_IMAGE} ${params.PORT} ${params.PASSWORD_FILE}
                   """
            }
        }
stage('Copy Jar to Local Directory') {
    steps {
        // Step A: Ensure the directory exists
        sh 'mkdir -p /var/lib/jenkins/Program'
        
        // Step B: Copy the jar
        sh 'cp target/${JAR_NAME} /var/lib/jenkins/Program/'
    }
}

    }

    post {
        always {
            echo 'Pipeline complete!'
        }
    }
}
