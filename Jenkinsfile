pipeline {
	
	agent any

	parameters {

		string(name: 'Greetings', defaultValue: 'Hello', description: 'How should i greet the world')
	}

	stages {

		stage('Build') {

			steps {
				def example = load "${rootDir}@script/test.groovy "
				example.test1()
				echo 'make build'
				echo "${params.Environment}"

			}

		}

		stage('Deploy') {

			when {

				expression {
					currentBuild.result == null || currentBuild.result == 'SUCCESS'
					}
			}

			steps {

				echo 'make publish'
			}

		}
	}
}