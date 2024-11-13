package de.maucon.mauconframework.stereotype

/**
 * Represents an entity with a unique identifier.
 *
 * @param ID the type of the entity's identifier.
 */
interface Entity<ID> {
    /**
     * The unique identifier for the entity.
     */
    var id: ID
}