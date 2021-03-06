package edu.mentorship.votes.core.stave.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Data
@Document(collection = "stave")
public class Stave {

    @Id
    private String id;

    private String theme;

    private String description;

    private String state;

    private LocalDateTime startSessionVoting;

    private Long timeToLeaveSession;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private int totalVotesYes;

    private int totalVotesNo;

    private int totalVoteInvalid;

    private String identify;

    public Stave() {
        createAt = LocalDateTime.now(ZoneId.of("UTC"));
        updateAt = LocalDateTime.now(ZoneId.of("UTC"));
        identify = UUID.nameUUIDFromBytes(LocalDateTime.now().toString().getBytes()).toString();
    }

    public Boolean verifyState() {
        return state.equals(StateStave.CREATE.name());
    }
}
