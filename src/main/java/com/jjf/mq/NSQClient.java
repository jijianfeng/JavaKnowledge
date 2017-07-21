package com.jjf.mq;
import javax.annotation.Resource;

import com.youzan.nsq.client.exception.NSQException;
import com.youzan.pay.core.nsq.NsqConfig;
import com.youzan.pay.core.nsq.NsqProducer;
import com.youzan.pay.provision.core.service.inst.msg.model.CommandEvent;

import com.jjf.mybatis.BaseSpringTest;

import org.junit.Before;
import org.junit.Test;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by jijianfeng on 2017/7/18.
 */
@Slf4j
public class NSQClient extends BaseSpringTest {

//  @Resource
//  NsqProducer testTrans;

  private static NsqProducer testTrans = new NsqProducer();

  @Before
  public void init() throws NSQException {
    NsqConfig config = new NsqConfig();
    config.setServerAddresses("nsq-dev.s.qima-inc.com:4161");
    testTrans.setTopic("citic_settlement_inst");
    testTrans.setNsqConfig(config);
    testTrans.start();

  }

  @Test
  public void testProducer() throws Exception{
    CommandEvent commandEvent = new CommandEvent();
    commandEvent.setCmd("CASH_TRANCE");
    String msg = "{\n"
                 + "    \"cashNo\": 2017071806829051011,\n"
                 + "    \"desc\": \"ddd---18600069039提现操\",\n"
                 + "    \"AcctNo\": 1210010917,\n"
                 + "    \"cashMoney\": 1000,\n"
                 + "    \"isWaitSettle\": 1,\n"
                 + "    \"cashState\": 1\n"
                 + "}";
    int i = 0;
    while (true) {
      commandEvent.setBody(msg);
      System.out.println("发送消息" + commandEvent.toString());
      testTrans.send(commandEvent);
      System.out.println("发送消息结束" + commandEvent.toString());
      Thread.sleep(1000);
    }
  }

}
