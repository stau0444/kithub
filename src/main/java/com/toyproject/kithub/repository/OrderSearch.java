package com.toyproject.kithub.repository;

import com.toyproject.kithub.domain.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class OrderSearch {
    private String memberName;
    private Status orderStatus;

}
