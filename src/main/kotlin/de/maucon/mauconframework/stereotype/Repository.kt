package de.maucon.mauconframework.stereotype

interface Repository<ID, ENTITY : Entity<ID>> {
    fun findAll(): List<ENTITY>
    fun findById(id: ID): ENTITY?
    fun getById(id: ID): ENTITY

    fun exists(id: ID): Boolean

    fun save(entity: ENTITY): ENTITY
    fun saveAll(entities: Collection<ENTITY>): List<ENTITY>

    fun deleteById(id: ID): ENTITY?
    fun delete(entity: ENTITY): ENTITY?
}