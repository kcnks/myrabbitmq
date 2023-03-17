package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Feedback;
import com.javatiaocao.myblog.utils.DataMap;

public interface FeedbackService {
    DataMap getAllFeedback(int rows, int pageNum);

    DataMap submitFeedback(Feedback feedback);
}
