package com.spring.agendalive.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document
@Data
public class LiveDocument {

    @Id
    private String id;
    private String liveName;
    private String channelName;
    private LocalDateTime liveDate;
    private String liveLink;
    private LocalDateTime registrationDate;
}
