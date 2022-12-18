package user;

import java.util.ArrayList;

/// 유저의 ArrayList를 담는 클래스
public class UserList {
    ArrayList<User> ul; // 유저리스트


    //생성자
    public UserList() {
        ul = new ArrayList<>();
    }

    // ArrayList에 추가
    public void add(User u) {
        ul.add(u);
    }
    //List 출력
    public void print() {
        for (User u : ul) u.print();
    }

    // 아이디가 존재하는 지 여부
    public boolean isValidID(String i) {
        for (User u : ul)
            if (u.username.equals(i)) return true;
        return false;
    }

    // username을 통한 User객체 반환
    public User getCurrentUser(String i) {
        for (User u : ul)
            if (u.username.equals(i)) return u;
        return null;
    }

    // 해당 id와 password가 일치하는 유저가 존재하는 지
    public boolean isValidPass(String i, String p) {
        String password = "";
        for (User u : ul)
            if (u.username.equals(i)) {
                password = u.password;
                break;
            }
        return password.equals(p);
    }

    // 사용자명: 이름 플레이횟수: 형식으로 출력
    public String playsRank() {
        StringBuilder result = new StringBuilder();
        for (User u : ul) {
            result.append(String.format("사용자명: %20s\t 플레이수: %10s\r\n", u.username, u.plays));
        }
        return result.toString();
    }
}
