# Setup Instructions

## 1. Install Devbox
Download and install Devbox from [Jetify's official website](https://www.jetify.com/devbox).

## 2. Initialize Devbox Shell
Open a terminal and run:
```bash
    devbox shell
```
This command installs the required tools: Java, Kotlin, Gradle, Ruby, and SQLite.

## 3. Build and Run gRPC Server
Within the Devbox shell, execute:
```bash
    ./gradlew run
```
This builds and starts the gRPC server using the Exposed ORM.

## 4. Install Ruby Dependencies and Run gRPC Client
In another Devbox shell instance, execute:
```bash
    bundle install
```
This installs the `grpc-ruby` gem. Then, run:
```bash
    bundle exec ruby client.rb
```
This starts the gRPC client.

---

# How It Works

1. **Kotlin Server**: Acts as the ORM, powered by Exposed ORM.
2. **Ruby Client**: A simple client that communicates with the ORM via gRPC.
3. **gRPC**: Serves as the communication channel between the services, following a client-server architecture.

---

# Implementation Steps

## Kotlin Server

1. **Initialize the Project**: Create a Kotlin server using `gradle init`.
2. **Setup SQLite and Exposed ORM**: Install and configure SQLite and the Exposed ORM.
3. **Define Protobuf**: Create the proto file at `kotlinserver/app/src/main/proto/cross_orm.proto`.
4. **Install and Setup gRPC-Kotlin**: Follow the [gRPC-Kotlin setup guide](https://github.com/grpc/grpc-kotlin/blob/master/compiler/README.md).
5. **Generate gRPC Server Code**: Run:
```bash
        ./gradlew installDist
```
    This generates the gRPC server code.

6. **Implement RPC Methods**: Add implementations in the `CrossORMService` class located at `kotlinserver/app/src/main/kotlin/org/example/cross_orm/App.kt`.

## Ruby Client

1. **Initialize the Project**: Create a Ruby client using `bundle init`.
2. **Install gRPC-Ruby**: Add the `grpc-ruby` gem to the Gemfile and run `bundle install`.
3. **Generate gRPC Client Code**: Run:
```bash
        grpc_tools_ruby_protoc --proto_path=kotlinserver/app/src/main/proto/ \
          --ruby_out=rubyclient/ \
          --grpc_out=rubyclient/ \
          kotlinserver/app/src/main/proto/cross_orm.proto
```
4. **Call the gRPC Client**: Implement client logic in `rubyclient/client.rb` and execute it to interact with the Kotlin server.
