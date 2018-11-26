
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class Score {
     private int score;
     private String scoreAsString;
     private ArrayList<String> highScores;
     private File scores;

     Score(){
         score = 0;

         highScores = new ArrayList<String>();
         getScores();
     }

     private void getScores(){
         try {
             scores = new File("C:\\Users\\Ian\\IdeaProjects\\OOP_Project_Ian\\src\\Resources\\HighScores");
             BufferedReader br = new BufferedReader(new FileReader(scores));
             String st;
             while ((st = br.readLine()) != null){
                 System.out.println(st);
                 highScores.add(st);
            }
         }catch(FileNotFoundException ex){
             System.out.println("File not found");
         }
         catch (IOException ex){
             System.out.println("IO not found");
         }
     }

     public void saveScores(){
         try (PrintWriter out = new PrintWriter("C:\\Users\\Ian\\IdeaProjects\\OOP_Project_Ian\\src\\Resources\\HighScores")) {
                 out.println(scoreAsString);
         }
         catch (FileNotFoundException ex){
             System.out.println("File Highscores not found");
         }
     }
     public void addScore(){

         highScores.add(scoreAsString);
     }
     public void increaseScore(int i){
         score += i;
     }
     public String getScoreAsString(){
         scoreAsString = Integer.toString(score);
         return scoreAsString;
     }
     public int getScore(){
         return score;
     }
     public void compareScore(int score){
         boolean add = false;
         Iterator<String> iterator = highScores.iterator();
         while(iterator.hasNext()){
             Object e = iterator.next();
             int HSI = Integer.parseInt(e.toString());
             if (score > HSI){
                 System.out.println("Congratulations you made it into the top 10");
                 add = true;
             }
         }
         if (add){
             addScore();
         }
         saveScores();
     }
     @Override
     public String toString(){
         String string = "Scores: ";
         for (int i=0; i<highScores.size();i++){
             string += "\n" + highScores.get(i).toString();
         }
         return string;
     }
}
