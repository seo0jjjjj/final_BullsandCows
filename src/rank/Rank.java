package rank;
/// 랭킹에 대한 정보를 담는 클래스입니다.
/// 단순하게 username과 recode만 가집니다.
public class Rank implements Comparable<Rank>{
    public String username; // 유저이름
    public int recode; //기록

    public Rank(String username, int recode) {
        this.username = username;
        this.recode = recode;
    }

    /// 정렬을 위한 재정의입니다. 이후, Rank를 sort하기 위하여 정의하였고, 오름차순(1->10)으로 정렬합니다.
    @Override
    public int compareTo(Rank r) {
        if(r.recode>this.recode)
            return -1;
        else return 1;
    }

    /// 출력을 위한 재정의,
    @Override
    public String toString() {
        return username+": "+recode+"\r\n";
    }
}
