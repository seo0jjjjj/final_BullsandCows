package bGame;

import java.util.Random;

public class BGame {
    public int count;
    private int[] target = new int[4];

    public BGame() { count = 0; }
    void incCount() { count ++; }

    public void init() {
        Random r = new Random();
        int newNum;
        for(int i = 0; i < 4; i++) {
            do {
                newNum = r.nextInt(9)+1;
            } while (isDup(i, newNum));
            target[i] = newNum;
        }
    }
    // 중복 체크
    private boolean isDup (int i, int num) {
        boolean dup = false;
        for (int j = 0; j < i; j++)
        if (target[j] == num) {
            dup = true;
            break;
        }
        return dup;
    }
    // 정답 출력
    public void print() {
        for(int i = 0; i < 4; i++) System.out.print(target[i] + " ");
        System.out.println();
    }
    // 스트라이크 구하기
    public int getStrike(int[] input) {
        int s = 0;
        for(int i = 0; i < 4; i++) if (target[i] == input[i]) s++;
        return s;
    }
    // ball 구하기
    public int getBall(int[] input) {
        int b = 0;
        for(int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                if (target[i] == input[j]) b++;
        return b - getStrike(input);
    }

}
