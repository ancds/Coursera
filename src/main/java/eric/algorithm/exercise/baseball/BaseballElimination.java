package eric.algorithm.exercise.baseball;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BaseballElimination {

  private final int teamNumber;
  private final int vertexNumber;
  private final int sourceIndex;
  private final int sinkIndex;

  private final int[] wins;
  private final int[] losses;
  private final int[] remaining;
  private final int[] against;

  private final String[] teamName;
  private final HashMap<String, Integer> teamMap;
  private Set<String> result;

  /**
   * create a baseball division from given filename in format specified below.
   */
  public BaseballElimination(String filename) {

    In in = new In(filename);

    teamNumber = in.readInt();
    wins = new int[teamNumber];
    losses = new int[teamNumber];
    remaining = new int[teamNumber];
    teamName = new String[teamNumber];
    teamMap = new HashMap<>();

    /*
     * i from 0 to n - 1 are team vertices. sourceIndex = n. sinkIndex = n + 1. the
     * rest are game vertices.
     */
    vertexNumber = (teamNumber * teamNumber - teamNumber) / 2 + teamNumber + 2;
    sourceIndex = teamNumber;
    sinkIndex = teamNumber + 1;

    against = new int[vertexNumber];
    int gameIndex = teamNumber + 2;
    for (int i = 0; i < teamNumber; i++) {

      teamName[i] = in.readString();
      teamMap.put(teamName[i], i);
      wins[i] = in.readInt();
      losses[i] = in.readInt();
      remaining[i] = in.readInt();

      for (int j = 0; j < teamNumber; j++) {

        int againstGames = in.readInt();

        if (j > i) {

          against[gameIndex] = againstGames;
          gameIndex++;
        }

      }

    }

    in.close();
  }

  /**
   * number of teams.
   */
  public int numberOfTeams() {
    return teamNumber;

  }

  /**
   * all teams.
   */
  public Iterable<String> teams() {
    return Arrays.asList(teamName);
  }

  /**
   * number of wins for given team.
   */
  public int wins(String team) {

    return wins[getTeamIndex(team)];

  }

  /**
   * number of losses for given team.
   */
  public int losses(String team) {

    return losses[getTeamIndex(team)];

  }

  /**
   * number of remaining games for given team.
   */
  public int remaining(String team) {

    return remaining[getTeamIndex(team)];

  }

  /**
   * number of remaining games between team1 and team2.
   */
  public int against(String team1, String team2) {

    int t1 = getTeamIndex(team1);
    int t2 = getTeamIndex(team2);

    if (t1 == t2) {
      return 0;
    }

    int gameIndex = getGameIndex(t1, t2);

    return against[gameIndex];
  }

  /**
   * is given team eliminated?.
   */
  public boolean isEliminated(String teamString) {

    int team = getTeamIndex(teamString);

    result = null;
    for (int i = 0; i < teamNumber; i++) {

      if (team != i) {
        int cap = wins[team] + remaining[team] - wins[i];
        if (cap < 0) {
          result = new HashSet<>(Arrays.asList(teamName[i]));
          return true;
        }
      }

    }

    FlowNetwork flowNetwork = constructFlowNetwork(team);
    FordFulkerson ff = new FordFulkerson(flowNetwork, sourceIndex, sinkIndex);

    for (FlowEdge edge : flowNetwork.adj(sourceIndex)) {
      if (edge.residualCapacityTo(edge.to()) > 0) {

        result = new HashSet<>();
        for (int i = 0; i < teamNumber; i++) {
          if (ff.inCut(i)) {
            result.add(teamName[i]);
          }
        }
        return true;
      }
    }

    return false;

  }

  /**
   * subset R of teams that eliminates given team; null if not eliminated.
   */
  public Iterable<String> certificateOfElimination(String team) {

    if (!isEliminated(team)) {
      return null;
    }

    return new HashSet<String>(result);

  }

  private int getTeamIndex(String team) {

    Integer index = teamMap.get(team);

    if (index == null) {
      throw new IllegalArgumentException();
    }

    return index;
  }

  private int getGameIndex(int t1, int t2) {

    int index = teamNumber + 2;
    if (t1 == t2) {
      return index;
    }

    int min = Math.min(t1, t2);
    int max = Math.max(t1, t2);

    for (int i = 0; i < min; i++) {
      index += teamNumber - 1 - i;
    }

    index = index + max - min - 1;

    return index;

  }

  private FlowNetwork constructFlowNetwork(int team) {

    FlowNetwork flowNetwork = new FlowNetwork(vertexNumber);

    for (int i = 0; i < teamNumber; i++) {
      for (int j = i + 1; j < teamNumber; j++) {

        if (i != team && j != team) {

          int gameIndex = getGameIndex(i, j);

          int againstGames = against[gameIndex];

          FlowEdge sourceToGame = new FlowEdge(sourceIndex, gameIndex, againstGames);
          FlowEdge gameToTeamI = new FlowEdge(gameIndex, i, Integer.MAX_VALUE);
          FlowEdge gameToTeamJ = new FlowEdge(gameIndex, j, Integer.MAX_VALUE);

          flowNetwork.addEdge(sourceToGame);
          flowNetwork.addEdge(gameToTeamI);
          flowNetwork.addEdge(gameToTeamJ);

        }

      }
    }

    for (int i = 0; i < teamNumber; i++) {

      if (team != i) {
        int cap = wins[team] + remaining[team] - wins[i];
        FlowEdge teamToSink = new FlowEdge(i, sinkIndex, cap);

        flowNetwork.addEdge(teamToSink);
      }

    }

    return flowNetwork;

  }

  /**
   * test.
   */
  public static void main(String[] args) {
    BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = { ");
        for (String t : division.certificateOfElimination(team)) {
          StdOut.print(t + " ");
        }
        StdOut.println("}");
      } else {
        StdOut.println(team + " is not eliminated");
      }
    }
  }

}
