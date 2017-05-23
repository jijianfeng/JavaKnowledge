package com.jjf.dwy;

//整个程序按C/S结构设计，这里是Server端
//引入jdk自带的其他包
import java.net.*; //关于网络通信的
import java.io.*; //关于文件流读取写入的
import java.util.*; //j常用工具类包
public class OmokServer{
  private ServerSocket server; //声明一个ServerSocket对象，用于通信等
  private BManager bMan=new BManager();  //DManager继承了Vector类，是一个集合类,用于存储各个客户端的线程集合的关系（五子棋对战关系）
  private Random rnd= new Random(); //创建生成Random对象，用于生成随机数等
  public OmokServer(){}  //创建OmokServer（也就是这个类本身）的构造方法
  void startServer(){    //创建startServer方法，调用就是证明服务开启
    try{
      server=new ServerSocket(7777); //初始化了上面声明的ServerSocket对象，连接端口为7777，使其有意义
      System.out.println("服务器套接字已被创建.");
      while(true){      //无限循环，这里可以理解为保证服务端一直运行，可以接受其他客户端的请求
         Socket socket=server.accept();  //获取请求方（客户端）地socket对象，用于互相连接
         Omok_Thread ot=new Omok_Thread(socket);  //用刚才获取到的socket客户端对象，建立新的线程ot，线程可以理解为程序能够同时运行的另外一条线路
         ot.start();  //启动线程ot
         bMan.add(ot);  //将ot线程添加到bman集合中
         System.out.println("连接数: "+bMan.size());
      }
    }catch(Exception e){
      System.out.println(e);  //如果上面被try{}包裹的代码中，有报错，就会跳转到catch{}中，并输出异常信息
    }
  }
  public static void main(String[] args){ //java主函数
    OmokServer server=new OmokServer();
    server.startServer();   //调用startServer方法，开启服务
  }
  class Omok_Thread extends Thread{ //Omok_Thread继承Thread类，实现了多线程，实现多线程有两张方法，1.继承Thread类 2.实现Runable接口
    private int roomNumber=-1; //房间号
    private String userName=null;  //用户名
    private Socket socket; //声明了对象，==会保存客户端地socket对象

    private boolean ready=false; //是否准备
    //read和write就是java.io文件包的核心操作，就是读和写
    private BufferedReader reader; //缓冲读取流，继承read（读取类），可以加快读取速度，减少一次性内存消耗
    private PrintWriter writer; // 过滤流，也叫处理流,继承write（写类），可以向某个地方输出流（流可以理解为数据，是网络通信的介质）
    Omok_Thread(Socket socket){ //Omok_Thread线程的初始化方法，将上面声明的socket对象初始化为客户端socket
      this.socket=socket;
    }
    Socket getSocket(){
      return socket;
    } //获取该线程的客户端socket对象
    int getRoomNumber(){
      return roomNumber;
    } //获取房间号
    String getUserName(){
      return userName;
    } //获取用户名
    boolean isReady(){
      return ready;
    } //获取是否准备
    public void run(){
      try{
        reader=new BufferedReader(new InputStreamReader(socket.getInputStream())); //读取socket穿过来的信息
        writer=new PrintWriter(socket.getOutputStream(), true);
        String msg;
        while((msg=reader.readLine())!=null){
          if(msg.startsWith("[NAME]")){ //如果消息以[NAME]开头，获取用户名
            userName=msg.substring(6);
          }
          else if(msg.startsWith("[ROOM]")){ //如果消息以[ROOM]开头，获取房间号
            int roomNum=Integer.parseInt(msg.substring(6));
            if( !bMan.isFull(roomNum)){  //判断房间有没有满，是不是有两个人
              if(roomNumber!=-1) {  //如果房间号不等于-1,也就是要进入新的房间，那就是告诉当前所在的房间的其他人，我exit退出当前的房间
                bMan.sendToOthers(this, "[EXIT]" + userName);
            }
            roomNumber=roomNum;
            writer.println(msg);
            writer.println(bMan.getNamesInRoom(roomNumber));//告诉对方我来了
            bMan.sendToOthers(this, "[ENTER]"+userName); //向对方发送我进来了
            }
            else writer.println("[FULL]");
          }
          else if(roomNumber>=1 && msg.startsWith("[STONE]")) //落子的信息
            bMan.sendToOthers(this, msg);
          else if(msg.startsWith("[MSG]")) //如果消息以[MSG]开头，可以向特定房间的人说话
            bMan.sendToRoom(roomNumber, "["+userName+"]: "+msg.substring(5));
          else if(msg.startsWith("[START]")){
            ready=true;
            if(bMan.isReady(roomNumber)){ //如果房间内人都准备了，就开始
              int a=rnd.nextInt(2); //生成随机数
              if(a==0){ //根据随机数判断颜色
                writer.println("[COLOR]BLACK");
                bMan.sendToOthers(this, "[COLOR]WHITE");
              }
              else{
                writer.println("[COLOR]WHITE");
                bMan.sendToOthers(this, "[COLOR]BLACK");
              }
            }
          }
          else if(msg.startsWith("[STOPGAME]")) //取消准备
            ready=false;
          else if(msg.startsWith("[DROPGAME]")){ //关闭游戏
            ready=false;
            bMan.sendToOthers(this, "[DROPGAME]");
          }
          else if(msg.startsWith("[WIN]")){ //结局判断
            ready=false;
            writer.println("[WIN]");
            bMan.sendToOthers(this, "[LOSE]");
          }
        }
      }catch(Exception e){
      }finally{
        try{
          bMan.remove(this);
          if(reader!=null) reader.close();
          if(writer!=null) writer.close();
          if(socket!=null) socket.close();
          reader=null; writer=null; socket=null;
          System.out.println(userName+"已断线.");
          System.out.println("连接人数: "+bMan.size());
          bMan.sendToRoom(roomNumber,"[DISCONNECT]"+userName);
        }catch(Exception e){}
      }
    }
  }

  class BManager extends Vector{//DManager继承了Vector类，是一个集合类,用于存储各个客户端的线程集合的关系（五子棋对战关系）
                                // 跟Vector差不多的有ArrayList,但是ArrayList线程不安全，Vector线程安全，这里大量用到多线程，所以用Vector
    BManager(){} //构造方法
    void add(Omok_Thread ot){
      super.add(ot);
    } //往集合里添加客户端请求线程
    void remove(Omok_Thread ot){
       super.remove(ot);
    }//移除客户端请求线程
    Omok_Thread getOT(int i){//获取 客户端请求线程
      return (Omok_Thread)elementAt(i);
    }
    Socket getSocket(int i){
      return getOT(i).getSocket();
    } //获得客户端请求线程中的客户端Socket，通信对象
    void sendTo(int i, String msg){//向集合里的第N个人发消息
      try{
        PrintWriter pw= new PrintWriter(getSocket(i).getOutputStream(), true);
        pw.println(msg);
      }catch(Exception e){}
    }
    int getRoomNumber(int i){
      return getOT(i).getRoomNumber();
    } //获取房间号
    synchronized boolean isFull(int roomNum){ //判断房间是否满，用synchronized修饰，保证线程安全，不会出现三个人进入房间的情况
      if(roomNum==0)return false;
      int count=0;
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i))count++;
      if(count>=2)return true;
      return false;
    }
    void sendToRoom(int roomNum, String msg){ //向房间【roomnum】说msg
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i))
          sendTo(i, msg);
    }
    void sendToOthers(Omok_Thread ot, String msg){ //向房间内其他人说msg
      for(int i=0;i<size();i++)
        if(getRoomNumber(i)==ot.getRoomNumber() && getOT(i)!=ot) { //确认房间的就是自己socket对象
          sendTo(i, msg);
        }
    }
    synchronized boolean isReady(int roomNum){ //是否准备，用synchronized修饰，保证不会出现以及取消准备，却仍然开了游戏的情况
      int count=0;
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i) && getOT(i).isReady())
          count++;
      if(count==2)return true;
      return false;
    }
    String getNamesInRoom(int roomNum){ //获得房间内用户的名字
      StringBuffer sb=new StringBuffer("[PLAYERS]");
      for(int i=0;i<size();i++)
        if(roomNum==getRoomNumber(i))
          sb.append(getOT(i).getUserName()+"\t");
      return sb.toString();
    }
  }
}