package com.example.politicgame.GamesActivity.BabyGame;

import android.os.CountDownTimer;

class Timer {

  private long timeLeftInMillis;
  private CountDownTimer timer;

  private BabyDraw babyDraw;
  private BabyView babyView;

  Timer(BabyDraw babyDraw, BabyView babyView) {
    timeLeftInMillis = 60000;
    this.babyDraw = babyDraw;
    this.babyView = babyView;
  }

  void pause() {
    if (timer != null) {
      timer.cancel();
      timer = null;
    }
  }

  void resume() {
    System.out.println("resuming with time " + timeLeftInMillis);
    timer =
        new CountDownTimer(timeLeftInMillis, 1000) {

          public void onTick(long millisUntilFinished) {
            timeLeftInMillis = millisUntilFinished;
            Integer timeLeft = (int) timeLeftInMillis / 1000;
            if (timeLeft % 3 == 0) {
              babyView.randomEvent(timeLeft);
            }
            System.out.println(timeLeft);
            babyDraw.updateTime(timeLeft.toString(), false);
          }

          public void onFinish() {
            babyDraw.updateTime("Time's up!", true);
          }
        };
    timer.start();
  }

  long getTimeLeft() {
    return timeLeftInMillis;
  }
}