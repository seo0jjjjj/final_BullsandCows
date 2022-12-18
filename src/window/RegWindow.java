package window;
import ioManager.IOManager;
import user.User;
import user.UserList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/// 가입을 위한 Window
public class RegWindow extends JFrame {
    private JTextField tf; // 아이디
    private JPasswordField pf1, pf2; // 비밀번호와 비밀번호 확인
    private UserList ul;
    private JButton reg;
    private JCheckBox adminCheckBox; // 관리자 여부 체크용 체크박스
    public RegWindow() {
        setTitle("회원 등록");
        setLocation(250,250);
        setLayout(new GridLayout(5, 1));

        JPanel p1 = new JPanel();
        add(p1);
        JPanel p2 = new JPanel();
        add(p2);
        JPanel p3 = new JPanel();
        add(p3);
        JPanel p4 = new JPanel();
        add(p4);
        JPanel p5 = new JPanel();
        add(p5);

        p1.add(new JLabel("아 이 디"));
        tf = new JTextField(20);
        p1.add(tf);

        p2.add(new JLabel("암 호"));
        pf1 = new JPasswordField(20);
        p2.add(pf1);

        p3.add(new JLabel("암호확인"));
        pf2 = new JPasswordField(20);
        p3.add(pf2);

        reg = new JButton("등록");
        p4.add(reg);
        reg.addActionListener(new MyListener());
        JButton cancel = new JButton("취소");
        p4.add(cancel);

        adminCheckBox = new JCheckBox("관리자 여부");
        p5.add(adminCheckBox);

        cancel.addActionListener(e -> { dispose();});
        pack();
        setVisible(true);
    }

    void setUserList(UserList ul) {
        this.ul = ul;
    }

    /// 등록버튼을 눌렀을 떄,
    class MyListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
                String id = tf.getText(); // 아이디
                String pass1 = new String(pf1.getPassword());
                String pass2 = new String(pf2.getPassword());
            // 입력값 없을 경우,(null)
                if(id.equals("") || pass1.equals("")) {
                    JOptionPane.showMessageDialog(null, "아이디나 비밀번호를 입력해주세요.", "회원등록", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String permission = adminCheckBox.isSelected()? "admin":"user"; // 체크 박스가 선택된 경우, admin
            // 가입 여부 확인
                if (ul.isValidID(id))
                    JOptionPane.showMessageDialog(null, "등록된 아이디", "회원등록", JOptionPane.ERROR_MESSAGE);
                else if (!pass1.equals(pass2)) // 비밀번호 일치 여부
                    JOptionPane.showMessageDialog(null, "암호 불일치", "회원등록", JOptionPane.ERROR_MESSAGE);
                else {
                    // 가입 성공 유저정보등록
                    User newUser = new User(id, pass1,permission,0);
                    IOManager.regNewUser(newUser); // data.txt에 기록
                    ul.add(newUser); // 유저리스트에 추가.
                    JOptionPane.showMessageDialog(null, "회원 등록 완료", "회원등록", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
        }
    }



}

