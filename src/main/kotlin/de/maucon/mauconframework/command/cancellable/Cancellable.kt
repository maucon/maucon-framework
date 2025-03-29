package de.maucon.mauconframework.command.cancellable

interface Cancellable {
    fun isCancelled(): Boolean

    fun setCancelled(cancel: Boolean)
}
