package de.maucon.mauconframework.command.cancellable

/**
 * Abstract class representing a cancellable command.
 *
 * Extends the `Cancellable` interface, allowing commands to be cancelled before execution.
 */
abstract class CancellableCommand : Cancellable {
    private var isCancelled = false

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    /**
     * Cancels the command, preventing further execution.
     */
    fun cancel() {
        isCancelled = true
    }
}