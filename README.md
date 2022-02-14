
# Echo Server

An echo server which opens a server socket and client socket and allows messages typed into the terminal to be bounced back to the user.

## Instructions For Running With Gradle

Clone the repo locally and cd into repo.

This server uses Gradle. To build and run the project, from the root of the project folder:

` ./gradlew build`
Then, run:
`./gradlew run`

This will start the server socket. It will be listening on socket 8080 of localhost.

### Starting the client

Open another terminal window. Make sure you have `curl` installed. 

Run:
`curl -v  http://localhost:5000/`

You can add any url you want after the slash, and will receive a response.

To perform a post request:

`curl -v -d "example" -X POST http://localhost:5000/`