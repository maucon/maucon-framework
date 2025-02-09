package de.maucon.mauconframework.event

import java.time.Instant

abstract class Event {
    val creationTime: Instant = Instant.now()
}