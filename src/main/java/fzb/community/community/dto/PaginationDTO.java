package fzb.community.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fzb
 */
@Data
public class PaginationDTO {
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage,Integer page){
        this.page=page;
        this.totalPage=totalPage;

        if (page==1) {
            this.showPrevious=false;
        } else {
            this.showPrevious=true;
        }

        this.showFirstPage= page > 4;

        this.showNext= !page.equals(totalPage);

        this.showEndPage= page <= totalPage - 4;

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
