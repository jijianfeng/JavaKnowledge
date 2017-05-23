package com.jjf.dwy;

import java.awt.*; //图形包
import java.awt.event.*; //事件包，点击事件什么的
//io 关于文件流的
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;
class OmokBoard extends Canvas{ //OmokBoard是Canvas（画布）的子类，方面布局显示
  public static final int BLACK=1, WHITE=-1;
  private int[][] map; ///棋盘各个点坐标集
  private int size, cell; //大小（棋盘），节点（棋子）
  private String info="游戏终止";
  private int color=BLACK;

  private boolean enable=false; //暂停的意思，应该
  private boolean running=false;//是否正在运行

  private PrintWriter writer;
  private Graphics gboard, gbuff;
  private Image buff;

  OmokBoard(int s, int c){ //初始化棋盘
    this.size=s;this.cell=c;
    map=new int[size+2][];
    for(int i=0;i<map.length;i++)
      map[i]=new int[size+2];
    setBackground(new Color(200,200,100));//设置背景颜色
    setSize(size*(cell+1)+size, size*(cell+1)+size); //设置棋子大小

    addMouseListener(new MouseAdapter(){
      public void mousePressed(MouseEvent me){ //添加点击事件
        if(!enable)return; //如果暂停就不动
        int x=(int)Math.round(me.getX()/(double)cell);
        int y=(int)Math.round(me.getY()/(double)cell);
        if(x==0 || y==0 || x==size+1 || y==size+1)return;//超出棋盘边界不动
        if(map[x][y]==BLACK || map[x][y]==WHITE)return;//已经有棋子不动
        writer.println("[STONE]"+x+" "+y); //发送落子信息
        map[x][y]=color;
        if(check(new Point(x, y), color)){
          info="获胜.";
          writer.println("[WIN]");
        }
        else info="等待对方落子.";
        repaint();
        enable=false;
      }
    });
  }
  public boolean isRunning(){
    return running;
  }//判断是否正在运行
  public void startGame(String col){ //开始游戏
    running=true;
    if(col.equals("BLACK")){
      enable=true; color=BLACK;
      info="开始游戏...请落子.";
    }
    else{
      enable=false; color=WHITE;
      info="开始游戏... 请等待.";
    }
  }
  public void stopGame(){ //结束游戏
    reset();
    writer.println("[STOPGAME]");
    enable=false;
    running=false;
  }
  public void putOpponent(int x, int y){ //落子
    map[x][y]=-color;
    info="对手已落子，请落子.";
    repaint();
  }
  public void setEnable(boolean enable){
    this.enable=enable;
  } //暂停
  public void setWriter(PrintWriter writer){
    this.writer=writer;
  }//设置发送方
  public void update(Graphics g){
    paint(g);
  } //更新棋盘
  public void paint(Graphics g){ //渲染棋盘
    if(gbuff==null){
      buff=createImage(getWidth(),getHeight());
      gbuff=buff.getGraphics();
    }
    drawBoard(g);
  }
  public void reset(){ //重新开始
    for(int i=0;i<map.length;i++)
      for(int j=0;j<map[i].length;j++)
        map[i][j]=0;
    info="游戏终止";
    repaint();
  }
  private void drawLine(){ //渲染行
    gbuff.setColor(Color.black);
    for(int i=1; i<=size;i++){
      gbuff.drawLine(cell, i*cell, cell*size, i*cell);
      gbuff.drawLine(i*cell, cell, i*cell , cell*size);
    }
  }
  private void drawBlack(int x, int y){//渲染黑色
    Graphics2D gbuff=(Graphics2D)this.gbuff;
    gbuff.setColor(Color.black);
    gbuff.fillOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
    gbuff.setColor(Color.white);
    gbuff.drawOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
  }
  private void drawWhite(int x, int y){ //渲染白色
    gbuff.setColor(Color.white);
    gbuff.fillOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
    gbuff.setColor(Color.black);
    gbuff.drawOval(x*cell-cell/2, y*cell-cell/2, cell, cell);
  }
  private void drawStones(){ //绘制暂停的样子
    for(int x=1; x<=size;x++)
     for(int y=1; y<=size;y++){
       if(map[x][y]==BLACK)
         drawBlack(x, y);
       else if(map[x][y]==WHITE)
         drawWhite(x, y);
     }
  }
  synchronized private void drawBoard(Graphics g){
    gbuff.clearRect(0, 0, getWidth(), getHeight());
    drawLine();
    drawStones();
    gbuff.setColor(Color.red);
    gbuff.drawString(info, 20, 15);
    g.drawImage(buff, 0, 0, this);
  }
  private boolean check(Point p, int col){ //检查胜利条件
    if(count(p, 1, 0, col)+count(p, -1, 0, col)==4)
      return true;
    if(count(p, 0, 1, col)+count(p, 0, -1, col)==4)
      return true;
    if(count(p, -1, -1, col)+count(p, 1, 1, col)==4)
      return true;
    if(count(p, 1, -1, col)+count(p, -1, 1, col)==4)
      return true;
    return false;
  }
  private int count(Point p, int dx, int dy, int col){ //判断连续
    int i=0;
    for(; map[p.x+(i+1)*dx][p.y+(i+1)*dy]==col ;i++);
    return i;
  }
}
public class OmokClient extends Frame implements Runnable, ActionListener{//client客户端，继承Frame可以做界面，实现Runable接口可以读线程，ActionListener可以处理事件
  private TextArea msgView=new TextArea("", 1,1,1); //信息窗口
  private TextField sendBox=new TextField(""); //发送窗
  private TextField nameBox=new TextField(); //用户名
  private TextField roomBox=new TextField("0"); //房间号
  private Label pInfo=new Label("待机室:  名"); //待机室消息
  private java.awt.List pList=new java.awt.List(); //玩家集合？？
  private Button startButton=new Button("开始对决"); //Button都是按钮
  private Button stopButton=new Button("弃权");
  private Button enterButton=new Button("入场");
  private Button exitButton=new Button("去待机室");
  private Label infoView=new Label("< 五子棋 >", 1);
  private OmokBoard board=new OmokBoard(15,30); //初始化棋盘
  private BufferedReader reader;
  private PrintWriter writer;
  private Socket socket; //客户端socket
  private int roomNumber=-1; //房间号
  private String userName=null; //用户名
  public OmokClient(String title){ //初始化界面
    super(title);
    setLayout(null);
    msgView.setEditable(false);
    infoView.setBounds(10,30,480,30);
    infoView.setBackground(new Color(200,200,255));
    board.setLocation(10,70);
    add(infoView);
    add(board);
    Panel p=new Panel();
    p.setBackground(new Color(200,255,255));
    p.setLayout(new GridLayout(3,3));
    p.add(new Label("名  子:", 2));p.add(nameBox);
    p.add(new Label("房间号:", 2)); p.add(roomBox);
    p.add(enterButton); p.add(exitButton);
    enterButton.setEnabled(false);
    p.setBounds(500,30, 250,70);

    Panel p2=new Panel();
    p2.setBackground(new Color(255,255,100));
    p2.setLayout(new BorderLayout());
    Panel p2_1=new Panel();
    p2_1.add(startButton); p2_1.add(stopButton);
    p2.add(pInfo,"North"); p2.add(pList,"Center"); p2.add(p2_1,"South");
    startButton.setEnabled(false); stopButton.setEnabled(false);
    p2.setBounds(500,110,250,180);

    Panel p3=new Panel();
    p3.setLayout(new BorderLayout());
    p3.add(msgView,"Center");
    p3.add(sendBox, "South");
    p3.setBounds(500, 300, 250,250);

    add(p); add(p2); add(p3);
    sendBox.addActionListener(this);
    enterButton.addActionListener(this);
    exitButton.addActionListener(this);
    startButton.addActionListener(this);
    stopButton.addActionListener(this);
    addWindowListener(new WindowAdapter(){
       public void windowClosing(WindowEvent we){
         System.exit(0);
       }
    });
  }
  public void actionPerformed(ActionEvent ae){ //添加按钮点击事件
    if(ae.getSource()==sendBox){ //发送按钮
      String msg=sendBox.getText();
      if(msg.length()==0)return;
      if(msg.length()>=30)msg=msg.substring(0,30);
      try{
        writer.println("[MSG]"+msg);
        sendBox.setText("");
      }catch(Exception ie){}
    }
    else if(ae.getSource()==enterButton){ //进入房间按钮
      try{

        if(Integer.parseInt(roomBox.getText())<1){
          infoView.setText("房间号错误.大于 1");
          return;
        }
          writer.println("[ROOM]"+Integer.parseInt(roomBox.getText()));
          msgView.setText("");
        }catch(Exception ie){
          infoView.setText("输入的事项发生错误.");
        }
    }
    else if(ae.getSource()==exitButton){//退出按钮
      try{
        goToWaitRoom();
        startButton.setEnabled(false);
        stopButton.setEnabled(false);
      }catch(Exception e){}
    }
    else if(ae.getSource()==startButton){ //开始按钮
      try{
        writer.println("[START]");
        infoView.setText("等待对方决定.");
        startButton.setEnabled(false);
      }catch(Exception e){}
    }
    else if(ae.getSource()==stopButton){//弃权按钮
      try{
        writer.println("[DROPGAME]");
        endGame("已弃权.");
      }catch(Exception e){}
    }
  }
  void goToWaitRoom(){ //直接去只有一个人的房间
    if(userName==null){
      String name=nameBox.getText().trim();
      if(name.length()<=2 || name.length()>10){
        infoView.setText("名字错误. 3~10个字");
        nameBox.requestFocus();
        return;
      }
      userName=name;
      writer.println("[NAME]"+userName);
      nameBox.setText(userName);
      nameBox.setEditable(false);
    }
    msgView.setText("");
    writer.println("[ROOM]0");
    infoView.setText("已进入待机室.");
    roomBox.setText("0");
    enterButton.setEnabled(true);
    exitButton.setEnabled(false);
  }
  public void run(){ //运行
    String msg;
    try{
    while((msg=reader.readLine())!=null){ //下面的操作和前面的server一样，只不过这里需要转到view（视图）上
        if(msg.startsWith("[STONE]")){
          String temp=msg.substring(7);
          int x=Integer.parseInt(temp.substring(0,temp.indexOf(" ")));
          int y=Integer.parseInt(temp.substring(temp.indexOf(" ")+1));
          board.putOpponent(x, y);
          board.setEnable(true);
        }
        else if(msg.startsWith("[ROOM]")){
          if(!msg.equals("[ROOM]0")){
            enterButton.setEnabled(false);
            exitButton.setEnabled(true);
            infoView.setText(msg.substring(6)+"号房间已被进入.");
          }
          else infoView.setText("已进入待机室.");
          roomNumber=Integer.parseInt(msg.substring(6));
          if(board.isRunning()){
            board.stopGame();
          }
        }
        else if(msg.startsWith("[FULL]")){
          infoView.setText("房间满员，禁止入内.");
        }
        else if(msg.startsWith("[PLAYERS]")){
          nameList(msg.substring(9));
        }
        else if(msg.startsWith("[ENTER]")){
          pList.add(msg.substring(7));
          playersInfo();
          msgView.append("["+ msg.substring(7)+"]入场.\n");
        }
        else if(msg.startsWith("[EXIT]")){
          pList.remove(msg.substring(6));
          playersInfo();
          msgView.append("["+msg.substring(6)+"]进入其它房间.\n");
          if(roomNumber!=0)
            endGame("对方已离开.");
        }
        else if(msg.startsWith("[DISCONNECT]")){
          pList.remove(msg.substring(12));
          playersInfo();
          msgView.append("["+msg.substring(12)+"]中断连接.\n");
          if(roomNumber!=0)
            endGame("对方离开.");
        }
        else if(msg.startsWith("[COLOR]")){
          String color=msg.substring(7);
          board.startGame(color);
          if(color.equals("BLACK"))
            infoView.setText("得到黑子.");
          else
            infoView.setText("得到白子.");
          stopButton.setEnabled(true);
        }
        else if(msg.startsWith("[DROPGAME]"))
          endGame("对方弃权.");
        else if(msg.startsWith("[WIN]"))
          endGame("获胜.");
        else if(msg.startsWith("[LOSE]"))
          endGame("失利.");
        else msgView.append(msg+"\n");
    }
    }catch(IOException ie){
     msgView.append(ie+"\n");
    }
    msgView.append("连接中断.");
  }
  private void endGame(String msg){ //结束游戏
    infoView.setText(msg);
    startButton.setEnabled(false);
    stopButton.setEnabled(false);
    try{ Thread.sleep(2000); }catch(Exception e){}
    if(board.isRunning())board.stopGame();
    if(pList.getItemCount()==2)startButton.setEnabled(true);
  }
  private void playersInfo(){ //玩家s的信息
    int count=pList.getItemCount();
    if(roomNumber==0)
      pInfo.setText("待机室: "+count+"号");
    else pInfo.setText(roomNumber+" 号房: "+count+"名");
    if(count==2 && roomNumber!=0)
      startButton.setEnabled(true);
    else startButton.setEnabled(false);
  }
  private void nameList(String msg){ //将玩家s的信息↑ 加入列表plist
    pList.removeAll();
    StringTokenizer st=new StringTokenizer(msg, "\t");
    while(st.hasMoreElements())
      pList.add(st.nextToken());
    playersInfo();
  }
  private void connect(){ //客户端于Server端的连接
    try{
      msgView.append("请求连接服务器.\n");
      socket=new Socket("127.0.0.1", 7777);
      //上机时，请根据实际情况填写IP地址。本机一般可以用127.0.0.1
      msgView.append("---连接成功--.\n");
      msgView.append("请输入大名然后进入待机室.\n");
      reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
      writer=new PrintWriter(socket.getOutputStream(), true);
      new Thread(this).start();
      board.setWriter(writer);
    }catch(Exception e){
      msgView.append(e+"\n\n连接失败..\n");
    }
  }
  public static void main(String[] args){
    OmokClient client=new OmokClient("网络五子棋");
    client.setSize(760,560);
    client.setVisible(true);
    client.connect();
  }
}