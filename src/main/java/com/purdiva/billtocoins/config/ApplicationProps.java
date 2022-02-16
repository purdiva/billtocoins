package com.purdiva.billtocoins.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "denominations")
public class ApplicationProps {

    private List<Integer> accepted;

    public List<Integer> getAccepted() {
        return accepted;
    }

    public void setAccepted(List<Integer> accepted) {
        this.accepted = accepted;
    }
}
