package app.v1.dto;

import java.time.LocalDateTime;

public class TheoryDTO {
    private Long id;

    private String theme;
    private LocalDateTime time;
    private float price;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
