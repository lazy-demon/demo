package com.example.scoreboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Score {
    private String userName;

    public Score(String userName){
        this.userName = userName;
    }

    public void setUserScoreBoard() {

        try {
            FileWriter fileWriter = new FileWriter("scoreboard.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(this.userName);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showUserScoreBoard() {

        try {
            File file = new File("scoreboard.txt");

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

