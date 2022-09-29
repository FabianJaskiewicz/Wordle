import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;


public class Solution {

    //WINDOW SIZE
    private static final int WIN_WIDTH = 1080;
    private static final int WIN_HEIGHT = 720;

    //Dictionary class used to import the wordle text file
    static Dictionary dic = new Dictionary();

    public static void main(String[] args) {Window window = new Window(WIN_WIDTH,WIN_HEIGHT,"Wordle");}

}

class Window{
    static JFrame frame;
    static JPanel[][] board;
    static JLabel[][] boardText;
    static JPanel[] alphabet;
    static JLabel[] alphabetLabel;
    JPanel titlePanel;
    JLabel titleLabel;
    static Dictionary dic = new Dictionary();
    static String word = chooseWord();
    static String[] letters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    static LinkedList words = new LinkedList();

    static int x = 0;
    static int y = 0;

    Window(int width, int height, String title) {
        board = new JPanel[6][5];
        boardText = new JLabel[6][5];
        alphabet = new JPanel[26];
        alphabetLabel = new JLabel[26];
        titlePanel = new JPanel();
        titleLabel = new JLabel();

        //Frame
        frame = new JFrame();
        frame.setBounds(0, 0, width, height);
        frame.setTitle(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(Color.BLACK);

        //Title
        titlePanel.setBounds((1080/2)-100,20,200,50);
        titleLabel.setText("Wordle");
        titleLabel.setFont(new Font("Serif",Font.PLAIN,40));
        titleLabel.setForeground(Color.GRAY);
        titlePanel.setBackground(Color.BLACK);
        titlePanel.add(titleLabel);
        frame.add(titlePanel);

        //Rows
        x = (frame.getWidth() / 2) - 147;
        y = 100;
        for (int i = 0; i < 6; i++) {
            for(int j=0; j<5; j++)
            {
                board[i][j] = new JPanel();
                board[i][j].setBounds(x,y,55,55);
                board[i][j].setBackground(Color.BLACK);
                board[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
                frame.add(board[i][j]);
                x+=60;
            }
            x=(frame.getWidth() / 2) - 147;
            y+=60;
        }


        //Alphabet rows
        x = 15;
        y = 550;
        for(int i=0;i< alphabet.length;i++)
        {
            alphabet[i] = new JPanel();
            alphabetLabel[i] = new JLabel();
            alphabetLabel[i].setText(letters[i]);
            alphabetLabel[i].setForeground(Color.white);
            alphabetLabel[i].setBounds(14,8,25,20);
            alphabet[i].add(alphabetLabel[i]);

            alphabet[i].setBounds(x,y,35,35);
            alphabet[i].setBackground(Color.BLACK);
            alphabet[i].setBorder(BorderFactory.createLineBorder(Color.GRAY));
            frame.add(alphabet[i]);
            x+=40;
            }

        //Words Array
        for(int i=0; i<2315; i++){
            words.add(dic.getWord(i));
        }

        //Testing
        //System.out.println(word);
        //System.out.println((String)words.get(words.size()-1));
        //System.out.println("index 0: " + (String)words.get(0));
        //System.out.println("index 1: " + (String)words.get(1));
        //System.out.println("index 2314: " + (String)words.get(2314));
        //System.out.println(words.size());

        /*
        int wins = 0;
        for(int i=0; i<10; i++){
            if(play()){
                wins++;
            }
            frame.removeAll();
            words.clear();
            for(int j=0; j<2315; j++){
                words.add(dic.getWord(j));
            }
        }
        System.out.println("WINS: " + wins);

         */

        play();
    }

        public static boolean play()
        {
            Scanner scanner = new Scanner(System.in);
            int pos = 0;
            int wins = 0;
            String input = "";
            while(pos<6){
                //input = recommendWord(words);
                //input = textInput.showInputDialog(frame,"please enter value");
                input = scanner.nextLine();
                if(allowable(input)){
                    if(input.length() == 5 && !isInteger(input)){
                        for(int i=0;i<5;i++){

                            //Graphics
                            boardText[pos][i] = new JLabel();
                            boardText[pos][i].setBounds(15,0,60,60);
                            boardText[pos][i].setForeground(Color.WHITE);
                            boardText[pos][i].setText(Character.toString(input.charAt(i)).toUpperCase());
                            boardText[pos][i].setFont(new Font("Serif",Font.BOLD,35));
                            board[pos][i].add(boardText[pos][i]);
                            frame.add(board[pos][i]);

                            //GREEN
                            if(input.charAt(i) == word.charAt(i)){
                                System.out.print("G");
                                board[pos][i].setBackground(Color.GREEN);
                                alphabet[alphabetIndex(input.charAt(i))].setBackground(Color.GREEN);
                                updateWords("green",input.charAt(i),i);
                            //YELLOW
                            }else if(word.contains(Character.toString(input.charAt(i)))){
                                System.out.print('Y');
                                board[pos][i].setBackground(Color.YELLOW);
                                alphabet[alphabetIndex(input.charAt(i))].setBackground(Color.YELLOW);
                                updateWords("yellow",input.charAt(i),i);
                            //GRAY
                            }else{
                                System.out.print('-');
                                board[pos][i].setBackground(Color.GRAY);
                                alphabet[alphabetIndex(input.charAt(i))].setBackground(Color.GRAY);
                                updateWords("gray",input.charAt(i),i);

                            }

                        }
                        pos++;

                        System.out.println("");
                        System.out.println("Array Size: " + words.size());
                        System.out.println("Try: " + recommendWord(words));
                        System.out.println("");
                    }
                }else{
                    System.out.println("Invalid word!");
                    System.out.println("");
                }

                if(input.equals(word)) {
                    System.out.println("Correct!");
                    return true;
                }
            }
            scanner.close();
            System.out.println("The word was: " + word);
            System.out.println("words left:");
            for(int i=0; i<words.size(); i++){
                System.out.println((String) words.get(i));
            }
            return false;
        }


        public static boolean allowable(String str){
            for (int i=2315; i<dic.getSize();i++){
                if(str.equals(dic.getWord(i)))
                    return true;
            }
            return false;
        }

        public static String chooseWord()
        {
            Random random = new Random();
            String word = dic.getWord(random.nextInt(2315));
            return word;
        }

        public static boolean isInteger(String str){
            try {
                Integer.parseInt(str);
                return true;
            }
            catch( Exception e ) {
                return false;
            }
        }

        public static int alphabetIndex(char ch){
            int index = 0;
            String letter = Character.toString(ch).toUpperCase();
            for(int i=0; i<letters.length; i++){
                if(letters[i].equals(letter))
                {
                    index = i;
                }
            }
            return index;
        }

        public static void updateWords(String color, char letter, int index){

            if(color.equals("green")) {
                for (int i=0; i<words.size(); i++) {
                    String word = (String) words.get(i);
                    if(word.charAt(index) != letter){
                        words.remove(i);
                    }
                }
            }
            if (color.equals("yellow")) {
                for (int i=0; i<words.size(); i++) {
                    String word = (String) words.get(i);
                    if(word.indexOf(letter) == -1){
                        words.remove(i);
                    }
                }
            }
            if(color.equals("gray")){
                for(int i=0; i<words.size(); i++){
                    String word = (String) words.get(i);
                    if(word.indexOf(letter) != -1){
                        words.remove(i);
                    }
                }
            }
        }

        public static String recommendWord(LinkedList words){
            Random random = new Random();

            int index = random.nextInt(words.size());
            return (String)words.get(index);
        }

}

class Dictionary {

        private String input[];

        public Dictionary() {
            input = load("C:\\Users\\fabia\\Documents\\Wordle\\wordleWords.txt");
        }

        public int getSize() {
            return input.length;
        }

        public String getWord(int n) {
            return input[n].trim();
        }

        private String[] load(String file) {
            File aFile = new File(file);
            StringBuffer contents = new StringBuffer();
            BufferedReader input = null;
            try {
                input = new BufferedReader(new FileReader(aFile));
                String line = null;
                int i = 0;
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    i++;
                    contents.append(System.getProperty("line.separator"));
                }
            } catch (FileNotFoundException ex) {
                System.out.println("Can't find the file - are you sure the file is in this location: " + file);
                ex.printStackTrace();
            } catch (IOException ex) {
                System.out.println("Input output exception while processing file");
                ex.printStackTrace();
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                } catch (IOException ex) {
                    System.out.println("Input output exception while processing file");
                    ex.printStackTrace();
                }
            }
            String[] array = contents.toString().split("\n");
            for (String s : array) {
                s.trim();
            }
            return array;
        }
    }


