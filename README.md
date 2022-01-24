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

Open another terminal window. Make sure you have `netcat` installed. 

Run:
`nc localhost 8080`

From this window, you can put in a message and have it echoed back to you. If you use the token `byebye` on the client side, the server will shut down.
