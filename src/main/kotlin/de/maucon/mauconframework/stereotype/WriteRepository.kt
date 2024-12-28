package de.maucon.mauconframework.stereotype

/**
 * Repository interface for performing write-only operations on entities.
 *
 * @param ID the type of the identifier for the entity.
 * @param ENTITY the type of the entity that extends the [Entity] interface.
 */
interface WriteRepository<ID, ENTITY : Entity<ID>> {
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