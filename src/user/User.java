package user;
/// 유저의 정보를 담는 클래스입니다.
public class User {
    public String username; // 사용자 이름
    public String password; // 비밀번호
    public String permission; // 권한 (admin,user)
    public int plays; // 플레이 횟수

    // 생성자
    public User(String username, String password, String permission, int plays) {
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.plays = plays;
    }

    // 출력
    void print() {
        System.out.println(username + " " + password+" "+permission+" "+plays);
    }

}
