package edu.mentorship.votes.core.session.service;

import edu.mentorship.votes.core.session.SessionRepresentation;
import edu.mentorship.votes.core.session.entity.Session;
import edu.mentorship.votes.core.session.entity.SessionEventRepresentationAdapter;
import edu.mentorship.votes.core.shared.command.ServiceCommand;
import edu.mentorship.votes.core.shared.event.SessionStarted;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StartedSessionCommand implements ServiceCommand<Session, SessionRepresentation> {

    @Autowired
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public SessionEventRepresentationAdapter execute(Session input) {
        SessionEventRepresentationAdapter representationAdapter = SessionEventRepresentationAdapter.of(input.getIdentify(),
                input.getTimeToLive(), input.getSessionState().name(), input.getCreatedAt(), input.getFinishedAt());

        SessionStarted sessionStarted = new SessionStarted(representationAdapter);

        applicationEventPublisher.publishEvent(sessionStarted);

        return representationAdapter;
    }
}