package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Friendlink {

    private int id;
    private String blogger;
    private String url;

    public Friendlink(String blogger, String url) {
        this.blogger = blogger;
        this.url = url;
    }
}
