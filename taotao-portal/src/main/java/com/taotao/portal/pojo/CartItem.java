package com.taotao.portal.pojo;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.pojo
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/12/12 19:45
 *   *
 **/
public class CartItem {
    private long itemId;
    private String title;
    private Long price;
    private Integer num;
    private String image;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

//    public String[] getImages(){
//        String[] images=image.split(",");
//        return images;
//    }
}
