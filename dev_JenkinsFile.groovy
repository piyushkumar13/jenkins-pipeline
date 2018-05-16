def getName() {

    return "Pk"
}

pipeline {
    agent none
//    tools {
//        maven 'my-maven'
//        jdk 'my-java'
//    }

    tools {
        maven 'mvn325'
        jdk 'jdk18'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '4'))
    }

    environment {
        MY_NAME = "Piyush Kumar"
        MY_ADDRESS = "abc"
        MY_COMPANY = 'abcCompany'
    }

    parameters {
        string(name: 'jobName', defaultValue: 'my-pipeline-job', description: 'Please enter the jobname')
    }

    stages {
        stage('Print environment variables') {

            input {
                id 'userInput'
                message 'Let\'s promote?'
                parameters {

                    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
                    string(name: 'Last', defaultValue: 'kumar', description: 'Who should I say hello to?')

//                        [$class: 'GlobalVariableStringParameterDefinition',defaultValue: 'Piyush', description: 'Your name', name: 'pname']
//                        [$class: 'GlobalVariableStringParameterDefinition',defaultValue: 'Kumar', description: 'Your Last name', name: 'lname']

                }

            }

            agent {
                label 'rhel6'
            }

            steps {
                echo "My name is ${MY_NAME}"
                echo "The person is ${PERSON}"

                script{

                    def someDummyValue = "someThing"

                    env.MY_COMPANY = someDummyValue
//                    env.MY_COMPANY = "someThing"
                }

                echo "Echoing environments in jenkins file"
                sh "printenv"

                script{
                    env.MY_COMPANY = "Logmein"
                }

                echo "Echoing environments in jenkins file after setting my company to logmein"
                sh "printenv"
                checkpoint "Printing environment variables complete!!"

            }

        }

        stage('Printing predefined environment variables') {

            agent {
                label 'rhel6'
            }
            steps {
                sh '''
                            echo "Running ${BUILD_ID} on ${JENKINS_URL} \n"
                            echo "Jenkins home is ${JENKINS_HOME} \n"
                            echo "Jenkins Job url is ${JOB_URL} \n"
                            echo "This is the example of echoing predefined environment variables."

                          '''
                checkpoint "Printing predefined environment variables complete!!"

            }

        }

        stage('Printing Parameter values') {
            agent {
                label 'rhel6'
            }
            steps {
                sh 'echo "The name of the job is ${jobName}"'
                checkpoint "Printing parameter values complete!!"

            }


        }

        stage('Compile Stage') {
            agent {
                label 'rhel6'
            }
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test Stage') {
            agent {
                label 'rhel6'
            }
            steps {
                sh 'mvn test'
            }
        }


        stage('Install stage') {
//            input {
//                message "Should we continue?"
//                ok "Yes, we should."
//                parameters {
//                    string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
//                }
//            }
//            agent {
//                label 'rhel6'
//            }
            steps {


//                The following script was placed to use the environment variable in the parameters for the input()
                script {
                    def inputValues = input(
                            id: 'userInput', message: 'Define following to continue', parameters: [
                            string(name: 'COMPANY', defaultValue: 'LogMeIn', description: 'Name of the company'),
                            string(name: 'LOCATION', defaultValue: "$MY_ADDRESS", description: 'Location of the company')
                    ])

                    println("The input values are $inputValues")

//                   We can move this node block outside the script also.
                    node("rhel6") {
                        withMaven(maven: 'mvn325') {
                            sh "mvn install"
                        }
                    }
                }

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