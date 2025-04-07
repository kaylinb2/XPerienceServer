pipeline {
    agent any

    environment {
        DOCKER_IMAGE   = "xperience-server"
        CONTAINER_NAME = "xserver"
        PORT           = "${PORT_ENV ?: '9020'}"
        PASSWORD_FILE  = "passwords.txt"
        JAR_NAME       = "xperience-project-1.0-SNAPSHOT-jar-with-dependencies.jar"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/kaylinb2/XPerienceServer.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean compile assembly:single'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build --no-cache -t ${DOCKER_IMAGE} .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker rm -f ${CONTAINER_NAME} || true'
                sh """
                   docker run -d --name ${CONTAINER_NAME} -p ${PORT}:${PORT} ${DOCKER_IMAGE} ${PORT} ${PASSWORD_FILE}
                   """
            }
        }

        stage('Copy Jar to Local Directory') {
            steps {
                sh 'cp target/${JAR_NAME} ~/Program/'
            }
        }

        stage('Run Test Client') {
            steps {
                sh """
                   docker exec ${CONTAINER_NAME} java -cp app.jar xperience.XPerienceTestClient localhost ${PORT} || exit 1
                   """
            }
        }
    }

    post {
        always {
            echo 'Pipeline complete!'
        }
    }
}
