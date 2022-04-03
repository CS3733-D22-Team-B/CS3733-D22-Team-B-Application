package edu.wpi.cs3733.D22.teamB.requests;

import java.util.LinkedList;

public class RequestQueue {
  private static LinkedList<Request> requests = new LinkedList<>();
  private static RequestQueue queue = new RequestQueue();

  private RequestQueue() {}

  public static RequestQueue getQueue() {
    return queue;
  }

  public static void addRequest(Request request) {
    requests.add(request);
  }

  public static void removeRequest(Request request) {
    requests.remove(request);
  }

  public static LinkedList<Request> getRequests() {
    return requests;
  }

  public static void clear() {
    requests.clear();
  }
}
