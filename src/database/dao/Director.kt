package com.alekseyld.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Directors : IntIdTable("director") {
    val fullName: Column<String> = varchar("full_name", 45)
}

class Director(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Director>(Directors)

    var fullName by Directors.fullName
}