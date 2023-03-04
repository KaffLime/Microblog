package com.artem.korolchuk.study.microblog.request;

import java.sql.Timestamp;
import lombok.Data;


@Data
public class CommentRequest {
    
    private Timestamp publicDate;
    private String content;
    private int clientId;

    public CommentRequest() {
        
    }

    public CommentRequest(String content) {
        if (content == null || content.isBlank()) {
            return;
        }
        
        this.content = content;
    }

    public CommentRequest(String publicDate, String content, int clientId) {
        if ((publicDate == null || publicDate.isBlank())    ||
                (content == null || content.isBlank())      ||
                (clientId < 1)) {
            return;
        }
        
        this.publicDate = Timestamp.valueOf(publicDate);
        this.content = content;
        this.clientId = clientId;
    }
    
    public String getPublicDate() {
        return publicDate.toString();
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = Timestamp.valueOf(publicDate);
    }
}
