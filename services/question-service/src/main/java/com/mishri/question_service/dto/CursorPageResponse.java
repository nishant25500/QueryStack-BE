package com.mishri.question_service.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CursorPageResponse<T> {
    private List<T> items;
    private String nextCursor;
    private boolean hasNext;

//    public CursorPageResponse(List<T> list, String nextCursor, boolean hasNext) {
//        this.items = list;
//        this.hasNext =hasNext;
//        this.nextCursor = nextCursor;
//    }
}
