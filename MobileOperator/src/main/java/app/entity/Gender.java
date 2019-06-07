package app.entity;

public enum Gender {
    MALE(1, "Male"),
    FEMALE(2, "Female");

    private Integer id;
    private String caption;

    Gender(Integer id, String caption) {
        this.id = id;
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public String getCaption() {
        return caption;
    }
}
