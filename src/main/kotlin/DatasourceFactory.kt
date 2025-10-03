package com.saulnunez

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

object DatasourceFactory {
    private val config = HikariConfig().apply {
        jdbcUrl = System.getenv("DATABASE_URL") ?: "jdbc:postgresql://localhost:5432/urlita"
        username = System.getenv("DB_USER") ?: "postgres"
        password = System.getenv("DB_PASSWORD") ?: "password"
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 10
    }

    val dataSource: HikariDataSource by lazy {
        HikariDataSource(config)
    }
}