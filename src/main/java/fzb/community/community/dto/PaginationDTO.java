package fzb.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage,Integer page){
        this.page=page;
        this.totalPage=totalPage;

        if (page==1) this.showPrevious=false;
        else this.showPrevious=true;

        if (page>4) this.showFirstPage=true;
        else this.showFirstPage=false;

        if (page==totalPage) this.showNext=false;
        else this.showNext=true;

        if (page>totalPage-4) this.showEndPage=false;
        else this.showEndPage=true;

        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }

            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }
    }
}
