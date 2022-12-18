package rank;

import ioManager.IOManager;
import rank.Rank;

import java.util.ArrayList;
import java.util.Collections;

/// Rank의 ArrayList를 담는 클래스입니다.
public class RankList {
    private ArrayList<Rank> rl;

    // 생성자
    public RankList() {
        rl = new ArrayList<>();
    }

    /// getter입니다.
    public ArrayList<Rank> getRankList() {
        return rl;
    }

    /* ArrayList에 값을 추가합니다. 이때, checkDup이 True일 경우, 중복검사를 진행하고, false일 경우 진행하지 않습니다.
    * IOManager에서 add를 할때는 이미 scanner 객체가 파일을 탐색하기 떄문에 false를 사용합니다.
    * 하지만 Game에서 결과를 저장할 땐 중복일 경우 랭킹을 등록하지 않습니다.*/
    public void add(Rank newRank, boolean checkDup) {
        // 배열이 10개 꽉 찼을 경우,
        if (rl.size() >= 10) {
            // 현재 rank중 가장 마지막 기록과 비교 후, Insert
            if (newRank.recode <= rl.get(9).recode) {
                System.out.println(rl.get(9).toString());
                rl.remove(9);
            }
        }
        // 중복 검사
        if (checkDup) {
            if (!isDup(newRank)) rl.add(newRank);
        } else {
            rl.add(newRank);
            Collections.sort(rl); // 정렬
        }
    }

    /// 중복 레코드검사 중복이 있을 시 true반환
    private boolean isDup(Rank newRank) {
        RankList ranklist = IOManager.getRankList();
        // 중복 레코드 검사
        for (Rank rank : ranklist.rl) {
            if (rank.username.equals(newRank.username))
                if (rank.recode == newRank.recode) {
                    return true; // 중복레코드가 있을시 True 반환
                }
        }
        return false;
    }

    @Override
    // 사용자명: 이름 기록: 형식으로 출력
    public String toString() {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (Rank r : rl) {
            result.append(String.format("%02d등: 사용자명: %20s\t 기록: %10s\r\n", ++i, r.username, r.recode));
        }
        return result.toString();
    }
}