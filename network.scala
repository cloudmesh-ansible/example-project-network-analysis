import Console.println

import scopt._

import org.apache.spark._
import org.apache.spark.SparkContext._
import org.apache.spark.rdd._

import org.apache.spark.graphx._
import org.apache.spark.graphx.lib._
import org.apache.spark.graphx.util.GraphGenerators


case class Config(
  action: String = "run",
  genmethod: String = "rmat",
  numnodes: Int = 100,
  numedges: Int = 1000,
  edgesfile: String = "edges.txt",
  nodesfile: String = "nodes.txt",
  metric: String = "pagerank",
  numiter: Int = 10,
  resetprob: Double = 0.15
)


object networkanalysis {

  val parser = new OptionParser[Config]("networkanalysis") {
    opt[String]('a', "action").action( (x, c) => c.copy(action=x) ).text("Action to execute {generate|run}")

    opt[String]('g', "genmethod").action( (x,c) => c.copy(genmethod=x) ).text("Graph generation method {rmat}")

    opt[Int]('n', "numnodes").action( (x,c) => c.copy(numnodes=x) ).text("Number of nodes")

    opt[Int]('e', "numedges").action( (x,c) => c.copy(numedges=x) ).text("Number of edges")

    opt[String]('E', "edgesfile").action( (x,c) => c.copy(edgesfile=x) ).text("Path to save edges to")

    opt[String]('N', "nodesfile").action( (x,c) => c.copy(nodesfile=x) ).text("Path to save nodes to")

    opt[String]('m', "metric").action( (x,c) => c.copy(metric=x) ).text("Analytics metric to run {connectedcomponents|labelpropagation|pagerank|stronglyconnectedcomponents|trianglecount}")

    opt[Int]('i', "numiter").action( (x,c) => c.copy(numiter=x) ).text("Number of iterations")

    opt[Double]('r', "resetprob").action( (x,c) => c.copy(resetprob=x) ).text("Reset probability")

  }


  def main(args: Array[String]) {

    parser.parse(args, Config()) match {

      case Some(config: Config) => entrypoint(config)
      case None => ()

    }

  }


  def entrypoint(opts: Config) {

    val conf = new SparkConf().setAppName("networkanalysis")
    val sc = new SparkContext()

    opts.action match {
      case "generate" => generate(opts, sc);
      case "run" => run(opts, sc);
    }

  }


  def generate(opts: Config, sc: SparkContext) {

    opts.genmethod match {
      case "rmat" => {
        val g = GraphGenerators.rmatGraph(sc, opts.numnodes, opts.numedges)
        g.vertices.saveAsTextFile(opts.nodesfile)
        g.edges.saveAsTextFile(opts.edgesfile)
      }
    }

  }


  def run(opts: Config, sc: SparkContext) {

    println("Loading graph from " ++ opts.nodesfile ++ ", " ++ opts.edgesfile)
    val nspat = "\\( *(\\d+) *, *(\\d+) *\\)".r
    val espat = "Edge\\((\\d+),(\\d+),(\\d+)\\)".r
    val nodes = sc.textFile(opts.nodesfile).map{ s => val nspat(id, attr) = s; (id.toLong,attr.toInt) }
    val edges = sc.textFile(opts.edgesfile).map{ s => val espat(i, j, k) = s; Edge(i.toInt,j.toInt, k.toInt) }
    val g = Graph(nodes, edges)


    println("Computing " ++ opts.metric)

    opts.metric match {

      case "connectedcomponents" => ConnectedComponents.run(g);
      case "labelpropagation" => LabelPropagation.run(g, opts.numiter);
      case "pagerank" => PageRank.run(g, opts.numiter, resetProb=opts.resetprob);
      case "stronglyconnectedcomponents" => StronglyConnectedComponents.run(g, opts.numiter);
      case "trianglecount" => TriangleCount.run(g);


    }

    println("Done")

  }




}
