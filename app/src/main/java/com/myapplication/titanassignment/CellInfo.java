package com.myapplication.titanassignment;

public class CellInfo {

  private boolean isCountdownStarted;
  private int cellNumber;
  private boolean isCellReset;

  public CellInfo(boolean isCountdownStarted,
                  int cellNumber,
                  boolean isCellReset) {
    this.isCountdownStarted = isCountdownStarted;
    this.cellNumber = cellNumber;
    this.isCellReset = isCellReset;
  }

  public boolean isCountdownStarted() {
    return isCountdownStarted;
  }

  public void setCountdownStarted(boolean countdownStarted) {
    isCountdownStarted = countdownStarted;
  }

  public int getCellNumber() {
    return cellNumber;
  }

  public void setCellNumber(int cellNumber) {
    this.cellNumber = cellNumber;
  }

  public boolean isCellReset() {
    return isCellReset;
  }

  public void setCellReset(boolean cellReset) {
    isCellReset = cellReset;
  }
}
