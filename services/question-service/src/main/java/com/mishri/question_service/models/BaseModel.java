package com.mishri.question_service.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BaseModel {
    @Id
    private String id;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;


}
