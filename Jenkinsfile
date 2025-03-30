pipeline {
    agent any

    environment {
        REPO_URL = 'https://github.com/kaylinb2/XPerienceServer.git'
    }

    stages {
        stage('Checkout') {
            steps {
                git credentialsId: 'b9301dff-6d0a-4a4e-8e82-00eb2186882c', url: "${REPO_URL}"
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t xperience-server .'
            }
        }

        stage('Run Docker Container') {
            steps {
                sh 'docker rm -f xserver || true'
                sh 'docker run -d --name xserver -p 8081:8080 xperience-server'
            }
        }
    }

    post {
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}
