package ioManager;

import rank.Rank;
import rank.RankList;
import user.User;
import user.UserList;

import java.io.*;
import java.util.Collections;
import java.util.Scanner;

/// 입출력과 관련된 클래스입니다.
public class IOManager {
    public static final String DataTxt = "data.txt";
    public static final String RankTxt = "rank.txt";

    // 파일의 이름으로, 파일의 객체를 반환합니다.
    public static File loadFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return file;
    }

    // Data.Txt를 UserList형식으로 반환합니다.
    public static UserList getUserList() {
        //파일 가져오기
        UserList ul = new UserList();
        try {
            Scanner sc = new Scanner(loadFile("data.txt"));
            while (sc.hasNext()) {
                boolean canLoop = true;
                String username = "";
                String password = "";
                String permission = "";
                int plays;
                while (sc.hasNext() && canLoop) {// :를 기준으로 key와 value를 분류합니다.
                    String[] str = sc.nextLine().split(": ");

                    switch (str[0]) {
                        case "사용자명" -> username = str[1];
                        case "  비밀번호" -> password = str[1];
                        case "  권한" -> permission = str[1];
                        case "  플레이수" -> {
                            plays = Integer.parseInt(str[1]);

                            ul.add(new User(username, password, permission, plays));
                            canLoop = false;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ul.print();
        return ul;
    }

    /// 새로가입한 User를 Data.txt에 기록합니다.
    public static void regNewUser(User user) {
        try (// 자원할당해제를 위한 필드
             FileWriter fwr = new FileWriter(loadFile(DataTxt), true);
             PrintWriter pwr = new PrintWriter(fwr)
        ) {
            pwr.println("사용자명: " + user.username);
            pwr.println("  비밀번호: " + user.password);
            pwr.println("  권한: " + user.permission);
            pwr.println("  플레이수: 0");
            pwr.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    User가 게임을 플레이한 횟수(plays)를 증가시킵니다.
    * key와 value를 통한 접근이 아닌, 해당 플레이어까지 데이터를 읽어 dummy에 저장하고
    * plays를 불러와 수정한 뒤 이후부터 다시 dummy에 저장한 후, 파일을 덮어쓰는 형태입니다.
    * */
    public static void addPlayerCount(User cUser) {
        File file = loadFile(DataTxt);
        StringBuilder dummy = new StringBuilder(); // 파일의 모든 텍스르를 저장할 dummy

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            //1. 삭제하고자 하는 사용자까지 이동하며 dummy에 저장
            String line;
            int count = -1;
            while (true) {
                line = br.readLine(); //읽으며 이동
                count--;
                if (line.equals("사용자명: " + cUser.username)) {
                    count = 3; // 비밀번호-> 권한-> 플레이수 이므로 이후 3번이동 후 멈춤.
                }
                if (count == 0) {
                    break; // 플레이수 :
                }
                dummy.append(line).append("\r\n");
            }
            //2. 데이터 수정
            String[] str = line.split(": "); // "플레이수" : "num"
            cUser.plays = Integer.parseInt(str[1]); // "num"
            dummy.append("  플레이수: ").append(++cUser.plays).append("\r\n"); // 버퍼에 기록
            //3. 수정한 데이터 이후부터 dummy에 저장
            while ((line = br.readLine()) != null) {
                dummy.append(line).append("\r\n");
            }
            //4. FileWriter를 이용해서 덮어쓰기
            FileWriter fw = new FileWriter(DataTxt);
            fw.write(dummy.toString());

            //bw.close();
            fw.close();
            br.close();
            System.out.println(dummy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /// Rank.txt의 파일을 RankList로 가져옵니다.
    public static RankList getRankList() {
        RankList rankList = new RankList();
        try (//scanner 할당해제를 위한 try-resourse
             Scanner scanner = new Scanner(loadFile(RankTxt));
        ) {
            //1. Rank.txt의 파일을 읽어들임.
            String line;
            int count = -1;
            while (scanner.hasNext()) {
                line = scanner.nextLine(); //읽으며 이동
                String[] str = line.split(": "); // username: rank
                rankList.add(new Rank(str[0], Integer.parseInt(str[1])), false); // RankList에 추가
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rankList;
    }

    /// RankList를 rank.txt에 덮어씁니다.
    public static void saveRank(RankList rankList) {
        try (//scanner 할당해제를 위한 try-resourse
             FileWriter fwr = new FileWriter(loadFile(RankTxt), false); // 덮어쓰기
             PrintWriter pwr = new PrintWriter(fwr)
        ) {
            Collections.sort(rankList.getRankList()); // rank 정렬
            for (Rank rank : rankList.getRankList()) {
                pwr.println(rank.username + ": " + rank.recode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

