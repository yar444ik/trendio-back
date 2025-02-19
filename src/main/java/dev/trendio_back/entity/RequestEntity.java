package dev.trendio_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.sql.Date;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "requests")
public class RequestEntity {

    public RequestEntity(String adress, Date createDate, Date updateDate, BigDecimal latitude, BigDecimal longitude, String headerRequest, String textRequest) {
        this.adress = adress;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.headerRequest = headerRequest;
        this.textRequest = textRequest;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String adress;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;

    private BigDecimal latitude;
    private BigDecimal longitude;

//    @ManyToMany(mappedBy = "id", fetch = FetchType.LAZY)
//    public List<TagEntity> tags;
    //private List<LikeEntity> likes;

    @Column(name = "header_request")
    private String headerRequest;
    @Column(name = "text_request")
    private String textRequest;

    //private List<CommentEntity> comments;
}
