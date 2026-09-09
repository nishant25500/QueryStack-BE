package com.mishri.answer_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@EnableReactiveMongoAuditing

public class BaseModel {
    @Id
    private String id;

    @CreatedDate
    private Instant createdAt ;

    @LastModifiedDate
    private Instant updatedAt ;


}
