package com.joaodev.labdesignpatternsspring.service.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationListener {

    @EventListener
    public void handle(OrderStatusChangedEvent event) {
        System.out.printf(
                "[NOTIFICATION] Pedido #%d mudou de %s para %s. Enviando notificação para o cliente...%n",
                event.order().getId(),
                event.previousStatus(),
                event.newStatus()
        );
    }
}
