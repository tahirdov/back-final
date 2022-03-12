pipeline {
    agent any
    tools {
        maven 'apache-maven-3.8.4'
        }
    stages {
        stage ('Compile') {
            steps {
                    sh 'mvn clean compile'
            }
        }
        stage ('Test') {
            steps {
                    sh 'mvn test'
            }
        }

        stage('Build Image') {
            steps {
                 script {
                     sh 'mvn clean install && docker build -t orkhan2000/phone-app-backend .'
                 }
            }
        }
        stage('Deploy') {
            steps {
                 script {
                     withCredentials([string(credentialsId: 'Docker-Hub-Password', variable: 'dockerhubpwd')]) {
                         sh 'docker login -u orkhan2000 -p ${dockerhubpwd}'
                     }
                     sh 'docker push  orkhan2000/phone-app-backend'
                 }
            }
        }
    }
}