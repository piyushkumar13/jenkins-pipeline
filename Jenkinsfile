pipeline {
    agent any
    tools {
        maven 'my-maven'
        jdk 'my-java'
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
            steps {
                sh 'echo printenv'
            }
        }

        stage('Printing predefined environment variables') {

            steps {
                sh '''
                            echo "Running ${env.BUILD_ID} on ${env.JENKINS_URL} \n"
                            echo "This is the example of echoing predefined environment variables."

                          '''
            }
        }

        stage('Printing Parameter values'){
            steps {
                sh 'echo "The name of the job is ${params.jobName}'
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
            echo 'The pipeline has finish!'
        }
    }
}