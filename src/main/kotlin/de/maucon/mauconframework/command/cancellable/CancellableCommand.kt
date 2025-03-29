package de.maucon.mauconframework.command.cancellable

abstract class CancellableCommand : Cancellable {
    private var isCancelled = false

    override fun isCancelled() = isCancelled

    override fun setCancelled(cancel: Boolean) {
        isCancelled = cancel
    }

    fun cancel() {
        isCancelled = true
    }
}