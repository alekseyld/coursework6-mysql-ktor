package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Genres : IntIdTable("genre") {
    val name: Column<String> = varchar("name", 256)
}

class Genre(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Genre>(Genres)

    var name by Genres.name
}