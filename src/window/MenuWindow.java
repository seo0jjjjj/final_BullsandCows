package window;

import ioManager.IOManager;
import user.User;

import javax.swing.*;
import java.awt.*;

/// 메뉴 화면 , 로그인 이후, 할 수 있는 선택지가 있는 메뉴로, "최다 사용자 조회"의 경우, 관리자의 권한이 필요함.
public class MenuWindow extends JFrame {
    public MenuWindow(User u){
        setLocation(200,200);
        setSize(300,300);
        setLayout(new GridLayout(10, 1,5,5));

        add(new JLabel(u.username+"님 반갑습니다."));

        String[] btnList = {"게임하러가기","로그아웃 하기","최고 기록 조회","최다 사용자 조회"};

        //4개의 버튼 추가
        for(String btnName : btnList){
            JButton jButton = new JButton(btnName);
            if(btnName.equals("최다 사용자 조회")&&u.permission.equals("user")) jButton.setEnabled(false); // 사용자는 최다 사용자 조회 불가
            if(btnName.equals("게임하러가기")&&u.permission.equals("admin")) jButton.setEnabled(false); // 관리자는 게임 플레이 불가.

            //이벤트 리스너
                jButton.addActionListener(e-> {
                JButton eventButton = (JButton) e.getSource();
                String btnFunc = eventButton.getText();

                    switch (btnFunc) {
                        case "게임하러가기" -> {
                            dispose();
                            GameWindow gw = new GameWindow(u); // 게임 윈도우
                        }
                        case "로그아웃 하기" -> {
                            dispose();
                            LoginWindow lw = new LoginWindow(IOManager.getUserList()); // 로그인창으로 이동
                        }
                        case "최고 기록 조회" -> {
                            showDialog("최고 기록 조회",IOManager.getRankList().toString()); // 기록조회 팝업
                        }
                        case "최다 사용자 조회" -> {
                            showDialog("최다 사용자 조회",IOManager.getUserList().playsRank()); // 사용자들의 플레이 횟수
                        }
                    }
            });
            add(jButton);
        }


        setVisible(true);

    }
    // 등수 보여주는 팝업창
    private void showDialog(String title, String content){
        JTextArea textArea2 = new JTextArea(content);
        textArea2.setEditable(false);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);
        textArea2.setLineWrap(true);
        textArea2.setWrapStyleWord(true);
        scrollPane2.setPreferredSize(new Dimension(300, 300));
        JOptionPane.showMessageDialog(null, scrollPane2, title,
                JOptionPane.INFORMATION_MESSAGE);

    }
}
