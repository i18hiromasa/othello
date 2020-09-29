public class Othello{
    private int board[][];
    private boolean isTurn;//true = black
    private int turn;
    private int numBlackStone;
    private int numWhiteStone;
    public static final int WALL = 5;
    public static final int BLACK_STONE = 1;
    public static final int WHITE_STONE = -1;
    public static final int NOT_STONE = 0;
    public static final int CAN_PUT_STONE = 2;
    private String enemyPut;
    private int putX;
    private int putY;
    Othello(){
        board = new int[10][10];
        for(int i = 0; i < 10 ; i++){
            board[0][i] = WALL;
            board[9][i] = WALL;
            board[i][0] = WALL;
            board[i][9] = WALL;
        }
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                board[i][j] = NOT_STONE;
            }
        }
        board[4][4] = WHITE_STONE;
        board[4][5] = BLACK_STONE;
        board[5][4] = BLACK_STONE;
        board[5][5] = WHITE_STONE;
        isTurn = true;
        turn = 1;
        numBlackStone = 2;
        numWhiteStone = 2;
        enemyPut = "";
        putX = -1;
        putY = -1;
    }
    //setter,getter系統群
    private void setBoard(int x, int y,int a){
        board[x][y] = a;
    }
    public int getBoard(int x, int y){
        return board[x][y];
    }
    public void reverseIsTurn(){
        isTurn = !isTurn;
    }
    public boolean getIsTurn(){
        return isTurn;
    }
    private void addTurn(){
        turn++;
    }
    public int getTurn(){
        return turn;
    }
    public void cntNumBlackStone(){
        int cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(board[i][j] == BLACK_STONE)cnt++;
            }
        }
        numBlackStone = cnt;
    }
    public void cntNumWhiteStone(){
        int cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(board[i][j] == WHITE_STONE)cnt++;
            }
        }
        numWhiteStone = cnt;
    }
    public int getNumBlackStone(){
        return numBlackStone;
    }
    public int getNumWhiteStone(){
        return numWhiteStone;
    }
    public void setEnemyPut(String s){
        enemyPut = s;
    }
    public void addEnemyPut(String s){
        enemyPut += s;
    }
    public String getEnemyPut(){
        return enemyPut;
    }
    public void setPutX(int x){
        putX = x;
    }
    public int getPutX(){
        return putX;        
    }
    public void setPutY(int y){
        putY = y;
    }
    public int getPutY(){
        return putY;
    }
    
    public void reGame(){//盤面を再初期化するメソッド
        board = new int[10][10];
        for(int i = 0; i < 10 ; i++){
            board[0][i] = WALL;
            board[9][i] = WALL;
            board[i][0] = WALL;
            board[i][9] = WALL;
        }
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                board[i][j] = NOT_STONE;
            }
        }
        board[4][4] = WHITE_STONE;
        board[4][5] = BLACK_STONE;
        board[5][4] = BLACK_STONE;
        board[5][5] = WHITE_STONE;
        isTurn = true;
        turn = 1;
        numBlackStone = 2;
        numWhiteStone = 2;
    }
    public void printBoard(){//盤面をCUIに出力,debug用
        System.out.printf("          ");
        for(int i = 1; i < 9; i++){
            System.out.printf("%4d",i);
        }
        System.out.println();
        System.out.printf("   ");
        for(int i = 0; i < 11; i++){
            System.out.printf("----");
        }
        System.out.println();
        for(int i = 0; i < 10; i++){
            if((i > 0) && (i < 9))System.out.printf("%4d",i);
            else System.out.printf("    ");
            System.out.printf(" |");
            for(int j = 0; j < 10; j++){
                System.out.printf("%4d", board[i][j]);
            }
            System.out.println();
            System.out.println();
        }
    }
    public void updateBoard(){//盤面のデータを更新する
        reverseIsTurn();
        cntNumBlackStone();
        cntNumWhiteStone();
        addTurn();
        clearCanPut();
    }
    public void clearCanPut(){//置ける情報をクリアするメソッド
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(getBoard(i, j) == CAN_PUT_STONE){
                    setBoard(i, j, NOT_STONE);
                }
            }
        }
    }
    public boolean putCheck(){//石を置ける場所を調べる
        //System.out.println("putcheck");
        int myStone;
        int putCnt = 0;
        if(isTurn)myStone = BLACK_STONE;
        else myStone = WHITE_STONE;
        for(int x = 1; x < 9; x++){
            for(int y = 1; y < 9; y++){
                if(getBoard(x, y) != NOT_STONE)continue;
                for(int i = -1; i < 2; i++){
                    for(int j = -1; j < 2; j++){
                        if((i == 0) && (j == 0))continue;
                        int cnt = 0;
                        int x0 = x;
                        int y0 = y;           
                        while(getBoard(x0 + i, y0 + j) == -myStone){
                            cnt++;
                            x0 += i;
                            y0 += j;
                        }
                        if(cnt != 0){
                            if(getBoard(x0 + i, y0 + j) == myStone){
                                putCnt++;
                                setBoard(x, y, 2);
                            }
                        }
                    }
                }
            }
        }
        if(putCnt == 0)return false;
        return true;
    }
    public void putStone(int x, int y){//石をおく関数
        int myStone;
        if(isTurn)myStone = BLACK_STONE;
        else myStone = WHITE_STONE;
        setBoard(x, y, myStone);
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                int cnt = 0;
                int x0 = x;
                int y0 = y;
                while(getBoard(x0 + i, y0 + j) == -myStone){
                    cnt++;
                    x0 += i;
                    y0 += j;
                }
                if(cnt != 0){
                    if(getBoard(x0 + i, y0 + j) == myStone){
                        for(int k = 0; k < cnt; k++){
                            setBoard(x0, y0, myStone);
                            x0 -= i;
                            y0 -= j;
                        }
                    }
                }
            }
        }
    }
    public boolean othelloPlay(int x, int y){//自分が置く->相手がランダムに置くの一連の流れを行うメソッド
        boolean pass;
        putStone(x, y);
        updateBoard();
        putCheck();
        printBoard();
        pass = enemyPutRandom();
        if((pass)){
            while(true){
                updateBoard();
                pass = putCheck();
                if(pass)break;
                else {
                    reverseIsTurn();
                    clearCanPut();
                    if(!(putCheck()))break;

                }
                enemyPutRandom();
            }  
        }
        else {
            reverseIsTurn();
            clearCanPut();
            pass = putCheck();
            if(pass){
                printBoard();
            }
            else {
                putStone(x, y);
                int black = getNumBlackStone();
                int white = getNumWhiteStone();
                System.out.printf("黒の石の数は%d\n",black);
                System.out.printf("白の石の数は%d\n",white);
            }
        }
        return pass;
    }
    public boolean enemyPutRandom(){//ランダムに敵の手をうつメソッド,敵の手をランダムで打てるように改造したときに使用
        int cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(getBoard(i, j) == CAN_PUT_STONE){
                    cnt++;
                }
            }
        }
        if(cnt == 0)return false;
        int next = (int)(Math.random() * cnt) + 1;
        int nextX = 0;
        int nextY = 0;
        cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(getBoard(i, j) == CAN_PUT_STONE){
                    cnt++;
                    if(cnt == next){
                        nextX = i;
                        nextY = j;
                    }
                }
            }
        }
        putStone(nextX, nextY);
        addEnemyPut(String.valueOf(turn));
        addEnemyPut(". □:");
        addEnemyPut(String.valueOf((char)(nextY + 'a' - 1)));
        addEnemyPut(String.valueOf(nextX));
        addEnemyPut("\n");
        //System.out.println(getEnemyPut());
        return true;
    } 
    public boolean putRandom(){//ランダムに手をうつメソッド
        this.putCheck();
        int cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(getBoard(i, j) == CAN_PUT_STONE){
                    cnt++;
                }
            }
        }
        if(cnt == 0)return false;
        int next = (int)(Math.random() * cnt) + 1;
        int nextX = 0;
        int nextY = 0;
        cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(getBoard(i, j) == CAN_PUT_STONE){
                    cnt++;
                    if(cnt == next){
                        nextX = i;
                        nextY = j;
                    }
                }
            }
        }
        putStone(nextX, nextY);
        return true;
    }
    
    public boolean othelloPlayUCT(int x, int y){//自分が置く->相手が探索アルゴリズムを用いて手を選択置くの一連の流れを行うメソッド
        boolean pass;
        putStone(x, y);
        //System.out.println("player x:" + x + " y:" + y);
        updateBoard();
        putCheck();
        //printBoard();
        pass = putUCT();
        if((pass)){
            while(true){
                updateBoard();
                pass = putCheck();
                if(pass)break;
                else {
                    reverseIsTurn();
                    clearCanPut();
                    if(!(putCheck()))break;
                }
                putUCT();
            }  
        }
        else {
            reverseIsTurn();
            clearCanPut();
            pass = putCheck();
            if(pass){
                //printBoard();
            }
            else {
                //putStone(x, y);
                int black = getNumBlackStone();
                int white = getNumWhiteStone();
                System.out.printf("黒の石の数は%d\n",black);
                System.out.printf("白の石の数は%d\n",white);
            }
        }
        return pass;
    }

    public boolean putUCT(){//探索アルゴリズムを用いて相手の手を選択し置くメソッド
        int cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(getBoard(i, j) == CAN_PUT_STONE){
                    cnt++;
                }
            }
        }
        if(cnt == 0)return false;
        int next = MonteCarlo.selectBestUct(this);
        int nextX = next / 10;
        int nextY = next % 10;
        //`System.out.println("computer x:" + nextX + " y:" + nextY);
        putStone(nextX, nextY);
        putX = nextX;
        putY = nextY;
        addEnemyPut(String.valueOf(turn));
        if(isTurn)addEnemyPut(". ●:");
        else addEnemyPut(". ○:");
        addEnemyPut(String.valueOf((char)(nextY + 'a' - 1)));
        addEnemyPut(String.valueOf(nextX));
        addEnemyPut("\n");
        return true;
    }
    
    //computer同士の対戦用メソッド
    public void comOnly(){
        boolean pass;
        boolean end = false;
        while(true){
            pass = putCheck();
            if(pass){
                end = false;
                putUCT();
                updateBoard();
            }
            else {
                if(!(end)){
                    end = true;
                    reverseIsTurn();
                }
                else break;
            }
            printBoard();
        }
    }
    public void comOnly(double C, double maxC){
        boolean pass;
        boolean end = false;
        while(true){
            pass = putCheck();
            if(pass){
                end = false;
                if(getIsTurn())putUCT(C);
                else putUCT(maxC);
                updateBoard();
            }
            else {
                if(!(end)){
                    end = true;
                    reverseIsTurn();
                }
                else break;
            }
            //printBoard();
        }
    }
    public void comOnly1(double C){
        boolean pass;
        boolean end = false;
        while(true){
            pass = putCheck();
            if(pass){
                end = false;
                if(getIsTurn())putUCT(C);
                else putUCT();
                updateBoard();
            }
            else {
                if(!(end)){
                    end = true;
                    reverseIsTurn();
                }
                else break;
            }
            //printBoard();
        }
    }
    public void comOnly2(double C){
        boolean pass;
        boolean end = false;
        while(true){
            pass = putCheck();
            if(pass){
                end = false;
                if(getIsTurn())putUCT();
                else putUCT(C);
                updateBoard();
            }
            else {
                if(!(end)){
                    end = true;
                    reverseIsTurn();
                }
                else break;
            }
            //printBoard();
        }
    }
    public boolean putUCT(double C){
        int cnt = 0;
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                if(getBoard(i, j) == CAN_PUT_STONE){
                    cnt++;
                }
            }
        }
        if(cnt == 0)return false;
        int next = MonteCarlo.selectBestUct(this, C);
        int nextX = next / 10;
        int nextY = next % 10;
        putStone(nextX, nextY);
        return true;
    }

    public void copy(Othello othello){//copyメソッド
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                othello.board[i][j] = this.board[i][j];
            }
        }
        othello.isTurn = this.isTurn;
        othello.turn = this.turn;
        othello.numBlackStone = this.numBlackStone;
        othello.numWhiteStone = this.numWhiteStone;
    }
}