pipeline {
    agent none
    tools {
        maven 'my-maven'
        jdk 'my-java'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '4'))
    }

    environment {
        MY_NAME = "Piyush Kumar"
        MY_ADDRESS = "abc"
    }

    parameters {
        string(name: 'jobName', defaultValue: 'my-pipeline-job',description: 'Please enter the jobname')
    }

    stages {
        stage('Print environment variables') {

            input(
                    id: 'userInput', message: 'Let\'s promote?', parameters: [
                    [$class: 'TextParameterDefinition', defaultValue: 'valueone', description: 'one', name: 'valueone'],
                    [$class: 'TextParameterDefinition', defaultValue: 'valuetwo', description: 'two', name: 'valuetwo'],
                    [$class: 'TextParameterDefinition', defaultValue: 'valuethree', description: 'three', name: 'valuethree'],
                    [$class: 'TextParameterDefinition', defaultValue: 'valuefour', description: 'fouter', name: 'valuefour']
            ])

            agent any

            steps {
                echo "My name is ${MY_NAME}"
            }
        }

        stage('Printing predefined environment variables') {

            steps {
                sh '''
                            echo "Running ${BUILD_ID} on ${JENKINS_URL} \n"
                            echo "Jenkins home is ${JENKINS_HOME} \n"
                            echo "Jenkins Job url is ${JOB_URL} \n"
                            echo "This is the example of echoing predefined environment variables."

                          '''
            }
        }

        stage('Printing Parameter values'){
            steps {
                sh 'echo "The name of the job is ${jobName}"'
            }
        }

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

        add(ED)

        stage('Install stage') {
            input {
                message "Should we continue?"
                ok "Yes, we should."
                parameters {
                    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
                }
            }
            steps {
                sh 'mvn install'

            }
        }
//        stage('Deploy stage') {
//            steps {
//                sh 'mvn spring-boot:run'
//
//            }
//        }
    }
    post {
        always {
            echo "The pipeline has finish!"
        }
    }
}