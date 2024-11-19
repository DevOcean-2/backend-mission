package com.devocean.Balbalm.mission.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfo {
    private Long id;
    private String userId;
    private String name;
    private String profileImageUrl;
    private String nickName;
}
