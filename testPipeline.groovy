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

    stage('Show input based on parameter value') {

      steps {

        script {
          def inputValues = null

          if (${jobName} == "my-pipeline-job") {
            inputValues = input(
              id: 'userInput', message: 'Define following to continue', parameters: [
              string(name: 'COMPANY', defaultValue: 'LogMeIn', description: 'Name of the company'),
              string(name: 'LOCATION', defaultValue: "$MY_ADDRESS", description: 'Location of the company')
            ])
          }

          node("rhel6") {

            sh 'echo "The name of the job is ${jobName}"'
            sh 'echo "the input value is ${inputValues}"'
            println("The input value in println is "+ inputValues)
          }

        }



        checkpoint "Printing parameter values complete!!"
      }

    }


  }
  post {
    always {
      echo "The pipeline has finish!"
    }
  }
}
