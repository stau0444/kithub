package com.toyproject.kithub.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;

@Embeddable
@Getter  // 임베디드 타입은 불변객체로 만드는 것이 좋다
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

    private String city;

    private String street;

    private String zipcode;


}
