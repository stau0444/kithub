package com.toyproject.kithub.repository.order.query;

import com.toyproject.kithub.domain.Address;
import com.toyproject.kithub.domain.Order;
import com.toyproject.kithub.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SimpleOrderQueryDto {

        private Long id;
        private String name;
        private LocalDateTime orderDateTime;
        private Status status;
        private Address address;


}
