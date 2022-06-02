node(any)
    stages{
        stage("git"){
            steps{
                git 'https://github.com/wakaleo/game-of-life.git'
            }
        }
        stage("build"){
            steps{
                sh 'mvn package'
                 
            }
        }
        stage("artifact"){
            steps{
               archiveArtifacts artifacts: 'gameoflife-web/target/*.war', followSymlinks: false
                 
            }
        }
        stage("test results"){
            steps{
                junit 'gameoflife-web/target/surefire-reports/*.xml'
                 
            }
        }


}

## node with credentialsId
node {
    withCredentials([usernameColonPassword(credentialsId: 'nodenode', variable: '')]) {
    // some block
}
}