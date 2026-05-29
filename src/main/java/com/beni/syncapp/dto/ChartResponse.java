package com.beni.syncapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChartResponse {

    private List<String> categories;

    private List<Long> data;
}