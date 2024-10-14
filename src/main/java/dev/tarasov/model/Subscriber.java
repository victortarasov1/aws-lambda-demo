package dev.tarasov.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "subscribers")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@ToString
@Getter @Setter
public class Subscriber {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String email;

    public Subscriber(String email) {
        this.email = email;
    }
}
