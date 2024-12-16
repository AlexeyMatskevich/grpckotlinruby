# Setup Instructions

### 1. Install Devbox
Download and install Devbox from [Jetify's official website](https://www.jetify.com/devbox).

### 2. Initialize Devbox Shell
Open a terminal and run:
```bash
devbox shell
```
This will install the required tools: Java, Kotlin, Gradle, Ruby, and SQLite.
### 3. Build and Run gRPC Server
In the devbox shell, execute:
```bash
./gradlew run
```
This will build and start the gRPC server using the Exposed ORM.
### 4. Install Ruby Dependencies and Run gRPC Client

In another devbox shell instance, run:
```bash
bundle install
```
to install the grpc-ruby gem. Then, execute:
```bash
bundle exec ruby client.rb
```
to start the gRPC client.
