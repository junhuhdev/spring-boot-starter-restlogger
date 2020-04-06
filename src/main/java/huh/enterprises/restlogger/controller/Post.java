package huh.enterprises.restlogger.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private String name;
    private int userId;
    private int id;
    private String title;
    private boolean completed;
}
