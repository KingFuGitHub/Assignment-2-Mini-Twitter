import java.util.HashMap;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

public class AdminPanel extends JFrame {

    private User user;
    private Group group;
    private DefaultMutableTreeNode root;
    private UserPanel userPanel = new UserPanel();
    Object nodeInfo;
    private int totalMessage = 0;

    public HashMap<String, User> userData = new HashMap<String, User>();
    public HashMap<String, Group> groupData = new HashMap<String, Group>();

    private static AdminPanel adminPanelObject;
    private AdminPanel(){}

    public static AdminPanel getInstance(){
        if(adminPanelObject == null){
            synchronized(AdminPanel.class){
                if(adminPanelObject == null){
                    adminPanelObject = new AdminPanel();
                }
            }
        }

        return adminPanelObject;
    }

    public void increaseTotalMessage(){
        totalMessage+=1;
    }

    public void adminPanel() {

        group = new Group();
        group.setID("Root");
        root = new DefaultMutableTreeNode(group);
        nodeInfo = root.getUserObject();

        JFrame adminPanelFrame = new JFrame("Mini Twitter");
        adminPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTree tree = new JTree(root);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JScrollPane treeScrollPane = new JScrollPane(tree);

        JLabel labelTreeView = new JLabel("Tree View");
        
        JButton buttonAddUser = new JButton("Add User");
        JTextField textFieldAddUser = new JTextField();
        textFieldAddUser.setHorizontalAlignment(JTextField.CENTER);

        JButton buttonAddGroup = new JButton("Add group");
        JTextField textFieldAddGroup = new JTextField();
        textFieldAddGroup.setHorizontalAlignment(JTextField.CENTER);

        JButton buttonOpenUser = new JButton("Open user");
        JButton buttonShowUserTotal = new JButton("Show total user");
        JButton buttonShowGroupTotal = new JButton("Show total group");
        JButton buttonShowMessageTotal = new JButton("Show total message");
        JButton buttonShowPositivePercentage = new JButton("Show positive %");

        adminPanelFrame.setSize(800, 500);
        adminPanelFrame.setVisible(true);
        adminPanelFrame.setLayout(null);

        treeScrollPane.setBounds(25, 25, 400, 425);

        labelTreeView.setBounds(30, 5, 100, 20);

        buttonAddUser.setBounds(610, 30, 150, 40);
        textFieldAddUser.setBounds(440, 30, 165, 40);

        buttonAddGroup.setBounds(610, 80, 150, 40);
        textFieldAddGroup.setBounds(440, 80, 165, 40);

        buttonOpenUser.setBounds(440, 130, 320, 40);

        buttonShowUserTotal.setBounds(440, 350, 150, 40);

        buttonShowGroupTotal.setBounds(600, 350, 150, 40);

        buttonShowMessageTotal.setBounds(440, 400, 150, 40);

        buttonShowPositivePercentage.setBounds(600, 400, 150, 40);

        adminPanelFrame.add(labelTreeView);
        adminPanelFrame.add(buttonAddUser);
        adminPanelFrame.add(textFieldAddUser);
        adminPanelFrame.add(buttonAddGroup);
        adminPanelFrame.add(textFieldAddGroup);
        adminPanelFrame.add(buttonOpenUser);
        adminPanelFrame.add(buttonShowUserTotal);
        adminPanelFrame.add(buttonShowGroupTotal);
        adminPanelFrame.add(buttonShowMessageTotal);
        adminPanelFrame.add(buttonShowPositivePercentage);
        adminPanelFrame.add(treeScrollPane);

        buttonAddUser.addActionListener(e -> {

            String userName = textFieldAddUser.getText().toString().toLowerCase();

            if (nodeInfo instanceof Group && userName.length() > 0 && !userData.containsKey(userName)) {
                user = new User();
                user.setID(userName);

                DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
                if (root != null) {
                    root.add(userNode);
                } else {
                    root = (DefaultMutableTreeNode) tree.getModel().getRoot();
                    root.add(userNode);
                }

                userData.put(userName, user);
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                model.reload(root);
                textFieldAddUser.setText("");
            }

        });

        buttonAddGroup.addActionListener(e -> {

            String groupName = textFieldAddGroup.getText().toString().toUpperCase();

            if (nodeInfo instanceof Group && groupName.length() > 0 && !groupData.containsKey(groupName)) {
                group = new Group();
                group.setID(groupName);

                DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(group);
                if (root != null) {
                    root.add(groupNode);
                } else {
                    root = (DefaultMutableTreeNode) tree.getModel().getRoot();
                    root.add(groupNode);
                }

                groupData.put(groupName, group);
                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                model.reload(root);
                textFieldAddGroup.setText("");
            }

        });

        buttonShowUserTotal.addActionListener(e -> {
            Popup popup = new Popup();
            popup.showPopup("Total user", "Total user(s): " + Integer.toString(userData.size()));
        });

        buttonShowGroupTotal.addActionListener(e -> {
            Popup popup = new Popup();
            popup.showPopup("Total group", "Total group(s): " + Integer.toString(groupData.size()));
        });

        buttonOpenUser.addActionListener(e -> {
            userPanel.userPanel(nodeInfo, userData);
        });

        buttonShowMessageTotal.addActionListener(e->{
            Popup popup = new Popup();
            popup.showPopup("Total message", "Total message(s): " + totalMessage);
        });

        tree.addTreeSelectionListener(e -> {
            root = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

            if (root == null) {
                return;
            }
            
            nodeInfo = root.getUserObject();
        });

    }
}