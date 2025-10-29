package ru.pachan.reader_kotlin.service

import com.google.protobuf.Any
import com.google.rpc.Code
import com.google.rpc.ErrorInfo
import com.google.rpc.Status
import io.grpc.protobuf.StatusProto.toStatusException
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import ru.pachan.grpc.NotificationServiceGrpc.NotificationServiceImplBase
import ru.pachan.grpc.Reader
import ru.pachan.grpc.Reader.FindByPersonIdNotificationRequest
import ru.pachan.reader_kotlin.exception.data.RequestException
import ru.pachan.reader_kotlin.model.Notification
import ru.pachan.reader_kotlin.repository.NotificationRepository
import ru.pachan.reader_kotlin.util.ExceptionEnum

@GrpcService
class NotificationService(
    private val repository: NotificationRepository,
) : NotificationServiceImplBase() {

    override fun findByPersonIdNotification(
        request: FindByPersonIdNotificationRequest?,
        responseObserver: StreamObserver<Reader.FindByPersonIdNotificationResponse?>?,
    ) {

        var notification: Notification
        try {
            notification = repository.findByPersonId(request!!.personId) ?: throw RequestException(
                ExceptionEnum.OBJECT_NOT_FOUND.message,
                HttpStatus.GONE
            )
            val grpcNotification = Reader.Notification.newBuilder()
                .setNotificationId(notification.id)
                .setPersonId(notification.personId)
                .setCount(notification.count)
                .build()
            val response = Reader.FindByPersonIdNotificationResponse.newBuilder()
                .setNotification(grpcNotification)
                .build()
            responseObserver!!.onNext(response)
        } catch (e: RequestException) {
            val errorInfo = ErrorInfo.newBuilder()
                .setReason(e.message)
                .putMetadata("message", e.message)
                .putMetadata("httpStatus", e.httpStatus.value().toString())
                .build()
            val status = Status.newBuilder()
                .setCode(Code.NOT_FOUND.number)
                .setMessage(e.message)
                .addDetails(Any.pack(errorInfo))
                .build()
            responseObserver!!.onError(toStatusException(status))
            return
        }
        responseObserver.onCompleted()
    }

    override fun findByIdNotification(
        request: Reader.FindByIdNotificationRequest?,
        responseObserver: StreamObserver<Reader.FindByIdNotificationResponse?>?,
    ) {
        var notification: Notification
        try {
            notification = repository.findByIdOrNull(request!!.notificationId) ?: throw RequestException(
                ExceptionEnum.OBJECT_NOT_FOUND.message,
                HttpStatus.GONE
            )
            val grpcNotification = Reader.Notification.newBuilder()
                .setNotificationId(notification.id)
                .setPersonId(notification.personId)
                .setCount(notification.count)
                .build()
            val response = Reader.FindByIdNotificationResponse.newBuilder()
                .setNotification(grpcNotification)
                .build()
            responseObserver!!.onNext(response)
        } catch (e: RequestException) {
            val errorInfo = ErrorInfo.newBuilder()
                .setReason(e.message)
                .putMetadata("message", e.message)
                .putMetadata("httpStatus", e.httpStatus.value().toString())
                .build()
            val status = Status.newBuilder()
                .setCode(Code.NOT_FOUND.number)
                .setMessage(e.message)
                .addDetails(Any.pack(errorInfo))
                .build()
            responseObserver!!.onError(toStatusException(status))
            return
        }
        responseObserver.onCompleted()
    }

}