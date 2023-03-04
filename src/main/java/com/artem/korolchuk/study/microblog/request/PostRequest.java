package com.artem.korolchuk.study.microblog.request;

import java.sql.Timestamp;
import lombok.Data;


@Data
public class PostRequest {
    
    private Timestamp publicDate;
    private String header;
    private String content;
    private int clientId;

    public PostRequest() {
        
    }
    
    public PostRequest(String header, String content) {
        if ((header == null || header.isBlank()) ||
                (content == null || content.isBlank())) {
            return;
        }
        
        this.header = header;
        this.content = content;
    }

    public PostRequest(String publicDate, String header, String content, int clientId) {
        if ((publicDate == null || publicDate.isBlank())    ||
                (header == null || header.isBlank())        ||
                (content == null || content.isBlank())      ||
                (clientId < 1)) {
            return;
        }
        
        this.publicDate = Timestamp.valueOf(publicDate);
        this.header = header;
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
