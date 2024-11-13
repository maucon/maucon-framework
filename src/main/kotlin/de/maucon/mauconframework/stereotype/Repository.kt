package de.maucon.mauconframework.stereotype

/**
 * Repository interface for performing CRUD operations on entities.
 *
 * @param ID the type of the identifier for the entity.
 * @param ENTITY the type of the entity that extends the [Entity] interface.
 */
interface Repository<ID, ENTITY : Entity<ID>> {
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

    /**
     * Saves an entity to the repository.
     *
     * @param entity the entity to be saved.
     * @return the saved entity.
     */
    fun save(entity: ENTITY): ENTITY

    /**
     * Saves a collection of entities to the repository.
     *
     * @param entities the collection of entities to be saved.
     * @return a list of saved entities.
     */
    fun saveAll(entities: Collection<ENTITY>): List<ENTITY>

    /**
     * Deletes an entity by its identifier.
     *
     * @param id the identifier of the entity to delete.
     * @return the deleted entity, or null if no entity with the given id exists.
     */
    fun deleteById(id: ID): ENTITY?

    /**
     * Deletes the specified entity.
     *
     * @param entity the entity to delete.
     * @return the deleted entity, or null if the entity does not exist.
     */
    fun delete(entity: ENTITY): ENTITY?
}