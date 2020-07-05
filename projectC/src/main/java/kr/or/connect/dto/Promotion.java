package kr.or.connect.dto;

public class Promotion {
    private int id;
    private int productId;
    private int categoryId;
    private String categoryName;
    private String description;
    private int fileId;

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", productId=" + productId +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", fileId=" + fileId +
                '}';
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
}
