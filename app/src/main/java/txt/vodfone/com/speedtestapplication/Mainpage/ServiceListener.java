package txt.vodfone.com.speedtestapplication.Mainpage;

import com.tm.corelib.ROContextListener;

import java.util.Observable;

/**
 * ROSDK: This listener is triggered by the background service to broadcast the current
 * service state.
 *
 * Net Perform 3 Sample View
 * Date: 2015-08-24
 *
 * Copyright (c) 2016 RadioOpt GmbH. All rights reserved.
 */
public class ServiceListener extends Observable implements ROContextListener {
  private static ServiceListener listener;

  private ServiceListener() {
  }

  public synchronized static ServiceListener getInstance() {
    if (listener == null) {
      listener = new ServiceListener();
    }
    return listener;
  }

  @Override
  public void onServiceStarted() {
    notifyChanges();
  }

  @Override
  public void onServiceTerminated() {
    notifyChanges();
  }

  public void notifyChanges() {
    setChanged();
    notifyObservers();
  }
}
