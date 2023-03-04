package com.artem.korolchuk.study.microblog.request;

import lombok.Data;


@Data
public class LikeRequest {
    
    private int clientId;

    public LikeRequest() {
        
    }
    
    public LikeRequest(int clientId) {
        if (clientId < 1) {
            return;
        }
        
        this.clientId = clientId;
    }
}
