package com.joaodev.labdesignpatternsspring.service.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderAuditListener {

    @EventListener
    public void handle(OrderStatusChangedEvent event) {
        System.out.printf(
                "[AUDIT] Pedido #%d: %s -> %s em %s%n",
                event.order().getId(),
                event.previousStatus(),
                event.newStatus(),
                LocalDateTime.now()
        );
    }
}
