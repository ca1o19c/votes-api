package edu.mentorship.votes.application.usecase;

import edu.mentorship.votes.application.dto.InputNewStaveDto;
import edu.mentorship.votes.application.dto.StaveDto;
import edu.mentorship.votes.core.shared.command.ServiceCommand;
import edu.mentorship.votes.core.stave.domain.StateStave;
import edu.mentorship.votes.core.stave.domain.Stave;
import edu.mentorship.votes.core.stave.service.CreatedServiceCommand;
import edu.mentorship.votes.structure.exception.BusinessException;
import edu.mentorship.votes.structure.mapper.MessageMapper;
import edu.mentorship.votes.structure.repository.StaveRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import static edu.mentorship.votes.core.shared.command.CommandNameConstants.CREATED_COMMAND;

@Component
public class CreateStave {

    private final ModelMapper modelMapper;

    private final StaveRepository staveRepository;

    private final ServiceCommand<Stave, Stave> serviceCommand;

    public CreateStave(ModelMapper modelMapper, StaveRepository staveRepository,
                       @Qualifier(CREATED_COMMAND) ServiceCommand<Stave, Stave> serviceCommand) {
        this.modelMapper = modelMapper;
        this.staveRepository = staveRepository;
        this.serviceCommand = serviceCommand;
    }

    public StaveDto create(InputNewStaveDto inputNewStaveDto) {

        var staveExists = staveRepository.existsStaveByThemeAndStateNot(
                inputNewStaveDto.getTheme(), StateStave.SESSION_VOTES_DONE.name());

        if (staveExists) {
            throw new BusinessException(HttpStatus.CONFLICT, MessageMapper.THEME_CONFLICT.getCode());
        }

        var entity = modelMapper.map(inputNewStaveDto, Stave.class);

        staveRepository.save(entity);

        serviceCommand.execute(entity);

        return modelMapper.map(entity, StaveDto.class);
    }
}
