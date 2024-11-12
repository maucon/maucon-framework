package de.maucon.mauconframework.extension

import de.maucon.mauconframework.stereotype.Entity
import de.maucon.mauconframework.stereotype.Repository

abstract class InMemoryMapRepository<ID, ENTITY : Entity<ID>> : Repository<ID, ENTITY> {
    protected var map = mutableMapOf<ID, ENTITY>()

    override fun findAll() = map.values.toList()

    override fun findById(id: ID) = map[id]

    override fun getById(id: ID) = findById(id)!!

    override fun exists(id: ID) = map.containsKey(id)

    override fun save(entity: ENTITY): ENTITY = entity.also { map[entity.id] = entity }

    override fun saveAll(entities: Collection<ENTITY>) = entities.map { save(it) }

    override fun deleteById(id: ID) = map.remove(id)

    override fun delete(entity: ENTITY) = map.remove(entity.id)
}