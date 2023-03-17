package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

public interface LeaveMessageService {
    DataMap getUserLeaveWord(int rows, int pageNum);

    DataMap newLeaveWord(int rows, int pageNum);
}
