package com.joaodev.labdesignpatternsspring.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "order_id", nullable = false, unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @Column(name = "payment_type_method", nullable = false)
    @NotBlank
    private String paymentTypeMethod;

    @Column(nullable = false, precision = 10, scale = 2)
    @NotNull
    private BigDecimal amount;

    @Column(nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;
}
