package de.maucon.mauconframework.stereotype.extension

import de.maucon.mauconframework.stereotype.Entity
import de.maucon.mauconframework.stereotype.Repository

/**
 * An abstract implementation of the [Repository] interface that uses an in-memory map
 * to store entities.
 *
 * @param ID the type of the identifier for the entity.
 * @param ENTITY the type of the entity that extends the [Entity] interface.
 *
 * @see Repository
 */
abstract class InMemoryMapRepository<ID, ENTITY : Entity<ID>> : Repository<ID, ENTITY> {
    /**
     * The in-memory map that stores entities, with the entity ID as the key.
     */
    protected var map = mutableMapOf<ID, ENTITY>()

    override fun findAll(): List<ENTITY> = map.values.toList()
    override fun findById(id: ID): ENTITY? = map[id]

    /**
     * Retrieves an entity by its identifier, throwing an exception if not found.
     *
     * @param id the identifier of the entity to retrieve.
     * @return the entity associated with the given id.
     * @throws NullPointerException if no entity with the given id exists.
     */
    override fun getById(id: ID): ENTITY = findById(id)!!

    override fun exists(id: ID): Boolean = map.containsKey(id)

    override fun save(entity: ENTITY): ENTITY = entity.also { map[entity.id] = entity }
    override fun saveAll(entities: Collection<ENTITY>): List<ENTITY> = entities.map { save(it) }

    override fun deleteById(id: ID): ENTITY? = map.remove(id)
    override fun delete(entity: ENTITY): ENTITY? = map.remove(entity.id)
}