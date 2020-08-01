/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passwordgenerator;

/**
 *
 * @author Panagiotis Sklidas
 */

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

/**
 *
 * @author Panagiotis Sklidas
 */
public class PasswordGenerator extends Application{

    Button bOK, bRe;
    CheckBox lc, uc, sy, nu;
    TextField tLe;
    TextArea pW;
            
    @Override
    public void start(Stage primaryStage){
        
        /*--Creating the acceptable characters section--*/
        Label lCha = new Label("Characters:");
        /*Creating the check boxes*/
        lc = new CheckBox ("Lower Case");
        lc.setTooltip(new Tooltip("Ex.: a b c . . . z"));
        uc = new CheckBox("Upper Case");
        uc.setTooltip(new Tooltip("Ex.: A B C . . . Z"));
        sy = new CheckBox("Symbols");
        sy.setTooltip(new Tooltip("Ex.: ! @ # $ % ^ & * _"));
        nu = new CheckBox("Numbers");
        nu.setTooltip(new Tooltip("Ex.: 1 2 3 4 5 6 7 8 9 0"));
        VBox vBC = new VBox();
        vBC.setPadding(new Insets(25, 25, 25, 25));
        vBC.setSpacing(10);
        vBC.getChildren().addAll(lCha, lc, uc, sy, nu);
        
        /*--Creating the password's length section--*/
        Label lLe = new Label("Length:");
        /*Creating text field*/
        tLe = new TextField();
        tLe.setTooltip(new Tooltip("Must be at least 4 characters long and a max of 32"));
        tLe.setPrefColumnCount(4);
        VBox vBL = new VBox();
        vBL.setPadding(new Insets(25, 25, 25, 25));
        vBL.setSpacing(10);
        vBL.getChildren().addAll(lLe, tLe);
        
        /*--Buttons--*/
        bOK = new Button("_OK");
        bOK.setDefaultButton(true);
        bOK.setOnAction(e-> ok());
        bRe = new Button("_Reset");
        bRe.setOnAction(e-> reset());
        HBox hBB = new HBox();
        hBB.setPadding(new Insets(25, 25, 25, 25));
        hBB.setSpacing(10);
        hBB.getChildren().addAll(bRe, bOK);
        
        /*--Creating the place where password will appear--*/
        pW = new TextArea();
        pW.setPrefRowCount(2);
        pW.setPrefColumnCount(10);
        pW.setPromptText("Here will appear your password . . .");
        
        /*--Scene set-up--*/
        GridPane gPane = new GridPane();
        gPane.setBackground(new Background(new BackgroundFill(Color.web("#f3f3f3"), CornerRadii.EMPTY, Insets.EMPTY)));
        gPane.setHgap(10);
        gPane.setVgap(10);
        gPane.setPadding(new Insets(12.5, 12.5, 12.5, 12.5));
        gPane.add(vBC, 0, 0, 1, 5);
        gPane.add(vBL, 2, 0, 1, 2);        
        gPane.add(pW, 2, 2, 1, 1);
        gPane.add(hBB, 2, 6, 2, 1);
        BorderPane scrn1 = new BorderPane();
        scrn1.setBackground(new Background(new BackgroundFill(Color.web("#f3f3f3"), CornerRadii.EMPTY, Insets.EMPTY)));
        scrn1.setPadding(new Insets(25, 25, 25, 25));
        scrn1.setCenter(gPane);
        
        /*--Stage set-up--*/
        Scene scn1 = new Scene(scrn1, 400, 350);
        JMetro jMetro = new JMetro(Style.LIGHT);
        jMetro.setScene(scn1);
        /*Image img = new Image("/icons/pwg.png");
        primaryStage.getIcons().add(img);*/
        primaryStage.setScene(scn1);
        primaryStage.setTitle("Password Generator");
        primaryStage.setResizable(false);
        primaryStage.show();
        
    }
    
    ArrayList<String> passWord = new ArrayList<>();
    int length;
    
    public void ok(){
        //if error() is false then check which check box is chcked and add the symbols at passWord
        if(error() == false){
            if(lc.isSelected() == true){
            for(char i='a'; i<'z'; i++){
                passWord.add(Character.toString(i));
            }
        }
            if(uc.isSelected() == true){
                for(char i='A'; i<'Z'; i++){
                    passWord.add(Character.toString(i));
                }
            }
            if(sy.isSelected() == true){
                passWord.add("!");
                passWord.add("@");
                passWord.add("#");
                passWord.add("$");
                passWord.add("%");
                passWord.add("^");
                passWord.add("&");
                passWord.add("*");
                passWord.add("_");
            } 
            if(nu.isSelected() == true){
                for(int i=0; i<10; i++){
                    passWord.add(Integer.toString(i));
                }
            }
            length = Integer.parseInt(tLe.getText());
        
            generatePassword();
        }
        //if error() is true run errorMessage()
        else{
            errorMessage();
        }
    }
    
    public void reset(){
        lc.setSelected(false); 
        uc.setSelected(false); 
        sy.setSelected(false); 
        nu.setSelected(false);
        tLe.setText("");
        pW.setText("");
    }
    
    public boolean error(){
        boolean e = false;
        String tLetSt = tLe.getText();
        if(tLetSt.isBlank() == true || tLetSt.contains(",") == true || tLetSt.contains(".") == true || Integer.valueOf(tLetSt) < 4 == true || Integer.valueOf(tLetSt) > 32 == true){
            e = true;
        }
        if(lc.isSelected() == false && uc.isSelected() == false && sy.isSelected() == false && nu.isSelected() == false){
            e = true;
        }
        return e;
    }
    
    //Creating the error message
    public void errorMessage(){
        String msg="";
        String tLetSt = tLe.getText();
        if(tLetSt.isBlank() == true || tLetSt.contains(",") == true || tLetSt.contains(".") == true || Integer.valueOf(tLetSt) < 4 == true || Integer.valueOf(tLetSt) > 32 == true){
            msg += "You did not enter a valid length for your password.\n";
        }
        if(lc.isSelected() == false && uc.isSelected() == false && sy.isSelected() == false && nu.isSelected() == false){
            msg += "You did not select any character for your password.\n";
        }
        msg += "Try again!";
        MessageBox.show(msg, "Error");
    }
    
    public void generatePassword(){
        String str = passWord.toString().replaceAll(",", "");
        char[] chars = str.substring(1, str.length()-1).replaceAll(" ", "").toCharArray();        
        Random random = new SecureRandom();    
        StringBuilder password = new StringBuilder();    
        for(int i = 0; i < length; i++){
            password.append(chars[random.nextInt(chars.length)]);
        }
        pW.setText(password.toString());
    }    
    
    public static void main(String[] args) {
        launch(args);
    }
        
}
