pipeline {
    agent any
    tools {
        maven 'my-maven'
        jdk 'my-java'
    }
    stages {

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test Stage') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Install stage') {
            steps {
                sh 'mvn install'

            }
        }
    }
}