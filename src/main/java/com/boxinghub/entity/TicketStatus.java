package com.boxinghub.entity;

public enum TicketStatus {
    PENDING,     // Đang chờ xử lý
    IN_PROGRESS, // Đang giải quyết
    RESOLVED,    // Đã xử lý xong
    REJECTED     // Bị từ chối
}