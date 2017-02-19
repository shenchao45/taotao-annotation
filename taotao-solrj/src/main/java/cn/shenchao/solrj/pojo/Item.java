package cn.shenchao.solrj.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.solr.client.solrj.beans.Field;
@JsonIgnoreProperties(value = {"num","barcode"})
public class Item {
    @Field("id")
    private Long id;
    @Field("title")
    private String title;
    @Field("sell_point")
    private String sellPoint;
    @Field("price")
    private Long price;
    @Field("image")
    private String image;
    @Field("cid")
    private Long cid;
    @Field("status")
    private Integer status;
    @Field("created")
    private Long created;
    @Field("updated")
    private Long updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSellPoint() {
        return sellPoint;
    }

    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }
}