package org.example.crossorm

import io.grpc.Server
import io.grpc.ServerBuilder
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import io.grpc.Status
import org.jetbrains.exposed.dao.id.IntIdTable

class App(
    val port: Int,
    val server: Server = ServerBuilder.forPort(port).addService(CrossORMService()).build(),
) {
    init {
        Database.connect("jdbc:sqlite:sample.db", driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.create(Users)

            Users.insertIgnore {
                it[name] = "John Doe"
                it[age] = 30
            }
        }
    }

    fun start() {
        server.start()
        println("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(
            Thread {
                println("*** shutting down gRPC server since JVM is shutting down")
                this@App.stop()
                println("*** server shut down")
            },
        )
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }

    object Users : IntIdTable() {
        val name = varchar("name", length = 50)
        val age = integer("age")
    }

    internal class CrossORMService() : CrossORMGrpcKt.CrossORMCoroutineImplBase() {
        override suspend fun getUser(request: UserID): User {
            val user = transaction {
                Users.select(Users.id, Users.name, Users.age).where { Users.id eq request.id }
                    .mapNotNull {
                        User.newBuilder()
                            .setId(it[Users.id].value)
                            .setName(it[Users.name])
                            .setAge(it[Users.age])
                            .build()
                    }
                    .singleOrNull()
            }

            return user ?: throw Status.NOT_FOUND
                .withDescription("User with ID ${request.id} not found")
                .asRuntimeException()
        }

        override suspend fun createUser(request: NewUser): User {
            val id = transaction {
                Users.insertAndGetId {
                    it[name] = request.name
                    it[age] = request.age
                }
            }.value

            return User.newBuilder()
                .setId(id)
                .setName(request.name)
                .setAge(request.age)
                .build()
        }
    }
}

fun main() {
    val port = 8980
    val server = App(port)
    server.start()
    server.blockUntilShutdown()
}
