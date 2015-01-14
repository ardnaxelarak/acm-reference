import java.util.Arrays;
import java.util.PriorityQueue;

public class Graph
{
	private static class Edge implements Comparable<Edge>
	{
		public int source, dest, weight;

		public Edge(int source, int dest, int weight)
		{
			this.source = source;
			this.dest = dest;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Edge arg0)
		{
			return weight - arg0.weight;
		}
	}
	private int[][] weights;
	private boolean[][] conn;

	public Graph(int n)
	{
		weights = new int[n][n];
		conn = new boolean[n][n];
		for (int i = 0; i < n; i++)
		{
			Arrays.fill(weights[i], -1);
			Arrays.fill(conn[i], false);
		}
	}

	public void addEdge(int source, int dest, int weight)
	{
		conn[source][dest] = true;
		weights[source][dest] = weight;
		conn[dest][source] = true;
		weights[dest][source] = weight;
	}

	public void removeEdge(int source, int dest)
	{
		conn[source][dest] = false;
		conn[dest][source] = false;
	}

	public boolean isEdge(int source, int dest)
	{
		return conn[source][dest];
	}
	
	public int getWeight(int source, int dest, int def)
	{
		if (!conn[source][dest])
			return def;
		else
			return weights[source][dest];
	}
	
	public int[] getAllWeights(int source, int def)
	{
		int[] ret = new int[size()];
		for (int i = 0; i < size(); i++)
		{
			ret[i] = getWeight(source, i, def);
		}
		return ret;
	}
	
	public int countEdges()
	{
		int count = 0;
		for (int i = 0; i < size(); i++)
			for (int j = 0; j < size(); j++)
				if (conn[i][j])
					count++;
		return count;
	}
	
	public void printEdges()
	{
		for (int i = 0; i < size(); i++)
			for (int j = i; j < size(); j++)
				if (conn[i][j])
					System.out.printf("%2d to %2d: %4d\n", i, j, weights[i][j]);
	}
	
	public int totalWeight()
	{
		int total = 0;
		for (int i = 0; i < size(); i++)
			for (int j = 0; j < i; j++)
				total += getWeight(i, j, 0);
		return total;
	}
	
	public int size()
	{
		return conn.length;
	}
	
	// finds shortest path from source to dest
	public int dijkstra(int source, int dest)
	{
		boolean[] visited = new boolean[size()];
		int[] value = new int[size()];
		int[] prev = new int[size()];
		int[] dists = new int[size()];
		Arrays.fill(visited, false);
		Arrays.fill(value, Integer.MAX_VALUE);
		value[source] = 0;
		prev[source] = -1;
		dists[source] = 1;
		int current = source;
		
		while (!visited[dest])
		{
			for (int i = 0; i < size(); i++)
			{
				if (visited[i] || i == current)
					continue;
				if (!conn[current][i])
					continue;
				
				int dist = value[current] + weights[current][i];
				if (dist < value[i])
				{
					value[i] = dist;
					prev[i] = current;
					dists[i] = dists[current] + 1;
				}
			}
			
			visited[current] = true;
			current = dest;
			for (int i = 0; i < size(); i++)
			{
				if (!visited[i] && value[i] < value[current])
					current = i;
			}
		}
		
		int[] path = new int[dists[dest]];
		current = dest;
		for (int i = dists[dest] - 1; i >= 0; i--)
		{
			path[i] = prev[current];
			current = prev[current];
		}

		// path contains the path
		// return path;

		// value is the distance
		return value[dest];
	}

	// Ax + B transformation of weights
	// finds minimum-cost spanning tree
	public Graph kruskal(int A, int B)
	{
		Graph g = new Graph(size());
		PriorityQueue<Edge> Q = new PriorityQueue<Edge>();
		boolean[][] connected = new boolean[size()][size()];
		for (int i = 0; i < size(); i++)
		{
			Arrays.fill(connected[i], false);
			connected[i][i] = true;
			for (int j = 0; j < i; j++)
			{
				if (conn[i][j])
					Q.add(new Edge(i, j, A * weights[i][j] + B));
			}
		}
		
		while (!Q.isEmpty())
		{
			Edge e = Q.poll();
			if (!connected[e.source][e.dest])
			{
				g.addEdge(e.source, e.dest, e.weight);
				for (int i = 0; i < size(); i++)
				{
					if (connected[e.source][i] || connected[e.dest][i])
					{
						connected[e.source][i] = true;
						connected[i][e.source] = true;
						connected[i][e.dest] = true;
						connected[e.dest][i] = true;
					}
				}
			}
		}
		
		return g;
	}
}
