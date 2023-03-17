package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

public interface CommentService {
    DataMap newComment(int rows, int pageNum);
}
