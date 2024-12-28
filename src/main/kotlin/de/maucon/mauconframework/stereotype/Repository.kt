package de.maucon.mauconframework.stereotype

/**
 * Repository interface for performing CRUD operations on entities.
 *
 * @param ID the type of the identifier for the entity.
 * @param ENTITY the type of the entity that extends the [Entity] interface.
 *
 * @see ReadRepository
 * @see WriteRepository
 */
interface Repository<ID, ENTITY : Entity<ID>>
    : ReadRepository<ID, ENTITY>, WriteRepository<ID, ENTITY>