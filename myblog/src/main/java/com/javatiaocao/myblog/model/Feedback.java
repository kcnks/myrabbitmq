package com.javatiaocao.myblog.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    private int id;
    private String feedbackContent;
    private String contactInfo;
    private int personId;
    private String feedbackDate;

    public Feedback(String feedbackContent, String contactInfo, int personId, String feedbackDate) {
        this.feedbackContent = feedbackContent;
        this.contactInfo = contactInfo;
        this.personId = personId;
        this.feedbackDate = feedbackDate;
    }
}
