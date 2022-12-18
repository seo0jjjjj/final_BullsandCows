package window;

import bGame.BGame;
import rank.Rank;
import ioManager.IOManager;
import rank.RankList;
import user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * Game을 진행하는 Window입니다.
 *
 */
public class GameWindow extends JFrame implements ActionListener {
    private JTextField txt; // 정답을 입력을 받는 칸
    private JTextArea textArea; // 결과를 보는 창(Srike Ball과 입력값)
    private JLabel cTry; // 시도 횟수
    private BGame g; // 게임 객체
    private int[] input; // 사용자가 입력한 숫자
    private User cUser; // 플레이중인 유저 객체

    GameWindow(User u) {
        // 게임 초기화
        g = new BGame();
        g.init();
        g.print();
        input = new int[4];
        cUser = u;

        // JFrame 구조 생성
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        left.setLayout(new BorderLayout());
        add(left, BorderLayout.WEST);
        left.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        right.setLayout(new BorderLayout());
        add(right, BorderLayout.EAST);
        right.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        // 왼쪽 화면
        txt = new JTextField(20);
        left.add(txt, BorderLayout.NORTH);
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new GridLayout(3, 3));
        left.add(bPanel, BorderLayout.CENTER);
        for (int i = 1; i <= 9; i++) {
            JButton btn = new JButton("" + i);
            btn.addActionListener(this);
            btn.setPreferredSize(new Dimension(50, 30));
            bPanel.add(btn);
        }
        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);

        left.add(okButton, BorderLayout.SOUTH);
        JLabel cu = new JLabel("사용자 : " + cUser.username);
        // 오른쪽 화면
        right.add(cu, BorderLayout.NORTH);
        textArea = new JTextArea(6, 15); // 텍스트 영역의 행과 열을 지정
        textArea.setEditable(false);
        JScrollPane sp = new JScrollPane(textArea);
        right.add(sp, BorderLayout.CENTER);
        cTry = new JLabel("시도횟수 : " + g.count);
        right.add(cTry, BorderLayout.SOUTH);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


    }

    // 이벤트 핸들러 (0~9, OK버튼)
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton jButton = (JButton) e.getSource(); // 이벤트가 발생한 버튼
        String buttonText = jButton.getText(); // 이벤트가 발생한 버튼의 Text
        String inputText = txt.getText(); // 사용자 input 숫자 4자리
        // OK버튼을 누를 경우,
        if (buttonText.equals("OK")) {
            //텍스트가 변환 불가능일 경우, 오류팝업 출력
            if (!isAllowInput(txt.getText())) {
                JOptionPane.showMessageDialog(null, "입력값이 문자열을 포함하거나, 길이가 4가 아닙니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
                txt.setText("");
                return;
            }
            // 시도횟수 증가, (만약 텍스트에 문자나, 갯수가 맞지 않을 경우, 증가시키지 않음.)
            cTry.setText("시도횟수 : " + ++g.count);

            //텍스트를 int[]로 변환 (String -> char[] -> int[] )
            for (int i = 0; i < 4; i++) {
                input[i] = inputText.charAt(i) - '0';
            }
            //텍스트 비우기
            txt.setText("");

            // 결과 출력
            int strike = g.getStrike(input);
            String result = String.format("%s\t%dS %dB\n", inputText, strike, g.getBall(input));
            textArea.setText(textArea.getText() + result);

            // 정답을 맞힌 경우,
            if (strike == 4) {
                JOptionPane.showMessageDialog(null, "시도횟수: " + g.count + " \n축하합니다~ 게임완료", "게임 완료", JOptionPane.INFORMATION_MESSAGE);
                saveResult(cUser, g.count); // 결과 기록
                initGame(); // 게임 초기화
            }
            return;
        }
        // 0~9에 해당하는 버튼을 누를 경우, + 길이가 4이하일 경우만 작동.
        if (txt.getText().length() < 4)
            txt.setText(txt.getText() + buttonText); //현재 input + 누르는 버튼(0~9)
    }

    /// 결과 Rank 저장과 user의 플레이 횟수 저장을 위한 함수
    private void saveResult(User cUser, int count) {
        RankList rankArrayList = IOManager.getRankList(); // 랭킹 리스트
        Rank rank = new Rank(cUser.username, count); // 랭킹 객체
        rankArrayList.add(rank, true); // 중복검사 후 리스트에 추가
        IOManager.addPlayerCount(cUser); // data.txt 게임 플레이 횟수(plays) 증가
        IOManager.saveRank(rankArrayList); // rank.txt 랭킹 기록
    }

    /// 사용자가 버튼이 아닌 키보드를 통해 입력한 입력값이 0~9의 숫자로 이루어져 있는지,길이가 4인지 체크하는 함수
    private boolean isAllowInput(String inputText) {
        return inputText.matches("[0-9]+") && inputText.length() == 4;
    }

    /// 게임초기화 함수, 할당을 해제하고 새로운 GameWindow 생성,
    private void initGame() {
        dispose();
        new GameWindow(cUser);
    }

}
