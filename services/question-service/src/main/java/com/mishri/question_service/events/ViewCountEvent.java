package com.mishri.question_service.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewCountEvent {

    private String targetId;
    private String targetType;
    private Instant timestamp;
}
