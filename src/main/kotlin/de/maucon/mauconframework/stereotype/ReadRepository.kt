package de.maucon.mauconframework.stereotype

/**
 * Repository interface for performing read-only operations on entities.
 *
 * @param ID the type of the identifier for the entity.
 * @param ENTITY the type of the entity that extends the [Entity] interface.
 */
interface ReadRepository<ID, ENTITY : Entity<ID>> {
    /**
     * Retrieves all entities from the repository.
     *
     * @return a list of all entities.
     */
    fun findAll(): List<ENTITY>

    /**
     * Retrieves an entity by its identifier.
     *
     * @param id the identifier of the entity to retrieve.
     * @return the entity if found, or null if no entity with the given id exists.
     */
    fun findById(id: ID): ENTITY?

    /**
     * Retrieves an entity by its identifier, throwing an exception if not found.
     *
     * @param id the identifier of the entity to retrieve.
     * @return the entity associated with the given id.
     */
    fun getById(id: ID): ENTITY

    /**
     * Checks if an entity with the specified identifier exists in the repository.
     *
     * @param id the identifier of the entity.
     * @return true if an entity with the given id exists, false otherwise.
     */
    fun exists(id: ID): Boolean
}