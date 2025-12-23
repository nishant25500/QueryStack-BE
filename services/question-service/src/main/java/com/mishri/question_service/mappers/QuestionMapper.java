package com.mishri.question_service.mappers;

import com.mishri.question_service.dto.QuestionRequestDTO;
import com.mishri.question_service.dto.QuestionResponseDTO;
import com.mishri.question_service.models.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    //request -> entity
    public Question toModel(QuestionRequestDTO questionRequestDTO);

    //entity -> response
    public QuestionResponseDTO toDto(Question question);
}


//Note: we have to implement static methods in an interface
