package com.alekseyld.database

import org.jetbrains.exposed.sql.Database

object DatabaseInit {

    private const val HOSTNAME = "localhost"
    private const val PORT = "3306"
    private const val DATABASE_NAME = "cinema_database"

    private const val JDBC_URL = "jdbc:mysql://$HOSTNAME:$PORT/$DATABASE_NAME?characterEncoding=utf8&useUnicode=true"

    private const val USERNAME = "root"
    private const val PASSWORD = "password"



    fun connect() {

        Database.connect(
            JDBC_URL,
            driver = "com.mysql.jdbc.Driver",
            user = USERNAME,
            password = PASSWORD
        )

    }

}