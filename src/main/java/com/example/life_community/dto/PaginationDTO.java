package com.example.life_community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {

    private List<QuestionDTO> questionDTOList;
    private boolean showPrevions;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    // 计算
    public void setPagination(Integer totalPage, Integer page) {

        // 计算总页码
//        if(totalCount % size == 0) {
//            totalPage = totalCount / size;
//        } else {
//            totalPage = totalCount / size + 1;
//        }


        // 容错
//        if(page < 1) page = 1;
//        if(page > totalPage) page = totalPage;

        this.page = page;
        this.totalPage = totalPage;

        // 计算 pages
        /*
            page = 1/2/3/4
                1,2,3,4,5,6,7
            page = 5
                2,3,4,5,6,7,8
         */
        pages.add(page);
        for (int i=1; i<=3; i++) {
            if(page-i>0) {
                pages.add(0, page - i);
            }
            if(page+i<=totalPage) {
                pages.add(page+i);
            }
        }

        // 如果当前页码第一页，不展示上一页按钮
        if(page == 1) showPrevions = false;
        else showPrevions = true;
        // 如果当前页码最后页，不展示下一页按钮
        if(page == totalPage) showNext = false;
        else showNext = true;

        // 如果页码集合包含第一页，不展示首页按钮
        if(pages.contains(1)) showFirstPage = false;
        else showFirstPage = true;
        // 如果页码集合包含最后一页，不展示最后页按钮
        if(pages.contains(totalPage)) showEndPage = false;
        else showEndPage = true;

    }
}
