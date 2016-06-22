name := "Network"
version := "1.0"
scalaVersion := "2.10.6"

resolvers ++= Seq(
  "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
  Resolver.sonatypeRepo("public")
)

libraryDependencies ++= Seq(
  "org.apache.hadoop" % "hadoop-core" % "1.2.1" % "provided",
  "org.apache.hbase" % "hbase-client" % "1.2.0" % "provided",
  "org.apache.hbase" % "hbase-common" % "1.2.0" % "provided",
  "org.apache.hbase" % "hbase-server" % "1.2.0" % "provided",
  "org.apache.spark" %% "spark-core" % "1.6.0" % "provided",
  "org.apache.spark" %% "spark-graphx" % "1.6.0" % "provided",
  "commons-io" % "commons-io" % "2.4" % "provided",
  "com.github.scopt" %% "scopt" % "3.5.0"
)
