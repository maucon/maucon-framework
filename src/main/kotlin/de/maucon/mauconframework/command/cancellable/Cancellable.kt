package de.maucon.mauconframework.command.cancellable

/**
 * Interface representing a cancellable operation.
 *
 * Classes implementing this interface can be cancelled during execution.
 */
interface Cancellable {
    /**
     * Checks whether the operation has been cancelled.
     *
     * @return `true` if the operation is cancelled, `false` otherwise.
     */
    fun isCancelled(): Boolean

    /**
     * Sets the cancelled state of the operation.
     *
     * @param cancel `true` to cancel the operation, `false` to allow execution.
     */
    fun setCancelled(cancel: Boolean)
}
