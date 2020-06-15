package com.codegym.games.game2048;
import com.codegym.engine.cell.*;
import java.util.Arrays;


public class Game2048 extends Game{
    
    private static final int SIDE = 4;
    private int[][] gameField = new int [SIDE][SIDE];
    private boolean isGameStopped = false;
    int max = Integer.MIN_VALUE;
    private int score;
    
    //Public Method
    
    @Override
    public void initialize(){
        setScreenSize(SIDE, SIDE);
        createGame();
        drawScene();
    }
    
  //private Methods
    
    private void createGame(){
        gameField = new int[SIDE][SIDE];
        createNewNumber();
        createNewNumber();
    }
    
    private void drawScene(){
        for(int x = 0; x<SIDE; x++){
            for(int y = 0; y<SIDE; y++){
                setCellColoredNumber(x, y, gameField[y][x]); // set color
            }
        }
    }
    
    private void createNewNumber(){
        getMaxTileValue();
        int x;
        int y;
        int rand = getRandomNumber(10);
        int value = 0;
        do{
            x = getRandomNumber(SIDE);
            y = getRandomNumber(SIDE);
        } while(gameField[x][y] != 0);
        
        if(rand == 9){ //10% probability
                value=4;
            } else {       //90% probablility
                value=2;
            }
        
        gameField[x][y]= value;
    }
    
    private void setCellColoredNumber(int x, int y, int value){
        Color color = getColorByValue(value);
        if(value != 0){
            setCellValueEx(x, y, color, Integer.toString(value));
        }else {
            setCellValueEx(x, y, color, "");
        }
    }
    
    // private void setCellColoredNumber(int x, int y, int value){
    //     if (value != 0) {
    //         setCellValueEx(x, y, getColorByValue(value), Integer.toString(value));
    //     } else {
    //         setCellValueEx(x, y, getColorByValue(value), "");

    //     }
    // }
    
    
    private Color getColorByValue(int value){
        Color color = null;
        switch(value){
            case 0:
               color = Color.WHITE;
               break;
            case 2:
               color = Color.BLUE;
               break;
            case 4:
                color = Color.YELLOW;
                break;
            case 8:
                color = Color.ORANGE;
                break;
            case 16:
                color = Color.PURPLE;
                break;
            case 32:
                color = Color.GREEN;
                break;
            case 64: 
                color = Color.GRAY;
                break;
            case 128:
                color = Color.BROWN;
                break;
            case 256:
                color = Color.PINK;
                break;
            case 512:
                color = Color.CYAN;
                break;
            case 1024:
                color = Color.RED;
                break;
            case 2048:
                color = Color.VIOLET;
                break;
            default:
                break;
        }
        return color;
        
    }
    
    private boolean compressRow(int[] row){
        
        int temp = 0;
        int[] rowtemp = row.clone();
        boolean isChanged = false;
        for(int i = 0; i < row.length; i++) {
            for(int j = 0; j < row.length-i-1; j++){
                if(row[j] == 0) {
                    temp = row[j];
                    row[j] = row[j+1];
                    row[j+1] = temp;
                }
            }
        }
        if(!Arrays.equals(row,rowtemp)){
            isChanged = true;
        }
       // row = rowtemp;
        return isChanged;
    }
    
    private boolean mergeRow(int[] row){
        boolean mergeChanged = false;
    for (int i=0; i< row.length-1;i++){
        if ((row[i] == row[i+1])&&(row[i]!=0)){
            row[i] = 2*row[i];
            row[i+1] = 0;
            mergeChanged = true;
            setScore(score = score + row[i]);
}

        }

    return mergeChanged;
    }
    
    
    public void onKeyPress (Key key) {
        if ( isGameStopped) {               
            if (key == Key.SPACE){
                restart();
                return;
            }
        }
        if (!canUserMove()) {
            gameOver();
            if (key == Key.SPACE){
                restart();
            }
            return;
        }
       

            switch (key) {                      
                case LEFT:
                    moveLeft();
                    break;
                case RIGHT:
                    moveRight();
                    break;
                case UP:
                    moveUp();
                    break;
                case DOWN:
                    moveDown();
                    break;
                default:
                    return;
        }
    }

    
    private void restart () {
        createGame();
        drawScene();
        isGameStopped = false;
        setScore(score = 0);
    }
        // canUserMove();
        // if(canUserMove()){
        //     if(key == Key.LEFT){
        //     moveLeft();
        //     drawScene();
        // } else if(key == Key.UP){
        //     moveUp();
        //     drawScene();
        // } else if(key == Key.RIGHT){
        //     moveRight();
        //     drawScene();
        // } else if (key == Key.DOWN){
        //     moveDown();
        //     drawScene();
        // } else{}
        // } else {
        //     gameOver();
        // }
        //}
        
    
    
    private void moveLeft(){
        boolean counter = false;
        
        for(int[] row : gameField) {
            
            boolean compressed = compressRow(row); 
            boolean merged = mergeRow(row);
            
            if( (merged || compressed) && !counter) {
                
                createNewNumber();
                counter = true;
                
            }
            
            for(int num : row) {
                
                compressRow(row);
                
            }
            
        }
    }
    
    private void moveUp(){
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        
    }
    
    private void moveRight(){
        rotateClockwise();
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
    }
    
    private void moveDown(){
        rotateClockwise();
        moveLeft();
        rotateClockwise();
        rotateClockwise();
        rotateClockwise();
    }
    
    private void rotateClockwise(){
        for (int i = 0; i < SIDE / 2; i++)
        {
            for (int j = i; j < SIDE - i - 1; j++)
            {
                int temp = gameField[i][j];
                gameField[i][j] = gameField[SIDE - 1 - j][i];
                gameField[SIDE - 1 - j][i] = gameField[SIDE - 1 - i][SIDE - 1 - j];
                gameField[SIDE - 1 - i][SIDE - 1 - j] = gameField[j][SIDE - 1 - i];
                gameField[j][SIDE - 1 - i] = temp;
            }
        }
    }
    
    private int getMaxTileValue(){
        
        
        for(int i = 0; i < gameField.length; i++){
            for(int j = 0; j < gameField[i].length; j++){
                if(gameField[i][j] > max){
                    max = gameField[i][j];
                    if(max == 2048){
                        win();
                    } 
                }
            }
            
        }
        
        // int max = Collections.max(Arrays.asList(gameField[]));
        return max;
    }
    
    private void win(){
            isGameStopped = true;
            showMessageDialog(Color.WHITE, "YOU WIN!", Color.BLUE, 20);
    }
    
    private boolean canUserMove(){
       boolean movesLeft = false;
       //compare each
       for (int i = 0 ; i < SIDE; i++){
           for (int j = 0 ; j<SIDE; j++)
           {
               if (gameField[i][j] == 0)
               {
                   movesLeft = true;
               }
               //checks up
               if((i-1) > 0 && (gameField[i][j] == gameField[i-1][j]))
               {
                   movesLeft = true;
               }
               //checks down
               if ((i+1) < SIDE && (gameField[i][j] == gameField[i+1][j]))
               {
                   movesLeft = true;
               }
               //checks right
               if ((j+1) < SIDE && (gameField[i][j] == gameField[i][j+1]))
               {
                   movesLeft = true;
               }
               //checks left
               if ((j-1)>0 && (gameField[i][j] == gameField[i][j-1]))
               {
                   movesLeft = true;
               }
}
           }
           return movesLeft;
    }
    
    private void gameOver(){
        isGameStopped = true;
        showMessageDialog(Color.GRAY, "YOU LOSE :(", Color.BLACK, 20);
    }

   
    
}
