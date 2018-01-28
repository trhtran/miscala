name := "MiscScala"
version := "0.0.1"

scalaVersion := "2.11.8"
val sparkVersion = "2.1.0"
val slf4jVersion = "1.7.5"

scalacOptions ++= Seq(
    "-encoding", "UTF-8",
    "-Ywarn-value-discard"
    )

libraryDependencies ++= Seq(
   "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
	,"org.apache.spark" %% "spark-sql" % sparkVersion % "provided"
	,"org.scalactic" %% "scalactic" % "3.0.4"
	,"com.typesafe" % "config" % "1.2.0"
	,"org.slf4j" % "slf4j-api" % slf4jVersion % "provided"
   ,"org.slf4j" % "slf4j-simple" % slf4jVersion
	)

libraryDependencies ++= Seq(
  ("org.elasticsearch" %% "elasticsearch-spark-20" % "5.6.1").
    exclude("com.google.guava", "guava").
    exclude("org.apache.hadoop", "hadoop-yarn-api").
    exclude("org.eclipse.jetty.orbit", "javax.mail.glassfish").
    exclude("org.eclipse.jetty.orbit", "javax.servlet").
    exclude("org.slf4j", "slf4j-api")
)
libraryDependencies ~= { _ map {
  case m if Seq("org.apache.hbase", "org.elasticsearch").contains(m.organization) =>
    m.exclude("commons-logging", "commons-logging").
      exclude("commons-collections", "commons-collections").
      exclude("commons-beanutils", "commons-beanutils-core").
      exclude("com.esotericsoftware.minlog", "minlog")
  case m => m
}}

dependencyOverrides += "org.scalatest" %% "scalatest" % "3.0.1"

// META-INF discarding
assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}

