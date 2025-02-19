package ru.pachan.reader_kotlin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.pachan.reader_kotlin.model.Notification

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {

    fun findByPersonId(personId: Long): Notification?

}