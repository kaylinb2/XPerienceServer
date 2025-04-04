pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "xperience-server"
        CONTAINER_NAME = "xserver"
        JAR_NAME = "xperience-server-db-jar-with-dependencies.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/kaylinb2/XPerienceServer.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker rm -f ${CONTAINER_NAME} || true'
                sh 'docker run -d --name ${CONTAINER_NAME} -p 8081:8080 ${DOCKER_IMAGE}'
            }
        }

        stage('Copy JAR to ~/Program') {
            steps {
                sh 'cp target/${JAR_NAME} /home/kaylinb2/Program/'
            }
        }
    }
}
