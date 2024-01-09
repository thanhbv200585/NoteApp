package com.task.noteapp.data.source

import java.sql.DriverManager

class PostgresNote {
    private val jdbcUrl: String = "jdbc:postgresql://localhost:5432/notedb"
    val user : String = "postgres"
    val password : String = "password"

    fun main() {

    }
    fun connect() {
        val connection = DriverManager.getConnection(jdbcUrl, user, password)
        print(connection.isValid(0))
    }
}
