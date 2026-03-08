package com.pharmacy.Pharmacy_mgmt.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_platforms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPlatform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;
}
