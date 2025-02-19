package dev.trendio_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "likes")
public class LikeEntity {
    public LikeEntity(Long id, Date createDate, long userId) {
        this.id = id;
        this.createDate = createDate;
        this.userId = userId;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "user_id")
    private long userId;
    //private long requestId;
}
