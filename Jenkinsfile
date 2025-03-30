pipeline {
    agent any

    stages {
        stage('Clone Repository') {
            steps {
                git credentialsId: '23157542-1d1a-483e-bc03-b7e85a061afb', url: 'https://github.com/kaylinb2/XPerienceServer.git', branch: 'main'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}
