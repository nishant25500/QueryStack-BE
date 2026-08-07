package com.mishri.answer_service.mappers;

import com.mishri.answer_service.dto.request.CreateAnswerRequest;
import com.mishri.answer_service.dto.response.AnswerResponse;
import com.mishri.answer_service.models.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "createdBy", source = "createdBy")
    public Answer toModel(CreateAnswerRequest request, String createdBy);

    public AnswerResponse toResponse(Answer answer);
}