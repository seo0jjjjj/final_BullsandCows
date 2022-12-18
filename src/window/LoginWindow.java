package window;

import user.User;
import user.UserList;

import javax.swing.*;
import java.awt.*;

/// 로그인 창
public class LoginWindow extends JFrame {
    private JTextField tf; // 아이디 필드
    private JPasswordField pf; // 비밀번호 필드
    private UserList ul; // 로그인 성공여부 판단을 위한 리스트

    public LoginWindow(UserList ul) {
        // 화면 구성
        this.ul = ul;
        setTitle("로그인");
        setLocation(200, 200);
        setLayout(new GridLayout(3, 1));
        JPanel p1 = new JPanel();
        add(p1);
        JPanel p2 = new JPanel();
        add(p2);
        JPanel p3 = new JPanel();
        add(p3);

        p1.add(new JLabel("아이디"));
        tf = new JTextField(20);
        p1.add(tf);

        p2.add(new JLabel("암 호"));
        pf = new JPasswordField(20);
        p2.add(pf);

        JButton login = new JButton("로그인");
        p3.add(login);
        // 로그인 버튼 이벤트
        login.addActionListener(e -> {
            String id = tf.getText(); // 아이디
            String pass = new String(pf.getPassword()); //비밀번호
            if (!ul.isValidID(id)) // 아이디가 유효한지,
                JOptionPane.showMessageDialog(null, "유효하지 않은 아이디", "로그인", JOptionPane.ERROR_MESSAGE);
            else if (!ul.isValidPass(id, pass)) // 아이디가 유효하다면, 암호가 맞는 지,
                JOptionPane.showMessageDialog(null, "부정확한 암호", "로그인", JOptionPane.ERROR_MESSAGE);
            else { //로그인 성공
                JOptionPane.showMessageDialog(null, "로그인 완료", "로그인 정보", JOptionPane.INFORMATION_MESSAGE);
                User u = ul.getCurrentUser(id); // 로그인한 유저를 가져옴.
                MenuWindow mw = new MenuWindow(u); // 다음 화면에 전달
                //GameWindow gw = new GameWindow(u);
                dispose();
            }
        });
        JButton reg = new JButton("회원등록"); // RegWindow로 이동하는 버튼.
        p3.add(reg);
        reg.addActionListener(e -> {
            RegWindow rw = new RegWindow();
            rw.setUserList(ul);
        });
        JButton cancel = new JButton("취소"); // 종료
        cancel.addActionListener(e -> {
            dispose();
        });
        p3.add(cancel);
        pack();
        setVisible(true);
    }

}
