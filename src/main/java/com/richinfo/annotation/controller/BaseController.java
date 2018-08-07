package com.richinfo.annotation.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class BaseController {
    protected int pageNo;
    protected int pageSize;
    protected PageRequest buildPageRequest(int pageNo, int pageSize, String properties, Sort.Direction direction) {
        Sort sort = null;
        sort = new Sort(direction, properties);
        //参数1表示当前第几页,参数2表示每页的大小,参数3表示排序
        return new PageRequest(pageNo-1,pageSize,sort);
    }
}
