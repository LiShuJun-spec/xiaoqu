package com.baizhi.travels.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
public class Result {
    private Boolean state = true;
    private String msg;
    private String userId;
}
