package com.jjf.enums;

/**
 * 描述说明
 *
 * @author tuoniao 17/6/14.
 */
public enum CITICHDSignStateEnum {
  NEW_WAIT_AUDIT("0","新增待审批"),
  NORMAL("1","正常"),
  EDIT_WAIT_AUDIT("2","修改待审批"),
  CANCEL_WAIT_AUDIT("3","注销待审批"),
  CANCEL("4","注销"),
  NEW_AUDIT_UNPASS("5","新增审批不通过"),
  EDIT_AUDIT_UNPASS("6","修改审批不通过"),
  STOP_TRADE_WAIT_AUDIT("7","暂停交易待审批"),
  STOP_TRADE("8","暂停交易"),
  STOP_TRADE_ENABLE_WAIT_AUDIT("9","暂停交易-启用待审批"),
  STOP_INCOME_WAIT_AUDIT("A","暂禁入账待审批"),
  STOP_INCOME("B","暂禁入账"),
  STOP_INCOME_ENABLE_WAIT_AUDIT("C","暂禁入账-启用待审批");

  private String code;
  private String desc;

  CITICHDSignStateEnum(String code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
